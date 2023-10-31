package ca.app.service.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.AdminTabType;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.util.LogUtil;
import ca.app.web.dto.category.CategoryDTO;

@Controller
public class LoadCategoryController extends BaseController {
	@Autowired
	private ApplicationService applicationService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		List<CategoryDTO> categories = applicationService.getAllCategories();
		
		ModelAndView mav = new ModelAndView("/appAdmin/categories");
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("categories", categories);
		mav.addObject("tabs", AdminTabType.values());
		mav.addObject("tab", AdminTabType.CATEGORIES);
		return mav;
	}
}