package ca.app.persistence.listing;

import ca.app.model.listing.BusinessDetails;

public interface BusinessDetailsDAO {

	public BusinessDetails getByDetailsId(int detailsId);
	public void saveOrUpdate(BusinessDetails details);
}