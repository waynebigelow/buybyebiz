package ca.app.service.common;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.common.SupportIssue;
import ca.app.model.user.AppUser;
import ca.app.service.mail.MailService;
import ca.app.service.mail.SupportNotificationType;

@Controller
public class SendSupportIssueController extends BaseController {
	@Autowired
	private MailService mailService;
	@Autowired
	private CommonService commonService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		
		String firstName = request.getParameter("issueFirstName");
		String lastName = request.getParameter("issueLastName");
		String email = request.getParameter("issueEmail");
		String summary = request.getParameter("summary");
		String description = request.getParameter("description");

		Application application = (Application)request.getAttribute("application");

		SupportIssue issue = new SupportIssue();
		issue.setApplicationId(application.getApplicationId());
		issue.setUserId(appUser.getUserId());
		issue.setFirstName(firstName);
		issue.setLastName(lastName);
		issue.setEmail(email);
		issue.setSummary(summary);
		issue.setDescription(description);
		issue.setPostDate(new Timestamp(System.currentTimeMillis()));
		commonService.save(issue);
		
		mailService.sendAdminEmail(application, SupportNotificationType.SUPPORT_ISSUE);
	
		return null;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}