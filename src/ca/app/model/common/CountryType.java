package ca.app.model.common;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.app.util.ProjectUtil;

public enum CountryType {
	// Source: http://www.columbia.edu/~fdc/postal/#index
	//		id			sn			lng				zoom		currencyType
	CA		(39,		"CA",		"Canada",		"3",		CurrencyType.CAD),
	US	(239,		"US",		"USA",			"4",		CurrencyType.USD);
	
//	AF		(1,			"AF",		"AFGHANISTAN"),
//	AL		(2,			"AL",		"ALBANIA"),
//	DZ		(4,			"DZ",		"ALGERIA"),
//	AS		(5,			"AS",		"AMERICAN SAMOA"),
//	AD		(6,			"AD",		"ANDORRA"),
//	AO		(7,			"AO",		"ANGOLA"),
//	AI		(8,			"AI",		"ANGUILLA"),
//	AG		(9,			"AG",		"ANTIGUA AND BARBUDA"),
//	AR		(10,		"AR",		"ARGENTINA"),
//	AM	(11,		"AM",		"ARMENIA"),
//	AW	(12,		"AW",		"ARUBA"),
//	AC		(13,		"AC",		"ASCENSION"),
//	AU		(14,		"AU",		"AUSTRALIA"),
//	AT		(15,		"AT",		"AUSTRIA"),
//	AZ		(16,		"AZ",		"AZERBAIJAN"),
//	BS		(17,		"BS",		"BAHAMAS"),
//	BH		(18,		"BH",		"BAHRAIN"),
//	BD		(19,		"BD",		"BANGLADESH"),
//	BB		(20,		"BB",		"BARBADOS"),
//	BY		(21,		"BY",		"BELARUS"),
//	BE		(22,		"BE",		"BELGIUM"),
//	BZ		(23,		"BZ",		"BELIZE"),
//	BJ		(24,		"BJ",		"BENIN"),
//	BM	(25,		"BM",		"BERMUDA"),
//	BT		(26,		"BT",		"BHUTAN"),
//	BO		(27,		"BO",		"BOLIVIA"),
//	BQ		(28,		"BQ",		"BONAIRE"),
//	BA		(29,		"BA",		"BOSNIA-HERZEGOVINA"),
//	BW	(30,		"BW",		"BOTSWANA"),
//	BR		(31,		"BR",		"BRAZIL"),
//	VG		(32,		"VG",		"BRITISH VIRGIN ISLANDS"),
//	BN		(33,		"BN",		"BRUNEI DARRUSALAM"),
//	BG		(34,		"BG",		"BULGARIA"),
//	BF		(35,		"BF",		"BURKINA FASO"),
//	BI		(36,		"BI",		"BURUNDI"),
//	KH		(37,		"KH",		"CAMBODIA"),
//	CM	(38,		"CM",		"CAMEROON"),
//	CV		(41,		"CV",		"CAPE VERDE"),
//	KY		(42,		"KY",		"CAYMAN ISLANDS"),
//	CF		(43,		"CF",		"CENTRAL AFRICAN REPUBLIC"),
//	TD		(44,		"TD",		"CHAD"),
//	GB1	(45,		"GB",		"CHANNEL ISLANDS"),
//	CL		(46,		"CL",		"CHILE"),
//	CN		(47,		"CN",		"CHINA"),
//	CO		(48,		"CO",		"COLOMBIA"),
//	KM	(49,		"KM",		"COMOROS"),
//	CR		(51,		"CR",		"COSTA RICA"),
//	CI		(52,		"CI",		"CÔTE D'IVOIRE"),
//	HR		(53,		"HR",		"CROATIA"),
//	CU		(54,		"CU",		"CUBA"),
//	CW	(55,		"CW",		"CURACAO"),
//	CY		(56,		"CY",		"CYPRUS"),
//	CZ		(57,		"CZ",		"CZECH REPUBLIC"),
//	CD		(58,		"CD",		"DEMOCRATIC REPUBLIC OF THE CONGO"),
//	DK		(59,		"DK",		"DENMARK"),
//	DJ		(60,		"DJ",		"DJIBOUTI"),
//	DM	(61,		"DM",		"DOMINICA"),
//	DO	(62,		"DO",		"DOMINICAN REPUBLIC"),
//	TL		(63,		"TL",		"EAST TIMOR"),
//	EC		(64,		"EC",		"ECUADOR"),
//	EG		(65,		"EG",		"EGYPT"),
//	SV		(66,		"SV",		"EL SALVADOR"),
//	GB2	(67,		"GB",		"ENGLAND"),
//	GQ	(68,		"GQ",		"EQUATORIAL GUINEA"),
//	ER		(69,		"ER",		"ERITREA"),
//	EE		(70,		"EE",		"ESTONIA"),
//	ET		(71,		"ET",		"ETHIOPIA"),
//	FK		(72,		"FK",		"FALKLAND ISLANDS"),
//	FO		(73,		"FO",		"FAROE ISLANDS"),
//	FJ		(74,		"FJ",		"FIJI"),
//	FI		(75,		"FI",		"FINLAND"),
//	FR		(76,		"FR",		"FRANCE"),
//	GF		(77,		"GF",		"FRENCH GUIANA"),
//	PF		(78,		"PF",		"FRENCH POLYNESIA"),
//	GA		(79,		"GA",		"GABON"),
//	GM	(80,		"GM",		"GAMBIA"),
//	GE		(81,		"GE",		"GEORGIA"),
//	DE		(82,		"DE",		"GERMANY"),
//	GH	(83,		"GH",		"GHANA"),
//	GI		(84,		"GI",		"GIBRALTAR"),
//	GR		(85,		"GR",		"GREECE"),
//	GL		(86,		"GL",		"GREENLAND"),
//	GD	(87,		"GD",		"GRENADA"),
//	GP		(88,		"GP",		"GUADELOUPE"),
//	GU	(89,		"GU",		"GUAM"),
//	GT		(90,		"GT",		"GUATEMALA"),
//	GG	(91,		"GG",		"GUERNSEY"),
//	GN	(92,		"GN",		"GUINEA"),
//	GW	(93,		"GW",	"GUINEA-BISSAU"),
//	GY		(94,		"GY",		"GUYANA"),
//	HT		(95,		"HT",		"HAITI"),
//	HN	(96,		"HN",		"HONDURAS"),
//	HK		(97,		"HK",		"HONG KONG"),
//	HU	(98,		"HU",		"HUNGARY"),
//	IS		(99,		"IS",		"ICELAND"),
//	IN		(100,		"IN",		"INDIA"),
//	ID		(101,		"ID",		"INDONESIA"),
//	IR		(102,		"IR",		"IRAN"),
//	IQ		(103,		"IQ",		"IRAQ"),
//	IE		(104,		"IE",		"IRELAND"),
//	IM		(105,		"IM",		"ISLE OF MAN"),
//	IL		(106,		"IL",		"ISRAEL"),
//	IT		(107,		"IT",		"ITALY"),
//	JM		(108,		"JM",		"JAMAICA"),
//	JP		(109,		"JP",		"JAPAN"),
//	JE		(110,		"JE",		"JERSEY"),
//	JO		(111,		"JO",		"JORDAN"),
//	KZ		(112,		"KZ",		"KAZAKHSTAN"),
//	KE		(113,		"KE",		"KENYA"),
//	KI		(114,		"KI",		"KIRIBATI"),
//	KR		(115,		"KR",		"KOREA"),
//	KW	(116,		"KW",		"KUWAIT"),
//	KG		(117,		"KG",		"KYRGYZSTAN"),
//	LA		(118,		"LA",		"LAOS"),
//	LV		(119,		"LV",		"LATVIA"),
//	LB		(120,		"LB",		"LEBANON"),
//	LS		(121,		"LS",		"LESOTHO"),
//	LR		(122,		"LR",		"LIBERIA"),
//	LY		(123,		"LY",		"LIBYA"),
//	LI		(124,		"LI",		"LIECHTENSTEIN"),
//	LT		(125,		"LT",		"LITHUANIA"),
//	LU		(126,		"LU",		"LUXEMBOURG"),
//	MO	(127,		"MO",		"MACAO"),
//	MK	(128,		"MK",		"MACEDONIA"),
//	MG	(129,		"MG",		"MADAGASCAR"),
//	MW	(130,		"MW",	"MALAWI"),
//	MY		(131,		"MY",		"MALAYSIA"),
//	MV	(132,		"MV",		"MALDIVES"),
//	ML		(133,		"ML",		"MALI"),
//	MT		(134,		"MT",		"MALTA"),
//	MH	(135,		"MH",		"MARSHALL ISLANDS"),
//	MQ	(136,		"MQ",		"MARTINIQUE"),
//	MR	(137,		"MR",		"MAURITANIA"),
//	MU	(138,		"MU",		"MAURITIUS"),
//	YT		(139,		"YT",		"MAYOTTE"),
//	MX	(140,		"MX",		"MEXICO"),
//	FM		(141,		"FM",		"MICRONESIA"),
//	MD	(142,		"MD",		"MOLDOVA"),
//	MC	(143,		"MC",		"MONACO"),
//	MN	(144,		"MN",		"MONGOLIA"),
//	ME		(145,		"ME",		"MONTENEGRO"),
//	MS	(146,		"MS",		"MONTSERRAT"),
//	MA	(147,		"MA",		"MOROCCO"),
//	MZ	(148,		"MZ",		"MOZAMBIQUE"),
//	MM	(149,		"MM",		"MYANMAR"),
//	NA		(150,		"NA",		"NAMIBIA"),
//	NR		(151,		"NR",		"NAURU"),
//	NP		(152,		"NP",		"NEPAL"),
//	NL		(153,		"NL",		"NETHERLANDS"),
//	AN		(154,		"AN",		"NETHERLANDS ANTILLES"),
//	NC		(155,		"NC",		"NEW CALEDONIA"),
//	NZ		(156,		"NZ",		"NEW ZEALAND"),
//	NI		(157,		"NI",		"NICARAGUA"),
//	NE		(158,		"NE",		"NIGER"),
//	NG	(159,		"NG",		"NIGERIA"),
//	KP		(160,		"KP",		"NORTH KOREA"),
//	GB3	(161,		"GB",		"NORTHERN IRELAND"),
//	MP		(162,		"MP",		"NORTHERN MARIANA ISLANDS"),
//	NO	(163,		"NO",		"NORWAY"),
//	OM	(164,		"OM",		"OMAN"),
//	AS2	(165,		"AS",		"PAGO PAGO"),
//	PK		(166,		"PK",		"PAKISTAN"),
//	PW	(167,		"PW",		"PALAU"),
//	PS		(168,		"PS",		"PALESTINIAN TERRITORY"),
//	PA		(169,		"PA",		"PANAMA"),
//	PG		(170,		"PG",		"PAPUA NEW GUINEA"),
//	PY		(171,		"PY",		"PARAGUAY"),
//	PE		(172,		"PE",		"PERU"),
//	PH		(173,		"PH",		"PHILIPPINES"),
//	PN		(174,		"PN",		"PITCAIRN ISLAND"),
//	PL		(175,		"PL",		"POLAND"),
//	PT		(176,		"PT",		"PORTUGAL"),
//	PR		(177,		"PR",		"PUERTO RICO"),
//	QA		(178,		"QA",		"QATAR"),
//	CG		(179,		"CG",		"REPUBLIC OF THE CONGO"),
//	RE		(180,		"RE",		"REUNION"),
//	RO		(181,		"RO",		"ROMANIA"),
//	RU		(182,		"RU",		"RUSSIA"),
//	RW	(183,		"RW",		"RWANDA"),
//	VI2	(184,		"VI",		"SAINT CROIX"),
//	SH		(185,		"SH",		"SAINT HELENA"),
//	VI3	(186,		"VI",		"SAINT JOHN"),
//	KN		(187,		"KN",		"SAINT KITTS AND NEVIS"),
//	LC		(188,		"LC",		"SAINT LUCIA"),
//	SX		(189,		"SX",		"SAINT MAARTEN"),
//	PM		(190,		"PM",		"SAINT PIERRE AND MIQUELON"),
//	VI4	(191,		"VI",		"SAINT THOMAS"),
//	VC		(192,		"VC",		"SAINT VINCENT AND THE GRENADINES"),
//	SM	(193,		"SM",		"SAN MARINO"),
//	ST		(194,		"ST",		"SAO TOME AND PRINCIPE"),
//	SA		(196,		"SA",		"SAUDI ARABIA"),
//	GB4	(197,		"GB",		"SCOTLAND"),
//	SN		(198,		"SN",		"SENEGAL"),
//	RS		(199,		"RS",		"SERBIA"),
//	SC		(200,		"SC",		"SEYCHELLES"),
//	SL		(201,		"SL",		"SIERRA LEONE"),
//	SG		(202,		"SG",		"SINGAPORE"),
//	SK		(203,		"SK",		"SLOVAK REPUBLIC"),
//	SI		(204,		"SI",		"SLOVENIA"),
//	SB		(205,		"SB",		"SOLOMON ISLANDS"),
//	SO		(206,		"SO",		"SOMALIA"),
//	ZA		(207,		"ZA",		"SOUTH AFRICA"),
//	GS		(208,		"GS",		"SOUTH GEORGIA"),
//	ES		(210,		"ES",		"SPAIN"),
//	LK		(211,		"LK",		"SRI LANKA"),
//	SD		(212,		"SD",		"SUDAN"),
//	SR		(213,		"SR",		"SURINAME"),
//	SZ		(214,		"SZ",		"SWAZILAND"),
//	SE		(215,		"SE",		"SWEDEN"),
//	CH		(216,		"CH",		"SWITZERLAND"),
//	SY		(217,		"SY",		"SYRIA"),
//	TW	(218,		"TW",		"TAIWAN"),
//	TJ		(219,		"TJ",		"TAJIKISTAN"),
//	TZ		(220,		"TZ",		"TANZANIA"),
//	TH		(221,		"TH",		"THAILAND"),
//	MP2	(222,		"MP",		"TINIAN"),
//	TG		(223,		"TG",		"TOGO"),
//	TO		(224,		"TO",		"TONGA"),
//	TT		(225,		"TT",		"TRINIDAD AND TOBAGO"),
//	FM2	(226,		"FM",		"TRUK"),
//	TN		(227,		"TN",		"TUNISIA"),
//	TR		(228,		"TR",		"TURKEY"),
//	TM		(230,		"TM",		"TURKMENISTAN"),
//	TC		(231,		"TC",		"TURKS AND CAICOS ISLANDS"),
//	TV		(232,		"TV",		"TUVALU"),
//	UG	(233,		"UG",		"UGANDA"),
//	UA		(234,		"UA",		"UKRAINE"),
//	AE		(235,		"AE",		"UNITED ARAB EMIRATES"),
//	GB		(236,		"GB",		"UNITED KINGDOM"),
//	UY		(237,		"UY",		"URUGUAY"),
//	VI		(238,		"VI",		"US VIRGIN ISLANDS"),
//	SU		(240,		"SU",		"USSR"),
//	UZ		(241,		"UZ",		"UZBEKISTAN"),
//	VU		(242,		"VU",		"VANUATU"),
//	VA		(243,		"VA",		"VATICAN CITY"),
//	VE		(244,		"VE",		"VENEZUELA"),
//	VN		(245,		"VN",		"VIETNAM"),
//	GB5	(246,		"GB",		"WALES"),
//	WF	(247,		"WF",		"WALLIS AND FORTUNA ISLANDS"),
//	WS	(248,		"WS",		"WESTERN SAMOA"),
//	EH		(249,		"EH",		"WESTERN SAHARA"),
//	YE		(250,		"YE",		"YEMEN"),
//	YU		(251,		"YU",		"YUGOSLAVIA"),
//	ZW	(253,		"ZW",		"ZIMBABWE")
	
