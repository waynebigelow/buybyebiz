package ca.app.model.photo;

import java.util.Map;

public class PhotoUploadResponse {
	
	private Photo photo;
	private  Map<String, String[]> errors;
	
	public Photo getPhoto() {
		return photo;
	}
	
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	public Map<String, String[]> getErrors() {
		return errors;
	}
	
	public void setErrors(Map<String, String[]> errors) {
		this.errors = errors;
	}
}