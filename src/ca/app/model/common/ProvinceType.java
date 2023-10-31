package ca.app.model.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum ProvinceType {
	// Got the French translations from here: http://www.canadapost.ca/tools/pg/manual/PGaddress-e.asp
	//		id			sn			lngEng									lngFre									zoom
	AB	(1,			"AB",		"Alberta",								"Alberta",								"5"),
	BC		(2,			"BC",		"British Columbia",					"Colombie-Britannique",			"5"),
	MB	(3,			"MB",		"Manitoba",								"Manitoba",								"5"),
	NB	(4,			"NB",		"New Brunswick",						"Nouveau-Brunswick",				"7"),
	NL		(5,			"NL",		"Newfoundland and Labrador",	"Terre-Neuve-et-Labrador",		"5"),
	NS	(6,			"NS",		"Nova Scotia",							"Nouvelle-Écosse",					"7"),
	NT	(7,			"NT",		"Northwest Territories",			"Territoires du Nord-Ouest ",		"5"),
	NU	(8,			"NU",		"Nunavut",								"Nunavut",								"4"),
	ON	(9,			"ON",		"Ontario",								"Ontario",								"5"),
	PE		(10,		"PE",		"Prince Edward Island",				"Île-du-Prince-Édouard",			"8"),
	QC	(11,		"QC",		"Quebec",								"Québec",								"5"),
	SK		(12,		"SK",		"Saskatchewan",						"Saskatchewan",						"5"),
	YT		(13,		"YT",		"Yukon",									"Yukon",									"5")
	;
	
	private int id;
	private String shortName;
	private String longNameEng;
	private String longNameFre;
	private String mapZoom;
	
	ProvinceType(int id, String shortName, String longNameEng, String longNameFre, String mapZoom) {
		this.id = id;
		this.shortName = shortName;
		this.longNameEng = longNameEng;
		this.longNameFre = longNameFre;
		this.mapZoom = mapZoom;
	}
	
	private static final Map<String,ProvinceType> lookup = new HashMap<String,ProvinceType>();
	static {
		for (ProvinceType currEnum : EnumSet.allOf(ProvinceType.class)) {
			lookup.put(currEnum.getShortName(), currEnum);
		}
	}
	
	public static ProvinceType get(String shortName) {
		return lookup.get(shortName);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongNameEng() {
		return longNameEng;
	}
	public void setLongNameEng(String longNameEng) {
		this.longNameEng = longNameEng;
	}
	
	public String getLongNameFre() {
		return longNameFre;
	}
	public void setLongNameFre(String longNameFre) {
		this.longNameFre = longNameFre;
	}
	
	public String getLongName(Locale locale) {
		if ("fr".equalsIgnoreCase(locale.getLanguage())) {
			return getLongNameFre();
		}
		return getLongNameEng();
	}

	public String getMapZoom() {
		return mapZoom;
	}
	public void setMapZoom(String mapZoom) {
		this.mapZoom = mapZoom;
	}
}