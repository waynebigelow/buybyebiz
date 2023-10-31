package ca.app.service.corporate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;

@Controller
public class LoadPrivacyPolicyController extends BaseController {
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(getAuthenticatedUser());
		
		ModelAndView mav = new ModelAndView("/corporate/pp");
		mav.addObject("pageAccess", pageAccess);
		return mav;
	}
}