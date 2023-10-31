package ca.app.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.service.common.TokenFieldType;
import ca.app.util.RequestUtil;

@Entity
@Table(name="listing_lead")
public class ListingLead implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqListingLead", sequenceName="seq_listing_lead", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqListingLead")
	@Column(name="listing_lead_id", unique=true)
	private int listingLeadId;
	
	@Column(name="business_name")
	private String businessName;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="website")
	private String website;
	
	@Column(name="is_promo_sent")
	private boolean promoSent = false;
	
	@Column(name="enable_promotional_email")
	private boolean enablePromotionalEmail = true;
	
	public int getListingLeadId() {
		return listingLeadId;
	}
	public void setListingLeadId(int listingLeadId) {
		this.listingLeadId = listingLeadId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.LISTING_LEAD.getKey(), listingLeadId);
	}
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public boolean isPromoSent() {
		return promoSent;
	}
	public void setPromoSent(boolean promoSent) {
		this.promoSent = promoSent;
	}
	
	public boolean isEnablePromotionalEmail() {
		return enablePromotionalEmail;
	}
	public void setEnablePromotionalEmail(boolean enablePromotionalEmail) {
		this.enablePromotionalEmail = enablePromotionalEmail;
	}
}