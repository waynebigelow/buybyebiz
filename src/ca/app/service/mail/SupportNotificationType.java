package ca.app.service.mail;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SupportNotificationType {
	//										message
	LISTING_CREATED				("A new listing has been created."),
	LISTING_UPDATED			("A listing has been updated."),
	LISTING_EXTENDED			("A listing has been extended."),
	PUBLISH_REQUESTED		("A publish request has been submitted."),
	CONTENT_MODIFIED			("Content has changed for an active listing."),
	USER_UPDATED					("A user account has been updated."),
	USER_LOCKED_OUT			("A user account has been locked out."),
	LISTING_EXPIRED				("A listing has expired."),
	SUPPORT_ISSUE				("A support issue has been posted.")
	;
	
	private String message;
	
	private SupportNotificationType(String message) {
		this.message = message;
	}

	private static final Map<String,SupportNotificationType> lookup = new HashMap<String,SupportNotificationType>();
	static {
		for (SupportNotificationType currEnum : EnumSet.allOf(SupportNotificationType.class)) {
			lookup.put(currEnum.name(), currEnum);
		}
	}
	public static SupportNotificationType get(int id) {
		return lookup.get(id);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}