package ca.app.service.commerce;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import flexjson.JSONSerializer;

@Controller
public class LoadPaymentDetailsController extends BaseController {
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_LISTING_OWNER")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		User user = userService.getByUserId(appUser.getUserId());
		
		JSONSerializer json = new JSONSerializer();
		json.include("email","firstName","lastName");
		json.exclude("*");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.serialize("user", user));
		response.getWriter().flush();
		return null;
	}
}