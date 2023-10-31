package ca.app.model.user;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserType {
	
	BUYER			(10),
	SELLER			(20)
	;
	
	private int typeId;
	
	UserType(int typeId) {
		this.typeId = typeId;
	}
	
	private static final Map<Integer, UserType> lookup = new HashMap<Integer, UserType>();
	static {
		for (UserType currEnum : EnumSet.allOf(UserType.class)) {
			lookup.put(currEnum.getTypeId(), currEnum);
		}
	}
	
	public static UserType get(int typeId) {
		return lookup.get(typeId);
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}