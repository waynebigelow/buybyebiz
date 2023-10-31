package ca.app.service.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MailMessage implements Serializable {

	private static final long serialVersionUID = 7292896287846213889L;
	
	private List<String> toList;
	private List<String> ccList;
	private List<String> bccList;
	private String from;
	private String subject;
	private String body;
	private List<MailAttachment> attachmentList = null;
	private int applicationId;
	private int listingId;
	private int userId;
	private int notificationTypeId;
	
	public MailMessage() {}
	
	public MailMessage(List<String> toList, List<String> ccList, List<String> bccList, String from, String subject, 
			String body, List<MailAttachment> attachmentList, int applicationId, int listingId, int userId, int notificationTypeId) {
		this.setToList(toList);
		this.setCcList(ccList);
		this.setBccList(bccList);
		this.setFrom(from);
		this.setSubject(subject);
		this.setBody(body);
		this.setAttachmentList(attachmentList);
		this.setApplicationId(applicationId);
		this.setListingId(listingId);
		this.setUserId(userId);
		this.setNotificationTypeId(notificationTypeId);
	}
	
	public List<String> getToList() {
		return toList;
	}
	
	public void setToList(List<String> toList) {
		this.toList = toList;
	}
	
	public List<String> getCcList() {
		return ccList;
	}
	
	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public List<String> getBccList() {
		return bccList;
	}
	
	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}

	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public List<MailAttachment> getAttachmentList() {
		return attachmentList;
	}
	
	public void setAttachmentList(List<MailAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	public void addAttachment(MailAttachment attachment) {
		if (attachmentList==null) {
			attachmentList = new ArrayList<MailAttachment>();
		}
		attachmentList.add(attachment);
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNotificationTypeId() {
		return notificationTypeId;
	}
	
	public void setNotificationTypeId(int notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}
}