package ca.app.model.photo;

import java.io.File;

import ca.app.model.common.FileMetadata;
import ca.app.util.FileUtil;
import ca.app.util.ImageUtil;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;

public class PhotoFactory {
	public static  Photo createPhoto(File uploadedFile, int listingId, FileMetadata fileMetadata) {
		Photo photo = new Photo();
		photo.setListingId(listingId);
		photo.setPhotoTypeId(StringUtil.convertStringToInt(fileMetadata.getInfo().get("photoTypeId"), PhotoType.GALLERY.getId()));
		photo.setStatusId(PhotoStatus.PENDING_REVIEW.getId());
		if (fileMetadata.getInfo().get("caption") != null) {
			photo.setCaption(fileMetadata.getInfo().get("caption"));
		}
		
		if (uploadedFile != null) {
			photo.setSize(uploadedFile.length());
			photo.setFileName(uploadedFile.getName());
			
			try {
				byte[] photoFile = FileUtil.getBytesFromFile(uploadedFile);
				photo.setWidth((Integer)ImageUtil.getXY(photoFile).get("width"));
				photo.setHeight((Integer)ImageUtil.getXY(photoFile).get("height"));
			} catch (Exception ex) {
				LogUtil.logException(ImageUtil.class, "Exception", ex);
			}
		}
		
		return photo;
	}
}