	private int id;
	private String shortName;
	private String longName;
	private String mapZoom;
	private CurrencyType currencyType;
	
	CountryType(int id, String shortName, String longName, String mapZoom, CurrencyType currencyType) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.mapZoom = mapZoom;
		this.currencyType = currencyType;
	}
	
	private static final Map<String, CountryType> lookup = new HashMap<String, CountryType>();
	static {
		for (CountryType currEnum : EnumSet.allOf(CountryType.class)) {
			lookup.put(currEnum.getLongName(), currEnum);
		}
	}
	
	public static CountryType get(String longName) {
		return lookup.get(longName);
	}
	
	private static final Map<String, CountryType> lookupByShortName = new HashMap<String, CountryType>();
	static {
		for (CountryType currEnum : EnumSet.allOf(CountryType.class)) {
			lookupByShortName.put(currEnum.getShortName(), currEnum);
		}
	}
	
	public static CountryType getByShortName(String shortName) {
		return lookupByShortName.get(shortName);
	}
	
	public static List<CountryType> getTypes() {
		List<CountryType> list = new ArrayList<CountryType>();
		String[] activeCountries = ProjectUtil.getProperty("active.countries").split(",");
		for (CountryType type : CountryType.values()) {
			for (String activeCountry : activeCountries) {
				if (type.getShortName().equals(activeCountry)) {
					list.add(type);
				}
			}
		}
		
		return list;
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

	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getMapZoom() {
		return mapZoom;
	}
	public void setMapZoom(String mapZoom) {
		this.mapZoom = mapZoom;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
}