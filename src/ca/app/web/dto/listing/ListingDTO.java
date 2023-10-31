package ca.app.web.dto.listing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.common.CountryType;
import ca.app.model.common.CurrencyType;
import ca.app.model.common.ProvinceType;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.listing.ListingStatus;
import ca.app.model.listing.MetaDataApproval;
import ca.app.model.listing.MetaDataStatus;
import ca.app.model.listing.MetaDataType;
import ca.app.model.photo.Photo;
import ca.app.model.photo.PhotoStatus;
import ca.app.model.photo.PhotoType;
import ca.app.service.common.TokenFieldType;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.CurrencyFormatUtil;
import ca.app.util.DateUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.category.CategoryDTO;
import ca.app.web.dto.category.SubCategoryDTO;
import ca.app.web.dto.common.AddressDTO;
import ca.app.web.dto.photo.PhotoDTO;
import ca.app.web.dto.user.UserDTO;

public class ListingDTO implements Serializable {
	private static final long serialVersionUID = 6369716355745105638L;

	private int listingId;
	private ApplicationDTO application;
	private UserDTO user;
	private CategoryDTO category;
	private SubCategoryDTO subCategory;
	private String title;
	private String description;
	private String listingURI;
	private BigDecimal price;
	private int currencyTypeId;
	private AddressDTO address;
	private BusinessDetailsDTO businessDetails;
	private int statusId;
	private boolean enabled;
	private boolean includeContactName;
	private boolean includeContactTelephone;
	private Timestamp expirationDate;
	private List<PhotoDTO> photos;
	private List<EnquiryMapDTO> enquiries;
	private List<ListingPackageDTO> packages;
	private List<MetaDataApproval> metaDataApprovals;
	private int pendingCount;
	private int rejectedCount;
	private int enquiryUnreadCount;
	private int pageHitCount;
	
	public ListingDTO() {}
	
	public ListingDTO(Listing listing, boolean isPreview) {
		this.listingId = listing.getListingId();
		this.listingURI = listing.getListingURI();
		this.price = listing.getPrice();
		this.currencyTypeId = listing.getCurrencyTypeId();
		this.statusId = listing.getStatusId();
		this.enabled = listing.isEnabled();
		this.includeContactName = listing.isIncludeContactName();
		this.includeContactTelephone = listing.isIncludeContactTelephone();
		this.expirationDate = listing.getExpirationDate();
		this.title = listing.getTitle();
		this.description = listing.getDescription();
		
		if (listing.getMetaDataApprovals() != null && isPreview) {
			for (MetaDataApproval metaData : listing.getMetaDataApprovals()) {
				if (metaData.getTypeId() == MetaDataType.TITLE.getId()){this.title = metaData.getValue();}
				if (metaData.getTypeId() == MetaDataType.DESCRIPTION.getId()){this.description = metaData.getValue();}
			}
		}
		
		if (listing.getApplication() != null) {
			this.application = new ApplicationDTO(listing.getApplication());
		}
		
		if (listing.getUser() != null) {
			this.user = new UserDTO(listing.getUser());
		}
		
		if (listing.getCategory() != null) {
			this.category = new CategoryDTO(listing.getCategory());
			this.subCategory = new SubCategoryDTO(listing.getSubCategory());
		}
		
		if (listing.getAddress() != null) {
			this.address = new AddressDTO(listing.getAddress());
		}
		
		if (listing.getBusinessDetails() != null) {
			this.businessDetails = new BusinessDetailsDTO(listing.getBusinessDetails(), listing.getMetaDataApprovals(), isPreview);
		}
		
		this.photos = new ArrayList<PhotoDTO>();
		for (Photo photo : listing.getPhotos()) {
			if (isPreview || (!isPreview && photo.getStatus() == PhotoStatus.APPROVED)) {
				this.photos.add(new PhotoDTO(photo));
			}
			
			if (photo.getStatus() == PhotoStatus.PENDING_REVIEW) {
				pendingCount++;
			} else if (photo.getStatus() == PhotoStatus.REJECTED) {
				rejectedCount++;
			}
		}
		
		if (listing.getEnquiries() != null) {
			this.enquiries = new ArrayList<EnquiryMapDTO>();
			for (EnquiryMap enquiryMap : listing.getEnquiries()) {
				this.enquiries.add(new EnquiryMapDTO(enquiryMap)); 
				
				for(EnquiryPost post : enquiryMap.getPosts()) {
					if (!post.isRead() && post.getAuthorId() != user.getUserId()) {
						enquiryUnreadCount++;
					}
				}
			}
		}
		
		if (listing.getPackages() != null) {
			this.packages = new ArrayList<ListingPackageDTO>();
			for (ListingPackage listingPackage : listing.getPackages()) {
				this.packages.add(new ListingPackageDTO(listingPackage)); 
			}
		}
		
		if (listing.getMetaDataApprovals() != null) {
			this.metaDataApprovals = listing.getMetaDataApprovals();
			
			setMetaDataStatusCounts();
		}
	}
	
