package ca.app.service.photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.common.FileMetadata;
import ca.app.model.listing.Listing;
import ca.app.model.photo.Photo;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class SavePhotoController extends BaseController {
	@Autowired
	private PhotoUploadService photoUploadService;
	@Autowired
	private ListingService listingService;
	@Autowired
	private PhotoService photoService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_ACCOUNT_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);

		String targetFolder = getBaseDir() + File.separator + "tmp";
		File targetDirectory = new File(targetFolder);
		if(!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}
		
		int photoId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.PHOTO.getAlias()), TokenFieldType.PHOTO);
		String fileName = request.getParameter("fileName");
		String caption = request.getParameter("caption");
		
		boolean isUpdate = false;
		if (photoId > 0) {
			if (fileName != null && !fileName.equals("")) {
				photoService.delete(photoId, listing);
			} else {
				Photo photo = photoService.get(photoId);
				photo.setCaption(caption);
				photoService.save(photo, listing, appUser);
				isUpdate = true;
			}
		}
		
		if (!isUpdate) {
			String photoTypeId = request.getParameter("photoType");
			String base64Img = request.getParameter("imgBase64");
			String[] imgParts = base64Img.split(",");
			String img = imgParts[1];
			
			byte[] data = Base64.decodeBase64(img);
			try (OutputStream stream = new FileOutputStream(targetDirectory + File.separator + fileName)) {
				stream.write(data);
				FileCopyUtils.copy(data, stream);
				
				FileMetadata fileMetadata = new FileMetadata();
				fileMetadata.setName(fileName);
				fileMetadata.setPathToFile(targetFolder);
				fileMetadata.getInfo().put("caption", caption == null ? "" : caption);
				fileMetadata.getInfo().put("photoTypeId", photoTypeId == null ? "" : photoTypeId);
				
				ActivityType activityType = ActivityType.PHOTO_ADDED;
				if (photoId > 0 && fileName != null && !fileName.equals("")) {
					activityType = ActivityType.PHOTO_REPLACED;
				}
				photoUploadService.handlePhotoUpload(fileMetadata, listing, appUser, activityType);
			}
		}
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"success\": \""+getToken(TokenFieldType.LISTING, listing.getListingId())+"\"}");
		response.getWriter().flush();
		return null;
	}
}