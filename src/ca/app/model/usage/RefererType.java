package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RefererType {
	UNKNOWN					(0,			"", 					""),
	LOGIN_PAGE					(10,		"login", 				""),
	NO_REFERER					(20,		"none", 				""),
	CURRENT_LISTING		(30,		"currlisting", 		""),
	REDIRECT						(40,		"redirect", 			""),
	SUPER_ADMIN				(60,		"sadmin", 			""),
	FACEBOOK						(70,		"facebook", 		""),
	GOOGLEPLUS					(80,		"googleplus", 		""),
	TWITTER						(90,		"twitter", 			""),
	EMAIL_GENERIC			(110,		"email", 				""),
	WEB_SERVICE				(120,		"webservice",		"");
	
	private int id;
	private String source;
	private String i18nCode;
	
	RefererType(int id, String source, String i18nCode) {
		this.id = id;
		this.source = source;
		this.i18nCode = i18nCode;
	}
	
	private static final Map<Integer,RefererType> lookup = new HashMap<Integer,RefererType>();
	static {
		for (RefererType currEnum : EnumSet.allOf(RefererType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static RefererType get(int id) {
		return lookup.get(id);
	}
	
	private static final Map<String,RefererType> lookupBySource = new HashMap<String,RefererType>();
	static {
		for (RefererType currEnum : EnumSet.allOf(RefererType.class)) {
			lookupBySource.put(currEnum.getSource(), currEnum);
		}
	}
	
	public static RefererType getBySource(String source) {
		return lookupBySource.get(source);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

	public String getI18nCode() {
		return i18nCode;
	}
	
	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}
}