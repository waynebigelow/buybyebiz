package ca.app.model.listing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ListingStatus {
	//										id		i18n					userAdmin
	DRAFT								(1,		"20.status.1",		false),
	PENDING_APPROVAL			(7,		"20.status.7",		false),
	ACTIVE								(2,		"20.status.2",		true),
	SUSPENDED						(3,		"20.status.3",		true),
	EXPIRED							(6,		"20.status.6",		false),
	SOLD									(4,		"20.status.4",		true),
	DELETED							(5,		"20.status.5",		false)
	;
	
	private int id;
	private String i18n;
	private boolean userAdmin;
	
	ListingStatus(int id, String i18n, boolean userAdmin){
		this.id = id;
		this.i18n = i18n;
		this.userAdmin = userAdmin;
	}
	
	private static final Map<Integer, ListingStatus> lookup = new HashMap<Integer, ListingStatus>();
	static {
		for (ListingStatus currEnum : EnumSet.allOf(ListingStatus.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static ListingStatus get(int id) {
		return lookup.get(id);
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	public boolean isUserAdmin() {
		return userAdmin;
	}
	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}
}