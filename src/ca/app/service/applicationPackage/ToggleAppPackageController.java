package ca.app.service.applicationPackage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.ApplicationPackage;
import ca.app.model.user.AppUser;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class ToggleAppPackageController extends BaseController {
	@Autowired
	private ApplicationService applicationService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int packageId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.APP_PACKAGE.getAlias()), TokenFieldType.APP_PACKAGE);
		ApplicationPackage appPackage = applicationService.getByPackageId(packageId);
		
		if (appPackage.isEnabled()) {
			appPackage.setEnabled(false);
		} else {
			appPackage.setEnabled(true);
		}
		
		applicationService.saveOrUpdate(appPackage);
		return null;
	}
}