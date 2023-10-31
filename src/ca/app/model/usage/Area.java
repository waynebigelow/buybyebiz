package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Area {
	//											id			i18n									AAOnly
	APP_ADMIN							(10,		"100.area.app.admin",			true),
	EMAIL									(20,		"100.area.email",					true),
	HOME									(30,		"100.area.home",					true),
	INBOX									(40,		"100.area.inbox",					false),
	LISTING								(50,		"100.area.listing",				false),
	LISTING_ENQUIRY				(60,		"100.area.listing.enquiry",		false),
	SEARCH									(70,		"100.area.search",				true),
	SECURITY								(80,		"100.area.security",				false),
	SITE										(90,		"100.area.site",					false),
	USER_ACCOUNT						(100,		"100.area.user.account",		false),
	USER_ADMIN							(110,		"100.area.user.admin",			false);
	
	private int id;
	private String i18n;
	private boolean appAdminOnly;
	
	Area(int id, String i18n, boolean appAdminOnly) {
		this.id = id;
		this.i18n = i18n;
		this.appAdminOnly = appAdminOnly;
	}

	private static final Map<Integer,Area> lookup = new HashMap<Integer,Area>();
	static {
		for (Area currEnum : EnumSet.allOf(Area.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static Area get(int id) {
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

	public boolean isAppAdminOnly() {
		return appAdminOnly;
	}
	public void setAppAdminOnly(boolean appAdminOnly) {
		this.appAdminOnly = appAdminOnly;
	}
}