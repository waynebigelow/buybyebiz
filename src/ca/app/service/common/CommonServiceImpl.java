package ca.app.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.common.HashToken;
import ca.app.model.common.ListingLead;
import ca.app.model.common.SupportIssue;
import ca.app.persistence.common.HashTokenDAO;
import ca.app.persistence.common.ListingLeadDAO;
import ca.app.persistence.common.SupportIssueDAO;
import ca.app.web.paging.Page;

public class CommonServiceImpl implements CommonService {
	@Autowired
	private HashTokenDAO hashTokenDAO;
	@Autowired
	private SupportIssueDAO supportIssueDAO;
	@Autowired
	private ListingLeadDAO listingLeadDAO;

	public void saveHashToken(HashToken token) {
		hashTokenDAO.insert(token);
	}
	
	public void deleteHashToken(HashToken token) {
		hashTokenDAO.delete(token);
	}
	
	public void deleteByUserIdTypeId(int userId, int typeId) {
		hashTokenDAO.deleteByUserIdTypeId(userId, typeId);
	}

	public HashToken getHashToken(String hash) {
		return hashTokenDAO.getByHash(hash);
	}
	
	// Support Issue
	public void save(SupportIssue issue) {
		supportIssueDAO.insert(issue);
	}
	
	public void update(SupportIssue issue) {
		supportIssueDAO.update(issue);
	}
	
	public void delete(SupportIssue issue) {
		supportIssueDAO.delete(issue);
	}
	
	// Listing Leads
	public void save(ListingLead listingLead) {
		listingLeadDAO.insert(listingLead);
	}
	
	public void update(ListingLead listingLead) {
		listingLeadDAO.update(listingLead);
	}
	
	public void delete(ListingLead listingLead) {
		listingLeadDAO.delete(listingLead);
	}
	
	public ListingLead getByListingLeadId(int listngLeadId) {
		return listingLeadDAO.getByListingLeadId(listngLeadId);
	}
	
	public ListingLead getByEmail(String emailAddress) {
		return listingLeadDAO.getByEmail(emailAddress);
	}
	
	public List<ListingLead> getAvailableLeads() {
		return listingLeadDAO.getAvailableLeads();
	}
	
	public Page<ListingLead> searchLeadsByPage(Page<ListingLead> page) {
		return listingLeadDAO.searchLeadsByPage(page);
	}
	
	public void setOneTimeHashTokenDAO(HashTokenDAO oneTimeHashTokenDAO) {
		this.hashTokenDAO = oneTimeHashTokenDAO;
	}
	public void setSupportIssueDAO(SupportIssueDAO supportIssueDAO) {
		this.supportIssueDAO = supportIssueDAO;
	}
	public void setListingLeadDAO(ListingLeadDAO listingLeadDAO) {
		this.listingLeadDAO = listingLeadDAO;
	}
}