package ca.app.service.usage;

import ca.app.model.listing.Listing;
import ca.app.model.usage.RefererType;

public interface RefererParsingService {
	
	public RefererType parseReferer(Listing listing, String rawReferer, String source, String s);
}