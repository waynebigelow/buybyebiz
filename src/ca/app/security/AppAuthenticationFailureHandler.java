package ca.app.security;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import ca.app.model.application.Application;
import ca.app.util.LogUtil;

public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private SecurityService securityService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String username = request.getParameter("j_username");
		if (username != null && username.contains(":")) {
			String[] userNameArray = username.split(":");
			username = userNameArray[1];
		
			LogUtil.logDebug(getClass(), "Failed login for : " + username + " Exception was " + exception.getMessage());
			Application application = (Application) request.getAttribute("application");
			
			try {
				securityService.logFailedLogin(username, application);
			} catch (MessagingException ex) {
				LogUtil.logException(getClass(), "Exception", ex);
			}
		}
		
		response.setStatus(401);
		response.getWriter().print("");
		response.getWriter().flush();
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}