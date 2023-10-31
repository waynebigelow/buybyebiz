package ca.app.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserListingRolePK implements Serializable {
	private static final long serialVersionUID = 2274941377856942244L;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="application_id")
	private int applicationId;
	
	public UserListingRolePK() {}
	public UserListingRolePK(int userId, int listingId, int roleId, int applicationId) {
		this.userId = userId;
		this.listingId = listingId;
		this.roleId = roleId;
		this.applicationId = applicationId;
	}
	
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

	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + listingId;
		result = prime * result + roleId;
		result = prime * result + userId;
		result = prime * result + applicationId;
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
		UserListingRolePK other = (UserListingRolePK) obj;
		if (listingId != other.listingId)
			return false;
		if (roleId != other.roleId)
			return false;
		if (userId != other.userId)
			return false;
		if (applicationId != other.applicationId)
			return false;
		return true;
	}
}