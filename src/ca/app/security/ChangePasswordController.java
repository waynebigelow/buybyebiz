package ca.app.security;

 import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.usage.Usage;
import ca.app.model.user.Password;
import ca.app.model.user.User;
import ca.app.persistence.user.PasswordDAO;
import ca.app.service.common.BaseController;
import ca.app.service.mail.MailService;
import ca.app.service.usage.PageHitService;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;

@Controller
public class ChangePasswordController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private PasswordDAO passwordDAO;
	@Autowired
	private MailService mailService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String error = null;
		
		if (ProjectUtil.getBooleanProperty("recaptcha.enabled") && !isReCaptchaValid(request)) {
			error = "Invalid reCAPTCHA response!";
		}
		
		String email = request.getParameter("changePwdUsername");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");

		User user = userService.getByEmail(email);
		if (user == null || user.getUserId() <= 0) {
			error = getMsg(request, "0.msg.email.not.exists.warning");
		}
		
		// Check that old password is valid
		if (error == null) {
			User compareUser = new User();
			compareUser.setUserId(user.getUserId());
			compareUser.setEmail(user.getEmail());
			compareUser.setFirstName(user.getFirstName());
			compareUser.setLastName(user.getLastName());
			compareUser.setPreferredLocale(user.getPreferredLocale());
			compareUser.setEnabled(user.isEnabled());
			compareUser.setPassword(oldPassword);
			
			String oldEncryptedPassword = user.getPassword();
			String encryptedPassword = securityService.encodePasswordForUser(compareUser);
			if (oldEncryptedPassword == null || encryptedPassword == null || !oldEncryptedPassword.equals(encryptedPassword)) {
				error = getMsg(request, "0.msg.username.password.warning");
			}
		}

		//check that the password is complex enough
		if (error == null) {
			if (newPassword1 != null) {
				if(!newPassword1.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$")) {
					error = getMsg(request, "0.msg.not.complex.warning");
				}
			}
		}

		// Check that new passwords are the same
		if (error == null) {
			if (newPassword1 == null || newPassword2 == null || !newPassword1.equals(newPassword2)) {
				error = getMsg(request, "0.msg.not.same.warning");
			}
		}
		
		// Check that the new password is different from the old password
		if (error == null) {
			if (oldPassword == null || newPassword1 == null || oldPassword.equals(newPassword1)) {
				error = getMsg(request, "0.msg.not.unique.warning");
			}
		}
		
		if(error == null && ProjectUtil.isEnforceMinPasswordRepeat()) {
			int minWithNoRepeats = ProjectUtil.getMinPasswordRepeat();
			
			user.setPassword(newPassword1);
			String chkEncryptedPassword = securityService.encodePasswordForUser(user);
			
			List<Password> history = passwordDAO.getPasswordHistory(user.getUserId(), minWithNoRepeats);
			for(Password password : history) {
				if(password.getHash().trim().equals(chkEncryptedPassword)) {
					error = getMsg(request, "0.msg.used.before.warning", new String[] {ProjectUtil.getMinPasswordRepeat() + ""});
					break;
				}
			}
		}
		
		// All checks passed - change the password
		if (error == null) {
			String chkEncryptedPassword = securityService.encodePasswordForUser(user);
			securityService.logOldPassword(user, chkEncryptedPassword);
			securityService.changeUserPassword(user, newPassword1);
			
			Application application = (Application)request.getAttribute("application");
			mailService.sendPasswordChanged(application, user);
		}
		
		JsonUtil.setupResponseForJSON(response);
		if (error == null) {
			response.getWriter().print("{\"success\": true}");
		} else {
			response.getWriter().print("{\"success\": false, \"error\": \"" + error + "\"}");
			
			try {
				pageHitService.logPageHit(null, request, Usage.CHANGE_PWD_FAILED);
			} catch (Exception ex) {
				LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
			}
		}
		response.getWriter().flush();
		
		return null;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public void setPasswordDAO(PasswordDAO passwordDAO) {
		this.passwordDAO = passwordDAO;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
}