package ca.app.service.photo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.photo.PhotoDTO;
import flexjson.JSONSerializer;

@Controller
public class EditPhotoController extends BaseController {
	@Autowired
	private PhotoService photoService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int photoId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.PHOTO.getAlias()), TokenFieldType.PHOTO);
		
		PhotoDTO photo = null;
		if (photoId > 0) {
			Application application = (Application)request.getAttribute("application");
			photo = new PhotoDTO(photoService.get(photoId));
			photo.setPhotoPath(AssetFolderUtil.getPath(AssetFolder.PHOTOS, application.getName(), photo.getListingId(), true)+"/"+photo.getFileName());
		}
		
		JSONSerializer json = new JSONSerializer();
		json.include("photoTypeId","caption","photoPath");
		json.exclude("*");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.serialize("photo", photo));
		response.getWriter().flush();
		return null;
	}
}