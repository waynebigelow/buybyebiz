package ca.app.model.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AddressType {
	//				id
	USER			(1), 
	LISTING		(2)
	;
	
	private int id;
	
	AddressType(int id) {
		this.id = id;
	}
	
	private static final Map<Integer, AddressType> lookup = new HashMap<Integer, AddressType>();
	static {
		for (AddressType currEnum : EnumSet.allOf(AddressType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static AddressType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}