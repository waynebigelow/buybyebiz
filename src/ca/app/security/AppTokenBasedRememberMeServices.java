package ca.app.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.StringUtils;

import ca.app.model.application.Application;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;

public class AppTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
	
	private String cookieDomain = null;
	
	@Override
	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {	
		LogUtil.logDebug(this.getClass(), "Cookie added: " + ((maxAge<0)?"SSO":"RememberMe"));
		Cookie cookie = new Cookie(this.getCookieName(), this.encodeCookie(tokens));
		if (cookieDomain != null && cookieDomain.length() > 0) {
			LogUtil.logDebug(this.getClass(), "Cookie domain: " + cookieDomain);
			cookie.setDomain(cookieDomain);
		}
		cookie.setPath(getCookiePath(request));
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	@Override
	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
		LogUtil.logDebug(this.getClass(), "Cookie cancelled");
		Cookie cookie = new Cookie(this.getCookieName(), null);
		if (cookieDomain != null && cookieDomain.length() > 0) {
			cookie.setDomain(cookieDomain);
		}
		cookie.setPath(getCookiePath(request));
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
		int applicationId = 0;
		Application application = (Application)request.getAttribute("application");
		if (application != null) {
			applicationId = application.getApplicationId();
			LogUtil.logDebug(this.getClass(), "Using applicationId that was identified by the ApplicationCheckFilter: " + applicationId);
		} else {
			applicationId = StringUtil.convertStringToInt(request.getParameter(TokenFieldType.APPLICATION.getAlias()), 0);
			LogUtil.logDebug(this.getClass(), "Using applicationId that was pushed in the request: " + applicationId);
		}
		
		if (applicationId > 0) {
			// Alter the Username token so that it contains a reference to the current sub-domain(application)
			cookieTokens[0] = applicationId + ":" + cookieTokens[0];
			LogUtil.logDebug(this.getClass(), "Altered cookie username token to be application specific: " + cookieTokens[0]);
		}
		
		return super.processAutoLoginCookie(cookieTokens, request, response);
	}
	
	public void createSsoToken(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
		Cookie[] cookies = request.getCookies();
		boolean exists = false;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(this.getCookieName())) {
				exists = true;
			}
		}
		
		if (!exists) {
			String username = retrieveUserName(successfulAuthentication);
			String password = retrievePassword(successfulAuthentication);
			
			// If unable to find a username and password, just abort as TokenBasedRememberMeServices is
			// unable to construct a valid token in this case.
			if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
				return;
			}
			
			int tokenLifetime = calculateLoginLifetime(request,successfulAuthentication);
			long expiryTime = System.currentTimeMillis();
			// SEC-949
			expiryTime += 1000L * (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);
			
			String signatureValue = makeTokenSignature(expiryTime, username, password);
			String[] tokens = new String[] {username, Long.toString(expiryTime), signatureValue};
			
			setCookie(tokens, -1, request, response);
		}
	}
	
	private String getCookiePath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return contextPath.length() > 0 ? contextPath : "/";
	}
	
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}
}