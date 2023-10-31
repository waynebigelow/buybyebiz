package ca.app.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.AdminTabType;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.usage.PageHitService;
import ca.app.service.user.UserService;
import ca.app.util.LogUtil;
import ca.app.web.dto.user.UserDTO;

@Controller
public class LoadAdminController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		List<UserDTO> users = userService.getAllUsers();
		
		try {
			pageHitService.logPageHit(null, request, Usage.APP_ADMIN);
		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
		}
		
		ModelAndView mav = new ModelAndView("/appAdmin/users");
		mav.addObject("users", users);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", AdminTabType.values());
		mav.addObject("tab", AdminTabType.USERS);
		return mav;
	}
}