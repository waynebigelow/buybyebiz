package ca.app.persistence.listing;

import java.util.List;

import ca.app.model.listing.EnquiryMap;
import ca.app.model.user.User;

public interface EnquiryMapDAO {
	public void create(EnquiryMap enquiryMap);
	
	public EnquiryMap getByEnquiryMapId(int enquiryMapId);
	public EnquiryMap getByListingIdPosterId(int listingId, int posterId);

	public List<EnquiryMap> getByPosterId(User poster);
	public List<EnquiryMap> getAllByListingId(int listingId);
}