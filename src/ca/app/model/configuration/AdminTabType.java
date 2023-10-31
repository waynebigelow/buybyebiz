package ca.app.model.configuration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AdminTabType {
	//									type	alias,		tabname					action,														i18n
	USERS							(2,		"u",		"users",					"/appAdmin/users.html?alias=u",					"10.tab.1"),
	LISTINGS						(2,		"l",		"listings",				"/appAdmin/listings.html?alias=l",					"10.tab.8"),
	LISTING_LEADS				(2,		"ll",		"leads",					"/appAdmin/listingLeads.html?alias=ll",			"10.tab.9"),
	ACTIVITY_LOG				(2,		"al",		"activityLog",			"/appAdmin/activityLog.html?alias=al",			"10.tab.6"),
	PAGE_HITS					(2,		"ph",		"pageHits",				"/appAdmin/pageHits.html?alias=ph",				"10.tab.10"),
	NOTIFICATION_LOG		(2,		"nl",		"notificationLog",		"/appAdmin/notificationLog.html?alias=nl",		"10.tab.3"),
	APPLICATIONS				(2,		"a",		"applications",			"/appAdmin/applications.html?alias=a",			"10.tab.2"),
	PACKAGES						(2,		"pk",		"packages",				"/appAdmin/packages.html?alias=pk",				"10.tab.7"),
	CATEGORIES					(2,		"ca",		"categories",			"/appAdmin/categories.html?alias=ca",			"10.tab.5"),
	SETTINGS						(2,		"s",		"settings",				"/appAdmin/settings.html?alias=s",					"10.tab.4")
	;
	
	private int type;
	private String alias;
	private String tabName;
	private String action;
	private String i18n;
	
	AdminTabType(int type, String alias, String tabName, String action, String i18n) {
		this.type = type;
		this.alias = alias;
		this.tabName = tabName;
		this.action = action;
		this.i18n = i18n;
	}

	private static final Map<String,AdminTabType> lookup = new HashMap<String,AdminTabType>();
	static {
		for (AdminTabType currEnum : EnumSet.allOf(AdminTabType.class)) {
			lookup.put(currEnum.getAlias(), currEnum);
		}
	}
	
	public static AdminTabType get(String alias) {
		return lookup.get(alias);
	}
	
	private static final Map<Integer,AdminTabType> lookupType = new HashMap<Integer,AdminTabType>();
	static {
		for (AdminTabType currEnum : EnumSet.allOf(AdminTabType.class)) {
			lookup.put(currEnum.getAlias(), currEnum);
		}
	}
	
	public static AdminTabType getType(int type) {
		return lookupType.get(type);
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}