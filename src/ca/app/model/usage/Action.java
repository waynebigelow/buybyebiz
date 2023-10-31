package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Action {
	ACCESS_FORBIDDEN		(150,		"140.action.150"),
	CHANGE_EMAIL				(145,		"140.action.145"),
	CHANGE_PWD				(130,		"140.action.130"),
	FORGOT_PWD				(140,		"140.action.140"),
	LOGIN							(110,		"140.action.110"),
	VIEW							(10,		"140.action.10");

	private int id;
	private String i18n;
	
	Action(int id, String i18n) {
		this.id = id;
		this.i18n = i18n;
	}

	private static final Map<Integer,Action> lookup = new HashMap<Integer,Action>();
	static {
		for (Action currEnum : EnumSet.allOf(Action.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static Action get(int id) {
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