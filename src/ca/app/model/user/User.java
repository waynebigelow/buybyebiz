package ca.app.model.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="user_account")
public class User implements Serializable{
	private static final long serialVersionUID = 6369716355745105638L;

	@Id
	@SequenceGenerator(name="seqUserAccount", sequenceName="seq_user_account", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqUserAccount")
	@Column(name="user_id", unique=true)
	private int userId;
	
	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="fax")
	private String fax;
	
	@Column(name="preferred_locale")
	private String preferredLocale = "en_US";
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Column(name="status_id")
	private int statusId;
	
	@Column(name="last_login")
	private Timestamp lastLogin;
	
	@Column(name="failed_logins")
	private int failedLogins;
	
	@Column(name="locked_out")
	private boolean lockedOut = false;
	
	@Column(name="disabled_reason")
	private String disabledReason;
	
	@Column(name="last_password_change")
	private Timestamp lastPasswordChange;
	
	@Column(name="ignore_password_rules")
	private boolean ignorePasswordRules = false;
	
	@Column(name="enable_promotional_email")
	private boolean enablePromotionalEmail = true;
	
	@Column(name="enable_expiration_email")
	private boolean enableExpirationEmail = true;
	
	@Column(name="enable_enquiry_email")
	private boolean enableEnquiryEmail = true;
	
	@Column(name="is_agent")
	private boolean agent = false;
	
	@Column(name="company_name")
	private String companyName;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	 private List<UserListingRole> roles = new ArrayList<UserListingRole>();

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	 private List<UserAgreement> agreements = new ArrayList<UserAgreement>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	
	public boolean isEnablePromotionalEmail() {
		return enablePromotionalEmail;
	}
	public void setEnablePromotionalEmail(boolean enablePromotionalEmail) {
		this.enablePromotionalEmail = enablePromotionalEmail;
	}
	
	public boolean isEnableExpirationEmail() {
		return enableExpirationEmail;
	}
	public void setEnableExpirationEmail(boolean enableExpirationEmail) {
		this.enableExpirationEmail = enableExpirationEmail;
	}
	
	public boolean isEnableEnquiryEmail() {
		return enableEnquiryEmail;
	}
	public void setEnableEnquiryEmail(boolean enableEnquiryEmail) {
		this.enableEnquiryEmail = enableEnquiryEmail;
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
}