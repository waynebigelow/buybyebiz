package ca.app.model.application;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ApplicationType {
	
	//							id			i18n
	BUSINESS				(10,		"35.type.10"),
	FRANCHISE			(20,		"35.type.20"),
	PERSONAL				(30,		"35.type.30"),
	REAL_ESTATE		(40,		"35.type.40"),
	RESORT					(50,		"35.type.50"),
	;
	
	private int id;
	private String i18n;
	
	ApplicationType(int id, String i18n) {
		this.id = id;
		this.i18n = i18n;
	}
	
	private static final Map<Integer, ApplicationType> lookup = new HashMap<Integer, ApplicationType>();
	static {
		for (ApplicationType currEnum : EnumSet.allOf(ApplicationType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static ApplicationType get(int typeId) {
		return lookup.get(typeId);
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