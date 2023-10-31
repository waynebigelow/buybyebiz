package ca.app.web.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.user.Roles;
import ca.app.model.user.User;
import ca.app.model.user.UserAgreement;
import ca.app.model.user.UserAgreementType;
import ca.app.model.user.UserListingRole;
import ca.app.service.common.TokenFieldType;
import ca.app.util.LocaleUtil;
import ca.app.util.RequestUtil;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 6369716355745105638L;

	private int userId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
//	private AddressDTO address;
	private String telephone;
	private String fax;
	private String preferredLocale = "en_US";
	private boolean enabled=false;
	private int statusId;
	private Timestamp lastLogin;
	private int failedLogins;
	private boolean lockedOut;
	private String disabledReason;
	private Timestamp lastPasswordChange;
	private boolean ignorePasswordRules;
	private boolean enableEnquiryEmail;
	private boolean enableExpirationEmail;
	private boolean enablePromotionalEmail;
	private boolean agent;
	private String companyName;
	private List<UserListingRole> roles = new ArrayList<UserListingRole>();
	private List<UserAgreement> agreements = new ArrayList<UserAgreement>();
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		this.userId = user.getUserId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
//		this.address = new AddressDTO(user.getAddress());
		this.telephone = user.getTelephone();
		this.fax = user.getFax();
		this.preferredLocale = user.getPreferredLocale();
		this.enabled = user.isEnabled();
		this.statusId = user.getStatusId();
		this.lastLogin = user.getLastLogin();
		this.failedLogins = user.getFailedLogins();
		this.lockedOut = user.isLockedOut();
		this.disabledReason = user.getDisabledReason();
		this.lastPasswordChange = user.getLastPasswordChange();
		this.ignorePasswordRules = user.isIgnorePasswordRules();
		this.enableEnquiryEmail = user.isEnableEnquiryEmail();
		this.enableExpirationEmail = user.isEnableExpirationEmail();
		this.enablePromotionalEmail = user.isEnablePromotionalEmail();
		this.agent = user.isAgent();
		this.companyName = user.getCompanyName();
		this.roles = user.getRoles();
		this.agreements = user.getAgreements();
	}
	
	@JsonIgnore
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.USER.getKey(), userId);
	}
	
	public String getPrimaryToken() {
		return RequestUtil.getToken(TokenFieldType.PRIMARY_ID.getKey(), userId);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getDisplayName() {
		if (firstName == null) {
			return "";
		}
		return firstName + " " + lastName;
	}
	public String getTableDisplayName() {
		if (firstName == null) {
			return "";
		}
		return lastName + ", " + firstName;
	}
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPreferredLocale() {
		return preferredLocale;
	}
	public void setPreferredLocale(String preferredLocale) {
		this.preferredLocale = preferredLocale;
	}
	public String getPreferredLanguage() {
		if (preferredLocale!=null && preferredLocale.length()>0) {
			Locale locale = LocaleUtil.toLocale(preferredLocale);
			return locale.getDisplayLanguage(locale);
		}
		return "";
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getFailedLogins() {
		return failedLogins;
	}
	public void setFailedLogins(int failedLogins) {
		this.failedLogins = failedLogins;
	}

	public boolean isLockedOut() {
		return lockedOut;
	}
	public void setLockedOut(boolean lockedOut) {
		this.lockedOut = lockedOut;
	}

	public String getDisabledReason() {
		return disabledReason;
	}
	public void setDisabledReason(String disabledReason) {
		this.disabledReason = disabledReason;
	}

	public Timestamp getLastPasswordChange() {
		return lastPasswordChange;
	}
	public void setLastPasswordChange(Timestamp lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public boolean isIgnorePasswordRules() {
		return ignorePasswordRules;
	}
	public void setIgnorePasswordRules(boolean ignorePasswordRules) {
		this.ignorePasswordRules = ignorePasswordRules;
	}
	
	public boolean isEnableEnquiryEmail() {
		return enableEnquiryEmail;
	}
	public void setEnableEnquiryEmail(boolean enableEnquiryEmail) {
		this.enableEnquiryEmail = enableEnquiryEmail;
	}

	public boolean isEnableExpirationEmail() {
		return enableExpirationEmail;
	}
	public void setEnableExpirationEmail(boolean enableExpirationEmail) {
		this.enableExpirationEmail = enableExpirationEmail;
	}

	public boolean isEnablePromotionalEmail() {
		return enablePromotionalEmail;
	}
	public void setEnablePromotionalEmail(boolean enablePromotionalEmail) {
		this.enablePromotionalEmail = enablePromotionalEmail;
	}

	public boolean isAgent() {
		return agent;
	}
	public void setAgent(boolean agent) {
		this.agent = agent;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<UserListingRole> getRoles() {
		return roles;
	}
	public void setRoles(List<UserListingRole> roles) {
		this.roles = roles;
	}
	
	public List<UserAgreement> getAgreements() {
		return agreements;
	}
	public void setAgreements(List<UserAgreement> agreements) {
		this.agreements = agreements;
	}

	public boolean isUserAgreementAccepted() {
		boolean hasTermsOfUse = false;
		boolean hasPrivacyPolicy = false;
		for (UserAgreement agreement : agreements) {
			if (agreement.getUserAgreementTypeId() == UserAgreementType.TERMS_OF_USE.getId()) {
				hasTermsOfUse = true;
			} else if (agreement.getUserAgreementTypeId() == UserAgreementType.PRIVACY_POLICY.getId()) {
				hasPrivacyPolicy = true;
			}
		}
		
		return (hasTermsOfUse && hasPrivacyPolicy);
	}
	
	public boolean hasRole(String role) {
		for (UserListingRole ulr : this.getRoles()) {
			Roles roleType = Roles.get(ulr.getRoleId());
			if (roleType.name().equals(role)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSuperAdmin() {
		return hasRole("ROLE_" + Roles.SUPER_ADMIN.name());
	}
	
	public String getToggleSpan(){
		if (enabled){
			return "<span class=\"glyphicon glyphicon-thumbs-down\"></span> Disable";
		} else {
			return "<span class=\"glyphicon glyphicon-thumbs-up\"></span> Enable";
		}
	}
	
	public String getEnableSpan(){
		if (enabled){
			return "<span class=\"glyphicon glyphicon-thumbs-up\"></span>";
		} else {
			return "<span class=\"glyphicon glyphicon-thumbs-down\"></span>";
		}
	}
}