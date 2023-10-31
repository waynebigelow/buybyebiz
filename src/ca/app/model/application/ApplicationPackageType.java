package ca.app.model.application;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ApplicationPackageType {
	//						id
	TRIAL				(1),
	EXTENSION		(2);
	
	private int id;
	
	ApplicationPackageType(int id) {
		this.id = id;
	}
	
	private static final Map<Integer,ApplicationPackageType> lookup = new HashMap<Integer, ApplicationPackageType>();
	static {
		for (ApplicationPackageType currEnum : EnumSet.allOf(ApplicationPackageType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	public static ApplicationPackageType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}