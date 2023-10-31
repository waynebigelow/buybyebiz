package ca.app.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.usage.Usage;
import ca.app.service.common.BaseController;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;

@Controller
public class LoadPageDeniedController extends BaseController {
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			pageHitService.logPageHit(null, request, Usage.DENIED);
		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
		}
		
		ModelAndView mav = new ModelAndView("/corporate/home");
		return mav;
	}
}