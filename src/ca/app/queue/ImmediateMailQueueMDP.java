package ca.app.queue;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import ca.app.model.application.Application;
import ca.app.model.listing.Listing;
import ca.app.model.notification.NotificationLog;
import ca.app.model.user.User;
import ca.app.service.application.ApplicationService;
import ca.app.service.listing.ListingService;
import ca.app.service.mail.MailAttachment;
import ca.app.service.mail.MailMessage;
import ca.app.service.notificationLog.NotificationLogService;
import ca.app.service.user.UserService;
import ca.app.util.LogUtil;

public class ImmediateMailQueueMDP implements MessageListener {

	private JavaMailSender mailSender;
	private ApplicationService applicationService;
	private ListingService listingService;
	private UserService userService;
	private NotificationLogService notificationLogService;
	
	@Override
	public void onMessage(Message message) { 
		if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage)message;
			
			try {
				MailMessage mm = (MailMessage)objMessage.getObject();
				
				Application application = null;
				int applicationId = mm.getApplicationId();
				if(applicationId > 0) {
					application = applicationService.getByApplicationId(applicationId);
				}
				
				Listing listing = null;
				int listingId = mm.getListingId();
				if(listingId > 0) {
					listing = listingService.getByListingId(listingId);
				}
				
				User user = null;
				int userId = mm.getUserId();
				if(userId > 0) {
					user = userService.getByUserId(userId);
				}
				
				logDistributionList(mm.getToList(),  DistributionType.TO, application, listing, user, mm.getSubject(), mm.getNotificationTypeId());
				logDistributionList(mm.getCcList(),  DistributionType.CC, application, listing, user, mm.getSubject(), mm.getNotificationTypeId());
				logDistributionList(mm.getBccList(), DistributionType.BCC, application, listing, user, mm.getSubject(), mm.getNotificationTypeId());
				
				sendMail(mm, application, listing);
			} catch (JMSException ex) {
				LogUtil.logException(this.getClass(), "JmsException", ex);
			} catch (Exception ex) {
				LogUtil.logException(this.getClass(), "Exception", ex);
			}
		} else {
			LogUtil.logException(this.getClass(),"Message must be of type ObjectMessage", new IllegalArgumentException("Message must be of type ObjectMessage"));
		}
	}
	
	private void sendMail(MailMessage mm, Application application, Listing listing) throws Exception {
		try {
			boolean isMultipart = mm.getAttachmentList() != null && mm.getAttachmentList().size() > 0;
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, isMultipart, "UTF8");
			
			if (mm.getToList() != null && mm.getToList().size() > 0) {
				String[] toArray = new String[mm.getToList().size()];
				for (int i=0; i < mm.getToList().size(); i++) {
					toArray[i] = (String)mm.getToList().get(i);
				}
				helper.setTo(toArray);
			}
			
			if (mm.getCcList() != null && mm.getCcList().size() > 0) {
				String[] ccArray = new String[mm.getCcList().size()];
				for (int i=0; i < mm.getCcList().size(); i++) {
					ccArray[i] = (String)mm.getCcList().get(i);
				}
				helper.setCc(ccArray);
			}
			
			if (mm.getBccList() != null && mm.getBccList().size() > 0) {
				String[] bccArray = new String[mm.getBccList().size()];
				for (int i=0; i < mm.getBccList().size(); i++) {
					bccArray[i] = (String)mm.getBccList().get(i);
				}
				helper.setBcc(bccArray);
			}

			helper.setFrom(mm.getFrom());
			helper.setReplyTo(mm.getFrom());
			helper.setSubject(mm.getSubject());
			helper.setText(mm.getBody(), true);// true means we're passing html
			
			// Handle attachments
			if (mm.getAttachmentList()!=null) {
				for (MailAttachment attachment : mm.getAttachmentList()) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					//PdfUtil.generatePdfWithFS(attachment.getBody(), baos);
					InputStreamSource isr = new ByteArrayResource(baos.toByteArray());
					helper.addAttachment(attachment.getFilename(), isr);
					LogUtil.logDebug(this.getClass(), "Adding mail attachment: [" + attachment.getFilename() + "]");
				}
			}
			
			LogUtil.logDebug(this.getClass(), "Sending mail: []");
			mailSender.send(mimeMessage);
		} catch (MessagingException ex) {
			LogUtil.logException(getClass(), "Error sending notification", ex);
		}
	}
	
	private void logDistributionList(List<String> distList, DistributionType distributionType, Application application, Listing listing, User user, String subject, int notificationTypeId) {
		NotificationLog logEntry;
		if(distList != null) {
			for(String to: distList) {
				logEntry = new NotificationLog();
				logEntry.setApplication(application);
				logEntry.setListing(listing);
				logEntry.setUser(user);
				logEntry.setAddressee(to);
				logEntry.setSubject(subject);
				logEntry.setNotificationTypeId(notificationTypeId);
				logEntry.setDistributionTypeId(distributionType.getId());
				logEntry.setSentTime(new Timestamp(System.currentTimeMillis()));
				LogUtil.logDebug(this.getClass(), logEntry.toString());
				notificationLogService.log(logEntry);
			}
		}
	}

	public enum DistributionType{
		TO		(0, 		"To"),
		CC		(1, 		"Cc"),
		BCC	(2, 		"Bcc");
		
		private int id;
		private String label;
		private static final Map<Integer,DistributionType> lookup = new HashMap<Integer,DistributionType>();
		
		DistributionType(int id, String label){
			this.setId(id);
			this.setLabel(label);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		static {
			for (DistributionType currEnum : EnumSet.allOf(DistributionType.class)) {
				lookup.put(currEnum.getId(), currEnum);
			}
		}
		
		public static DistributionType get(int id) {
			return lookup.get(id);
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setNotificationLogService(NotificationLogService notificationLogService) {
		this.notificationLogService = notificationLogService;
	}
}