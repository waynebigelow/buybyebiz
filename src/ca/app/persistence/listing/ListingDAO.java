package ca.app.persistence.listing;

import java.util.List;

import ca.app.model.category.Category;
import ca.app.model.listing.Listing;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.paging.Page;

public interface ListingDAO {
	public void insert(Listing listing);
	public void update(Listing listing);
	public void delete(Listing listing);
	
	public Listing getByListingId(int listingId);
	public Listing getByListingURI(String listingURI);
	
	public List<Listing> getAllActiveListings();
	public List<Category> getListedCategories(int applicationId);
	public List<String> getLocationsByCategoryId(int categoryId);
	public List<Listing> getAllListingsByUserId(int userId);
	public List<Listing> getActiveListingsByUserId(int userId);
	
	public Page<ListingDTO> searchListingByPage(Page<ListingDTO> page);
}