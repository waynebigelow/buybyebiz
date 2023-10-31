package ca.app.web.dto.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationPackage;
import ca.app.model.application.ApplicationType;
import ca.app.service.common.TokenFieldType;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;

public class ApplicationDTO implements Serializable{
	private static final long serialVersionUID = 3983118687958680553L;

	private int applicationId;
	private int typeId;
	private String name;
	private String description;
	private String key;
	private String domain;
	private String replyEmail;
	private String supportEmail;
	private String supportPhone;
	private String defaultLocaleCode = "en_US";
	private boolean enabled = false;
	private List<ApplicationPackageDTO> packages;
	
	public ApplicationDTO(Application application) {
		this.applicationId = application.getApplicationId();
		this.typeId = application.getTypeId();
		this.name = application.getName();
		this.description = application.getDescription();
		this.key = application.getKey();
		this.domain = application.getDomain();
		this.replyEmail = application.getReplyEmail();
		this.supportEmail = application.getSupportEmail();
		this.supportPhone = application.getSupportPhone();
		this.defaultLocaleCode = application.getDefaultLocaleCode();
		this.enabled = application.isEnabled();
		
		this.packages = new ArrayList<ApplicationPackageDTO>();
		for(ApplicationPackage applicationPackage : application.getPackages()) {
			this.packages.add(new ApplicationPackageDTO(applicationPackage));
		}
	}
	
	@JsonIgnore
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

	public List<ApplicationPackageDTO> getPackages() {
		return packages;
	}
	public void setPackages(List<ApplicationPackageDTO> packages) {
		this.packages = packages;
	}
	
	@JsonIgnore
	public String getSecureApplicationURL(){
		return "https://" + getDomain() + ProjectUtil.getProperty("webapp.name");
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