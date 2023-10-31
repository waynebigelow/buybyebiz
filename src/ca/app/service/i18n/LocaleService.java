package ca.app.service.i18n;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.app.model.i18n.GlobalLocale;
import ca.app.model.i18n.ListingLocale;
import ca.app.model.user.AppUser;

public interface LocaleService {
	public List<GlobalLocale> getAllGlobalLocales();
	public List<ListingLocale> getListingLocales(int listingId);
	public void create(ListingLocale listingLocale);
	public void update(ListingLocale listingLocale);
	public void delete(ListingLocale listingLocale);
	public void deleteAll(int listingId);
	public boolean isLocaleSupported(Locale locale);
	public Locale resolveSessionLocale(HttpServletRequest request, HttpServletResponse response, AppUser user, List<Locale> supportedLocales, Locale defaultLocale);
	public Locale resolveToSupportedLocale(Locale locale, List<Locale> supportedLocales);
}