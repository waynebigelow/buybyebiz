package ca.app.model.application;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ModuleType {
	//										id
	ADVERTISER_MODULE		(10),
	SELLER_MODULE				(20),
	BUYER_MODULE				(30),
	FRANCHISE_MODULE			(40)
	;

	private int moduleId;
	
	ModuleType (int moduleId) {
		this.moduleId = moduleId;
	}
	
	private static final Map<Integer, ModuleType> lookup = new HashMap<Integer, ModuleType>();
	static {
		for (ModuleType currEnum : EnumSet.allOf(ModuleType.class)) {
			lookup.put(currEnum.getModuleId(), currEnum);
		}
	}
	
	public static ModuleType get(int id) {
		return lookup.get(id);
	}

	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
}