package ca.app.service.listing;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import ca.app.model.category.Category;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.listing.MetaDataApproval;
import ca.app.model.listing.Purchase;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.paging.Page;

public interface ListingService {
	// ------------------------------------------------------
	// The following methods are for Listing
	// ------------------------------------------------------
	public Listing getByListingId(int listingId);
	public Listing getByListingURI(String listingURI);	
	public void create(Listing listing, AppUser appUser);
	public void update(Listing listing);
	public void update(Listing listing, AppUser appUser, ActivityType activityType);
	public void delete(Listing listing, AppUser appUser);
	public List<Listing> getAllActiveListings();
	public List<Category> getListedCategories(int applicationId);
	public List<String> getLocationsByCategoryId(int categoryId);
	public List<ListingDTO> getAllListingsByUserId(int userId, boolean isPreview);
	public List<ListingDTO> getActiveListingsByUserId(int userId, boolean isPreview);
	public Page<ListingDTO> searchListingByPage(Page<ListingDTO> page);
	public Timestamp calculateExpiryDate(List<ListingPackage> packages);
	
	// ------------------------------------------------------
	// The following methods are for MetaDataApproval
	// ------------------------------------------------------
	public void processApprovals(Map<Integer, String> approvalMap, Listing listing) throws Exception;
	public void saveOrUpdate(MetaDataApproval metaData);
	public void deleteMetaDataApprovalsByIds(String approvedIds);
	public List<MetaDataApproval> loadMetaDataApprovalByListingId(int listingId);
	public List<MetaDataApproval> loadMetaDataRejectionsByListingId(int listingId);
	
	// ------------------------------------------------------
	// The following methods are for EnquiryMap
	// ------------------------------------------------------
	public void create(EnquiryMap enquiryMap);
	public EnquiryMap getByEnquiryMapId(int enquiryMapId);
	public EnquiryMap getByListingIdPosterId(int listingId, int posterId);
	public List<EnquiryMap> getByPosterId(User poster);
	public List<EnquiryMap> getAllByListingId(int listingId);
	
	// ------------------------------------------------------
	// The following methods are for EnquiryPost
	// ------------------------------------------------------
	public void create(EnquiryPost enquiry, Listing listing);
	public void create(EnquiryPost enquiry);
	public void markReadByEnquiryMapId(int enquiryMapId, int userId);
	
	// ------------------------------------------------------
	// The following methods are for ListingPackage
	// ------------------------------------------------------
	public void create(ListingPackage listingPackage);
	public void delete(ListingPackage listingPackage);
	public void deleteByPackageId(int packageId);
	public ListingPackage getByPackageId(int packageId);
	public List<ListingPackage> getAllPackages();
	public int getPromoCount(int applicationId, int typeId, int currencyTypeId);
	
	// ------------------------------------------------------
	// The following methods are for Purchase
	// ------------------------------------------------------
	public void create(Purchase purchase);
	public void saveOrUpdate(Purchase purchase);
	public void delete(Purchase purchase);
	public void deleteByPurchaseId(int purchaseId);
	public Purchase getByPurchaseId(int purchaseId);
	public List<Purchase> getAllPurchases();
}