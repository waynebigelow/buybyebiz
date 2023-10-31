package ca.app.persistence.listing;

import java.util.List;

import ca.app.model.listing.MetaDataApproval;

public interface MetaDataApprovalDAO {
	public void saveOrUpdate(MetaDataApproval metaData);
	public void deleteMetaDataApprovalsByIds(String approvedIds);
	
	public MetaDataApproval getByListingIdTypeId(int listingId, int typeId);
	
	public List<MetaDataApproval> loadMetaDataApprovalByListingId(int listingId);
	public List<MetaDataApproval> loadMetaDataRejectionsByListingId(int listingId);
}