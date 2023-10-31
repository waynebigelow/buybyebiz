package ca.app.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.UserAdminTabType;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.util.LogUtil;
import ca.app.web.dto.listing.EnquiryMapDTO;
import ca.app.web.dto.listing.ListingDTO;

@Controller
public class LoadInboxController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_ACCOUNT_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		User user = userService.getByUserId(appUser.getUserId());
		List<EnquiryMap> enquiries = listingService.getByPosterId(user);
		List<EnquiryMapDTO> dtos = new ArrayList<EnquiryMapDTO>();
		int count = 0;
		for (EnquiryMap enquiry : enquiries) {
			EnquiryMapDTO dto = new EnquiryMapDTO(enquiry);
			dto.setInboxType(2);
			dto.setLoggedInId(appUser.getUserId());
			dto.setListing(new ListingDTO(listingService.getByListingId(enquiry.getListingId()), false));
			dtos.add(dto);
			
			for (EnquiryPost post : enquiry.getPosts()) {
				if (!post.isRead()) {
					if (post.getAuthorId() != appUser.getUserId()) {
						count++;
					}
				}
			}
		}
		
		ModelAndView mav = new ModelAndView("/userAdmin/inbox");
		mav.addObject("enquiries", dtos);
		mav.addObject("unread", count);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", UserAdminTabType.values());
		mav.addObject("tab", UserAdminTabType.INBOX);
		return mav;
	}
}