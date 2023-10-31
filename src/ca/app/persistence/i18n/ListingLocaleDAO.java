package ca.app.persistence.i18n;

import java.util.List;

import ca.app.model.i18n.ListingLocale;

public interface ListingLocaleDAO {
	public List<ListingLocale> getListingLocales(int listingId);
	public void create(ListingLocale listingLocale);
	public void update(ListingLocale listingLocale);
	public void delete(ListingLocale listingLocale);
	public void deleteAll(int listingId);
}