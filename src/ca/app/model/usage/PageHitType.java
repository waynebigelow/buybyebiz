package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PageHitType { 
	ENTRY			(1,			""),
	REGULAR		(2,			""),
	EXIT				(3,			"");

	private int id;
	private String i18nCode;
	
	PageHitType(int id, String i18nCode) {
		this.id = id;
		this.i18nCode = i18nCode;
	}

	private static final Map<Integer,PageHitType> lookup = new HashMap<Integer,PageHitType>();
	static {
		for (PageHitType currEnum : EnumSet.allOf(PageHitType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static PageHitType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getI18nCode() {
		return i18nCode;
	}
	
	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}
}