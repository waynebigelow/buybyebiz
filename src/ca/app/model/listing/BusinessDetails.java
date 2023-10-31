package ca.app.model.listing;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="business_details")
public class BusinessDetails implements Serializable {
	private static final long serialVersionUID = 6369716355745105638L;
	
	@Id
	@SequenceGenerator(name="seqBusinessDetails", sequenceName="seq_business_details", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqBusinessDetails")
	@Column(name="details_id", unique=true)
	private int detailsId;
	
	@Column(name="sales_rev_min")
	private BigDecimal salesRevenueMin = new BigDecimal("0.00");
	
	@Column(name="sales_rev_max")
	private BigDecimal salesRevenueMax = new BigDecimal("0.00");
	
	@Column(name="cash_flow")
	private BigDecimal cashFlow = new BigDecimal("0.00");
	
	@Column(name="inventory_total")
	private BigDecimal inventoryTotal = new BigDecimal("0.00");
	
	@Column(name="chattel_total")
	private BigDecimal chattelTotal = new BigDecimal("0.00");
	
	@Column(name="financial_other")
	private String financialOther;
	
	@Column(name="property_type_id")
	private int propertyTypeId;
	
	@Column(name="property_tax")
	private String propertyTax;
	
	@Column(name="frontage")
	private String frontage;
	
	@Column(name="square_footage")
	private String squareFootage;
	
	@Column(name="acreage")
	private String acreage;
	
	@Column(name="owners_residence")
	private String ownersResidence;
	
	@Column(name="property_other")
	private String propertyOther;
	
	@Column(name="hours_operation")
	private String hoursOfOperation;
	
	@Column(name="year_established")
	private String yearEstablished;
	
	@Column(name="number_employees")
	private String numberOfEmployees;
	
	@Column(name="operation_other")
	private String operationOther;
	
	@Column(name="selling_reason")
	private String sellingReason;
	
	@Column(name="support")
	private String support;
	
	@Column(name="owner_financing")
	private String ownerFinancing;
	
	@Column(name="website_url")
	private String websiteURL;
	
	@Column(name="facebook_url")
	private String facebookURL;
	
	@Column(name="twitter_url")
	private String twitterURL;
	
	@Column(name="tripadvisor_url")
	private String tripAdvisorURL;
	
	@Column(name="multi_media_link")
	private String multiMediaLink;
	
	@Column(name="agent_listing_link")
	private String agentListingLink;
	
	public int getDetailsId() {
		return detailsId;
	}
	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}
	
	public BigDecimal getSalesRevenueMin() {
		return salesRevenueMin;
	}
	public void setSalesRevenueMin(BigDecimal salesRevenueMin) {
		this.salesRevenueMin = salesRevenueMin;
	}

	public BigDecimal getSalesRevenueMax() {
		return salesRevenueMax;
	}
	public void setSalesRevenueMax(BigDecimal salesRevenueMax) {
		this.salesRevenueMax = salesRevenueMax;
	}

	public BigDecimal getCashFlow() {
		return cashFlow;
	}
	public void setCashFlow(BigDecimal cashFlow) {
		this.cashFlow = cashFlow;
	}
	
	public BigDecimal getInventoryTotal() {
		return inventoryTotal;
	}
	public void setInventoryTotal(BigDecimal inventoryTotal) {
		this.inventoryTotal = inventoryTotal;
	}
	
	public BigDecimal getChattelTotal() {
		return chattelTotal;
	}
	public void setChattelTotal(BigDecimal chattelTotal) {
		this.chattelTotal = chattelTotal;
	}
	
	public String getFinancialOther() {
		if (financialOther == null) {
			return "";
		}
		return financialOther;
	}
	public void setFinancialOther(String financialOther) {
		this.financialOther = financialOther;
	}
	
	public int getPropertyTypeId() {
		return propertyTypeId;
	}
	public void setPropertyTypeId(int propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}
	
	public String getPropertyTax() {
		if (propertyTax == null) {
			return "";
		}
		return propertyTax;
	}
	public void setPropertyTax(String propertyTax) {
		this.propertyTax = propertyTax;
	}

	public String getFrontage() {
		if (frontage == null) {
			return "";
		}
		return frontage;
	}
	public void setFrontage(String frontage) {
		this.frontage = frontage;
	}
	
	public String getSquareFootage() {
		if (squareFootage == null) {
			return "";
		}
		return squareFootage;
	}
	public void setSquareFootage(String squareFootage) {
		this.squareFootage = squareFootage;
	}
	
	public String getAcreage() {
		if (acreage == null) {
			return "";
		}
		return acreage;
	}
	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}
	
	public String getOwnersResidence() {
		if (ownersResidence == null) {
			return "";
		}
		return ownersResidence;
	}
	public void setOwnersResidence(String ownersResidence) {
		this.ownersResidence = ownersResidence;
	}
	
	public String getPropertyOther() {
		if (propertyOther == null) {
			return "";
		}
		return propertyOther;
	}
	public void setPropertyOther(String propertyOther) {
		this.propertyOther = propertyOther;
	}
	
	public String getHoursOfOperation() {
		if (hoursOfOperation == null) {
			return "";
		}
		return hoursOfOperation;
	}
	public void setHoursOfOperation(String hoursOfOperation) {
		this.hoursOfOperation = hoursOfOperation;
	}
	
	public String getYearEstablished() {
		if (yearEstablished == null) {
			return "";
		}
		return yearEstablished;
	}
	public void setYearEstablished(String yearEstablished) {
		this.yearEstablished = yearEstablished;
	}
	
	public String getNumberOfEmployees() {
		if (numberOfEmployees == null) {
			return "";
		}
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(String numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	
	public String getSellingReason() {
		if (sellingReason == null) {
			return "";
		}
		return sellingReason;
	}
	public void setSellingReason(String sellingReason) {
		this.sellingReason = sellingReason;
	}
	
	public String getSupport() {
		if (support == null) {
			return "";
		}
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	
	public String getOwnerFinancing() {
		if (ownerFinancing == null) {
			return "";
		}
		return ownerFinancing;
	}
	public void setOwnerFinancing(String ownerFinancing) {
		this.ownerFinancing = ownerFinancing;
	}
	
	public String getOperationOther() {
		if (operationOther == null) {
			return "";
		}
		return operationOther;
	}
	public void setOperationOther(String operationOther) {
		this.operationOther = operationOther;
	}
	
	public String getWebsiteURL() {
		if (websiteURL == null) {
			return "";
		}
		return websiteURL;
	}
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getFacebookURL() {
		if (facebookURL == null) {
			return "";
		}
		return facebookURL;
	}
	public void setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
	}

	public String getTwitterURL() {
		if (twitterURL == null) {
			return "";
		}
		return twitterURL;
	}
	public void setTwitterURL(String twitterURL) {
		this.twitterURL = twitterURL;
	}

	public String getTripAdvisorURL() {
		if (tripAdvisorURL == null) {
			return "";
		}
		return tripAdvisorURL;
	}
	public void setTripAdvisorURL(String tripAdvisorURL) {
		this.tripAdvisorURL = tripAdvisorURL;
	}
	
	public String getMultiMediaLink() {
		if (multiMediaLink == null) {
			return "";
		}
		return multiMediaLink;
	}
	public void setMultiMediaLink(String multiMediaLink) {
		this.multiMediaLink = multiMediaLink;
	}
	
	public String getAgentListingLink() {
		if (agentListingLink == null) {
			return "";
		}
		return agentListingLink;
	}
	public void setAgentListingLink(String agentListingLink) {
		this.agentListingLink = agentListingLink;
	}
}