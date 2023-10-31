package ca.app.model.usage;

import java.io.Serializable;

public class ManagedLink implements Serializable {

	private static final long serialVersionUID = 6297387052489460083L;
	
	private int managedLinkId;
	private int vendorId;
	private String name;
	private String alias;
	private String url;
	
	
	public int getManagedLinkId() {
		return managedLinkId;
	}
	
	public void setManagedLinkId(int managedLinkId) {
		this.managedLinkId = managedLinkId;
	}
	
	public int getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}