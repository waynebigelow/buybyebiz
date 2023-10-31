package ca.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ca.app.model.application.Application;
import ca.app.model.i18n.GlobalLocale;
import ca.app.model.listing.Listing;
import ca.app.model.listing.URLUtil;
import ca.app.model.user.AppUser;
import ca.app.service.common.TokenFieldType;
import ca.app.service.i18n.LocaleService;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.util.ThreadLocalUtil;

public class RequestCheckFilter implements Filter {
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		
		if(arg0 instanceof HttpServletRequest) {
			request = (HttpServletRequest)arg0;
			response = (HttpServletResponse)arg1;
			
			Enumeration<String> pragmaHeaders = request.getHeaders("Pragma");
			if (pragmaHeaders != null) {
				while (pragmaHeaders.hasMoreElements()) {
					String value = pragmaHeaders.nextElement();
					if (value != null && value.equalsIgnoreCase("getIfoFileURI.dlna.org")) {
						return;
					}
				}
			}
			
			if (!request.getRequestURI().endsWith("/status.jsp")) {
				loadSupportedLocalesIntoSession(request);
				if (!checkRequest(request,response)) {
					return;
				}
			}
		}
		
		arg2.doFilter(arg0, arg1);
		
		ThreadLocalUtil.cleanup();
	}
	
	@SuppressWarnings("unchecked")
	private void loadSupportedLocalesIntoSession(HttpServletRequest request) {
		List<Locale> locales = null;
		locales = (List<Locale>)request.getSession().getAttribute("SUPPORTED_LOCALES");
		
		if (locales == null) {
			LocaleService localeService = (LocaleService)getWAC(request).getBean("localeService");
			List<GlobalLocale> globalLocales = localeService.getAllGlobalLocales();
			
			locales = new ArrayList<Locale>();
			for (GlobalLocale gl : globalLocales) {
				locales.add(gl.getLocale());
			}
			
			request.getSession().setAttribute("SUPPORTED_LOCALES", locales);
		}
	}
	
	private boolean checkRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isSuperAdminRequest = request.getRequestURI().contains("/appAdmin/");
		boolean isUserAdminRequest = request.getRequestURI().contains("/userAdmin/");
		boolean isListingAdminRequest = request.getRequestURI().contains("/listingAdmin/");
		
		AppUser appUser = getAuthenticatedUser();
		Application requestApplication = (Application)request.getAttribute("application");
		if (isSuperAdminRequest) {
			if (!appUser.hasRole("ROLE_SUPER_ADMIN")) {
				LogUtil.logWarn(getClass(), "appUser: ["+appUser.toString()+"] attempted to access superAdmin interface without proper role");
				response.sendRedirect(getBaseURL(request));
				return false;
			}
		} else if (isUserAdminRequest && !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			if (!appUser.hasRole("ROLE_ACCOUNT_OWNER")) {
				LogUtil.logWarn(getClass(), "appUser: ["+appUser.toString()+"] attempted to access userAdmin interface without proper role");
				response.sendRedirect(getBaseURL(request));
				return false;
			}
		} else if (isListingAdminRequest && !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			if (!appUser.hasRole("ROLE_LISTING_OWNER")) {
				LogUtil.logWarn(getClass(), "appUser: ["+appUser.toString()+"] attempted to access listingAdmin interface without proper role");
				response.sendRedirect(getBaseURL(request));
				return false;
			}
			
			ListingService listingService = (ListingService)getWAC(request).getBean("listingService");
			Listing listing = null;
			
			int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
			
			String listingURI = null;
			if (request.getRequestURI().indexOf(".html") > -1) {
				listingURI = request.getRequestURI().substring(0, request.getRequestURI().indexOf(".html"));
				listingURI = listingURI.substring((request.getContextPath().length()+1), listingURI.length());
			}
			
			if (listingId > 0) {
				listing = (Listing)listingService.getByListingId(listingId);
			} else if (listingURI != null) {
				listing = listingService.getByListingURI(listingURI);
			}
			
			if (listing != null) {
				if (!appUser.isListingOwner(listing.getListingId())) {
					LogUtil.logWarn(getClass(), "appUser: ["+appUser.toString()+"] attempted to access wrong listing: ["+listingId+"]");
					response.sendRedirect(getBaseURL(request));
					return false;
				} else {
					if (listing.getApplication().getApplicationId() != requestApplication.getApplicationId()) {
						String redirectURL = requestApplication.getSecureApplicationURL() + listingURI;
						LogUtil.logWarn(getClass(), "appUser: ["+appUser.toString()+"] attempted to access listing in wrong app, user redirected: ["+redirectURL+"]");
						sendPermanentRedirect(response, redirectURL);
						return false;
					}
				}
			}
		}
		
		return isFileTypeValid(request.getRequestURI(), requestApplication);
	}
	
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
	
	private String getBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/home.html";
	}
	
	private void sendPermanentRedirect(HttpServletResponse response, String url) throws IOException {
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", url);
		response.flushBuffer();
	}
	
	private static WebApplicationContext getWAC(HttpServletRequest request) {
		return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	}

	public String getSecureListingURL(Application application, Listing listing) {
		return application.getSecureApplicationURL() + getListingURI(listing);
	}
	
	public String getListingURI(Listing listing) {
		return URLUtil.buildListingURI(listing.getListingURI());
	}
	
	private boolean isFileTypeValid(String fileName, Application application) {
		if (!fileName.contains("/app/"+application.getName().toLowerCase()+"/")) {
			return true;
		}
		
		if (fileName.contains("deleted_")) {
			LogUtil.logWarn(getClass(), "Attempt to access deleted file: " + fileName + ".");
			return false;
		}
		
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		
		if (extension != null && extension.contains("?")) {
			extension = extension.substring(0, extension.indexOf("?") - 1);
		}
		
		String validTypes = ProjectUtil.getProperty("allowed.file.types");
		boolean retValue = validTypes.contains("," + extension + ",");
		
		if (!retValue) {
			LogUtil.logWarn(getClass(), "Attempt to access prohibited file: " + fileName + ", extension : " + extension);
		}
		
		return retValue;
	}
	
	public void init(FilterConfig arg0) throws ServletException {}
	
	public void destroy() {}
}