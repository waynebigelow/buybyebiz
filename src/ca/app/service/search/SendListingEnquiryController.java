package ca.app.service.search;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.listing.Listing;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.service.mail.MailService;
import ca.app.service.usage.PageHitService;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.PasswordUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;

@Controller
public class SendListingEnquiryController extends BaseController {
	@Autowired
	private MailService mailService;
	@Autowired
	private ListingService listingService;
	@Autowired
	private UserService userService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		
		String error = null;
		
		if (ProjectUtil.getBooleanProperty("recaptcha.enabled") && !isReCaptchaValid(request)) {
			error = "Invalid reCaptcha response!";
		}
		
		if (error == null) {
			int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
			
			if (listingId == 0) {
				response.sendRedirect(getBaseURL(request));
				LogUtil.logDebug(SendListingEnquiryController.class, "listingEnqury: Failed attempt to send email for unknown listing");
				return null;
			}
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String ipAddress =request.getRemoteAddr();
			String comment = request.getParameter("comment");
			String acceptedTOU = request.getParameter("acceptedTOU");
			String acceptedPP = request.getParameter("acceptedPP");
			
			if (!appUser.isLoggedIn()) {
				if ((firstName == null || firstName.equals("")) 
						|| (lastName == null || lastName.equals("")) 
						|| (email == null || email.equals("") || !isValidEmail(email)) 
						|| (comment == null || comment.equals("")) 
						|| (acceptedTOU == null || !acceptedTOU.equals("true"))
						|| (acceptedPP == null || !acceptedPP.equals("true"))) {
					error = "You have provided incomplete details";
				}
			}
			
			if (error == null) {
				Application application = (Application)request.getAttribute("application");
				
				User user = null;
				if (!appUser.isLoggedIn()) {
					user = userService.getByEmail(email);
					if (user == null) {
						user = new User();
						String password = PasswordUtil.generatePassword();
						user.setPassword(password);
						user.setFirstName(firstName);
						user.setLastName(lastName);
						user.setEmail(email);
						user.setPreferredLocale(getLocale(request).toString());
						userService.register(user, application);
						
						mailService.sendWelcomeEmailForEnquiry(application, user, password);
					} else {
						if (appUser.getUserId() == 0 && user.getEmail().equals(email)) {
							error = "An account with your email address is already set-up. Please login or if you've forgotten your password then use the Forgot Password tool to have it reset.";
						}
					}
				} else {
					user = userService.getByUserId(appUser.getUserId());
				}
				
				if (error == null) {
					Listing listing = listingService.getByListingId(listingId);
					
					boolean isNew = false;
					EnquiryMap enquiryMap = listingService.getByListingIdPosterId(listing.getListingId(), user.getUserId());
					if (enquiryMap == null) {
						isNew = true;
						enquiryMap = new EnquiryMap();
						enquiryMap.setListingId(listingId);
						enquiryMap.setPoster(user);
						listingService.create(enquiryMap);
					}
					
					EnquiryPost post = new EnquiryPost();
					post.setEnquiryMapId(enquiryMap.getEnquiryMapId());
					post.setComment(comment);
					post.setIpAddress(ipAddress);
					post.setAuthorId(user.getUserId());
					post.setPostDate(new Timestamp(System.currentTimeMillis()));
					
					if (isNew) {
						listingService.create(post);
					} else {
						listingService.create(post, listing);
					}
					
					if (listing.getUser().isEnableEnquiryEmail()) {
						mailService.sendListingEnquiry(listing, listing.getUser());
					}
					
					try {
						pageHitService.logPageHit(listing, request, Usage.SITE);
					} catch (Exception ex) {
						LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
					}
				}
			}
		}
		
		JsonUtil.setupResponseForJSON(response);
		if (error == null) {
			response.getWriter().print("{\"success\": true}");
		} else {
			response.getWriter().print("{\"success\": false, \"error\": \""+error+"\"}");
		}
		response.getWriter().flush();
		return null;
	}
}