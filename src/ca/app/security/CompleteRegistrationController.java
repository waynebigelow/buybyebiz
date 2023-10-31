package ca.app.security;

 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.user.PageAccessiblity;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.RequestUtil;

@Controller
public class CompleteRegistrationController extends BaseController{
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(getAuthenticatedUser());
		pageAccess.setLoginRequired(true);
		
		int applicationId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.APPLICATION.getAlias()), TokenFieldType.APPLICATION);
		int userId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.USER.getAlias()), TokenFieldType.USER);
		
		if (applicationId == 0 || userId == 0) {
			// throw a violation
		}
		
		Application application = (Application)request.getAttribute("application");
		
		User user = userService.getByUserId(userId);
		
		ModelAndView mav = null;
		if ((application == null || application.getApplicationId() != applicationId) || user == null) {
			mav = new ModelAndView("/user/registrationFailure");
		} else {
			mav = new ModelAndView("/corporate/home");
			mav.addObject("pageAccess", pageAccess);
			
			if (!user.isEnabled()) {
				user.setEnabled(true);
				userService.completeRegistration(user, application);
				mav.addObject("actionId", 1);
			}
		}

		return mav;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}