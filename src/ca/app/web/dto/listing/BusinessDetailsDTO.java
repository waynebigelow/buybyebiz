package ca.app.web.dto.listing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import ca.app.model.listing.BusinessDetails;
import ca.app.model.listing.MetaDataApproval;
import ca.app.model.listing.PropertyType;

public class BusinessDetailsDTO implements Serializable {
	private static final long serialVersionUID = 6369716355745105638L;

	private int detailsId;

	private BigDecimal salesRevenueMin = new BigDecimal("0.00");
	private BigDecimal salesRevenueMax = new BigDecimal("0.00");
	private BigDecimal cashFlow = new BigDecimal("0.00");
	private BigDecimal inventoryTotal = new BigDecimal("0.00");
	private BigDecimal chattelTotal = new BigDecimal("0.00");
	private String financialOther;
	private int propertyTypeId;
	private String propertyTax;
	private String frontage;
	private String squareFootage;
	private String acreage;
	private String ownersResidence;
	private String propertyOther;
	private String hoursOfOperation;
	private String yearEstablished;
	private String numberOfEmployees;
	private String operationOther;
	private String sellingReason;
	private String support;
	private String ownerFinancing;
	private String websiteURL;
	private String facebookURL;
	private String twitterURL;
	private String tripAdvisorURL;
	private String multiMediaLink;
	private String agentListingLink;

	public BusinessDetailsDTO(BusinessDetails businessDetails, List<MetaDataApproval> metaDataApprovals, boolean isPreview) {
		this.detailsId = businessDetails.getDetailsId();
		this.salesRevenueMax = businessDetails.getSalesRevenueMax();
		this.salesRevenueMin = businessDetails.getSalesRevenueMin();
		this.cashFlow = businessDetails.getCashFlow();
		this.inventoryTotal = businessDetails.getInventoryTotal();
		this.chattelTotal = businessDetails.getChattelTotal();
		this.financialOther = businessDetails.getFinancialOther();
		this.propertyTypeId = businessDetails.getPropertyTypeId();
		this.propertyTax = businessDetails.getPropertyTax();
		this.squareFootage = businessDetails.getSquareFootage();
		this.acreage = businessDetails.getAcreage();
		this.frontage = businessDetails.getFrontage();
		this.ownersResidence = businessDetails.getOwnersResidence();
		this.hoursOfOperation = businessDetails.getHoursOfOperation();
		this.numberOfEmployees = businessDetails.getNumberOfEmployees();
		this.yearEstablished = businessDetails.getYearEstablished();
		this.operationOther = businessDetails.getOperationOther();
		this.sellingReason = businessDetails.getSellingReason();
		this.support = businessDetails.getSupport();
		this.ownerFinancing = businessDetails.getOwnerFinancing();
		this.websiteURL = businessDetails.getWebsiteURL();
		this.facebookURL = businessDetails.getFacebookURL();
		this.twitterURL = businessDetails.getTwitterURL();
		this.tripAdvisorURL = businessDetails.getTripAdvisorURL();
		this.multiMediaLink = businessDetails.getMultiMediaLink();
		this.agentListingLink = businessDetails.getAgentListingLink();
		
		if (metaDataApprovals != null && isPreview) {
			for (MetaDataApproval metaData : metaDataApprovals) {
				switch (metaData.getMetaDataType()) {
					case TITLE : break;
					case DESCRIPTION : break;
					case SELLING_REASON : this.sellingReason = metaData.getValue(); break;
					case HOURS_OF_OPERATION : this.hoursOfOperation = metaData.getValue(); break;
					case YEAR_ESTABLISHED : this.yearEstablished = metaData.getValue(); break;
					case NUMBER_OF_EMPLOYEES : this.numberOfEmployees = metaData.getValue(); break;
					case FRONTAGE : this.frontage = metaData.getValue(); break;
					case SQUARE_FOOTAGE : this.squareFootage = metaData.getValue(); break;
					case ACREAGE : this.acreage = metaData.getValue(); break;
					case SUPPORT : this.support = metaData.getValue(); break;
					case OWNER_FINANCING : this.ownerFinancing = metaData.getValue(); break;
					case WEBSITE_URL : this.websiteURL = metaData.getValue(); break;
					case FACEBOOK_URL : this.facebookURL = metaData.getValue(); break;
					case TWITTER_URL : this.twitterURL = metaData.getValue(); break;
					case TRIP_ADVISOR_URL : this.tripAdvisorURL = metaData.getValue(); break;
					case FINANCIAL_OTHER : this.financialOther = metaData.getValue(); break;
					case PROPERTY_OTHER : this.propertyOther = metaData.getValue(); break;
					case PROPERTY_TAX : this.propertyTax = metaData.getValue(); break;
					case OWNERS_RESIDENCE : this.ownersResidence = metaData.getValue(); break;
					case OPERATION_OTHER : this.operationOther = metaData.getValue(); break;
					case MULTIMEDIA_LINK : this.multiMediaLink = metaData.getValue(); break;
					case AGENT_LISTING_LINK : this.agentListingLink = metaData.getValue(); break;
				}
			}
		}
	}
	
