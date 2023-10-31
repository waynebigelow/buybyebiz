package ca.app.model.listing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PropertyType {
	//						id		i18n
	OWNED				(1,		"20.property.1"),
	LEASED				(2,		"20.property.2"),
	;
	
	private int id;
	private String i18n;
	
	PropertyType(int id, String i18n){
		this.id = id;
		this.i18n = i18n;
	}
	
	private static final Map<Integer, PropertyType> lookup = new HashMap<Integer, PropertyType>();
	static {
		for (PropertyType currEnum : EnumSet.allOf(PropertyType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static PropertyType get(int id) {
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
}