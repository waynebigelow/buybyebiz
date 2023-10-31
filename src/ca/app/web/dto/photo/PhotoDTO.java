package ca.app.web.dto.photo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.photo.Photo;
import ca.app.model.photo.PhotoStatus;
import ca.app.model.photo.PhotoType;
import ca.app.service.common.TokenFieldType;
import ca.app.util.RequestUtil;

public class PhotoDTO {
	private int photoId;
	private int listingId;
	private int photoTypeId;
	private String fileName;
	private String caption;
	private int photoStatusId;
	private String photoPath;
	private String reason;
	private Timestamp rejectedDate;
	
	public PhotoDTO(int photoId, int photoTypeId, String fileName, String caption, int photoStatusId) {
		this.photoId = photoId;
		this.photoTypeId = photoTypeId;
		this.fileName = fileName;
		this.caption = caption;
		this.photoStatusId = photoStatusId;
	}
	
	public PhotoDTO(Photo photo) {
		if (photo != null) {
			this.photoId = photo.getPhotoId();
			this.listingId = photo.getListingId();
			this.photoTypeId = photo.getPhotoTypeId();
			this.fileName = photo.getFileName();
			this.caption = photo.getCaption();
			this.photoStatusId = photo.getStatusId();
			this.reason = photo.getReason();
			this.rejectedDate = photo.getRejectedDate();
		}
	}

	@JsonIgnore
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.PHOTO.getKey(), photoId);
	}
	
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getPhotoTypeId() {
		return photoTypeId;
	}
	public void setPhotoTypeId(int photoTypeId) {
		this.photoTypeId = photoTypeId;
	}
	public PhotoType getPhotoType() {
		return PhotoType.get(photoTypeId);
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getPhotoStatusId() {
		return photoStatusId;
	}
	public void setPhotoStatusId(int photoStatusId) {
		this.photoStatusId = photoStatusId;
	}
	public PhotoStatus getPhotoStatus() {
		return PhotoStatus.get(photoStatusId);
	}

	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(Timestamp rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
}