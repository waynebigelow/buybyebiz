package ca.app.service.leads;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.common.ListingLead;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveListingLeadController extends BaseController {
	@Autowired
	private CommonService commonService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingLeadId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING_LEAD.getAlias()), TokenFieldType.LISTING_LEAD);
		ListingLead listingLead = commonService.getByListingLeadId(listingLeadId);
		if (listingLead == null) {
			listingLead = new ListingLead();
		}
		listingLead.setBusinessName(request.getParameter("leadBusinessName"));
		listingLead.setFirstName(request.getParameter("leadFirstName"));
		listingLead.setLastName(request.getParameter("leadLastName"));
		listingLead.setEmail(request.getParameter("leadEmail"));
		listingLead.setTelephone(request.getParameter("leadTelephone"));
		listingLead.setWebsite(request.getParameter("leadWebsite"));
		listingLead.setPromoSent(StringUtil.convertStringToBoolean(request.getParameter("leadPromoSent"), false));
		commonService.save(listingLead);
		return null;
	}
}