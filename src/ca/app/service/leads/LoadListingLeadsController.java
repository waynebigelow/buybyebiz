package ca.app.service.leads;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.common.ListingLead;
import ca.app.model.configuration.AdminTabType;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.paging.Page;

@Controller
public class LoadListingLeadsController extends BaseController {
	@Autowired
	private CommonService commonService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		//Application application = (Application)request.getAttribute("application");
		//String applicationId = ""+application.getApplicationId();
		int start = StringUtil.convertStringToInt(request.getParameter("start"), 0);
		int limit = 20;
		
		Map<String, String> params = new HashMap<String, String>();
		//params.put("applicationId", applicationId);
		
		Page<ListingLead> leadsPage = new Page<ListingLead>();
		leadsPage.setParams(params);
		leadsPage.setStart(start);
		leadsPage.setLimit(limit);
		commonService.searchLeadsByPage(leadsPage);
		
		ModelAndView mav = new ModelAndView("/appAdmin/leads/listingLeads");
		mav.addObject("leadsPage", leadsPage);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", AdminTabType.values());
		mav.addObject("tab", AdminTabType.LISTING_LEADS);
		return mav;
	}
}