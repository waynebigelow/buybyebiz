package ca.app.service.corporate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationPackageType;
import ca.app.model.common.CurrencyType;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.util.ProjectUtil;

public class LoadListItController extends BaseController {
	@Autowired
	private ListingService listingService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(getAuthenticatedUser());
		
		Application application = (Application)request.getAttribute("application");
		int usedCount = listingService.getPromoCount(application.getApplicationId(), ApplicationPackageType.TRIAL.getId(), CurrencyType.CAD.getId());
		int promoCount = ProjectUtil.getIntProperty("business.listing.promo.count");
		
		ModelAndView mav = new ModelAndView("/corporate/listIt");
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("isPromoActive", usedCount < promoCount);
		mav.addObject("promoRemaing", (promoCount - usedCount));
		return mav;
	}
}