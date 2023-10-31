package ca.app.service.photo;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.common.FileMetadata;
import ca.app.model.listing.Listing;
import ca.app.model.photo.Photo;
import ca.app.model.photo.PhotoFactory;
import ca.app.model.photo.PhotoType;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.FileUtil;
import ca.app.util.ImageUtil;
import ca.app.util.LogUtil;
import ca.app.util.ProcessUtils;
import ca.app.util.ProjectUtil;
import ca.app.util.StringUtil;

public class PhotoUploadServiceImpl implements PhotoUploadService {
	@Autowired
	private PhotoService photoService;

	public void handlePhotoUpload(FileMetadata fileMetadata, Listing listing, AppUser appUser, ActivityType activityType) throws IOException {
		File uploadedFile = new File(fileMetadata.getPathToFile() + File.separator + fileMetadata.getName());
		if (ProjectUtil.getBooleanProperty("virus.check.enabled")) {
			virusChecker(uploadedFile);
		}
		
		String baseDir = ProjectUtil.getProperty("upload.location");
		String photoPath = AssetFolderUtil.getPath(AssetFolder.PHOTOS, listing.getApplication().getName(), listing.getListingId(), true);
		
		new File(baseDir + File.separator + photoPath).mkdirs();
		
		String realFileName = uploadedFile.getName();
		File photoFile = new File(baseDir + File.separator + photoPath + File.separator + realFileName);
		FileUtil.copy(uploadedFile, photoFile);
		uploadedFile.delete();
		
		PhotoType photoType = PhotoType.get(StringUtil.convertStringToInt(fileMetadata.getInfo().get("photoTypeId"), PhotoType.GALLERY.getId()));
		ImageUtil.resizePhoto(photoFile, photoType.getPhotoWidth(), photoType.getPhotoHeight());
		ImageUtil.optimizeImage(photoFile, 75);
		
		Photo photo = PhotoFactory.createPhoto(photoFile, listing.getListingId(), fileMetadata);
		photoService.save(photo, listing, appUser, activityType);
	}
	
	private void virusChecker(File file) {
		if (file != null && !isFileTypeValid(file)) {
			file.delete();
			LogUtil.logError(getClass(), "File " + file.getAbsolutePath() + " is not a supported file type.");
			throw new IllegalArgumentException();
		}
		
		try {
			if(!isVirusChecked(file)) {
				LogUtil.logError(getClass(), "Virus scan failed. Can't proceed.");
			}
		} catch(InfectedFileException ex) {
			LogUtil.logError(getClass(), "File " + file.getAbsolutePath() + " is infected. It has been deleted and reported.");
			file.delete();
		} catch (IOException e) {
			LogUtil.logError(getClass(), "Virus scan failed. Can't proceed.");
		} catch (InterruptedException e) {
			LogUtil.logError(getClass(), "Virus scan failed. Can't proceed.");
		}
	}
	
	private boolean isVirusChecked (File file) throws InfectedFileException, IOException, InterruptedException {
		String antiVirusCommand = "/etc/clamscan " + file.getAbsolutePath();
		LogUtil.logDebug(getClass(), antiVirusCommand);
		
		int returnCode = -1;
		returnCode = ProcessUtils.processCommand(antiVirusCommand, file.getParent());
		
		if(returnCode == 0) {
			return true;
		} else if(returnCode == 1) {
			throw new InfectedFileException("File " + file.getAbsolutePath() + " is infected. It has been deleted and reported.");
		} else if(returnCode == 2) {
			throw new IOException();
		} else {
			return false;
		}
	}
	
	class InfectedFileException extends Exception{
		public InfectedFileException() {
			super();
		}

		public InfectedFileException(String message) {
			super(message);
		}

		private static final long serialVersionUID = 1L;
	}
	
	private boolean isFileTypeValid(File file) {
		String validTypes = ProjectUtil.getProperty("allowed.file.types");
		String fileName = file.getName();
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		boolean retValue = validTypes.contains("," + extension + ",");
		
		if(!retValue) {
			LogUtil.logWarn(getClass(), "Attempt to upload a prohibited file: " + fileName + ", extension : " + extension);
		}
		return retValue;
	}
	
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
}