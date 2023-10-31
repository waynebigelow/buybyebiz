package ca.app.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import ca.app.util.LogUtil;

public class AppLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private String xmlHttpRequestUrl;
	
	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		
		// If this is an XMLHttpRequest (I.e. sent by Extjs), then we'll return a 
		// 401 (Unauthorized) status.  This will allow the Ajax exception handling
		// in jsInclude.jsp to pickup and to do the appropriate screen refresh.
		String xRequest = request.getHeader("X-Requested-With");
		if (xRequest != null && xRequest.equalsIgnoreCase("XMLHttpRequest")) {
			LogUtil.logDebug(this.getClass(), "JSON Resource was requested which requires authentication: [uri=" + request.getServletPath() + "][from=" + request.getRemoteAddr() + "]");
			return xmlHttpRequestUrl;
		}

		return getLoginFormUrl();
	}

	public void setXmlHttpRequestUrl(String xmlHttpRequestUrl) {
		this.xmlHttpRequestUrl = xmlHttpRequestUrl;
	}
}