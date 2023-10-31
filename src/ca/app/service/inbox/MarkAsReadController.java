package ca.app.service.inbox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class MarkAsReadController extends BaseController {
	@Autowired
	private ListingService listingService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int enquiryMapId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.ENQUIRY.getAlias()), TokenFieldType.ENQUIRY);
		listingService.markReadByEnquiryMapId(enquiryMapId, appUser.getUserId());
		return null;
	}
}