package ca.app.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import ca.app.service.common.BaseController;

public class ReturnUnauthorizedJsonController extends BaseController{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setStatus(401);
		return null;
	}
}