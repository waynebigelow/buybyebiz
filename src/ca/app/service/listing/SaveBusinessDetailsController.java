package ca.app.service.listing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.Listing;
import ca.app.model.listing.MetaDataType;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveBusinessDetailsController extends BaseController {
	@Autowired
	private ListingService listingService;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);

		listing.getBusinessDetails().setSalesRevenueMin(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("salesRevMin"), 0)));
		listing.getBusinessDetails().setSalesRevenueMax(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("salesRevMax"), 0)));
		listing.getBusinessDetails().setCashFlow(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("cashFlow"), 0)));
		listing.getBusinessDetails().setInventoryTotal(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("inventoryTotal"), 0)));
		listing.getBusinessDetails().setChattelTotal(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("chattelTotal"), 0)));
		listing.getBusinessDetails().setPropertyTypeId(StringUtil.convertStringToInt(request.getParameter("propertyType"), 0));
		listingService.update(listing, appUser, ActivityType.LISTING_UPDATED);
		
		handleApprovals(request, listing);
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"success\": \""+getToken(TokenFieldType.LISTING, listing.getListingId())+"\"}");
		response.getWriter().flush();
		return null;
	}
	
	public void handleApprovals(HttpServletRequest request, Listing listing) throws Exception {
		Map<Integer, String> approvalMap = new HashMap<Integer, String>();
		
		String financialOther = request.getParameter("financialOther");
		if (financialOther == null || !listing.getBusinessDetails().getFinancialOther().equals(financialOther)) {
			approvalMap.put(MetaDataType.FINANCIAL_OTHER.getId(), financialOther);
		}
		
		String hoursOfOperation = request.getParameter("hoursOfOperation");
		if (hoursOfOperation == null || !listing.getBusinessDetails().getHoursOfOperation().equals(hoursOfOperation)) {
			approvalMap.put(MetaDataType.HOURS_OF_OPERATION.getId(), hoursOfOperation);
		}
		
		String numberOfEmployees = request.getParameter("numberOfEmployees");
		if (numberOfEmployees == null || !listing.getBusinessDetails().getNumberOfEmployees().equals(numberOfEmployees)) {
			approvalMap.put(MetaDataType.NUMBER_OF_EMPLOYEES.getId(), numberOfEmployees);
		}
		
		String yearEstablished = request.getParameter("yearEstablished");
		if (yearEstablished == null || !listing.getBusinessDetails().getYearEstablished().equals(yearEstablished)) {
			approvalMap.put(MetaDataType.YEAR_ESTABLISHED.getId(), yearEstablished);
		}
		
		String operationOther = request.getParameter("operationOther");
		if (operationOther == null || !listing.getBusinessDetails().getOperationOther().equals(operationOther)) {
			approvalMap.put(MetaDataType.OPERATION_OTHER.getId(), operationOther);
		}
		
		String propertyTax = request.getParameter("propertyTax");
		if (propertyTax == null || !listing.getBusinessDetails().getPropertyTax().equals(propertyTax)) {
			approvalMap.put(MetaDataType.PROPERTY_TAX.getId(), propertyTax);
		}
		
		String frontage = request.getParameter("frontage");
		if (frontage == null || !listing.getBusinessDetails().getFrontage().equals(frontage)) {
			approvalMap.put(MetaDataType.FRONTAGE.getId(), frontage);
		}
		
		String squareFootage = request.getParameter("squareFootage");
		if (squareFootage == null || !listing.getBusinessDetails().getSquareFootage().equals(squareFootage)) {
			approvalMap.put(MetaDataType.SQUARE_FOOTAGE.getId(), squareFootage);
		}
		
		String acreage = request.getParameter("acreage");
		if (acreage == null || !listing.getBusinessDetails().getAcreage().equals(acreage)) {
			approvalMap.put(MetaDataType.ACREAGE.getId(), acreage);
		}
		
		String ownersResidence = request.getParameter("ownersResidence");
		if (ownersResidence == null || !listing.getBusinessDetails().getOwnersResidence().equals(ownersResidence)) {
			approvalMap.put(MetaDataType.OWNERS_RESIDENCE.getId(), ownersResidence);
		}
		
		String propertyOther = request.getParameter("propertyOther");
		if (propertyOther == null || !listing.getBusinessDetails().getPropertyOther().equals(propertyOther)) {
			approvalMap.put(MetaDataType.PROPERTY_OTHER.getId(), propertyOther);
		}
		
		String sellingReason = request.getParameter("sellingReason");
		if (sellingReason == null || !listing.getBusinessDetails().getSellingReason().equals(sellingReason)) {
			approvalMap.put(MetaDataType.SELLING_REASON.getId(), sellingReason);
		}
		
		String support = request.getParameter("support");
		if (support == null || !listing.getBusinessDetails().getSupport().equals(support)) {
			approvalMap.put(MetaDataType.SUPPORT.getId(), support);
		}
		
		String ownerFinancing = request.getParameter("ownerFinancing");
		if (ownerFinancing == null || !listing.getBusinessDetails().getOwnerFinancing().equals(ownerFinancing)) {
			approvalMap.put(MetaDataType.OWNER_FINANCING.getId(), ownerFinancing);
		}
		
		if (!approvalMap.isEmpty()) {
			listingService.processApprovals(approvalMap, listing);
		}
	}
}