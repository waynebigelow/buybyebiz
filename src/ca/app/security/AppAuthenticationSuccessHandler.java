package ca.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.ServletRequestUtils;

import ca.app.model.common.HashTokenType;
import ca.app.model.listing.Listing;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.common.CommonService;
import ca.app.service.listing.ListingService;
import ca.app.service.user.UserService;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;

public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private SecurityService securityService;
	@Autowired
	private ListingService listingService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommonService commonService;
//	@Autowired
//	private AbstractRememberMeServices rememberMeServices;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		// Get user objects
		AppUser appUser = (AppUser)authentication.getPrincipal();
		User user = userService.getByUserId(appUser.getUserId());
		
		// Log successful login
		securityService.logSuccessfulLogin(user.getUserId());
		
		// Remove any password reset tokens
		commonService.deleteByUserIdTypeId(user.getUserId(), HashTokenType.PWD_RESET.getId());
		
		// Determine if the user should be forced to change their password
		boolean needsPwdChange = false;
		if(ProjectUtil.isEnforceMaxPasswordAge() && !user.isIgnorePasswordRules()) {
			if (user.getLastPasswordChange() != null) {
				long passwordChangeTime = user.getLastPasswordChange().getTime();
				long rightNow = System.currentTimeMillis();
				if((rightNow - passwordChangeTime) > (ProjectUtil.getMaxPasswordAge() * 86400000L)) {
					needsPwdChange = true;
				}
			} else {
				needsPwdChange = true;
			}
		}
		
		// When a user is logged in, the duration of their session must be of https
		String targetURL =  "https://" + request.getServerName();
		targetURL += ((request.getServerPort()!=80 && request.getServerPort()!=443)? ":" + request.getServerPort() : "");
		targetURL += request.getContextPath() + "/home.html";
		
		// For memorial logins, make sure we target the proper endpoint
		int listingId = ServletRequestUtils.getIntParameter(request, "p", -1);
		Listing listing = listingService.getByListingId(listingId);
		
		// Determine the rest of the target URL
		if (needsPwdChange){
			// User is required to change password.  Log them out and force them to the Change Password page.
			targetURL += "/security/changePassword.html";
			
			// Kill the RememberMe/SSO cookie if it exists.
			// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			// rememberMeServices.logout(request, response, auth);
			
			// Invalidate the session.
			SecurityContextHolder.clearContext();
			HttpSession session = request.getSession();
			if (session != null) {
				session.invalidate();
			}
			session = request.getSession(true);
			session.setAttribute("username", user.getEmail());
		} else {
			if (listing != null) {
				targetURL = targetURL.replaceAll("\\<listingId\\>", "" + listingId);
				targetURL = targetURL.replaceAll("\\<listingURI\\>", "" + listing.getListingURI());
			}
		}
		LogUtil.logDebug(this.getClass(), "Successful login Redirect: " + targetURL);
		
		getRedirectStrategy().sendRedirect(request, response, targetURL);
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
//	public void setRememberMeServices(AbstractRememberMeServices rememberMeServices) {
//		this.rememberMeServices = rememberMeServices;
//	}
}