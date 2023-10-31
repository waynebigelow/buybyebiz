package ca.app.service.photo;

import java.util.List;

import ca.app.model.listing.Listing;
import ca.app.model.photo.Photo;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.web.dto.photo.PhotoDTO;

public interface PhotoService {
	public Photo get(int photoId);
	public void save(Photo photo, Listing listing, AppUser appUser);
	public void save(Photo photo, Listing listing, AppUser appUser, ActivityType activityType);
	public void delete(int photoId, Listing listing);
	public void delete(int photoId, Listing listing, AppUser appUser, ActivityType activityType);
	public List<Photo> getApprovedPhotos(int listingId);
	public List<PhotoDTO> getPhotosByListingIdAndStatusId(int listingId, int statusId);
	public Photo getProfilePhoto(int listingId);
}