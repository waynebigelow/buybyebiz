package ca.app.model.application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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

import ca.app.model.common.CurrencyType;

@Entity
@Table(name="application_package")
public class ApplicationPackage implements Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@SequenceGenerator(name="seqApplicationPackage", sequenceName="seq_app_package", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqApplicationPackage")
	@Column(name="package_id", unique=true)
	private int packageId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="application_id")
	private int applicationId;
	
	@Column(name="type_id")
	private int typeId = ApplicationPackageType.EXTENSION.getId();
	
	@Column(name="time_period_id")
	private int timePeriodId;
	
	@Column(name="duration")
	private int duration;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="package_id")
	private List<PackagePrice> packagePrices;
	
	@Column(name="agent_discount")
	private BigDecimal agentDiscount = new BigDecimal("0.00");
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Column(name="link_id")
	private int linkId;
	
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
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
	
	public List<PackagePrice> getPackagePrices() {
		return packagePrices;
	}
	public void setPackagePrices(List<PackagePrice> packagePrices) {
		this.packagePrices = packagePrices;
	}
	
	public PackagePrice getPriceByCurrencyType(CurrencyType currencyType) {
		for (PackagePrice packagePrice : packagePrices) {
			if (packagePrice.getCurrencyType() == currencyType) {
				return packagePrice;
			}
		}
		return null;
	}
	
	public BigDecimal getAgentDiscount() {
		return agentDiscount;
	}
	public void setAgentDiscount(BigDecimal agentDiscount) {
		this.agentDiscount = agentDiscount;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
}