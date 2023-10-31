package ca.app.model.photo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="photo")
public class Photo implements Serializable{
	private static final long serialVersionUID = -6538846882583942637L;

	@Id
	@Column(name="photo_id", unique=true)
	@SequenceGenerator(name="seqPhoto", sequenceName="seq_photo", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqPhoto")
	private int photoId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="photo_type_id")
	private int photoTypeId;
	
	@Column(name="caption")
	private String caption;
	
	@Column(name="status_id")
	private int statusId;
	
	@Column(name="photo_size")
	private long size;

	@Column(name="height")
	private int height = 0;
	
	@Column(name="width")
	private int width = 0;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="rejected_date")
	private Timestamp rejectedDate;
	
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public PhotoStatus getStatus() {
		return PhotoStatus.get(statusId);
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
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
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + photoId;
		result = prime * result + listingId;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + photoTypeId;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + statusId;
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;
		
		if (photoId != other.photoId)
			return false;
		if (listingId != other.listingId)
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (photoTypeId != other.photoTypeId)
			return false;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (statusId != other.statusId)
			return false;
		if (size != other.size)
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		
		return true;
	}
}