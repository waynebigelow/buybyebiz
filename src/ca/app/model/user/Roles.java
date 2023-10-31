package ca.app.model.user;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Roles {
	
	//								id,				strength,		name,						label
	USER							(1, 			1,					"USER"	,					"Anonymous User"),
	ACCOUNT_OWNER		(10, 			100,				"ACCOUNT_OWNER",	"Account Owner"),
	LISTING_OWNER			(20, 			100,				"LISTING_OWNER",		"Listing Owner"),
	SUPER_ADMIN				(100, 		999,				"SUPER_ADMIN",			"Super Admin");

	private int roleId;
	private int roleStrength;
	private String roleName;
	private String roleLabel;
	
	Roles(int roleId, int roleStrength, String roleName, String roleLabel){
		this.roleId = roleId;
		this.roleStrength = roleStrength;
		this.roleName = roleName;
		this.roleLabel = roleLabel;
	}

	private static final Map<Integer, Roles> lookup = new HashMap<Integer, Roles>();
	static {
		for (Roles currEnum : EnumSet.allOf(Roles.class)) {
			lookup.put(currEnum.getRoleId(), currEnum);
		}
	}
	
	public static Roles get(int roleId) {
		return lookup.get(roleId);
	}
	
	private static final Map<String, Roles> lookup2 = new HashMap<String, Roles>();
	static {
		for (Roles currEnum : EnumSet.allOf(Roles.class)) {
			lookup2.put(currEnum.getRoleName(), currEnum);
		}
	}
	public static Roles get(String roleName) {
		return lookup2.get(roleName);
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getRoleStrength() {
		return roleStrength;
	}
	
	public void setRoleStrength(int roleStrength) {
		this.roleStrength = roleStrength;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleLabel() {
		return roleLabel;
	}

	public void setRoleLabel(String roleLabel) {
		this.roleLabel = roleLabel;
	}
}