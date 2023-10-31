package ca.app.model.common;

import java.io.Serializable;

public class MapData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String formattedAddress;
	private int zoom = 4;
	private String height;
	
	public MapData(String formattedAddress, int zoom, String height) {
		this.formattedAddress = formattedAddress;
		this.zoom = zoom;
		this.height = height;
	}
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
	public int getZoom() {
		return zoom;
	}
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
}