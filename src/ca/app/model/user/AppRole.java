package ca.app.model.user;

import java.io.Serializable;

public class AppRole implements Serializable{

	private static final long serialVersionUID = 2274941377856942244L;
	
	private int userId;
	private int listingId;
	private int roleId;
	private int roleLevel = 0;
	private User user;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public Roles getRole() {
		return Roles.get(roleId);
	}
	
	public void setRole(Roles role) {
		this.roleId = role.getRoleId();
	}
	
	public int getRoleLevel() {
		return roleLevel;
	}
	
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + listingId;
		result = prime * result + roleId;
		result = prime * result + roleLevel;
		result = prime * result + userId;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppRole other = (AppRole) obj;
		if (listingId != other.listingId)
			return false;
		if (roleId != other.roleId)
			return false;
		if (roleLevel != other.roleLevel)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
}