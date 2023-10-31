package ca.app.security;

 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.mail.MailService;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.ProjectUtil;

@Controller
public class RegistrationController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String error = null;
		
		if (ProjectUtil.getBooleanProperty("recaptcha.enabled") && !isReCaptchaValid(request)) {
			error = "Invalid reCAPTCHA response!";
		}
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("userName");
		String password = request.getParameter("password1");
		String confirmPassword = request.getParameter("password2");
		String acceptedTOU = request.getParameter("acceptedTOU");
		String acceptedPP = request.getParameter("acceptedPP");
		
		if ((firstName == null || firstName.equals("")) 
				|| (lastName == null || lastName.equals("")) 
				|| (email == null || email.equals("") || !isValidEmail(email)) 
				|| (acceptedTOU == null || !acceptedTOU.equals("true"))
				|| (acceptedPP == null || !acceptedPP.equals("true") )) {
			error = "You have provided incomplete details";
		}
		
		User user = userService.getByEmail(email);
		if (user != null && user.getUserId() > 0) {
			error = getMsg(request, "0.msg.email.exists.warning");
		}

		//check that the password is complex enough
		if (error == null) {
			if(!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$")) {
				error = getMsg(request, "0.msg.not.complex.warning");
			}
		}

		// Check that new passwords are the same
		if (error == null) {
			if (!password.equals(confirmPassword)) {
				error = getMsg(request, "0.msg.not.same.warning");
			}
		}
		
		if (error == null) {
			Application application = (Application)request.getAttribute("application");
			
			user = new User();
			user.setFirstName(request.getParameter("firstName"));
			user.setLastName(request.getParameter("lastName"));
			user.setEmail(request.getParameter("userName"));
			user.setPassword(request.getParameter("password1"));
			user.setPreferredLocale(getLocale(request).toString());
			userService.register(user, application);
			mailService.sendWelcomeEmail(application, user);
		}
		
		JsonUtil.setupResponseForJSON(response);
		if (error == null) {
			response.getWriter().print("{\"success\": true}");
		} else {
			response.getWriter().print("{\"success\": false, \"error\": \"" + error + "\"}");
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