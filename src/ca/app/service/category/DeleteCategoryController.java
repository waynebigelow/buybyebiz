package ca.app.service.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.AppUser;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;

@Controller
public class DeleteCategoryController extends BaseController {
	@Autowired
	private ApplicationService applicationService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int categoryId = StringUtil.convertStringToInt(request.getParameter("c"), 0);
		String type = request.getParameter("type");
		if (type.equals("c")) {
			applicationService.deleteCategory(categoryId);
		} else if (type.equals("s")) {
			applicationService.deleteSubCategory(categoryId);
		}
		return null;
	}
}