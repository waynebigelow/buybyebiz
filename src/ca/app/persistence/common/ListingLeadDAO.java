package ca.app.persistence.common;

import java.util.List;

import ca.app.model.common.ListingLead;
import ca.app.web.paging.Page;

public interface ListingLeadDAO {
	public void insert(ListingLead listingLead);
	public void update(ListingLead listingLead);
	public void delete(ListingLead listingLead);
	
	public ListingLead getByListingLeadId(int listngLeadId);
	public ListingLead getByEmail(String emailAddress);
	
	public List<ListingLead> getAvailableLeads();
	public Page<ListingLead> searchLeadsByPage(Page<ListingLead> page);
}