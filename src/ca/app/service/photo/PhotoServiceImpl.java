package ca.app.service.photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.listing.Listing;
import ca.app.model.photo.Photo;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.persistence.photo.PhotoDAO;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.user.UserService;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.ProjectUtil;
import ca.app.web.dto.photo.PhotoDTO;

public class PhotoServiceImpl implements PhotoService {
	@Autowired
	private PhotoDAO photoDAO;
	@Autowired
	UserService userService;
	@Autowired
	private ActivityLogService activityLogService;
	
	public Photo get(int photoId) {
		return photoDAO.get(photoId);
	}

	public void save(Photo photo, Listing listing, AppUser appUser) {
		photoDAO.save(photo);
		
		ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), ActivityType.PHOTO_UPDATED.getId(), listing.getListingId(), userService.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public void save(Photo photo, Listing listing, AppUser appUser, ActivityType activityType) {
		photoDAO.save(photo);
		
		ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), activityType.getId(), photo.getListingId(), userService.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public void delete(int photoId, Listing listing) {
		delete(photoId, listing, null, null);
	}
	
	public void delete(int photoId, Listing listing, AppUser appUser, ActivityType activityType) {
		Photo photo = get(photoId);
		
		String baseDir = ProjectUtil.getProperty("upload.location");
		String photoPath = AssetFolderUtil.getPath(AssetFolder.PHOTOS, listing.getApplication().getName(), listing.getListingId(), true);
		File photoFile = new File(baseDir + File.separator + photoPath + File.separator + photo.getFileName());
		
		photoFile.delete();
		photoDAO.delete(photo);
		
		if (activityType != null) {
			ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), activityType.getId(), photo.getListingId(), userService.getByUserId(appUser.getUserId()));
			activityLogService.insert(activityLog);
		}
	}
	
	public List<Photo> getApprovedPhotos(int listingId) {
		return photoDAO.getApprovedPhotos(listingId);
	}
	
	public List<PhotoDTO> getPhotosByListingIdAndStatusId(int listingId, int statusId) {
		List<Photo> photos = photoDAO.getPhotosByListingIdAndStatusId(listingId, statusId);
		
		List<PhotoDTO> dtos = new ArrayList<PhotoDTO>();
		for (Photo photo : photos) {
			dtos.add(new PhotoDTO(photo));
		}
		
		return dtos;
	}
	
	public Photo getProfilePhoto(int listingId) {
		return photoDAO.getProfilePhoto(listingId);
	}
	
	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}
	public void setActivityLogService(ActivityLogService activityLogService) {
		this.activityLogService = activityLogService;
	}
}