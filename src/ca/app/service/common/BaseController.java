package ca.app.service.common;

import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import ca.app.model.common.CountryType;
import ca.app.model.common.ProvinceType;
import ca.app.model.listing.Listing;
import ca.app.model.user.AppUser;
import ca.app.util.ProjectUtil;
import ca.app.util.ReCaptchaUtil;
import ca.app.util.RequestUtil;

public abstract class BaseController extends MultiActionController implements Controller, MessageSourceAware {
	
	private MessageSource messageSource;

	protected AppUser getAuthenticatedUser() {
		AppUser user = null;
		Object obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof AnonymousAuthenticationToken) {
			user = AppUser.getAnonymousUser();
		} else {
			if (SecurityContextHolder.getContext() != null) {
				if (SecurityContextHolder.getContext().getAuthentication() != null) {
					if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
						user = (AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					}
				}
			}
			
			if (user == null) {
				user = AppUser.getAnonymousUser();
			}
		}
		
		return user;
	}
	
	protected String createListingURI(String listingURI, Listing listing) {
		if (listingURI == null) {
			String[] listingURIArray = listing.getListingURI().split("/");
			listingURI = listingURIArray[listingURIArray.length - 1];
		}
		
		String listingUrl = (listing.getAddress().getCity() != null && !listing.getAddress().getCity().equals("") ? listing.getAddress().getCity() : "")
		+ "/" + ProvinceType.get(listing.getAddress().getProvince()).getLongNameEng()
		+ "/" + CountryType.getByShortName(listing.getAddress().getCountry()).getLongName()
		+ "/" + listingURI + "-For-Sale-" + System.currentTimeMillis();
		
		return listingUrl;
	}
	
	public boolean isReCaptchaValid(HttpServletRequest request) throws Exception{
		String recaptchaResponse = request.getParameter("g-recaptcha-response");
		if (recaptchaResponse == null){
			recaptchaResponse = request.getParameter("recaptchaResponse");
		}
		String remoteAddr = request.getRemoteAddr();
		
		return ReCaptchaUtil.isValid(remoteAddr, recaptchaResponse);
	}
	
	public boolean isValidEmail(String email) {
		Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
		return emailPattern.matcher(email).matches();
	}
	
	protected String getBaseDir() {
		return ProjectUtil.getProperty("upload.location");
	}
	
	protected String getBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/home.html";
	}
	
	protected String getProperty(String propName) {
		return ProjectUtil.getProperty(propName);
	} 
	
	public String getMsg(Locale locale, String code, Object[] args) {
		return messageSource.getMessage(code, args, locale);
	}
	
	public String getMsg(HttpServletRequest request, String code) {
		return messageSource.getMessage(code, null, getLocale(request));
	}
	
	public String getMsg(HttpServletRequest request, String code, Object[] args) {
		return messageSource.getMessage(code, args, getLocale(request));
	}

	public Locale getLocale(HttpServletRequest request) {
		return getLocaleStatic(request);
	}
	
	public static Locale getLocaleStatic(HttpServletRequest request) {
		Locale locale = (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	public String getToken(TokenFieldType tokenFieldType, int id) {
		return RequestUtil.getToken(tokenFieldType.getKey(), id);
	}
	
	public void setMessageSource (MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public MessageSource getMessageSource() {
		return this.messageSource;
	}
}