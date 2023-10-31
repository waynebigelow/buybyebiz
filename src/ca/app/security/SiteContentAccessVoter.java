package ca.app.security;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;

import ca.app.model.listing.Listing;
import ca.app.model.user.AppUser;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

public class SiteContentAccessVoter extends RoleVoter {
	private static int MULTIPART_REQUEST_FLAG = -99;
	
	@Autowired
	private ListingService listingService;
	
	@SuppressWarnings("rawtypes") 
	public boolean supports(Class clazz) {
		return super.supports(clazz);
	}
	
	public boolean supports(ConfigAttribute attribute) {
		return super.supports(attribute);
	}
	
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) {
		AppUser appUser = null;
		Object obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof AnonymousAuthenticationToken) {
			appUser = AppUser.getAnonymousUser();
		} else {
			appUser = (AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		int listingId = 0;
		FilterInvocation filter = null;
		HttpServletRequest request = null;
		if (object instanceof FilterInvocation) {
			filter = (FilterInvocation)object;
			request = (HttpServletRequest)filter.getHttpRequest();
			
			String contentType = request.getContentType();
			if (contentType != null && contentType.startsWith("multipart/form-data")) {
				listingId = MULTIPART_REQUEST_FLAG;
			} else {
				listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
			}
		}

		if (listingId == 0) {
			if (request.getRequestURI().indexOf(".html") > -1) {
				String listingURI = request.getRequestURI().substring(0, request.getRequestURI().indexOf(".html"));
				listingURI = listingURI.substring((request.getContextPath().length()+1), listingURI.length());
				Listing listing = listingService.getByListingURI(listingURI);
				listingId = ((listing != null) ? listing.getListingId() : 0 );
			}
		}
		
		LogUtil.logDebug(this.getClass()," # REQUEST URL: " + "[listingId=" + listingId + "][" + request.getRequestURI()+ "]");
		
		int vote = determineVote(configAttributes, appUser, listingId);
		switch(vote) {
			case AccessDecisionVoter.ACCESS_ABSTAIN:
				LogUtil.logDebug(this.getClass()," # ROLE VOTER: ACCESS_ABSTAIN"); 
				break;
			case AccessDecisionVoter.ACCESS_GRANTED:
				LogUtil.logDebug(this.getClass()," # ROLE VOTER: ACCESS_GRANTED"); 
				break;
			case AccessDecisionVoter.ACCESS_DENIED:
				LogUtil.logDebug(this.getClass()," # ROLE VOTER: ACCESS_DENIED"); 
				break;
		}

		return vote;
	}
	
	private int determineVote(Collection<ConfigAttribute> configAttributes, AppUser user, int listingId) {
		Iterator<ConfigAttribute> iter = configAttributes.iterator();
		while (iter.hasNext()) {
			ConfigAttribute attr = (ConfigAttribute) iter.next();
			
			int vote = determineVote(user, attr.getAttribute(), listingId);
			if (vote != AccessDecisionVoter.ACCESS_ABSTAIN) {
				return vote;
			}
		}
		
		return AccessDecisionVoter.ACCESS_DENIED;
	}
	
	public static int determineVote(AppUser appUser, String role, int listingId) {
		if (appUser != null && appUser.hasRole(role)) {
			if ("ROLE_LISTING_OWNER".equals(role)) {
				if (listingId > 0) {
					if (appUser.isListingOwner(listingId)) {
						LogUtil.logDebug(SiteContentAccessVoter.class," # Role check for [" + role + "] resulted in ACCESS_GRANTED based on " + appUser.toString());
						return AccessDecisionVoter.ACCESS_GRANTED;
					}
				} else if (listingId == 0) {
					LogUtil.logDebug(SiteContentAccessVoter.class," # Role check for [" + role + "] resulted in ACCESS_GRANTED based on " + appUser.toString());
					return AccessDecisionVoter.ACCESS_GRANTED;
				} else if (listingId==MULTIPART_REQUEST_FLAG) {
					LogUtil.logDebug(SiteContentAccessVoter.class," # Role check for [" + role + "] resulted in ACCESS_GRANTED based on " + appUser.toString());
					return AccessDecisionVoter.ACCESS_GRANTED;
				}
			} else {
				LogUtil.logDebug(SiteContentAccessVoter.class," # Role check for [" + role + "] resulted in ACCESS_GRANTED based on " + appUser.toString());
				return AccessDecisionVoter.ACCESS_GRANTED;
			}
		}

		LogUtil.logDebug(SiteContentAccessVoter.class," # Role check for [" + role + "] resulted in ACCESS_ABSTAIN based on " + appUser.toString());
		return AccessDecisionVoter.ACCESS_ABSTAIN;
	}
	
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}
}