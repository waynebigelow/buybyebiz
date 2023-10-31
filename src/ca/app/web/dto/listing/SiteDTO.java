package ca.app.web.dto.listing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import ca.app.model.common.MapData;
import ca.app.model.listing.ListingStatus;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.CurrencyFormatUtil;
import ca.app.web.dto.photo.PhotoDTO;
import ca.app.web.dto.user.ContactDTO;

public class SiteDTO {
	private ListingDTO listing;
	private ContactDTO contact;
	private List<PhotoDTO> photoList;
	private PhotoDTO profilePhoto;
	private PhotoDTO themePhoto;
	private MapData mapData;
	private String currencyCode;
	private String localeCode;
	
	public SiteDTO() {}
	
	public SiteDTO(ListingDTO listing, ContactDTO contact, List<PhotoDTO> photoList, PhotoDTO profilePhoto, PhotoDTO themePhoto) {
		this.listing = listing;
		this.contact = contact;
		this.photoList = photoList;
		this.profilePhoto = profilePhoto;
		this.themePhoto = themePhoto;
		//this.mapData = new MapData(listing.getAddress().getFormattedString(), listing.getAddress().getZoomForAddress(), "200");
		this.currencyCode = listing.getCurrency().getShortName();
	}
	
	public ListingDTO getListing() {
		return listing;
	}
	public void setListing(ListingDTO listing) {
		this.listing = listing;
	}
	
	public ContactDTO getContact() {
		return contact;
	}
	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	public List<PhotoDTO> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<PhotoDTO> photoList) {
		this.photoList = photoList;
	}

	public PhotoDTO getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(PhotoDTO profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public PhotoDTO getThemePhoto() {
		return themePhoto;
	}
	public void setThemePhoto(PhotoDTO themePhoto) {
		this.themePhoto = themePhoto;
	}

	public MapData getMapData() {
		return mapData;
	}
	public void setMapData(MapData mapData) {
		this.mapData = mapData;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	
	public boolean isDraft() {
		return listing.getStatus() == ListingStatus.DRAFT;
	}
	
	public String getCurrencyFormatted(BigDecimal currency) {
		Locale locale = Locale.getDefault();
		String currencyFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(currency, locale, currencyCode);
		return currencyFormatted;
	}
	
	public String getPhotoPath() {
		return AssetFolderUtil.getPath(AssetFolder.PHOTOS, listing.getApplication().getName(), listing.getListingId(), true);
	}
	
	public String getApplicationPath() {
		return AssetFolderUtil.getPath(AssetFolder.APPLICATION, listing.getApplication().getName(), 0, true);
	}
}