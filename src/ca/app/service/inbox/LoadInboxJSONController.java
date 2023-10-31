package ca.app.service.inbox;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.Listing;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.listing.EnquiryMapDTO;
import ca.app.web.dto.listing.ListingDTO;
import flexjson.JSONSerializer;

@Controller
public class LoadInboxJSONController extends BaseController {
	@Autowired
	private ListingService listingService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		List<EnquiryMapDTO> dtos = new ArrayList<EnquiryMapDTO>();
		for (EnquiryMap enquiry : listing.getEnquiries()) {
			EnquiryMapDTO dto = new EnquiryMapDTO(enquiry);
			dto.setListing(new ListingDTO(listing, false));
			dtos.add(dto);
		}
		
		JSONSerializer json = new JSONSerializer();
		json.exclude("*.class");
		json.exclude("obj");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.deepSerialize(dtos));
		response.getWriter().flush();
		return null;
	}
}