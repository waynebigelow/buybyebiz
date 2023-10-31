package ca.app.model.photo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PhotoStatus {
	//									id		i18n
	PLACEHOLDER				(5,		"90.status.5"),
	PENDING_REVIEW			(1,		"90.status.1"),
	REJECTED						(2,		"90.status.2"),
	APPROVED					(3,		"90.status.3"),
	DELETED						(4,		"90.status.4");
	
	private int id;
	private String i18n;
	
	PhotoStatus(int id, String i18n){
		this.setId(id);
		this.setI18n(i18n);
	}
	
	private static final Map<Integer, PhotoStatus> lookup = new HashMap<Integer, PhotoStatus>();
	static {
		for (PhotoStatus currEnum : EnumSet.allOf(PhotoStatus.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static PhotoStatus get(int id) {
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