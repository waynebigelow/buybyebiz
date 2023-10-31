package ca.app.service.corporate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationPackageType;
import ca.app.model.common.CurrencyType;
import ca.app.model.common.MetaPageSettings;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.web.dto.application.ApplicationDTO;

@Controller
public class LoadHomeController extends BaseController {
//	@Autowired
//	private SecurityService securityService;
	@Autowired
	private PageHitService pageHitService;
	@Autowired
	private ListingService listingService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		String loginRequired = request.getParameter("l");
		if (loginRequired != null) {
			pageAccess.setLoginRequired(true);
		}
		
		//user.getEmail(), user.getPassword(), user.isEnabled(), auths, user.getEmail(), user.getFirstName(), user.getLastName(), user.getPreferredLocale(), user.getUserId()
//		User user = new User();
//		user.setEmail("admin@buybyebiz.com");
//		user.setPassword("T@mmY3172011");
//		user.setEnabled(true);
//		user.setFirstName("admin");
//		user.setLastName("admin");
//		user.setPreferredLocale("en_US");
//		user.setUserId(1);
//		
//		String password = securityService.encodePasswordForUser(user);
		
		if (!appUser.isSuperAdmin()) {
			try {
				pageHitService.logPageHit(null, request, Usage.HOME);
			} catch (Exception ex) {
				LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
			}
		}
		
		Application application = (Application)request.getAttribute("application");
		int usedCount = listingService.getPromoCount(application.getApplicationId(), ApplicationPackageType.TRIAL.getId(), CurrencyType.CAD.getId());
		int promoCount = ProjectUtil.getIntProperty("business.listing.promo.count");
		
		ModelAndView mav = new ModelAndView("/corporate/home");
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("isPromoActive", usedCount < promoCount);
		mav.addObject("promoRemaing", (promoCount - usedCount));
		mav.addObject("metaPageSettings", getMetaPageSettings(request));
		return mav;
	}

	private MetaPageSettings getMetaPageSettings(HttpServletRequest request) {
		ApplicationDTO applicationDTO = new ApplicationDTO((Application)request.getAttribute("application"));
		
		MetaPageSettings metaPageSettings = new MetaPageSettings();
		metaPageSettings.setPageTitle(applicationDTO.getName() + " - Advertising Businesses for Sale");
		metaPageSettings.setMetaAuthor(applicationDTO.getName());
		metaPageSettings.setMetaDescription(applicationDTO.getDescription());
		metaPageSettings.setOgTitle(applicationDTO.getName() + " - Advertising Businesses for Sale");
		metaPageSettings.setOgDescription(applicationDTO.getDescription());
		metaPageSettings.setOgImage(applicationDTO.getSecureApplicationURL()+"/buybyemedia/app/buybyebiz/images/theme.png");
		
		return metaPageSettings;
	}
}