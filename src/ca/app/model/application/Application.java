package ca.app.model.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.LocaleUtils;

import ca.app.service.common.TokenFieldType;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.application.ApplicationPackageDTO;

@Entity
@Table(name="application")
public class Application implements Serializable{
	private static final long serialVersionUID = 3983118687958680553L;
	
	@Id
	@SequenceGenerator(name="seqApplication", sequenceName="seq_application", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqApplication")
	@Column(name="application_id", unique=true)
	private int applicationId;
	
	@Column(name="type_id")
	private int typeId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="key")
	private String key;
	
	@Column(name="domain")
	private String domain;
	
	@Column(name="reply_email")
	private String replyEmail;
	
	@Column(name="support_email")
	private String supportEmail;
	
	@Column(name="support_phone")
	private String supportPhone;
	
	@Column(name="default_locale")
	private String defaultLocaleCode = "en_US";
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="application_id")
	private List<ApplicationPackage> packages;
	
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.APPLICATION.getKey(), applicationId);
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public ApplicationType getApplicationType() {
		return ApplicationType.get(typeId);
	}
	public boolean isBusinessApplication() {
		return getApplicationType() == ApplicationType.BUSINESS;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getReplyEmail() {
		return replyEmail;
	}
	public void setReplyEmail(String replyEmail) {
		this.replyEmail = replyEmail;
	}

	public String getSupportEmail() {
		return supportEmail;
	}
	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}
	
	public String getSupportPhone() {
		return supportPhone;
	}
	public void setSupportPhone(String supportPhone) {
		this.supportPhone = supportPhone;
	}
	
	public String getDefaultLocaleCode() {
		return defaultLocaleCode;
	}	
	public void setDefaultLocaleCode(String defaultLocaleCode) {
		this.defaultLocaleCode = defaultLocaleCode;
	}
	public Locale getDefaultLocale() {
		if (this.defaultLocaleCode != null) {
			return LocaleUtils.toLocale(this.defaultLocaleCode);
		} else {
			return Locale.getDefault();
		}
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<ApplicationPackage> getPackages() {
		return packages;
	}
	public void setPackages(List<ApplicationPackage> packages) {
		this.packages = packages;
	}
	
	public List<ApplicationPackageDTO> getPackagesDTO() {
		List<ApplicationPackageDTO> dtos = new ArrayList<ApplicationPackageDTO>();
		for (ApplicationPackage appPackage : packages) {
			dtos.add(new ApplicationPackageDTO(appPackage));
		}
		return dtos;
	}
	
	public ApplicationPackageDTO getTrialPackageDTO() {
		for (ApplicationPackage appPackage : packages) {
			if (appPackage.getApplicationPackageType() == ApplicationPackageType.TRIAL) {
				return new ApplicationPackageDTO(appPackage);
			}
		}
		return null;
	}
	
	public String getSecureApplicationURL(){
		return "https://" + getDomain() + ProjectUtil.getProperty("webapp.name");
	}
}