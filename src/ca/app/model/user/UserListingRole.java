package ca.app.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="user_listing_role")
public class UserListingRole implements Serializable{
	private static final long serialVersionUID = 2274941377856942244L;
	
	@Id
	@SequenceGenerator(name="seqUserListingRole", sequenceName="seq_user_listing_role", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqUserListingRole")
	@Column(name="user_listing_role_id", unique=true)
	private int userListingRoleId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="application_id")
	private int applicationId;
	
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
		UserListingRole other = (UserListingRole) obj;
		return true;
	}
}