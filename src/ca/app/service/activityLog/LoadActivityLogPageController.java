package ca.app.service.activityLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.AdminTabType;
import ca.app.model.configuration.UserAdminTabType;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.service.user.UserService;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.user.ActivityLogDTO;
import ca.app.web.paging.Page;

@Controller
public class LoadActivityLogPageController extends BaseController {
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private UserService userService;
	@Autowired
	private ListingService listingService;
	
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
		
		String areaId = request.getParameter("area");
		String typeId = request.getParameter("type");
		String userId = ""+appUser.getUserId();
		if (pageAccess.isSystemAdmin()) {
			userId = request.getParameter("author");
		}
		
		int start = StringUtil.convertStringToInt(request.getParameter("start"), 0);
		int limit = 10;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("areaId", areaId);
		params.put("typeId", typeId);
		
		Page<ActivityLogDTO> activityLogPage = new Page<ActivityLogDTO>();
		activityLogPage.setParams(params);
		activityLogPage.setStart(start);
		activityLogPage.setLimit(limit);
		activityLogService.getActivityLogPage(activityLogPage);
		
		ModelAndView mav = new ModelAndView("/commonAdmin/activityLog");
		mav.addObject("unread", count);
		mav.addObject("page", activityLogPage);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", (!pageAccess.isSystemAdmin() ? UserAdminTabType.values() : AdminTabType.values()));
		mav.addObject("tab", (!pageAccess.isSystemAdmin() ? UserAdminTabType.ACTIVITY_LOG : AdminTabType.ACTIVITY_LOG));
		return mav;
	}

	public void setActivityLogService(ActivityLogService activityLogService) {
		this.activityLogService = activityLogService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}
}