package ca.app.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import ca.app.service.common.BaseController;

@Controller
public class LogoutController extends BaseController{
//	@Autowired
//	private AbstractRememberMeServices rememberMeServices;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Remember the locale of the session being killed
		LocaleResolver lc = RequestContextUtils.getLocaleResolver(request);
		Locale locale = lc.resolveLocale(request);
		
		// Kill the RememberMe/SSO cookie if it exists.
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// rememberMeServices.logout(request, response, auth);
		
		// Invalidate the session.
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		session = request.getSession(true);
		
		// Propagate the locale to the new session
		lc.setLocale(request, response, locale);
		
		return null;
	}
}