	public int getDetailsId() {
		return detailsId;
	}
	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}
	
	public boolean hasFinancialDetails() {
		return salesRevenueMax.intValue() > 0 
				|| salesRevenueMin.intValue() > 0 
				|| cashFlow.intValue() > 0 
				|| inventoryTotal.intValue() > 0 
				|| chattelTotal.intValue() > 0
				|| (financialOther != null && !financialOther.equals(""));
	}
	
	public BigDecimal getSalesRevenueMax() {
		return salesRevenueMax;
	}
	public void setSalesRevenueMax(BigDecimal salesRevenueMax) {
		this.salesRevenueMax = salesRevenueMax;
	}	
	
	public BigDecimal getSalesRevenueMin() {
		return salesRevenueMin;
	}
	public void setSalesRevenueMin(BigDecimal salesRevenueMin) {
		this.salesRevenueMin = salesRevenueMin;
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
		return financialOther;
	}
	public void setFinancialOther(String financialOther) {
		this.financialOther = financialOther;
	}
	
	// Property Details
	public boolean hasPropertyDetails() {
		return propertyTypeId > 0 
				|| (frontage != null && frontage.length() > 0)
				|| (squareFootage != null && squareFootage.length() > 0) 
				|| (acreage != null && acreage.length() > 0)
				|| (propertyTax != null && !propertyTax.equals(""))
				|| (ownersResidence != null && !ownersResidence.equals(""))
				|| (propertyOther != null && !propertyOther.equals(""));
	}
	
	public int getPropertyTypeId() {
		return propertyTypeId;
	}
	public void setPropertyTypeId(int propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}
	public PropertyType getPropertyType() {
		return PropertyType.get(propertyTypeId);
	}
	
	public String getPropertyTax() {
		return propertyTax;
	}
	public void setPropertyTax(String propertyTax) {
		this.propertyTax = propertyTax;
	}

	public String getFrontage() {
		return frontage;
	}
	public void setFrontage(String frontage) {
		this.frontage = frontage;
	}
	
	public String getSquareFootage() {
		return squareFootage;
	}
	public void setSquareFootage(String squareFootage) {
		this.squareFootage = squareFootage;
	}
	
	public String getAcreage() {
		return acreage;
	}
	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}
	
	public String getOwnersResidence() {
		return ownersResidence;
	}
	public void setOwnersResidence(String ownersResidence) {
		this.ownersResidence = ownersResidence;
	}

	public String getPropertyOther() {
		return propertyOther;
	}
	public void setPropertyOther(String propertyOther) {
		this.propertyOther = propertyOther;
	}
	
	// Operational Details
	public boolean hasOperationalDetails() {
		return (hoursOfOperation != null && hoursOfOperation.length() > 0)
				|| (numberOfEmployees != null && numberOfEmployees.length() > 0)
				|| (yearEstablished != null && yearEstablished.length() > 0)
				|| (operationOther != null && !operationOther.equals(""));
	}
	
	public String getHoursOfOperation() {
		return hoursOfOperation;
	}
	public void setHoursOfOperation(String hoursOfOperation) {
		this.hoursOfOperation = hoursOfOperation;
	}
	
	public String getNumberOfEmployees() {
		return numberOfEmployees;
	}
	public void setNumberOfEmployees(String numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	
	public String getYearEstablished() {
		return yearEstablished;
	}
	public void setYearEstablished(String yearEstablished) {
		this.yearEstablished = yearEstablished;
	}
	
	public String getOperationOther() {
		return operationOther;
	}
	public void setOperationOther(String operationOther) {
		this.operationOther = operationOther;
	}
	
	// Other Details
	public boolean hasOtherDetails() {
		return (sellingReason != null && sellingReason.length() > 0) ||
				(support != null && support.length() > 0) ||
				(ownerFinancing != null && ownerFinancing.length() > 0);
	}
	
	public String getSellingReason() {
		return sellingReason;
	}
	public void setSellingReason(String sellingReason) {
		this.sellingReason = sellingReason;
	}
	
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	
	public String getOwnerFinancing() {
		return ownerFinancing;
	}
	public void setOwnerFinancing(String ownerFinancing) {
		this.ownerFinancing = ownerFinancing;
	}
	
	public boolean hasSocialMedia() {
		return (websiteURL != null && websiteURL.length() > 0) 
				|| (facebookURL != null && facebookURL.length() > 0) 
				|| (twitterURL != null && twitterURL.length() > 0) 
				|| (tripAdvisorURL != null && tripAdvisorURL.length() > 0); 
	}
	
	public String getWebsiteURL() {
		return websiteURL;
	}
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getFacebookURL() {
		return facebookURL;
	}
	public void setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
	}

	public String getTwitterURL() {
		return twitterURL;
	}
	public void setTwitterURL(String twitterURL) {
		this.twitterURL = twitterURL;
	}

	public String getTripAdvisorURL() {
		return tripAdvisorURL;
	}
	public void setTripAdvisorURL(String tripAdvisorURL) {
		this.tripAdvisorURL = tripAdvisorURL;
	}
	
	public boolean hasListingLinks() {
		return (multiMediaLink != null && multiMediaLink.length() > 0) 
				|| (agentListingLink != null && agentListingLink.length() > 0); 
	}
	
	public String getMultiMediaLink() {
		return multiMediaLink;
	}
	public void setMultiMediaLink(String multiMediaLink) {
		this.multiMediaLink = multiMediaLink;
	}
	
	public String getAgentListingLink() {
		return agentListingLink;
	}
	public void setAgentListingLink(String agentListingLink) {
		this.agentListingLink = agentListingLink;
	}
}