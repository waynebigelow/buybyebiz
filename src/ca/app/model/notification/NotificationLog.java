package ca.app.model.notification;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.application.Application;
import ca.app.model.listing.Listing;
import ca.app.model.user.User;

@Entity
@Table(name="notification_log")
public class NotificationLog implements Serializable {
	private static final long serialVersionUID = -2838269294684129360L;

	@Id
	@SequenceGenerator(name="seqNotificationLog", sequenceName="seq_notification_log", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqNotificationLog")
	@Column(name="notification_log_id", unique=true)
	private int notificationLogId;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="application_id")
	private Application application;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="listing_id")
	private Listing listing;
	
	@Column(name="addressee")
	private String addressee;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="sent_time")
	private Timestamp sentTime;
	
	@Column(name="distribution_type_id")
	private int distributionTypeId;
	
	@Column(name="notification_type_id")
	private int notificationTypeId;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Listing getListing() {
		return listing;
	}
	public void setListing(Listing listing) {
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

	public int getNotificationLogId() {
		return notificationLogId;
	}
	public void setNotificationLogId(int notificationLogId) {
		this.notificationLogId = notificationLogId;
	}
}