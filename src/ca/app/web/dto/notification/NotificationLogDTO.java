package ca.app.web.dto.notification;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.notification.NotificationLog;
import ca.app.service.common.TokenFieldType;
import ca.app.util.DateUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.dto.user.UserDTO;

public class NotificationLogDTO implements Serializable {
	private static final long serialVersionUID = -2838269294684129360L;

	private int notificationLogId;
	private ApplicationDTO application;
	private UserDTO user;
	private ListingDTO listing;
	private String addressee;
	private String subject;
	private Timestamp sentTime;
	private int distributionTypeId;
	private int notificationTypeId;
	
	public NotificationLogDTO (NotificationLog notificationLog) {
		this.notificationLogId = notificationLog.getNotificationLogId();
		this.application = new ApplicationDTO(notificationLog.getApplication());
		this.user = new UserDTO(notificationLog.getUser());
		if (notificationLog.getListing() != null) {
			this.listing = new ListingDTO(notificationLog.getListing(), false);
		}
		this.addressee = notificationLog.getAddressee();
		this.subject = notificationLog.getSubject();
		this.sentTime = notificationLog.getSentTime();
		this.distributionTypeId = notificationLog.getDistributionTypeId();
		this.notificationTypeId = notificationLog.getNotificationTypeId();
	}
	
	@JsonIgnore
	public int getNotificationLogId() {
		return notificationLogId;
	}
	public void setNotificationLogId(int notificationLogId) {
		this.notificationLogId = notificationLogId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.NOTIFICATION_LOG.getKey(), notificationLogId);
	}
	
	
	public ApplicationDTO getApplication() {
		return application;
	}
	public void setApplication(ApplicationDTO application) {
		this.application = application;
	}

	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public ListingDTO getListing() {
		return listing;
	}
	public void setListing(ListingDTO listing) {
		this.listing = listing;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public Timestamp getSentTime() {
		return sentTime;
	}
	public void setSentTime(Timestamp sentTime) {
		this.sentTime = sentTime;
	}
	public String getSentTimeFormatted() {
		if(sentTime == null) {
			return "";
		}
		return DateUtil.getStringDate(sentTime.getTime(), ProjectUtil.getProperty("standard.date.format"));
	}
	
	public String getAddressee() {
		return addressee;
	}
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public int getDistributionTypeId() {
		return distributionTypeId;
	}
	public void setDistributionTypeId(int distributionTypeId) {
		this.distributionTypeId = distributionTypeId;
	}

	public int getNotificationTypeId() {
		return notificationTypeId;
	}
	public void setNotificationTypeId(int notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}
	
	public String getType() {
		if (user != null) {
			return "user";
		} else {
			return "listing";
		}
	}

	@Override
	public String toString() {
		return "NotificationEntry [application=" + application + ", user=" + user + ", listing=" + listing
			+ ", addressee=" + addressee + ", subject=" + subject + ", sentTime=" + sentTime + ", "
			+ ", distributionTypeId=" + distributionTypeId + ", notificationTypeId=" + notificationTypeId + "]";
	}
}