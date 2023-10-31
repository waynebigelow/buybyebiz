package ca.app.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveUserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityLogService activityLogService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_ACCOUNT_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int userId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.USER.getAlias()), TokenFieldType.USER);
		
		boolean isCreatedBySuperAdmin = false;
		User user = userService.getByUserId(userId);
		if (appUser.isSuperAdmin() && user == null) {
			user = new User();
			isCreatedBySuperAdmin = true;
		}
		
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setTelephone(request.getParameter("telephone"));
		user.setAgent(StringUtil.convertStringToBoolean(request.getParameter("agent"), false));
		user.setCompanyName(request.getParameter("companyName"));
		user.setEnableEnquiryEmail(StringUtil.convertStringToBoolean(request.getParameter("enableEnquiry"), false));
		user.setEnableExpirationEmail(StringUtil.convertStringToBoolean(request.getParameter("enableExpiry"), false));
		user.setEnablePromotionalEmail(StringUtil.convertStringToBoolean(request.getParameter("enablePromo"), false));
		
		if (appUser.isSuperAdmin()) {
			String email = request.getParameter("email");
			if (email != null && !email.equals(user.getEmail())) {
				user.setEmail(email);
				
				if (!isCreatedBySuperAdmin) {
					ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.USER_UPDATED.getId(), 0, userService.getByUserId(appUser.getUserId()));
					activityLogService.insert(activityLog);
				}
			}
			
			String password = request.getParameter("password");
			if (password != null && !password.equals("")) {
				user.setPassword(password);
				
				if (!isCreatedBySuperAdmin) {
					ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.PWD_CHANGED.getId(), 0, userService.getByUserId(appUser.getUserId()));
					activityLogService.insert(activityLog);
				}
			}
			
			if (isCreatedBySuperAdmin) {
				Application application = (Application)request.getAttribute("application");
				
				user.setEnabled(true);
				userService.createUser(user, application, appUser);
			} else {
				userService.update(user, true, appUser);
			}
		} else {
			userService.update(user, appUser);
		}
		return null;
	}
}