package ca.app.service.mail;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.context.MessageSource;

import ca.app.model.application.Application;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.user.User;
import ca.app.queue.ImmediateMailQueuePublisher;
import ca.app.service.common.TokenFieldType;
import ca.app.util.DateUtil;
import ca.app.util.LocaleUtil;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.dto.listing.ListingPackageDTO;
import ca.app.web.dto.user.UserDTO;

public class MailServiceImpl implements MailService {

	private ImmediateMailQueuePublisher immediateMailQueuePublisher;
	private VelocityEngine velocityEngine;
	private MessageSource resources;

	public void sendMessage(MailMessage message, GlobalEmailType emailType) throws MessagingException {
		this.immediateMailQueuePublisher.sendMessageToQueue(message.getToList(), message.getCcList(), message.getBccList(), 
			message.getFrom(), message.getSubject(), message.getBody(), message.getAttachmentList(), 
			message.getApplicationId(), message.getListingId(), message.getUserId(), emailType);
	}
	
	public MailMessage formatMailMessage(Application application, User user, GlobalEmailType emailType, Map<String,Object> args) {
		return formatMailMessage(application, user, emailType, args, null);
	}
	
	public MailMessage formatMailMessage(Application application, User user, GlobalEmailType emailType, Map<String,Object> args, String newEmail) {
		TemplateFinder templateFinder = new TemplateFinder(velocityEngine, application);
	
		Locale locale = resolveLocale(user, application);
		
		MailMessage mailMessage = getBaseMailMessage(user, application);
		
		UserDTO dto = new UserDTO(user);
		if (newEmail != null) {
			dto.setEmail(newEmail);
		}
		
		VelocityContext context = new VelocityContext();
		context.put("application", new ApplicationDTO(application));
		context.put("user", dto);
		context.put("locale", locale);
		context.put("date", new DateTool());
		context.put("today", new Date());
		context.put("resources", resources);
		context.put("templateFinder", templateFinder);
		context.put("optOutUrl", getLegalUrl(application, "/optOut.html?"+RequestUtil.getRequestParamURL(TokenFieldType.EMAIL_ADDRESS, user.getEmail())));
		context.put("termsOfUseUrl", getLegalUrl(application, "/tou.html?locale="+locale));
		context.put("privacyPolicyUrl", getLegalUrl(application, "/pp.html?locale="+locale));
		context.put("copyrightYear", (new SimpleDateFormat("yyyy")).format(new Date()));
	
		if (args != null) {
			for (String key : args.keySet()) {
				context.put(key, args.get(key));
			}
		}
		
		try {
			StringWriter writer = new StringWriter();
			Template t = velocityEngine.getTemplate(templateFinder.resolve(emailType.getTemplate()));
			t.merge(context, writer);
			
			mailMessage.setSubject((String)context.get("subject"));
			mailMessage.setBody(writer.toString());
			
			return mailMessage;
		} catch (Exception ex) {
			LogUtil.logException(getClass(), "Error sending notification", ex);
		}
		
		return null;
	}
	
	private MailMessage formatAdminMessage(Application application, User user, GlobalEmailType emailType, Map<String,Object> args, SupportNotificationType supportType) {
		TemplateFinder templateFinder = new TemplateFinder(velocityEngine, application);

		List<String> toList = new ArrayList<String>();
		toList.add(application.getSupportEmail());
		
		MailMessage mailMessage = new MailMessage();
		mailMessage.setApplicationId(application.getApplicationId());
		mailMessage.setFrom(application.getReplyEmail());
		mailMessage.setToList(toList);
		mailMessage.setSubject("Support Notification");
		mailMessage.setUserId(1);
		
		VelocityContext context = new VelocityContext();
		context.put("requestMessage", supportType.getMessage());
		context.put("application", new ApplicationDTO(application));
		context.put("resources", resources);
		context.put("templateFinder", templateFinder);
		
		if (args != null) {
			for (String key : args.keySet()) {
				context.put(key, args.get(key));
			}
		}
		
		try {
			StringWriter writer = new StringWriter();
			Template t = velocityEngine.getTemplate(templateFinder.resolve(emailType.getTemplate()));
			t.merge(context, writer);
			
			mailMessage.setBody(writer.toString());
			
			return mailMessage;
		} catch (Exception ex) {
			LogUtil.logException(getClass(), "Error sending notification", ex);
		}
		
		return null;
	}
	
