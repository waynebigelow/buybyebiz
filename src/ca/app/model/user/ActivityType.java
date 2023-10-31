package ca.app.model.user;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ActivityType {
	//													id			i18n
	ENQUIRY_POSTED							(10,		"100.type.enquiry.posted"),
	ENQUIRY_REPLIED						(20,		"100.type.enquiry.reply"),
	FAILED_LOGINS_EXCEEDED			(30,		"100.type.failed.logins"),
	LISTING_ACTIVATED						(40,		"100.type.listing.activated"),
	LISTING_CONTENT_APPROVED		(50,		"100.type.listing.approved"),
	LISTING_CONTENT_REJECTED		(60,		"100.type.listing.rejected"),
	LISTING_CREATED							(70,		"100.type.listing.created"),
	LISTING_DELETED							(80,		"100.type.listing.deleted"),
	LISTING_EXTENDED						(90,		"100.type.listing.extended"),
	LISTING_PUBLISH_REQUESTED		(100,		"100.type.listing.publish.requested"),
	LISTING_PUBLISHED						(110,		"100.type.listing.published"),
	LISTING_SOLD								(120,		"100.type.listing.sold"),
	LISTING_SUSPENDED					(130,		"100.type.listing.suspended"),
	LISTING_UPDATED						(140,		"100.type.listing.updated"),
	PHOTO_ADDED								(150,		"100.type.photo.added"),
	PHOTO_DELETED							(160,		"100.type.photo.deleted"),
	PHOTO_REPLACED							(170,		"100.type.photo.replaced"),
	PHOTO_UPDATED							(180,		"100.type.photo.updated"),
	PWD_CHANGED								(190,		"100.type.pwd.changed"),
	PWD_RESET									(200,		"100.type.pwd.reset"),
	PP_ACCEPTED								(210,		"100.type.pp.accepted"),
	REGISTRATION_COMPLETED			(220,		"100.type.reg.compleled"),
	REGISTRATION_REQUESTED			(230,		"100.type.reg.requested"),
	SIGNED_IN									(240,		"100.type.logged.in"),
	TOU_ACCEPTED								(250,		"100.type.tou.accepted"),
	USER_CREATED								(255,		"100.type.user.created"),
	USER_DELETED								(260,		"100.type.user.deleted"),
	USER_EMAIL_CHANGED					(270,		"100.type.user.email.changed"),
	USER_UPDATED								(280,		"100.type.user.updated")
	;
	
	private int id;
	private String i18n;
	
	private ActivityType(int id, String i18n) {
		this.id = id;
		this.i18n = i18n;
	}

	private static final Map<Integer,ActivityType> lookup = new HashMap<Integer,ActivityType>();
	static {
		for (ActivityType currEnum : EnumSet.allOf(ActivityType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static ActivityType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}