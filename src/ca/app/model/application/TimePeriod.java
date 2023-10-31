package ca.app.model.application;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TimePeriod {
	//				id			i18n
	DAY			(1,			"105.time.period.1"),
	MONTH		(2,			"105.time.period.2"),
	YEAR			(3,			"105.time.period.3")
	;
	
	private int id;
	private String i18n;
	
	TimePeriod(int id, String i18n) {
		this.id = id;
		this.i18n = i18n;
	}
	
	private static final Map<Integer,TimePeriod> lookup = new HashMap<Integer,TimePeriod>();
	static {
		for (TimePeriod currEnum : EnumSet.allOf(TimePeriod.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static TimePeriod get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}