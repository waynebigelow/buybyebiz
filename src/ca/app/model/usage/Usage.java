package ca.app.model.usage;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Usage {
	LOGIN									(10,			Area.SECURITY,						Action.LOGIN),
	CHANGE_PWD_FAILED			(20,			Area.SECURITY,						Action.CHANGE_PWD),
	FORGOT_PWD						(30,			Area.SECURITY,						Action.FORGOT_PWD),
	CHANGE_EMAIL						(35,			Area.SECURITY,						Action.CHANGE_EMAIL),
	DENIED									(110,			Area.SECURITY,						Action.ACCESS_FORBIDDEN),
	APP_ADMIN							(40,			Area.APP_ADMIN,					Action.VIEW),
	USER_ADMIN							(50,			Area.USER_ADMIN,				Action.VIEW),
	USER_ACCOUNT						(60,			Area.USER_ACCOUNT,				Action.VIEW),
	LISTING_PREVIEW				(70,			Area.LISTING,						Action.VIEW),
	SEARCH									(80,			Area.SEARCH,							Action.VIEW),
	SITE										(90,			Area.SITE,								Action.VIEW),	
	HOME									(100,			Area.HOME,							Action.VIEW)
	;
	
	private int id;
	private Area area;
	private Action action;
	
	Usage(int id, Area area, Action action) {
		this.id = id;
		this.area = area;
		this.action = action;
	}

	private static final Map<Integer,Usage> lookup = new HashMap<Integer,Usage>();
	static {
		for (Usage currEnum : EnumSet.allOf(Usage.class)) {
			lookup.put(currEnum.getId(), currEnum);
		}
	}
	
	public static Usage get(int id) {
		return lookup.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Area getArea() {
		return area;
	}
	
	public void setArea(Area area) {
		this.area = area;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
}