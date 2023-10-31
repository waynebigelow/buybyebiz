package ca.app.service.photo;

import java.io.IOException;

import ca.app.model.common.FileMetadata;
import ca.app.model.listing.Listing;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;

public interface PhotoUploadService {
	public void handlePhotoUpload(FileMetadata fileMetadata, Listing listing, AppUser appUser, ActivityType activityType) throws IOException;
}