package ca.app.security;

 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.common.HashToken;
import ca.app.model.common.HashTokenType;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.RequestUtil;

@Controller
public class CompletePasswordResetController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private CommonService commonService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(getAuthenticatedUser());
		
		Application application = (Application)request.getAttribute("application");
		String appTk = request.getParameter(TokenFieldType.APPLICATION.getAlias());
		String hashTk = request.getParameter(TokenFieldType.PWD_RESET.getAlias());
		
		ModelAndView mav = null;
		if (isError(application, appTk, hashTk)) {
			mav = new ModelAndView("/user/pwdResetFailure");
		} else {
			int userId = RequestUtil.getPrimaryId(hashTk, TokenFieldType.PWD_RESET);
			
			securityService.resetUserPassword(userService.getByUserId(userId), application);
			
			commonService.deleteByUserIdTypeId(userId, HashTokenType.PWD_RESET.getId());
			
			mav = new ModelAndView("/corporate/home");
			mav.addObject("pageAccess", pageAccess);
		}

		return mav;
	}

	private boolean isError(Application application, String appTk, String hashTk) {
		if (appTk == null || appTk.equals("")) {
			return true;
		}
		
		int applicationId = RequestUtil.getPrimaryId(appTk, TokenFieldType.APPLICATION);
		if (applicationId == 0 || application.getApplicationId() != applicationId) {
			return true;
		}
		
		if (hashTk == null || hashTk.equals("")) {
			return true;
		}
		
		HashToken hashToken = commonService.getHashToken(hashTk);
		if (hashToken == null) {
			return true;
		}
		
		int userId = RequestUtil.getPrimaryId(hashTk, TokenFieldType.PWD_RESET);
		if (userId == 0) {
			return true;
		}
		
		return false;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}