package ca.app.service.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum TokenFieldType {
	//									key								alias
	USER								("userKEy",						"u"),
	APPLICATION				("APpLicationKEY",			"a"),
	LISTING						("LIStingkEy",					"l"),
	PHOTO							("PHOToKey",					"ph"),
	ACTIVITY_LOG				("activiTyKey",				"al"),
	PRIMARY_ID					("PRIMaryidKey",				"p"),
	NOTIFICATION_LOG		("notificaTIONKEY",			"nl"),
	PWD_RESET					("PasswordREStkey",		"ht"),
	APP_PACKAGE				("aPPpACkageKEY",			"pk"),
	ENQUIRY						("ENQuiRykEY",				"ek"),
	ENQUIRY_POST				("eNQuiRypostkEY",			"ep"),
	PURCHASE						("PURchasEKEy",				"po"),
	LISTING_LEAD				("lisTingLEADKey",			"ll"),
	EMAIL_ADDRESS			("uSerEMAilKeY",				"ue"),
	EMAIL_CHANGE				("uSerEmailCHangeKeY",	"ec")
	;
	
	private String key;
	private String alias;
	
	private TokenFieldType(String key, String alias) {
		this.key = key;
		this.alias = alias;
	}
	
	public static final Map<String,TokenFieldType> tokenFieldTypes = new HashMap<String,TokenFieldType>();
	static {
		for (TokenFieldType currEnum : EnumSet.allOf(TokenFieldType.class)) {
			tokenFieldTypes.put(currEnum.name(), currEnum);
		}
	}
	
	public static TokenFieldType get(String name) {
		return tokenFieldTypes.get(name);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}