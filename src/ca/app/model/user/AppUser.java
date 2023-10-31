package ca.app.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AppUser extends User implements Serializable{

	private static final long serialVersionUID = -5071969746558921752L;

	public AppUser(String username, String password, boolean enabled, Collection<GrantedAuthority> authorities, String email, String firstName, 
			String lastName, String preferredLocale, int userId) throws IllegalArgumentException {

		super(username, password, enabled, true, true, true, authorities);
		this.setEmail(email);
		this.firstName = firstName;
		this.lastName = lastName;
		this.preferredLocale = preferredLocale;
		this.userId = userId;
	}
	
	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String preferredLocale;
	private boolean acceptedTerms;
	private Collection<UserListingRole> roles;
	private int dominantRoleStrength = 0;
	
	public static AppUser getAnonymousUser() {
		Collection <GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new Role("ROLE_" + Roles.USER.getRoleName()));
		
		return new AppUser("Guest", "", true, roles, "", "Anonymous", "User", null, 0);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isLoggedIn() {
		return (this.userId > 0);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = (email!=null)?email.toLowerCase():email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getName() {
		return getFirstName() + " " + getLastName();
	}
	
	public String getPreferredLocale() {
		return preferredLocale;
	}
	public void setPreferredLocale(String preferredLocale) {
		this.preferredLocale = preferredLocale;
	}

	public boolean isAcceptedTerms() {
		return acceptedTerms;
	}
	public void setAcceptedTerms(boolean acceptedTerms) {
		this.acceptedTerms = acceptedTerms;
	}
	
	public Collection<UserListingRole> getRoles() {
		if (roles == null) {
			roles = new ArrayList<UserListingRole>();
		}
		return roles;
	}
	public void setRoles(Collection<UserListingRole> roles) {
		this.roles = roles;
	}
	
	public UserListingRole findRole(int listingId, Roles role) {
		for (UserListingRole userListingRole : roles) {
			if (userListingRole.getListingId() == listingId && userListingRole.getRoleId() == userListingRole.getRoleId()) {
				return userListingRole;
			}
		}
		return null;
	}
	
	public Roles getDominantRole() {
		Roles dominantRole = Roles.USER;
		if (this.getAuthorities() != null) {
			for (GrantedAuthority ga : this.getAuthorities()) {
				String roleString = ga.getAuthority().replaceFirst("ROLE_","");
				
				Roles role = Roles.get(roleString);
				if (role.getRoleStrength() > dominantRole.getRoleStrength()) {
					dominantRole = role;
				}
			}
		}
		return dominantRole;
	}
	
	public int getDominantRoleStrength() {
		if (dominantRoleStrength == 0) {
			dominantRoleStrength = getDominantRole().getRoleStrength();
		}
		return dominantRoleStrength;
	}
	
	public void setDominantRoleStrength(int dominantRoleStrength) {
		this.dominantRoleStrength = getDominantRoleStrength();
	}

	public boolean hasRole(String role) {
		if (this.getAuthorities() != null) {
			for (GrantedAuthority ga : this.getAuthorities()) {
				if (ga.getAuthority().equals(role)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isSuperAdmin() {
		return hasRole("ROLE_" + Roles.SUPER_ADMIN.name());
	}

	public boolean isAccountOwner() {
		return hasRole("ROLE_" + Roles.ACCOUNT_OWNER.name());
	}
	
	public boolean isListingOwner(int listingId) {
		for (UserListingRole role : getRoles()) {
			if (role.getRoleId() == Roles.LISTING_OWNER.getRoleId() && role.getListingId() == listingId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRole(int applicationId, int listingId, Roles role) {
		switch(role) {
			case SUPER_ADMIN:
				return isSuperAdmin();
			case ACCOUNT_OWNER:
				return isAccountOwner();
			case LISTING_OWNER:
				return isListingOwner(listingId);
			case USER:
				return hasRole("ROLE_" + role.getRoleName());
		}
		return false;
	}
	
	@Override
	public String toString() {
		String roles = "";
		for (GrantedAuthority ga : this.getAuthorities()) {
			if (ga instanceof Role) {
				roles += ((roles.length() == 0) ? "" : ", ") + ((Role)ga).getRoleName();
			} else {
				roles += ((roles.length() == 0) ? "" : ", ") + "{" + ga + "}";
			}
		}
		
		return "AppUser [userId=" + userId+ ", roles=" +roles + ", email=" + email
				+ ", firstname=" + firstName + ", lastname=" + lastName + "]";
	}
}