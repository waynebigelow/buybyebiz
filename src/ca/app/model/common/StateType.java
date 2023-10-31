package ca.app.model.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum StateType {
	// Got the French translations from here: http://www.canadapost.ca/tools/pg/manual/PGaddress-e.asp#1413027
	//		id			sn			lngEng							lngFre							zoom
	AL		(1,			"AL",		"Alabama",						"Alabama",						"7"),
	AK	(2,			"AK",		"Alaska",						"Alaska",						"4"),
	AZ		(3,			"AZ",		"Arizona",						"Arizona",						"6"),
	AR	(4,			"AR",		"Arkansas",						"Arkansas",						"7"),
	CA		(5,			"CA",		"California",					"Californie",					"5"),
	CO	(6,			"CO",		"Colorado",						"Colorado",						"7"),
	CT		(7,			"CT",		"Connecticut",					"Connecticut",					"8"),
	DE	(8,			"DE",		"Delaware",					"Delaware",					"8"),
	DC	(9,			"DC",		"District of Columbia",		"District de Columbia",		"9"),
	FL		(10,		"FL",		"Florida",						"Floride",						"6"),
	GA	(11,		"GA",		"Georgia",						"Georgie",						"7"),
	HI		(12,		"HI",		"Hawaii",						"Hawaii",						"7"),
	ID		(13,		"ID",		"Idaho",							"Idaho",							"6"),
	IL		(14,		"IL",		"Illinois",						"Illinois",						"7"),
	IN		(15,		"IN",		"Indiana",						"Indiana",						"7"),
	IA		(16,		"IA",		"Iowa",							"Iowa",							"7"),
	KS		(17,		"KS",		"Kansas",						"Kansas",						"7"),
	KY	(18,		"KY",		"Kentucky",						"Kentucky",						"7"),
	LA		(19,		"LA",		"Louisiana",						"Louisiane",						"7"),
	ME	(20,		"ME",		"Maine",							"Maine",							"7"),
	MD	(21,		"MD",		"Maryland",						"Maryland",						"8"),
	MA	(22,		"MA",		"Massachusetts",				"Massachusetts",				"8"),
	MI		(23,		"MI",		"Michigan",						"Michigan",						"7"),
	MN	(24,		"MN",		"Minnesota",					"Minnesota",					"6"),
	MS	(25,		"MS",		"Mississippi",					"Mississippi",					"7"),
	MO	(26,		"MO",		"Missouri",						"Missouri",						"7"),
	MT	(27,		"MT",		"Montana",						"Montana",						"6"),
	NE	(28,		"NE",		"Nebraska",					"Nebraska",					"6"),
	NV	(29,		"NV",		"Nevada",						"Nevada",						"6"),
	NH	(30,		"NH",		"New Hampshire",			"New Hampshire",			"7"),
	NJ		(31,		"NJ",		"New Jersey",					"New Jersey",					"8"),
	NM	(32,		"NM",		"New Mexico",					"Nouveau Mexique",			"6"),
	NY	(33,		"NY",		"New York",					"New York",					"8"),
	NC	(34,		"NC",		"North Carolina",				"Caroline du Nord",			"7"),
	ND	(35,		"ND",		"North Dakota",				"Dakota du Nord",			"7"),
	OH	(36,		"OH",		"Ohio",							"Ohio",							"7"),
	OK	(37,		"OK",		"Oklahoma",					"Oklahoma",					"6"),
	OR	(38,		"OR",		"Oregon",						"Oregon",						"6"),
	PA	(39,		"PA",		"Pennsylvania",				"Pennsylvanie",				"7"),
	PR	(40,		"PR",		"Puerto Rico",					"Puerto Rico",					"7"),
	RI		(41,		"RI",		"Rhode Island",				"Rhode Island",				"9"),
	SC		(42,		"SC",		"South Carolina",				"Caroline du Sud",			"7"),
	SD	(43,		"SD",		"South Dakota",				"Dakota du Sud",				"6"),
	TN	(44,		"TN",		"Tennessee",					"Tennessee",					"7"),
	TX		(45,		"TX",		"Texas",							"Texas",							"6"),
	UT	(46,		"UT",		"Utah",							"Utah",							"7"),
	VT		(47,		"VT",		"Vermont",						"Vermont",						"7"),
	VA	(48,		"VA",		"Virginia",						"Virginie",						"7"),
	VI		(49,		"VI",		"Virgin Islands",				"Îles Vierges",					"7"),
	WA	(50,		"WA",		"Washington",					"Washington",					"7"),
	WV	(51,		"WV",		"West Virginia",				"Virginie de l’Ouest",			"7"),
	WI	(52,		"WI",		"Wisconsin",					"Wisconsin",					"6"),
	WY	(53,		"WY",		"Wyoming",						"Wyoming",						"7")
	;
	
	private int id;
	private String shortName;
	private String longNameEng;
	private String longNameFre;
	private String mapZoom;
	
	StateType(int id, String shortName, String longNameEng, String longNameFre, String mapZoom) {
		this.id = id;
		this.shortName = shortName;
		this.longNameEng = longNameEng;
		this.longNameFre = longNameFre;
		this.mapZoom = mapZoom;
	}
	
	private static final Map<String,StateType> lookup = new HashMap<String,StateType>();
	static {
		for (StateType currEnum : EnumSet.allOf(StateType.class)) {
			lookup.put(currEnum.getShortName(), currEnum);
		}
	}
	
	public static StateType get(String shortName) {
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