	private MailMessage getBaseMailMessage(User user, Application application) {
		List<String> toList = new ArrayList<String>();
		if (user != null) {
			toList.add(user.getEmail());
		}
		
		MailMessage mailMessage = new MailMessage();
		mailMessage.setApplicationId(application.getApplicationId());
		mailMessage.setFrom(application.getReplyEmail());
		mailMessage.setToList(toList);
		mailMessage.setUserId(user.getUserId());
		
		return mailMessage;
	}
	
	public void sendWelcomeEmail(Application application, User user) throws MessagingException {
		String authUrl = application.getSecureApplicationURL() + "/security/completeRegistration.html?" + 
			RequestUtil.getRequestParamURL(TokenFieldType.APPLICATION, application.getApplicationId()) + "&" + 
			RequestUtil.getRequestParamURL(TokenFieldType.USER, user.getUserId());
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("authUrl", authUrl);
		args.put("subject", resources.getMessage(GlobalEmailType.WELCOME.getI18n(), new Object[]{application.getName()}, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.WELCOME, args), GlobalEmailType.WELCOME);
	}
	
	public void sendWelcomeEmailForEnquiry(Application application, User user, String password) throws MessagingException {
		String authUrl = application.getSecureApplicationURL() + "/security/completeRegistration.html?" + 
			RequestUtil.getRequestParamURL(TokenFieldType.APPLICATION, application.getApplicationId()) + "&" + 
			RequestUtil.getRequestParamURL(TokenFieldType.USER, user.getUserId());
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("authUrl", authUrl);
		args.put("password", password);
		args.put("subject", resources.getMessage(GlobalEmailType.WELCOME_ENQUIRY.getI18n(), new Object[]{application.getName()}, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.WELCOME_ENQUIRY, args), GlobalEmailType.WELCOME_ENQUIRY);
	}
	
