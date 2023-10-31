package ca.app.service.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.configuration.AdminTabType;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.paging.Page;

@Controller
public class LoadListingsController extends BaseController {
	@Autowired
	private ListingService listingService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		Application application = (Application)request.getAttribute("application");
		
		String applicationId = ""+application.getApplicationId();
//		String typeId = request.getParameter("type");
		int start = StringUtil.convertStringToInt(request.getParameter("start"), 0);
		int limit = 10;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("applicationId", applicationId);
//		params.put("typeId", "" + typeId);
		
		Page<ListingDTO> listingPage = new Page<ListingDTO>();
		listingPage.setParams(params);
		listingPage.setStart(start);
		listingPage.setLimit(limit);
		listingPage.setSort("title");
		listingService.searchListingByPage(listingPage);
		
		ModelAndView mav = new ModelAndView("/appAdmin/listings");
		mav.addObject("listingPage", listingPage);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", AdminTabType.values());
		mav.addObject("tab", AdminTabType.LISTINGS);
		return mav;
	}
}