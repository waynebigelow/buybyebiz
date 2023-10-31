package ca.app.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.photo.PhotoStatus;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.photo.PhotoService;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.photo.PhotoDTO;
import flexjson.JSONSerializer;

@Controller
public class LoadPhotoApprovalsController extends BaseController {
	@Autowired
	private PhotoService photoService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		Application application = (Application)request.getAttribute("application");
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		List<PhotoDTO> photos = photoService.getPhotosByListingIdAndStatusId(listingId, PhotoStatus.PENDING_REVIEW.getId());
		
		for (PhotoDTO photo : photos) {
			photo.setPhotoPath(AssetFolderUtil.getPath(AssetFolder.PHOTOS, application.getName(), listingId, true));
		}
		
		JSONSerializer json = new JSONSerializer();
		json.exclude("*.class");
		json.exclude("obj");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.deepSerialize(photos));
		response.getWriter().flush();
		return null;
	}
}