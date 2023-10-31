package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportChartType { 
	COLUMN_CHART		(1,			""),
	BAR_CHART			(2,			""),
	CIRCLE_CHART		(3,			"");
	
	private int id;
	private String i18nCode;
	
	ReportChartType(int id, String i18nCode) {
		this.id = id;
		this.i18nCode = i18nCode;
	}

	private static final Map<Integer,ReportChartType> lookup = new HashMap<Integer,ReportChartType>();
	static {
		for (ReportChartType currEnum : EnumSet.allOf(ReportChartType.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static ReportChartType get(int id) {
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