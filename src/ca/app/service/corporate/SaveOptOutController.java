package ca.app.service.corporate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.common.ListingLead;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveOptOutController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private CommonService commonService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddress = RequestUtil.getEmailAddress(request.getParameter(TokenFieldType.EMAIL_ADDRESS.getAlias()), TokenFieldType.EMAIL_ADDRESS);
		boolean enablePromotionalEmail = StringUtil.convertStringToBoolean(request.getParameter("enablePromotionalEmail"), true);
		
		if (!enablePromotionalEmail) {
			User user = userService.getByEmail(emailAddress);
			if (user == null) {
				ListingLead listingLead = commonService.getByEmail(emailAddress);
				listingLead.setEnablePromotionalEmail(false);
				commonService.save(listingLead);
			} else {
				user.setEnablePromotionalEmail(false);
				userService.update(user);
			}
		}
		return null;
	}
}