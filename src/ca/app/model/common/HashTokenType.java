package ca.app.model.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum HashTokenType {
	//							id
	PWD_RESET			(1)
	;
	
	private int id;
	
	HashTokenType(int id) {
		this.id = id;
	}
	
	private static final Map<Integer, HashTokenType> lookup = new HashMap<Integer, HashTokenType>();
	static {
		for (HashTokenType currEnum : EnumSet.allOf(HashTokenType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static HashTokenType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}