package ca.app.service.mail;

import java.sql.Timestamp;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationPackage;
import ca.app.model.common.ListingLead;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.listing.Purchase;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.service.common.CommonService;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SendMailController extends BaseController {
	@Autowired
	private MailService mailService;
	@Autowired
	private ListingService listingService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ApplicationService applicationService;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int typeId = StringUtil.convertStringToInt(request.getParameter("ti"), 0);
		boolean isTest = StringUtil.convertStringToBoolean(request.getParameter("it"), true);
		
		if (isTest) {
			int listingId = StringUtil.convertStringToInt(ProjectUtil.getProperty("system.demo.listingId"), 0);
			Listing listing = listingService.getByListingId(listingId);
			
			handleTestEmail(GlobalEmailType.get(typeId), listing);
		} else {
			handleEmail(request, GlobalEmailType.get(typeId));
		}
		
		return null;
	}

	public void handleTestEmail(GlobalEmailType emailType, Listing listing) throws MessagingException {
		switch (emailType) {
			case WELCOME : mailService.sendWelcomeEmail(listing.getApplication(), listing.getUser()); break;
			case WELCOME_ENQUIRY : mailService.sendWelcomeEmailForEnquiry(listing.getApplication(), listing.getUser(), ""); break;
			case CONFIRM_PWD_RESET : mailService.sendConfirmPasswordReset(listing.getApplication(), listing.getUser()); break;
			case PWD_RESET : mailService.sendPasswordReset(listing.getApplication(), listing.getUser(), ""); break;
			case PWD_CHANGED : mailService.sendPasswordChanged(listing.getApplication(), listing.getUser()); break;
			case ACCT_LOCKED : mailService.sendAccountLocked(listing.getApplication(), listing.getUser()); break;
			case LISTING_ENQUIRY : mailService.sendCommonSiteEmail(listing, GlobalEmailType.LISTING_ENQUIRY); break;
			case ENQUIRY_REPLY : mailService.sendEnquiryReply(listing, listing.getUser()); break;
			case SUPPORT_ISSUE : break;
			case SYSTEM_PROBLEM : break;
			case USER_LOCKOUT : mailService.sendAccountLocked(listing.getApplication(), listing.getUser()); break;
			case SITE_RECEIPT : break;
			case SHARE_BY_EMAIL :break;
			case ADMIN_REQUEST : break;
			case SITE_CONTENT_REJECTED : mailService.sendListingEnquiry(listing, listing.getUser()); break;
			case SITE_ACTIVATED : mailService.sendSiteActivated(listing, 1) ; break;
			case SITE_EXPIRY_PENDING : break;
			case SITE_EXPIRED : break;
			case PROMOTIONAL_WELCOME : mailService.sendPromotionalEmail(listing.getApplication(), listing.getUser(), "", emailType); break;
			case EMAIL_CHANGE : break;
			case SITE_EXTENDED : sendSiteExtended(listing) ; break;
		}
	}
	
	public void handleEmail(HttpServletRequest request, GlobalEmailType emailType) throws MessagingException {
		switch (emailType) {
			case WELCOME : break;
			case WELCOME_ENQUIRY : break;
			case CONFIRM_PWD_RESET : break;
			case PWD_RESET : break;
			case PWD_CHANGED : break;
			case ACCT_LOCKED :  break;
			case LISTING_ENQUIRY : break;
			case ENQUIRY_REPLY : break;
			case SUPPORT_ISSUE : break;
			case SYSTEM_PROBLEM : break;
			case USER_LOCKOUT : break;
			case SITE_RECEIPT : break;
			case SHARE_BY_EMAIL :break;
			case ADMIN_REQUEST : break;
			case SITE_CONTENT_REJECTED : break;
			case SITE_ACTIVATED : break;
			case SITE_EXPIRY_PENDING : break;
			case SITE_EXPIRED : break;
			case PROMOTIONAL_WELCOME : handlePromotional(request, emailType); break;
			case EMAIL_CHANGE : break;
			case SITE_EXTENDED : break;
		}
	}
	
	public void handlePromotional(HttpServletRequest request, GlobalEmailType emailType) throws MessagingException {
		Application application = (Application)request.getAttribute("application");
		
		String customMessage = request.getParameter("cs");
		String enabledEmails = request.getParameter("em");
		
		if (enabledEmails != null && !enabledEmails.equals("")) {
			enabledEmails = enabledEmails.substring(0, enabledEmails.length() - 1);
			String[] enabledIds = enabledEmails.split(",");
			
			ListingLead lead = null;
			int leadId = 0;
			for (String id : enabledIds) {
				leadId = RequestUtil.getPrimaryId(id, TokenFieldType.LISTING_LEAD);
				
				lead = commonService.getByListingLeadId(leadId);
				if (lead != null) {
					User user = new User();
					user.setUserId(lead.getListingLeadId());
					user.setFirstName(lead.getFirstName());
					user.setLastName(lead.getLastName());
					user.setEmail(lead.getEmail());
					
					mailService.sendPromotionalEmail(application, user, customMessage, emailType);
					
					lead.setPromoSent(true);
					commonService.save(lead);
				}
			}
		}
	}
	
	public void sendSiteExtended(Listing listing) throws MessagingException {
		ApplicationPackage applicationPackage = applicationService.getByPackageId(2);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		purchase.setPurchaseId(1);
		purchase.setPurchaserUserId(1);
		purchase.setTransactionId("T12345");
		
		ListingPackage listingPackage = new ListingPackage();
		listingPackage.setListingPackageId(1);
		listingPackage.setPurchase(purchase); 
		listingPackage.setListingId(listing.getListingId());
		listingPackage.setApplicationPackage(applicationPackage);
		
		mailService.sendSiteExtended(listing, listingPackage);
	}
	
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}