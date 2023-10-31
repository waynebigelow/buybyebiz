package ca.app.model.application;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Provider {
	//							id,			fieldName
	PAYPAL					(10,		"os0")
	;

	private int providerId;
	private String fieldName;
	
	Provider (int providerId, String fieldName) {
		this.providerId = providerId;
		this.fieldName = fieldName;
	}
	
	private static final Map<Integer, Provider> lookup = new HashMap<Integer, Provider>();
	static {
		for (Provider currEnum : EnumSet.allOf(Provider.class)) {
			lookup.put(currEnum.getProviderId(), currEnum);
		}
	}
	
	public static Provider get(int id) {
		return lookup.get(id);
	}

	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}