package ca.app.security;

 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.mail.MailService;
import ca.app.service.usage.PageHitService;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;

@Controller
public class ChangeEmailController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_ACCOUNT_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		String error = null;
		
		String newEmail = request.getParameter("changeEmailUsername");
		User user = userService.getByEmail(newEmail);
		if (user != null && user.getUserId() > 0) {
			error = getMsg(request, "0.msg.email.exists.warning");
		}
		
		if (error == null) {
			Application application = (Application)request.getAttribute("application");
			user = userService.getByUserId(appUser.getUserId());
			mailService.sendEmailChange(application, user, newEmail);
		}
		
		JsonUtil.setupResponseForJSON(response);
		if (error == null) {
			response.getWriter().print("{\"success\": true}");
		} else {
			response.getWriter().print("{\"success\": false, \"error\": \"" + error + "\"}");
			
			if (!appUser.isSuperAdmin()) {
				try {
					pageHitService.logPageHit(null, request, Usage.CHANGE_PWD_FAILED);
				} catch (Exception ex) {
					LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
				}
			}
		}
		response.getWriter().flush();
		
		return null;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
}