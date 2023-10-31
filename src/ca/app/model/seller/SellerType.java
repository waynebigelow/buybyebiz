package ca.app.model.seller;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SellerType {
	
	BUSINESS		(10),
	FRANCHISE		(20),
	PERSONAL		(20),
	REAL_ESTATE	(30),
	RESORT			(40)
	;
	
	private int typeId;
	
	SellerType(int typeId) {
		this.typeId = typeId;
	}
	
	private static final Map<Integer, SellerType> lookup = new HashMap<Integer, SellerType>();
	static {
		for (SellerType currEnum : EnumSet.allOf(SellerType.class)) {
			lookup.put(currEnum.getTypeId(), currEnum);
		}
	}
	
	public static SellerType get(int typeId) {
		return lookup.get(typeId);
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}