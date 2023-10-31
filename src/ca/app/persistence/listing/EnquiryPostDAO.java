package ca.app.persistence.listing;

import ca.app.model.listing.EnquiryPost;

public interface EnquiryPostDAO {
	public void create(EnquiryPost enquiry);
	public void markReadByEnquiryMapId(int enquiryMapId, int userId);
}