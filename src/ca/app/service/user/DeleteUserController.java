package ca.app.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class DeleteUserController extends BaseController {
	@Autowired
	private UserService userService;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int userId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.USER.getAlias()), TokenFieldType.USER);
		User user = userService.getByUserId(userId);
		if (user != null) {
			userService.cascadeDeleteAllForUser(user);
		} else {
			LogUtil.logDebug(DeleteUserController.class, "An attempt was made to delete this user [" + userId + "]");
		}
		return null;
	}
}