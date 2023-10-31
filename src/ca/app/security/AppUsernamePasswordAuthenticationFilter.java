package ca.app.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ca.app.model.user.AppUser;
import ca.app.util.LogUtil;

public class AppUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication authResult = super.attemptAuthentication(request, response);
		
		LogUtil.logDebug(this.getClass(), "Authentication successfull");
		
//		AppUser user = (AppUser)authResult.getPrincipal();
//		if (user.isSuperAdmin()) {
//			AppTokenBasedRememberMeServices rememberMe = (AppTokenBasedRememberMeServices)this.getRememberMeServices();
//			if (request.getParameter(rememberMe.getParameter()) == null) {
//				rememberMe.createSsoToken(request, response, authResult);
//			}
//		}
		
		return authResult;
	}
}