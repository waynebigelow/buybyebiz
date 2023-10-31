package ca.app.model.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CurrencyType {
	//			id				country,												sn
	CAD		(10,			CountryType.CA.getShortName(),			"CAD"),
	USD		(20,			CountryType.US.getShortName(),			"USD");
	
	private int id;
	private String country;
	private String shortName;
	
	CurrencyType(int id, String country, String shortName) {
		this.id = id;
		this.country = country;
		this.shortName = shortName;
	}
	
	private static final Map<Integer,CurrencyType> lookup = new HashMap<Integer,CurrencyType>();
	static {
		for (CurrencyType currEnum : EnumSet.allOf(CurrencyType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static CurrencyType get(int id) {
		return lookup.get(id);
	}
	
	private static final Map<String,CurrencyType> lookupByShortName = new HashMap<String,CurrencyType>();
	static {
		for (CurrencyType currEnum : EnumSet.allOf(CurrencyType.class)) {
			lookupByShortName.put(currEnum.getShortName(), currEnum);
		}
	}
	
	public static CurrencyType getByShortName(String shortName) {
		return lookupByShortName.get(shortName);
	}
	
	private static final Map<String,CurrencyType> lookupByCountryShortName = new HashMap<String,CurrencyType>();
	static {
		for (CurrencyType currEnum : EnumSet.allOf(CurrencyType.class)) {
			lookupByCountryShortName.put(currEnum.getCountry(), currEnum);
		}
	}
	
	public static CurrencyType getByCountryShortName(String shortName) {
		return lookupByCountryShortName.get(shortName);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}