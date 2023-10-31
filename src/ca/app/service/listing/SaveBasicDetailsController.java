package ca.app.service.listing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.common.AddressType;
import ca.app.model.listing.Listing;
import ca.app.model.listing.MetaDataType;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.persistence.category.CategoryDAO;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;

@Controller
public class SaveBasicDetailsController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		Application application = (Application)request.getAttribute("application");
		Listing listing = new Listing();
		listing.setApplication(application);
		listing.setCategory(categoryDAO.getByCategoryId(StringUtil.convertStringToInt(request.getParameter("category"), 0)));
		listing.setSubCategory(categoryDAO.getBySubCategoryId(StringUtil.convertStringToInt(request.getParameter("subCategory"), 0)));
		listing.setPrice(new BigDecimal(StringUtil.convertStringToFloat(request.getParameter("price"), 0)));
		listing.getAddress().setTypeId(AddressType.LISTING.getId());
		listing.getAddress().setAddress1(request.getParameter("address1"));
		listing.getAddress().setCity(request.getParameter("city"));
		listing.getAddress().setProvince(request.getParameter("province"));
		listing.getAddress().setCountry(request.getParameter("country"));
		listing.setUser(userService.getByUserId(appUser.getUserId()));
		listing.setListingURI(createListingURI(request.getParameter("listingURI"), listing));
		listingService.create(listing, appUser);
		
		handleApprovals(request, listing);
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"success\": \""+getToken(TokenFieldType.LISTING, listing.getListingId())+"\"}");
		response.getWriter().flush();
		return null;
	}

	public void handleApprovals(HttpServletRequest request, Listing listing) throws Exception {
		Map<Integer, String> approvalMap = new HashMap<Integer, String>();
		
		String title = request.getParameter("title");
		if (title == null || !listing.getTitle().equals(title)) {
			approvalMap.put(MetaDataType.TITLE.getId(), title);
		}
		
		String description = request.getParameter("description");
		if (description == null || !listing.getDescription().equals(description)) {
			approvalMap.put(MetaDataType.DESCRIPTION.getId(), description);
		}
		
		if (!approvalMap.isEmpty()) {
			listingService.processApprovals(approvalMap, listing);
		}
	}
}