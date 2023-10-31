package ca.app.service.mail;

import javax.mail.MessagingException;

import ca.app.model.application.Application;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.user.User;

public interface MailService {
	public void sendMessage(MailMessage message, GlobalEmailType emailType) throws MessagingException;
	public void sendWelcomeEmail(Application application, User user) throws MessagingException;
	public void sendWelcomeEmailForEnquiry(Application application, User user, String password) throws MessagingException;
	public void sendConfirmPasswordReset(Application application, User user) throws MessagingException;
	public void sendPasswordReset(Application application, User user, String newPassword) throws MessagingException;
	public void sendPasswordChanged(Application application, User user) throws MessagingException;
	public void sendEmailChange(Application application, User user, String newEmail) throws MessagingException;
	public void sendAccountLocked(Application application, User user) throws MessagingException;
	public void sendListingEnquiry(Listing listing, User poster) throws MessagingException;
	public void sendEnquiryReply(Listing listing, User receiver) throws MessagingException;
	public void sendSiteActivated(Listing listing, int duration) throws MessagingException;
	public void sendPendingExpiry(Listing listing) throws MessagingException;
	public void sendSiteExtended(Listing listing, ListingPackage listingPackage) throws MessagingException;
	public void sendCommonSiteEmail(Listing listing, GlobalEmailType emailType) throws MessagingException;
	public void sendAdminEmail(Listing listing, SupportNotificationType supportType) throws MessagingException;
	public void sendAdminEmail(Application application, SupportNotificationType supportType) throws MessagingException;
	public void sendSystemEmail(String to, String from, String subject, String body);
	public void sendPromotionalEmail(Application application, User user, String customMessage, GlobalEmailType emailType) throws MessagingException;
}