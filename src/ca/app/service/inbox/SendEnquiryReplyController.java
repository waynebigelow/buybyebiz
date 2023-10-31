package ca.app.service.inbox;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.listing.Listing;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.service.mail.MailService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class SendEnquiryReplyController extends BaseController {
	@Autowired
	private MailService mailService;
	@Autowired
	private ListingService listingService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		String error = null;

		int enquiryId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.ENQUIRY.getAlias()), TokenFieldType.ENQUIRY);
		if (enquiryId == 0) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(SendEnquiryReplyController.class, "listingEnqury: Failed attempt to send email for unknown listing");
			return null;
		}
		
		String comment = request.getParameter("comment");
		String ipAddress =request.getRemoteAddr();
		
		if (comment == null || comment.equals("")) {
			error = "You have provided incomplete details";
		}
		
		if (error == null) {
			EnquiryMap enquiryMap = listingService.getByEnquiryMapId(enquiryId);
			if (enquiryMap == null) {
				error = "listingEnqury: Failed attempt to send email for unknown enquiry";
			}
			
			if (error ==  null) {
				Listing listing = listingService.getByListingId(enquiryMap.getListingId());
				
				EnquiryPost post = new EnquiryPost();
				post.setEnquiryMapId(enquiryId);
				post.setComment(comment);
				post.setIpAddress(ipAddress);
				post.setAuthorId(appUser.getUserId());
				post.setPostDate(new Timestamp(System.currentTimeMillis()));
				listingService.create(post, listing);
				
				User receiver = enquiryMap.getPoster();
				if (appUser.getUserId()==enquiryMap.getPoster().getUserId()){
					receiver = listing.getUser();
				}
				
				if (receiver.isEnableEnquiryEmail()) {
					mailService.sendEnquiryReply(listing, receiver);
				}
			}
		}
		
		if (error != null) {
			JsonUtil.setupResponseForJSON(response);
			response.getWriter().print("{\"success\": false, \"error\": \""+error+"\"}");
			response.getWriter().flush();
		}
		return null;
	}
}