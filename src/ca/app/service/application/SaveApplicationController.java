package ca.app.service.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationType;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveApplicationController extends BaseController {
	@Autowired
	private ApplicationService applicationService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int applicationId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.APPLICATION.getAlias()), TokenFieldType.APPLICATION);
		Application application = new Application();
		if (applicationId > 0) {
			application.setApplicationId(applicationId);
		}
		
		application.setName(request.getParameter("name"));
		application.setDescription(request.getParameter("description"));
		application.setTypeId(StringUtil.convertStringToInt(request.getParameter("type"), ApplicationType.BUSINESS.getId()));
		application.setReplyEmail(request.getParameter("replyEmail"));
		application.setSupportEmail(request.getParameter("supportEmail"));
		application.setSupportPhone(request.getParameter("supportPhone"));
		application.setKey(request.getParameter("key"));
		application.setDomain(request.getParameter("domain"));
		application.setDefaultLocaleCode(request.getParameter("defaultLocaleCode"));
		applicationService.saveOrUpdate(application);
		return null;
	}
}