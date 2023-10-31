package ca.app.web.dto.application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.application.ApplicationPackage;
import ca.app.model.application.ApplicationPackageType;
import ca.app.model.application.ApplicationType;
import ca.app.model.application.PackagePrice;
import ca.app.model.application.TimePeriod;
import ca.app.service.common.TokenFieldType;
import ca.app.util.RequestUtil;

public class ApplicationPackageDTO implements Serializable {
	private static final long serialVersionUID = 2L;

	private int packageId;
	private String name;
	private String description;
	private int applicationId;
	private int typeId = ApplicationPackageType.EXTENSION.getId();
	private int timePeriodId;
	private int duration;
	private List<PackagePriceDTO> packagePrices;
	private BigDecimal agentDiscount;
	private int linkId;
	private boolean enabled = false;
	private MessageSource messageSource;
	private Locale locale;
	
	public ApplicationPackageDTO(ApplicationPackage applicationPackage) {
		this.packageId = applicationPackage.getPackageId();
		this.name = applicationPackage.getName();
		this.description = applicationPackage.getDescription();
		this.applicationId = applicationPackage.getApplicationId();
		this.typeId = applicationPackage.getTypeId();
		this.timePeriodId = applicationPackage.getTimePeriodId();
		this.duration = applicationPackage.getDuration();
		this.agentDiscount = applicationPackage.getAgentDiscount();
		this.linkId = applicationPackage.getLinkId();
		this.enabled = applicationPackage.isEnabled();
		
		this.packagePrices = new ArrayList<PackagePriceDTO>();
		for (PackagePrice packagePrice : applicationPackage.getPackagePrices()) {
			this.packagePrices.add(new PackagePriceDTO(packagePrice));
		}
	}
	
	@JsonIgnore
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.APP_PACKAGE.getKey(), packageId);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public ApplicationType getApplicationType() {
		return ApplicationType.get(applicationId);
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public ApplicationPackageType getApplicationPackageType() {
		return ApplicationPackageType.get(typeId);
	}
	
	public int getTimePeriodId() {
		return timePeriodId;
	}
	public void setTimePeriodId(int timePeriodId) {
		this.timePeriodId = timePeriodId;
	}
	public TimePeriod getTimePeriod() {
		return TimePeriod.get(timePeriodId);
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<PackagePriceDTO> getPackagePrices() {
		return packagePrices;
	}
	public void setPackagePrices(List<PackagePriceDTO> packagePrices) {
		this.packagePrices = packagePrices;
	}

	public BigDecimal getAgentDiscount() {
		return agentDiscount;
	}
	public void setAgentDiscount(BigDecimal agentDiscount) {
		this.agentDiscount = agentDiscount;
	}

	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getLocaleCountry() {
		if (locale == null) {
			return "";
		}
		return locale.getCountry();
	}
}