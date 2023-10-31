package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportMetricType { 
	PAGE_HITS				(1,			""),
	SESSIONS				(2,			""),
	IP_ADDRESSES		(3,			"");
	
	private int id;
	private String i18nCode;
	
	ReportMetricType(int id, String i18nCode) {
		this.id = id;
		this.i18nCode = i18nCode;
	}

	private static final Map<Integer,ReportMetricType> lookup = new HashMap<Integer,ReportMetricType>();
	static {
		for (ReportMetricType currEnum : EnumSet.allOf(ReportMetricType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static ReportMetricType get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getI18nCode() {
		return i18nCode;
	}
	
	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}
}