package ca.app.service.admin;

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
import ca.app.service.mail.MailService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveListingStatusController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private MailService mailService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(SaveListingStatusController.class, "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		int statusId = StringUtil.convertStringToInt(request.getParameter("listingStatus"), 0);
		
		Listing listing = listingService.getByListingId(listingId);
		listing.setStatusId(statusId);
		
		if (listing.getStatus() == ListingStatus.ACTIVE && listing.getExpirationDate() == null) {
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
			purchase.setPurchaserUserId(-999);
			purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
			listingService.create(purchase);
			
			ListingPackage listingPackage = new ListingPackage();
			listingPackage.setListingId(listingId);
			listingPackage.setApplicationPackage(applicationPackage);
			listingPackage.setPurchase(purchase);
			listingService.create(listingPackage);
			
			List<ListingPackage> packages = new ArrayList<ListingPackage>();
			packages.add(listingPackage);
			
			listing.setEnabled(true);
			listing.setExpirationDate(listingService.calculateExpiryDate(packages));
			listingService.update(listing, appUser, ActivityType.LISTING_PUBLISHED);
			
			mailService.sendSiteActivated(listing, applicationPackage.getDuration());
		} else if (listing.getStatus() == ListingStatus.ACTIVE) {
			listing.setEnabled(true);
			listingService.update(listing, appUser, ActivityType.LISTING_ACTIVATED);
		} else {
			listing.setEnabled(false);
			listingService.update(listing);
		}
		return null;
	}
}