	@JsonIgnore
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.LISTING.getKey(), listingId);
	}
	public String getPrimaryToken() {
		return RequestUtil.getToken(TokenFieldType.PRIMARY_ID.getKey(), listingId);
	}
	
	public ApplicationDTO getApplication() {
		return application;
	}
	public void setApplication(ApplicationDTO application) {
		this.application = application;
	}

	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	
	public SubCategoryDTO getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategoryDTO subCategory) {
		this.subCategory = subCategory;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
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
		return listingURI + ".html";
	}
	
	public String getUrlFormatted() {
		String listingUrl = "/" + address.getCity()
		+ "/" + ProvinceType.get(address.getProvince()).getLongNameEng()
		+ "/" + CountryType.getByShortName(address.getCountry()).getLongName()
		+ "/" + listingURI + ".html";
		
		return listingUrl;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPriceFormatted() {
		return getCurrencyFormatted(price);
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

	public AddressDTO getAddress() {
		if (address == null) {
			address = new AddressDTO();
		}
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public BusinessDetailsDTO getBusinessDetails() {
		return businessDetails;
	}
	public void setBusinessDetails(BusinessDetailsDTO businessDetails) {
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
	public boolean isDraft() {
		return getStatus() == ListingStatus.DRAFT;
	}
	public boolean isActive() {
		return getStatus() == ListingStatus.ACTIVE;
	}
	public boolean isExpired() {
		return getStatus() == ListingStatus.EXPIRED;
	}
	public boolean isSold() {
		return getStatus() == ListingStatus.SOLD;
	}
	public boolean isStatusEditable() {
		return getStatus() == ListingStatus.ACTIVE || getStatus() == ListingStatus.SUSPENDED || getStatus() == ListingStatus.SOLD;
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
	public String getExpirationDateFormatted() {
		if(expirationDate == null) {
			return "";
		}
		String parsedDate = DateUtil.getStringDate(expirationDate.getTime(), ProjectUtil.getProperty("standard.date.format"));
		return parsedDate;
	}
	
	public List<EnquiryMapDTO> getEnquiries() {
		return enquiries;
	}
	public void setEnquiries(List<EnquiryMapDTO> enquiries) {
		this.enquiries = enquiries;
	}
	
	public String getEnquiryCountColor() {
		return (enquiryUnreadCount > 0 ? "red" : "blue");
	}
	
	public int getEnquiryUnreadCount() {
		return enquiryUnreadCount;
	}
	public void setEnquiryUnreadCount(int enquiryUnreadCount) {
		this.enquiryUnreadCount = enquiryUnreadCount;
	}

	public int getPageHitCount() {
		return pageHitCount;
	}
	public void setPageHitCount(int pageHitCount) {
		this.pageHitCount = pageHitCount;
	}

	public List<ListingPackageDTO> getPackages() {
		return packages;
	}
	public void setPackages(List<ListingPackageDTO> packages) {
		this.packages = packages;
	}
	
	public List<MetaDataApproval> getMetaDataApprovals() {
		return metaDataApprovals;
	}
	public void setMetaDataApprovals(List<MetaDataApproval> metaDataApprovals) {
		this.metaDataApprovals = metaDataApprovals;
	}
	public void setMetaDataStatusCounts() {
		for (MetaDataApproval metaDataApproval : metaDataApprovals) {
			if (metaDataApproval.getStatusId() == MetaDataStatus.PENDING_REVIEW.getId()) {
				pendingCount++;
			} else if (metaDataApproval.getStatusId() == MetaDataStatus.REJECTED.getId()) {
				rejectedCount++;
			}
		}
	}
	
	public int getPendingCount() {
		return pendingCount;
	}
	
	public int getRejectedCount() {
		return rejectedCount;
	}
	
	public String getIcon() {
		if (enabled){
			return "down";
		} else {
			return "up";
		}
	}
	
	public String getOppositeIcon() {
		if (enabled){
			return "up";
		} else {
			return "down";
		}
	}
	
	public String getToggleText() {
		if (enabled){
			return "Disable";
		} else {
			return "Enable";
		}
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

	public List<PhotoDTO> getPhotos() {
		if (photos == null) {
			photos = new ArrayList<PhotoDTO>();
		}
		return photos;
	}
	public void setPhotos(List<PhotoDTO> photos) {
		this.photos = photos;
	}
	
	public String getPhotoPath() {
		return AssetFolderUtil.getPath(AssetFolder.PHOTOS, application.getName(), listingId, true);
	}
	
	public String getCurrencyFormatted(BigDecimal currency) {
		Locale locale = Locale.getDefault();
		String currencyFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(currency, locale, getCurrency().getShortName());
		return currencyFormatted;
	}
	
	public String getThemePhoto() {
		for (PhotoDTO photo : photos) {
			if (photo.getPhotoType() == PhotoType.THEME) {
				return photo.getFileName();
			}
		}
		
		return "";
	}
}