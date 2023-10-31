package ca.app.persistence.listing;

import java.util.List;

import ca.app.model.listing.ListingPackage;

public interface ListingPackageDAO {
	public void insert(ListingPackage listingPackage);
	public void delete(ListingPackage listingPackage);
	public void deleteByPackageId(int packageId);
	
	public ListingPackage getByPackageId(int packageId);
	
	public List<ListingPackage> getAllPackages();
	
	public int getPromoCount(int applicationId, int typeId, int currencyTypeId);
}