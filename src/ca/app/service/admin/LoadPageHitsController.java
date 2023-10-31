package ca.app.service.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.AdminTabType;
import ca.app.model.usage.PageHit;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.paging.Page;

@Controller
public class LoadPageHitsController extends BaseController {
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		String areaId = StringUtil.getString(request.getParameter("area"), "");
		String actionId = StringUtil.getString(request.getParameter("action"), "");
		String ipAddress = StringUtil.getString(request.getParameter("ipAddress"), "");
		
		int start = StringUtil.convertStringToInt(request.getParameter("start"), 0);
		int limit = 20;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("ipAddress", ipAddress);
		params.put("area", areaId);
		params.put("action", actionId);
		
		Page<PageHit> pageHitsPage = new Page<PageHit>();
		pageHitsPage.setParams(params);
		pageHitsPage.setStart(start);
		pageHitsPage.setLimit(limit);
		pageHitService.getPageHitsPage(pageHitsPage);
		
		ModelAndView mav = new ModelAndView("/appAdmin/pageHits");
		mav.addObject("page", pageHitsPage);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", AdminTabType.values());
		mav.addObject("tab", AdminTabType.PAGE_HITS);
		return mav;
	}
}