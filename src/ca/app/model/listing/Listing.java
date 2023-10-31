package ca.app.model.listing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.application.Application;
import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;
import ca.app.model.common.Address;
import ca.app.model.common.CurrencyType;
import ca.app.model.photo.Photo;
import ca.app.model.user.User;

@Entity
@Table(name="listing")
public class Listing implements Serializable {
	private static final long serialVersionUID = 6369716355745105638L;

	@Id
	@SequenceGenerator(name="seqListing", sequenceName="seq_listing", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqListing")
	@Column(name="listing_id", unique=true)
	private int listingId;

	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="application_id")
	private Application application;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="category_id")
	private Category category;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="sub_category_id")
	private SubCategory subCategory;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="listing_uri")
	private String listingURI;
	
	@Column(name="price")
	private BigDecimal price = new BigDecimal("0.00");
	
	@Column(name="currency_type_id")
	private int currencyTypeId = CurrencyType.CAD.getId();
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address address;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="details_id")
	private BusinessDetails businessDetails;
	
	@Column(name="status_id")
	private int statusId = ListingStatus.DRAFT.getId();
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Column(name="include_contact_name")
	private boolean includeContactName = true;
	
	@Column(name="include_contact_telephone")
	private boolean includeContactTelephone = false;
	
	@Column(name="expiration_date")
	private Timestamp expirationDate;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="listing_id")
	private List<Photo> photos;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="listing_id")
	private List<EnquiryMap> enquiries;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="listing_id")
	private List<ListingPackage> packages;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="listing_id")
	private List<MetaDataApproval> metaDataApprovals;
	
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public SubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
	public String getTitle() {
		if (title == null) {
			return "";
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		if (description == null) {
			return "";
		}
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setListingURI(String listingURI) {
		this.listingURI = listingURI;
	}
	public String getListingURI() {
		return listingURI;
	}
	public String getListingURIFormatted() {
		return URLUtil.buildListingURI(listingURI);
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public int getCurrencyTypeId() {
		return currencyTypeId;
	}
	public void setCurrencyTypeId(int currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}
	public CurrencyType getCurrency() {
		return CurrencyType.get(currencyTypeId);
	}

	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public BusinessDetails getBusinessDetails() {
		if (businessDetails == null) {
			businessDetails = new BusinessDetails();
		}
		return businessDetails;
	}
	public void setBusinessDetails(BusinessDetails businessDetails) {
		this.businessDetails = businessDetails;
	}
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public ListingStatus getStatus() {
		return ListingStatus.get(statusId);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isIncludeContactName() {
		return includeContactName;
	}
	public void setIncludeContactName(boolean includeContactName) {
		this.includeContactName = includeContactName;
	}
	
	public boolean isIncludeContactTelephone() {
		return includeContactTelephone;
	}
	public void setIncludeContactTelephone(boolean includeContactTelephone) {
		this.includeContactTelephone = includeContactTelephone;
	}
	
	public Timestamp getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public List<EnquiryMap> getEnquiries() {
		return enquiries;
	}
	public void setEnquiries(List<EnquiryMap> enquiries) {
		this.enquiries = enquiries;
	}
	
	public List<ListingPackage> getPackages() {
		return packages;
	}
	public void setPackages(List<ListingPackage> packages) {
		this.packages = packages;
	}

	public List<Photo> getPhotos() {
		if (photos == null) {
			photos = new ArrayList<Photo>();
		}
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public List<MetaDataApproval> getMetaDataApprovals() {
		return metaDataApprovals;
	}
	public void setMetaDataApprovals(List<MetaDataApproval> metaDataApprovals) {
		this.metaDataApprovals = metaDataApprovals;
	}
}