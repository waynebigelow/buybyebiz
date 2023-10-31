package ca.app.web.dto.listing;

import java.io.Serializable;

import ca.app.model.listing.ListingPackage;
import ca.app.web.dto.application.ApplicationPackageDTO;

public class ListingPackageDTO implements Serializable {
	private static final long serialVersionUID = 2L;

	private int listingPackageId;
	private int listingId;
	private PurchaseDTO purchase;
	private ApplicationPackageDTO applicationPackage;
	
	public ListingPackageDTO() {}
	
	public ListingPackageDTO(ListingPackage listingPackage) {
		this.listingPackageId = listingPackage.getListingPackageId();
		this.listingId = listingPackage.getListingId();
		this.purchase = new PurchaseDTO(listingPackage.getPurchase());
		this.applicationPackage = new ApplicationPackageDTO(listingPackage.getApplicationPackage());
	}
	
	public int getListingPackageId() {
		return listingPackageId;
	}
	public void setListingPackageId(int listingPackageId) {
		this.listingPackageId = listingPackageId;
	}

	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	
	public PurchaseDTO getPurchase() {
		return purchase;
	}
	public void setPurchase(PurchaseDTO purchase) {
		this.purchase = purchase;
	}
	
	public ApplicationPackageDTO getApplicationPackage() {
		return applicationPackage;
	}
	public void setApplicationPackage(ApplicationPackageDTO applicationPackage) {
		this.applicationPackage = applicationPackage;
	}
}