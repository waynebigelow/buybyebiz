package ca.app.model.configuration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserAdminTabType {
	//							type	alias,		tabname					action,														i18n
	LISTINGS				(2,		"li",		"listings",				"/userAdmin/admin.html?alias=li",					"15.tab.1"),
	INBOX					(2,		"in",		"inbox",					"/userAdmin/inbox.html?alias=in",					"15.tab.2"),
	ACCOUNT				(2,		"ac",		"account",				"/userAdmin/editUser.html?alias=ac",				"15.tab.3"),
	ACTIVITY_LOG		(2,		"al",		"activityLog",			"/userAdmin/activityLog.html?alias=al",			"15.tab.4"),
	;
	
	private int type;
	private String alias;
	private String tabName;
	private String action;
	private String i18n;
	
	UserAdminTabType(int type, String alias, String tabName, String action, String i18n) {
		this.type = type;
		this.alias = alias;
		this.tabName = tabName;
		this.action = action;
		this.i18n = i18n;
	}

	private static final Map<String,UserAdminTabType> lookup = new HashMap<String,UserAdminTabType>();
	static {
		for (UserAdminTabType currEnum : EnumSet.allOf(UserAdminTabType.class)) {
			lookup.put(currEnum.getAlias(), currEnum);
		}
	}
	
	public static UserAdminTabType get(String alias) {
		return lookup.get(alias);
	}
	
	private static final Map<Integer,UserAdminTabType> lookupType = new HashMap<Integer,UserAdminTabType>();
	static {
		for (UserAdminTabType currEnum : EnumSet.allOf(UserAdminTabType.class)) {
			lookup.put(currEnum.getAlias(), currEnum);
		}
	}
	
	public static UserAdminTabType getType(int type) {
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