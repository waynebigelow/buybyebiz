package ca.app.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.configuration.UserAdminTabType;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.model.user.User;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.category.CategoryDTO;
import ca.app.web.dto.listing.ListingDTO;

@Controller
public class LoadUserAdminController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private PageHitService pageHitService;
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_ACCOUNT_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		User user = userService.getByUserId(appUser.getUserId());
		List<EnquiryMap> enquiries = listingService.getByPosterId(user);
		int count = 0;
		for (EnquiryMap enquiry : enquiries) {
			for (EnquiryPost post : enquiry.getPosts()) {
				if (!post.isRead()) {
					if (post.getAuthorId() != appUser.getUserId()) {
						count++;
					}
				}
			}
		}
		
		List<ListingDTO> listings = listingService.getActiveListingsByUserId(pageAccess.getUser().getUserId(), true);
		
		Application application = (Application)request.getAttribute("application");
		List<CategoryDTO> categories = applicationService.getAllByTypeId(application.getTypeId());
		
		try {
			pageHitService.logPageHit(null, request, Usage.USER_ADMIN);
		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
		}
		
		int actionId = StringUtil.convertStringToInt(request.getParameter("s"), 0);
		if (actionId == 1) {
			String[] values = null;
			Map<String,String[]> parameters = request.getParameterMap();
			for(String key : parameters.keySet()) {
				values = parameters.get(key);
				
				for(String paramValue : values) {
					System.out.println("Key: " + key + "; Value: " + paramValue);
				}
			}
		}

		ModelAndView mav = new ModelAndView("/userAdmin/listings");
		mav.addObject("listings", listings);
		mav.addObject("unread", count);
		mav.addObject("categories", categories);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", UserAdminTabType.values());
		mav.addObject("tab", UserAdminTabType.LISTINGS);
		mav.addObject("actionId", actionId);
		return mav;
	}
}