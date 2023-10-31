package ca.app.service.category;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.category.SubCategory;
import ca.app.model.user.AppUser;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.SubCategoryDTO;
import flexjson.JSONSerializer;

@Controller
public class LoadSubCategoryController extends BaseController {
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
		List<SubCategoryDTO> subCategoryDTOList = new ArrayList<SubCategoryDTO>();
		
		List<SubCategory> subCategories = applicationService.getAllByCategoryId(categoryId);
		for (SubCategory subCategory : subCategories) {
			subCategoryDTOList.add(new SubCategoryDTO(subCategory, getMessageSource(), getLocale(request)));
		}
		
		JSONSerializer json = new JSONSerializer();
		json.exclude("*.class");
		json.exclude("obj");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.serialize(subCategoryDTOList));
		response.getWriter().flush();
		return null;
	}
}