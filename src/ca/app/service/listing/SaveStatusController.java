package ca.app.service.listing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingStatus;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveStatusController extends BaseController {
	@Autowired
	private ListingService listingService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int statusId = StringUtil.convertStringToInt(request.getParameter("statusId"), ListingStatus.SUSPENDED.getId());
		int listingId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		
		Listing listing = listingService.getByListingId(listingId);
		listing.setStatusId(statusId);
		
		ActivityType activityType = null;
		ListingStatus listingStatus = ListingStatus.get(statusId);
		if (listingStatus == ListingStatus.ACTIVE) {
			listing.setEnabled(true);
			activityType = ActivityType.LISTING_ACTIVATED;
		} else if (listingStatus == ListingStatus.SUSPENDED) {
			listing.setEnabled(false);
			activityType = ActivityType.LISTING_SUSPENDED;
		} else {
			listing.setEnabled(true);
			activityType = ActivityType.LISTING_SOLD;
		}
		listingService.update(listing, appUser, activityType);
		return null;
	}
}