package ca.app.model.user;

import java.io.Serializable;

public class PageAccessiblity implements Serializable{

	private static final long serialVersionUID = 3L;
	
	private AppUser user;
	private boolean expired = false;
	private boolean loggedIn = false;
	private boolean systemAdmin = false;
	private boolean listingAdmin = false;
	private boolean accountOwner = false;
	private boolean useCaptcha = false;
	private boolean loginRequired = false;
	
	public PageAccessiblity configure(AppUser user) {
		this.user = user;
		this.systemAdmin = (user != null) ? user.isSuperAdmin() : false;
		this.accountOwner = (user != null) ? user.isAccountOwner() : false;
		this.listingAdmin = (user != null) ? user.isAccountOwner() : false;
		
		if(user != null) {
			loggedIn = user.hasRole("ROLE_ACCOUNT_OWNER") || user.hasRole("ROLE_SUPER_ADMIN");
		}
		
		if(!loggedIn) {
			useCaptcha = true;
		}

		return this;
	}
	
	public AppUser getUser() {
		return user;
	}
	public void setUser(AppUser user) {
		this.user = user;
	}
	
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isSystemAdmin() {
		return systemAdmin;
	}
	public void setSystemAdmin(boolean systemAdmin) {
		this.systemAdmin = systemAdmin;
	}

	public boolean isListingAdmin() {
		return listingAdmin;
	}
	public void setListingAdmin(boolean listingAdmin) {
		this.listingAdmin = listingAdmin;
	}

	public boolean isAccountOwner() {
		return accountOwner;
	}
	public void setAccountOwner(boolean accountOwner) {
		this.accountOwner = accountOwner;
	}

	public boolean isUseCaptcha() {
		return useCaptcha;
	}
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}
	
	public boolean isLoginRequired() {
		return loginRequired;
	}
	public void setLoginRequired(boolean loginRequired) {
		this.loginRequired = loginRequired;
	}

	public String getActionType() {
		if (isSystemAdmin()) {
			return "appAdmin";
		}
		return "listingAdmin";
	}
}