	public void sendPasswordChanged(Application application, User user) throws MessagingException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("subject", resources.getMessage(GlobalEmailType.PWD_CHANGED.getI18n(), null, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.PWD_CHANGED, args), GlobalEmailType.PWD_CHANGED);
	}
	
	public void sendConfirmPasswordReset(Application application, User user) throws MessagingException {
		String resetUrl = application.getSecureApplicationURL() + "/security/completePwdReset.html?" + 
				RequestUtil.getRequestParamURL(TokenFieldType.APPLICATION, application.getApplicationId()) + "&" + 
				RequestUtil.getRequestParamURL(TokenFieldType.PWD_RESET, user.getUserId());
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("resetUrl", resetUrl);
		args.put("subject", resources.getMessage(GlobalEmailType.CONFIRM_PWD_RESET.getI18n(), null, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.CONFIRM_PWD_RESET, args), GlobalEmailType.CONFIRM_PWD_RESET);
	}
	
	public void sendPasswordReset(Application application, User user, String newPassword) throws MessagingException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("newPassword", newPassword);
		args.put("subject", resources.getMessage(GlobalEmailType.PWD_RESET.getI18n(), null, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.PWD_RESET, args), GlobalEmailType.PWD_RESET);
	}
	
	public void sendEmailChange(Application application, User user, String newEmail) throws MessagingException {
		String changeUrl = application.getSecureApplicationURL() + "/security/completeEmailChange.html?" + 
				RequestUtil.getRequestParamURL(TokenFieldType.APPLICATION, application.getApplicationId()) + "&" + 
				RequestUtil.getRequestParamURL(TokenFieldType.USER, user.getUserId()) + "&" + 
				RequestUtil.getRequestParamURL(TokenFieldType.EMAIL_ADDRESS, newEmail);
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("changeUrl", changeUrl);
		args.put("subject", resources.getMessage(GlobalEmailType.EMAIL_CHANGE.getI18n(), null, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.EMAIL_CHANGE, args, newEmail), GlobalEmailType.EMAIL_CHANGE);
	}
	
	public void sendAccountLocked(Application application, User user) throws MessagingException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("maxLogins", ProjectUtil.getIntProperty("max.failed.logins"));
		args.put("subject", resources.getMessage(GlobalEmailType.ACCT_LOCKED.getI18n(), null, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, GlobalEmailType.ACCT_LOCKED, args), GlobalEmailType.ACCT_LOCKED);
	}
	
	public void sendListingEnquiry(Listing listing, User receiver) throws MessagingException {
		String siteUrl = listing.getApplication().getSecureApplicationURL() + "/home.html";
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("siteUrl", siteUrl);
		args.put("subject", resources.getMessage(GlobalEmailType.LISTING_ENQUIRY.getI18n(), null, resolveLocale(receiver, listing.getApplication())));
		
		sendMessage(formatMailMessage(listing.getApplication(), receiver, GlobalEmailType.LISTING_ENQUIRY, args), GlobalEmailType.LISTING_ENQUIRY);
	}
	
	public void sendEnquiryReply(Listing listing, User receiver) throws MessagingException {
		String authUrl = listing.getApplication().getSecureApplicationURL() + "/security/completeRegistration.html?" + 
				RequestUtil.getRequestParamURL(TokenFieldType.APPLICATION, listing.getApplication().getApplicationId()) + "&" + 
				RequestUtil.getRequestParamURL(TokenFieldType.USER, receiver.getUserId());
		String siteUrl = listing.getApplication().getSecureApplicationURL() + "/home.html";
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("siteUrl", siteUrl);
		args.put("authUrl", authUrl);
		args.put("isEnabled", receiver.isEnabled());
		args.put("title", listing.getTitle());
		args.put("subject", resources.getMessage(GlobalEmailType.ENQUIRY_REPLY.getI18n(), null, resolveLocale(receiver, listing.getApplication())));
		
		sendMessage(formatMailMessage(listing.getApplication(), receiver, GlobalEmailType.ENQUIRY_REPLY, args), GlobalEmailType.ENQUIRY_REPLY);
	}
	
	public void sendSiteActivated(Listing listing, int duration) throws MessagingException {
		String siteUrl = listing.getApplication().getSecureApplicationURL() + "/home.html";
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("siteUrl", siteUrl);
		args.put("siteDuration", duration);
		args.put("subject", resources.getMessage(GlobalEmailType.SITE_ACTIVATED.getI18n(), null, resolveLocale(listing.getUser(), listing.getApplication())));
		
		sendMessage(formatMailMessage(listing.getApplication(), listing.getUser(), GlobalEmailType.SITE_ACTIVATED, args), GlobalEmailType.SITE_ACTIVATED);
	}
	
	public void sendPendingExpiry(Listing listing) throws MessagingException {
		String siteUrl = listing.getApplication().getSecureApplicationURL() + "/home.html";
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("siteUrl", siteUrl);
		args.put("expirationDate", getDateFormatted(listing.getExpirationDate()));
		args.put("subject", resources.getMessage(GlobalEmailType.SITE_EXPIRY_PENDING.getI18n(), null, resolveLocale(listing.getUser(), listing.getApplication())));
		
		sendMessage(formatMailMessage(listing.getApplication(), listing.getUser(), GlobalEmailType.SITE_EXPIRY_PENDING, args), GlobalEmailType.SITE_EXPIRY_PENDING);
	}
	
	public void sendSiteExtended(Listing listing, ListingPackage listingPackage) throws MessagingException {
		Locale locale = resolveLocale(listing.getUser(), listing.getApplication());
		
		ListingPackageDTO packageDTO = new ListingPackageDTO(listingPackage);
		packageDTO.getApplicationPackage().setLocale(locale);
		packageDTO.getApplicationPackage().setMessageSource(resources);
		
		ListingDTO listingDTO = new ListingDTO(listing, false);
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("listingPackage", packageDTO);
		args.put("listing", listingDTO);
		args.put("subject", resources.getMessage(GlobalEmailType.SITE_EXTENDED.getI18n(), null, resolveLocale(listing.getUser(), listing.getApplication())));
		
		sendMessage(formatMailMessage(listing.getApplication(), listing.getUser(), GlobalEmailType.SITE_EXTENDED, args), GlobalEmailType.SITE_EXTENDED);
	}
	
	public void sendCommonSiteEmail(Listing listing, GlobalEmailType emailType) throws MessagingException {
		String siteUrl = listing.getApplication().getSecureApplicationURL() + "/home.html";
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("siteUrl", siteUrl);
		args.put("subject", resources.getMessage(emailType.getI18n(), null, resolveLocale(listing.getUser(), listing.getApplication())));
		
		sendMessage(formatMailMessage(listing.getApplication(), listing.getUser(), emailType, args), emailType);
	}
	
	public void sendPromotionalEmail(Application application, User user, String customMessage, GlobalEmailType emailType) throws MessagingException {
		String siteUrl = application.getSecureApplicationURL() + "/home.html";
		String promoCount = ProjectUtil.getProperty("business.listing.promo.count");
		String promoDuration = ProjectUtil.getProperty("business.listing.promo.duration");
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("customMessage", customMessage);
		args.put("promoCount", promoCount);
		args.put("promoDuration", promoDuration);
		args.put("siteUrl", siteUrl);
		args.put("subject", resources.getMessage(emailType.getI18n(), new Object[]{application.getName(), promoDuration}, resolveLocale(user, application)));
		
		sendMessage(formatMailMessage(application, user, emailType, args), emailType);
	}
	
	public void sendAdminEmail(Listing listing, SupportNotificationType supportType) throws MessagingException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("listing", new ListingDTO(listing, true));
		
		sendMessage(formatAdminMessage(listing.getApplication(), listing.getUser(), GlobalEmailType.ADMIN_REQUEST, args, supportType), GlobalEmailType.ADMIN_REQUEST);
	}
	
	public void sendAdminEmail(Application application, SupportNotificationType supportType) throws MessagingException {
		sendMessage(formatAdminMessage(application, null, GlobalEmailType.ADMIN_REQUEST, null, supportType), GlobalEmailType.ADMIN_REQUEST);
	}
	
	public void sendSystemEmail(String to, String from, String subject, String body) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		
		this.immediateMailQueuePublisher.sendMessageToQueue(toList, null, null, from, subject, body, null, 0, 0, 1, GlobalEmailType.SYSTEM_PROBLEM);
	}
	
	public String getDateFormatted(Timestamp date) {
		String parsedDate = DateUtil.getStringDate(date.getTime(), ProjectUtil.getProperty("standard.date.format"));
		return parsedDate;
	}
	
	private Locale resolveLocale(User user, Application application) {
		Locale locale = null;
		if (user != null && user.getPreferredLocale() != null) {
			locale = LocaleUtil.toLocale(user.getPreferredLocale());
		}
		
		if (locale == null) {
			locale = application.getDefaultLocale();
		}
		
		return locale;
	}
	
	public String getLegalUrl(Application application, String path) {
		String url = "";

		if(application != null){
			url = application.getSecureApplicationURL() + path;
		} 

		return url;
	}
	
	public void setImmediateMailQueuePublisher(ImmediateMailQueuePublisher immediateMailQueuePublisher) {
		this.immediateMailQueuePublisher = immediateMailQueuePublisher;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	public void setResources(MessageSource resources) {
		this.resources = resources;
	}
}