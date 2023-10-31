package ca.app.service.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;
import ca.app.model.user.AppUser;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import flexjson.JSONSerializer;

@Controller
public class EditCategoryController extends BaseController {
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
		Object category = null;
		if (type.equals("c")) {
			category = applicationService.getByCategoryId(categoryId);
		} else if (type.equals("s")) {
			category = applicationService.getBySubCategoryId(categoryId);
		}
		
		JSONSerializer json = new JSONSerializer();
		json.include((type.equals("s")?"categoryId":""), (type.equals("c")?"typeId":""), "name","i18n");
		json.exclude("*");

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.serialize("category", type.equals("c") ? (Category)category : (SubCategory)category));
		response.getWriter().flush();
		return null;
	}
}