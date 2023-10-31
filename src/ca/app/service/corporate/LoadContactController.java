package ca.app.service.corporate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;

public class LoadContactController extends BaseController {
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(getAuthenticatedUser());
		
		ModelAndView mav = new ModelAndView("/corporate/contact");
		mav.addObject("pageAccess", pageAccess);
		return mav;
	}
}