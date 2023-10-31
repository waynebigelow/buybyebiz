package ca.app.service.corporate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.common.ListingLead;
import ca.app.model.user.PageAccessiblity;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class LoadOptOutController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private CommonService commonService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(getAuthenticatedUser());
		
		String error = null;
		User user = null;
		ListingLead listingLead = null;
		
		String emailAddress = RequestUtil.getEmailAddress(request.getParameter(TokenFieldType.EMAIL_ADDRESS.getAlias()), TokenFieldType.EMAIL_ADDRESS);
		if (emailAddress == null) {
			error = "There was a problem processing your request.";
			LogUtil.logInfo(this.getClass(), "Opt out link failure, emailAddress was missing or corrupted.");
		} else {
			user = userService.getByEmail(emailAddress);
			if (user == null) {
				listingLead = commonService.getByEmail(emailAddress);
				if (listingLead == null) {
					error = "The user record could not be found.";
					LogUtil.logInfo(this.getClass(), "Opt out link failed: provided emailAddress was " + emailAddress);
				} else {
					user = new User();
					user.setFirstName(listingLead.getFirstName());
					user.setLastName(listingLead.getLastName());
					user.setEmail(listingLead.getEmail());
				}
			}
		}
		
		ModelAndView mav = new ModelAndView("/corporate/optOut");
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("token", request.getParameter(TokenFieldType.EMAIL_ADDRESS.getAlias()));
		mav.addObject("error", error);
		mav.addObject("user", user);
		return mav;
	}
}