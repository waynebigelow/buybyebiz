package ca.app.model.user;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserAgreementType {
	
	//							id			version,			i18n
	TERMS_OF_USE		(1,			1,					""),
	PRIVACY_POLICY		(2,			1,					"");
	
	private int id;
	private int version;
	private String i18n;
	
	UserAgreementType(int id, int version, String i18n) {
		this.id = id;
		this.version = version;
		this.i18n = i18n;
	}
	
	private static final Map<Integer,UserAgreementType> lookup = new HashMap<Integer,UserAgreementType>();
	static {
		for (UserAgreementType currEnum : EnumSet.allOf(UserAgreementType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static UserAgreementType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}