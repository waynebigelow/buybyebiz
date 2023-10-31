package ca.app.model.listing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MetaDataStatus {
	//									id		i18n
	PENDING_REVIEW			(1,		"90.status.1"),
	REJECTED						(2,		"90.status.2");
	
	private int id;
	private String i18n;
	
	MetaDataStatus(int id, String i18n){
		this.setId(id);
		this.setI18n(i18n);
	}
	
	private static final Map<Integer, MetaDataStatus> lookup = new HashMap<Integer, MetaDataStatus>();
	static {
		for (MetaDataStatus currEnum : EnumSet.allOf(MetaDataStatus.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static MetaDataStatus get(int id) {
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