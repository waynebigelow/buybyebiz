package ca.app.service.listing;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.Listing;
import ca.app.model.listing.MetaDataType;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class SaveMlsLinksController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		handleApprovals(request, listing);
		
		ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), ActivityType.LISTING_UPDATED.getId(), listing.getListingId(), userService.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"success\": \""+getToken(TokenFieldType.LISTING, listing.getListingId())+"\"}");
		response.getWriter().flush();
		return null;
	}
	
	public void handleApprovals(HttpServletRequest request, Listing listing) throws Exception {
		Map<Integer, String> approvalMap = new HashMap<Integer, String>();
		
		String agentListingLink = request.getParameter("agentListingLink");
		if (agentListingLink == null || !listing.getBusinessDetails().getAgentListingLink().equals(agentListingLink)) {
			approvalMap.put(MetaDataType.AGENT_LISTING_LINK.getId(), agentListingLink);
		}
		
		String multiMediaLink = request.getParameter("multiMediaLink");
		if (multiMediaLink == null || !listing.getBusinessDetails().getMultiMediaLink().equals(multiMediaLink)) {
			approvalMap.put(MetaDataType.MULTIMEDIA_LINK.getId(), multiMediaLink);
		}
		
		if (!approvalMap.isEmpty()) {
			listingService.processApprovals(approvalMap, listing);
		}
	}
}