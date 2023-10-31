package ca.app.service.applicationPackage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.ApplicationPackage;
import ca.app.model.application.ApplicationPackageType;
import ca.app.model.application.ApplicationType;
import ca.app.model.application.TimePeriod;
import ca.app.model.user.AppUser;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveAppPackageController extends BaseController {
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
		ApplicationPackage appPackage = new ApplicationPackage();
		if (packageId > 0) {
			appPackage.setPackageId(packageId);
		}
		
		appPackage.setName(request.getParameter("name"));
		appPackage.setDescription(request.getParameter("description"));
		appPackage.setApplicationId(StringUtil.convertStringToInt(request.getParameter("appType"), ApplicationType.BUSINESS.getId()));
		appPackage.setTypeId(StringUtil.convertStringToInt(request.getParameter("type"), ApplicationPackageType.TRIAL.getId()));
		appPackage.setTimePeriodId(StringUtil.convertStringToInt(request.getParameter("timePeriod"), TimePeriod.YEAR.getId()));
		appPackage.setDuration(StringUtil.convertStringToInt(request.getParameter("duration"), 0));
		//appPackage.setPrice(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("price"), 0)));
		//appPackage.setCurrencyTypeId(StringUtil.convertStringToInt(request.getParameter("currencyType"), CurrencyType.CAD.getId()));
		applicationService.saveOrUpdate(appPackage);
		return null;
	}
}