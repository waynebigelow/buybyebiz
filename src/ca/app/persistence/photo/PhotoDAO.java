package ca.app.persistence.photo;

import java.util.List;

import ca.app.model.photo.Photo;

public interface PhotoDAO  {
	public Photo get(int photoId);
	public void save(Photo photo);
	public void delete(Photo photo);
	public List<Photo> getApprovedPhotos(int listingId);
	public List<Photo> getPhotosByListingIdAndStatusId(int listingId, int statusId);
	public Photo getProfilePhoto(int listingId);
}