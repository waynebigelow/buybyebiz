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
import ca.app.service.mail.MailService;
import ca.app.service.mail.SupportNotificationType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class PublishListingController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private MailService mailService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		listing.setStatusId(ListingStatus.PENDING_APPROVAL.getId());
		listingService.update(listing, appUser, ActivityType.LISTING_PUBLISH_REQUESTED);
		
		mailService.sendAdminEmail(listing, SupportNotificationType.PUBLISH_REQUESTED);
		return null;
	}
}