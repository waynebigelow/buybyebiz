package ca.app.service.i18n;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import ca.app.model.i18n.GlobalLocale;
import ca.app.model.i18n.ListingLocale;
import ca.app.model.user.AppUser;
import ca.app.persistence.i18n.GlobalLocaleDAO;
import ca.app.persistence.i18n.ListingLocaleDAO;
import ca.app.util.LocaleUtil;
import ca.app.util.LogUtil;

public class LocaleServiceImpl implements LocaleService {
	@Autowired
	private GlobalLocaleDAO globalLocaleDAO;
	@Autowired
	private ListingLocaleDAO listingLocaleDAO;

	
	public List<GlobalLocale> getAllGlobalLocales() {
		return this.globalLocaleDAO.getAllGlobalLocales();
	}
	
	public List<ListingLocale> getListingLocales(int listingId) {
		return this.listingLocaleDAO.getListingLocales(listingId);
	}
	
	public void create(ListingLocale listingLocale) {
		this.listingLocaleDAO.create(listingLocale);
	}
	
	public void update(ListingLocale listingLocale) {
		this.listingLocaleDAO.update(listingLocale);
	}

	public void delete(ListingLocale listingLocale) {
		this.listingLocaleDAO.delete(listingLocale);
	}
	
	public void deleteAll(int listingId) {
		this.listingLocaleDAO.deleteAll(listingId);
	}

	public boolean isLocaleSupported(Locale locale) {
		for(GlobalLocale gl: getAllGlobalLocales()){
			if(gl.getLocale().getLanguage().equals(locale.getLanguage()) && gl.getLocale().getCountry().equals(locale.getCountry())){
				return true;
			}
		}
		return false;
	}
	
	public Locale resolveSessionLocale(HttpServletRequest request, HttpServletResponse response, AppUser user, List<Locale> supportedLocales, Locale defaultLocale) {
		LocaleResolver lc = RequestContextUtils.getLocaleResolver(request);
		Locale locale = null;
		
		// The Spring SessionLocaleResolver determines the locale by:
		// - If a locale exists against the session, then use it
		// - otherwise use the locale given by the browser as the preferred locale
		Locale sessionLocale = lc.resolveLocale(request);
		LogUtil.logDebug(this.getClass(), "SessionLocaleResolver yields: " + sessionLocale);
		
		String siteLanguage = request.getParameter("siteLanguage");
		if (siteLanguage!=null && !siteLanguage.equals(sessionLocale.toString())) {
			// If this is a language change, then use the given locale
			locale = LocaleUtils.toLocale(siteLanguage);
			LogUtil.logDebug(this.getClass(), "Language change: " + siteLanguage + "->" + locale);
			
			// Update the user's preferred locale.
			if (user.getUserId()>0) {
				user.setPreferredLocale(locale.toString());
				//userDAO.updatePreferredLocale(user.getUserId(), locale.toString());
				LogUtil.logDebug(this.getClass(), "Updated user's preferred locale[" + user.getUserId() + "]: " + locale.toString());
			}
		} else if (user.getUserId()>0 && user.getPreferredLocale()!=null && user.getPreferredLocale().length()>0) {
			// User is logged in - check for a preferred locale
			locale = LocaleUtil.toLocale(user.getPreferredLocale());
			LogUtil.logDebug(this.getClass(), "User has a preferred locale: " + user.getPreferredLocale());
		} else {
			locale = sessionLocale;
		}
		
		if (locale!=null) {
			// Check any locale that was determined against the supported locales
			locale = resolveToSupportedLocale(locale, supportedLocales);
			LogUtil.logDebug(this.getClass(), "SupportedLocaleResolver yields: " + locale);
		}
			
		if (locale==null) {
			// If no locale chosen by now, use the given default
			locale = defaultLocale;
			LogUtil.logDebug(this.getClass(), "Using given default locale: " + locale);
		}
		
		if (locale==null) {
			// If no locale chosen by now, use the server default
			locale = Locale.getDefault();
			LogUtil.logDebug(this.getClass(), "Using server default locale: " + locale);
		}
		lc.setLocale(request, response, locale);

		if (user.getUserId()>0 && (user.getPreferredLocale()==null || user.getPreferredLocale().length()==0)) {
			// If user's preferred locale has never been set then initialize it
			user.setPreferredLocale(locale.toString());
			//userDAO.updatePreferredLocale(user.getUserId(), locale.toString());
			LogUtil.logDebug(this.getClass(), "Initialized user's preferred locale[" + user.getUserId() + "]: " + locale.toString());
		}
		
		return locale;
	}
	
	public Locale resolveToSupportedLocale(Locale locale, List<Locale> supportedLocales) {
		if (supportedLocales!=null) {
			// Try to match the full Locale
			for (Locale supportedLocale: supportedLocales) {
				if (supportedLocale.toString().equalsIgnoreCase(locale.toString())) {
					return supportedLocale;
				}
			}
			
			// Try to match the language
			for (Locale supportedLocale: supportedLocales) {
				String localeLang = locale.toString().substring(0,2);
				String supportedLang = supportedLocale.toString().substring(0,2);
				if (supportedLang.equalsIgnoreCase(localeLang)) {
					return supportedLocale;
				}
			}
		}
		return null;
	}
	
	public void setGlobalLocaleDAO(GlobalLocaleDAO globalLocaleDAO) {
		this.globalLocaleDAO = globalLocaleDAO;
	}
	
	public void setListingLocaleDAO(ListingLocaleDAO listingLocaleDAO) {
		this.listingLocaleDAO = listingLocaleDAO;
	}
}