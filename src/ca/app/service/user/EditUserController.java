package ca.app.service.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.UserAdminTabType;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.model.user.User;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.service.usage.PageHitService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.user.UserDTO;
import flexjson.JSONSerializer;

@Controller
public class EditUserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private PageHitService pageHitService;
	@Autowired
	private ListingService listingService;
	private String editType;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_ACCOUNT_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		if (editType.equals("json")) {
			return handleJSON(request, response);
		} else {
			return handleMAV(request, response);
		}
	}
	
	private ModelAndView handleMAV(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		User user = userService.getByUserId(appUser.getUserId());
		List<EnquiryMap> enquiries = listingService.getByPosterId(user);
		int count = 0;
		for (EnquiryMap enquiry : enquiries) {
			for (EnquiryPost post : enquiry.getPosts()) {
				if (!post.isRead()) {
					if (post.getAuthorId() != appUser.getUserId()) {
						count++;
					}
				}
			}
		}
		
		try {
			pageHitService.logPageHit(null, request, Usage.USER_ACCOUNT);
		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
		}

		ModelAndView mav = new ModelAndView("/userAdmin/userDetails");
		mav.addObject("user", new UserDTO(user));
		mav.addObject("unread", count);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("tabs", UserAdminTabType.values());
		mav.addObject("tab", UserAdminTabType.ACCOUNT);
		return mav;
	}
	
	private ModelAndView handleJSON(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int userId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.USER.getAlias()), TokenFieldType.USER);
		
		User user = userService.getByUserId(userId);
		
		JSONSerializer json = new JSONSerializer();
		json.include("email","firstName","lastName","telephone","agent","companyName");
		json.include("enablePromotionalEmail","enableExpirationEmail","enableEnquiryEmail");
		json.exclude("*");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.serialize("user", user));
		response.getWriter().flush();
		return null;
	}
	
	public void setEditType(String editType) {
		this.editType = editType;
	}
}