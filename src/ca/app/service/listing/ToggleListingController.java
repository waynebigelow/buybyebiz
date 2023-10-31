package ca.app.service.listing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.Listing;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class ToggleListingController extends BaseController {
	@Autowired
	private ListingService listingService;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		if (listing.isEnabled()) {
			listing.setEnabled(false);
		} else {
			listing.setEnabled(true);
		}
		
		listingService.update(listing);
		return null;
	}
}