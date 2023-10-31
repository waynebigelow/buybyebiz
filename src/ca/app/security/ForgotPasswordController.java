package ca.app.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.common.HashToken;
import ca.app.model.common.HashTokenType;
import ca.app.model.usage.Usage;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.service.common.TokenFieldType;
import ca.app.service.mail.MailService;
import ca.app.service.usage.PageHitService;
import ca.app.service.user.UserService;
import ca.app.util.HashUtil;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;

@Controller
public class ForgotPasswordController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String error = null;
		
		if (ProjectUtil.getBooleanProperty("recaptcha.enabled") && !isReCaptchaValid(request)) {
			error = "Invalid reCAPTCHA response";
		}
		
		String email = request.getParameter("forgotPwdUsername");
		User user = userService.getByEmail(email);
		if (error == null && (user == null || user.getUserId() <= 0)) {
			error = getMsg(request, "0.msg.email.not.exists.warning");
		}
		
		if (error == null) {
			String ht =  RequestUtil.getToken(TokenFieldType.PWD_RESET.getKey(), user.getUserId());
			if (commonService.getHashToken(ht) == null) {
				HashToken token = HashUtil.getOneTimeToken(user.getUserId(), HashTokenType.PWD_RESET.getId(), ht, 1440);
				commonService.saveHashToken(token);
			}
			
			Application application = (Application)request.getAttribute("application");
			mailService.sendConfirmPasswordReset(application, user);
		}
		
		try {
			pageHitService.logPageHit(null, request, Usage.FORGOT_PWD);
		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
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
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	public void setPageHitService(PageHitService pageHitService) {
		this.pageHitService = pageHitService;
	}
}