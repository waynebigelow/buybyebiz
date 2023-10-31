package ca.app.service.admin;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.ApplicationPackage;
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

@Controller
public class SavePaymentController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	ApplicationService applicationService;
	@Autowired
	private MailService mailService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		String transactionId = request.getParameter("transactionId");
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		int appPackageId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.APP_PACKAGE.getAlias()), TokenFieldType.APP_PACKAGE);
		ApplicationPackage applicationPackage = applicationService.getByPackageId(appPackageId);
		CurrencyType currencyType = CurrencyType.getByCountryShortName(listing.getAddress().getCountryType().getShortName());
		PackagePrice packagePrice = applicationPackage.getPriceByCurrencyType(currencyType);
		
		Purchase purchase = new Purchase();
		purchase.setTransactionId(transactionId);
		purchase.setDescription(applicationPackage.getName());
		purchase.setPrice(packagePrice.getPrice());
		purchase.setTax(packagePrice.getTax());
		purchase.setTaxProvince(ProvinceType.ON.getShortName());
		purchase.setDiscount(applicationPackage.getAgentDiscount());
		purchase.setCurrencyTypeId(packagePrice.getCurrencyTypeId());
		purchase.setTotal(getTotalPrice(packagePrice.getPrice(), packagePrice.getTax()));
		purchase.setPurchaserUserId(listing.getUser().getUserId());
		purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		listingService.saveOrUpdate(purchase);
		
		ListingPackage listingPackage = new ListingPackage();
		listingPackage.setListingId(listingId);
		listingPackage.setApplicationPackage(applicationPackage);
		listingPackage.setPurchase(purchase);
		listingService.create(listingPackage);
		
		listing = listingService.getByListingId(listingId);
		listing.setExpirationDate(listingService.calculateExpiryDate(listing.getPackages()));
		listing.setEnabled(true);
		listing.setStatusId(ListingStatus.ACTIVE.getId());
		listingService.update(listing, null, ActivityType.LISTING_EXTENDED);
		
		mailService.sendSiteExtended(listing, listingPackage);
		return null;
	}
	
	public BigDecimal getTotalPrice(BigDecimal price, BigDecimal tax) {
		Double total = price.doubleValue();
		total = total + (price.doubleValue() * (tax.doubleValue()/100));
		BigDecimal totalPrice = new BigDecimal(total);
		return totalPrice;
	}
}