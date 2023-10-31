package ca.app.service.common;

import java.util.List;

import ca.app.model.common.HashToken;
import ca.app.model.common.ListingLead;
import ca.app.model.common.SupportIssue;
import ca.app.web.paging.Page;

public interface CommonService {
	public void saveHashToken (HashToken token);
	public void deleteHashToken (HashToken token);
	public void deleteByUserIdTypeId(int userId, int typeId);
	public HashToken getHashToken(String hash);
	
	public void save (SupportIssue issue);
	public void update (SupportIssue issue);
	public void delete(SupportIssue issue);
	
	public void save (ListingLead listingLead);
	public void update (ListingLead listingLead);
	public void delete(ListingLead listingLead);
	public ListingLead getByListingLeadId(int listngLeadId);
	public ListingLead getByEmail(String emailAddress);
	public List<ListingLead> getAvailableLeads();
	public Page<ListingLead> searchLeadsByPage(Page<ListingLead> page);
}