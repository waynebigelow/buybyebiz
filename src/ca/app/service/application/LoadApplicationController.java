package ca.app.service.application;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.AdminTabType;
import ca.app.model.i18n.GlobalLocale;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.i18n.LocaleService;
import ca.app.util.LogUtil;
import ca.app.web.dto.application.ApplicationDTO;

@Controller
public class LoadApplicationController extends BaseController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private LocaleService localeService;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		Locale locale = getLocaleStatic(request);
		Map<String, String> glMap = new HashMap<String, String>();
		
		List<GlobalLocale> globalLocales = localeService.getAllGlobalLocales();
		for (GlobalLocale gl : globalLocales) {
			glMap.put(gl.getDisplayLanguage(locale), gl.getCode());
		}
		Map<String, String> treeMap = new TreeMap<String, String>(glMap);
		
		List<ApplicationDTO> applications = applicationService.getAllApplications();
		
		ModelAndView mav = new ModelAndView("/appAdmin/applications");
		mav.addObject("applications", applications);
		mav.addObject("globalLocales", treeMap);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", AdminTabType.values());
		mav.addObject("tab", AdminTabType.APPLICATIONS);
		return mav;
	}
}