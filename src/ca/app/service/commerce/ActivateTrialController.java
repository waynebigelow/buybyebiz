package ca.app.service.commerce;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.ApplicationPackage;
import ca.app.model.application.ApplicationPackageType;
import ca.app.model.application.PackagePrice;
import ca.app.model.common.CurrencyType;
import ca.app.model.common.ProvinceType;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.listing.ListingStatus;
import ca.app.model.listing.Purchase;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.service.application.ApplicationService;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class ActivateTrialController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private ApplicationService applicationService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		ApplicationPackage applicationPackage = applicationService.getByAppAndTypeId(listing.getApplication().getApplicationId(), ApplicationPackageType.TRIAL.getId());
		CurrencyType currencyType = CurrencyType.getByCountryShortName(listing.getAddress().getCountryType().getShortName());
		PackagePrice packagePrice = applicationPackage.getPriceByCurrencyType(currencyType);
		
		Purchase purchase = new Purchase();
		purchase.setDescription(applicationPackage.getName());
		purchase.setPrice(packagePrice.getPrice());
		purchase.setTax(packagePrice.getTax());
		purchase.setTaxProvince(ProvinceType.ON.getShortName());
		purchase.setDiscount(applicationPackage.getAgentDiscount());
		purchase.setCurrencyTypeId(packagePrice.getCurrencyTypeId());
		purchase.setPurchaserUserId(appUser.getUserId());
		purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		listingService.saveOrUpdate(purchase);
		
		ListingPackage listingPackage = new ListingPackage();
		listingPackage.setListingId(listing.getListingId());	
		listingPackage.setApplicationPackage(applicationPackage);
		listingPackage.setPurchase(purchase);
		listingService.create(listingPackage);
		
		List<ListingPackage> packages = new ArrayList<ListingPackage>();
		packages.add(listingPackage);
		
		listing.setExpirationDate(listingService.calculateExpiryDate(packages));
		listing.setEnabled(true);
		listing.setStatusId(ListingStatus.ACTIVE.getId());
		listingService.update(listing, null, ActivityType.LISTING_PUBLISHED);
		return null;
	}
}