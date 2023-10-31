package ca.app.service.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;

@Controller
public class SettingsActionController extends BaseController {
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		String action = request.getParameter("action");
		if (action.equals("cc")) {
			
		} else if (action.equals("rp")) {
			reloadProperties();
		} else if (action.equals("rr")) {
			reloadResourceBundle(request);
		}
		return null;
	}
	
	public void reloadResourceBundle(HttpServletRequest request) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		ReloadableResourceBundleMessageSource res = (ReloadableResourceBundleMessageSource) wac.getBean("messageSource");
		res.clearCache();
	}
	
	public void reloadProperties() {
		ProjectUtil.reload();
	}
}