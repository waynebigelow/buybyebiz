package ca.app.model.commerce;

import java.io.Serializable;

public class PayPalSettings implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String pageTitle;
	private String metaAuthor;
	private String metaDescription;
	private String ogDescription;
	private String ogTitle;
	private String ogImage;
	
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	public String getMetaAuthor() {
		return metaAuthor;
	}
	public void setMetaAuthor(String metaAuthor) {
		this.metaAuthor = metaAuthor;
	}
	
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	
	public String getOgDescription() {
		return ogDescription;
	}
	public void setOgDescription(String ogDescription) {
		this.ogDescription = ogDescription;
	}
	
	public String getOgTitle() {
		return ogTitle;
	}
	public void setOgTitle(String ogTitle) {
		this.ogTitle = ogTitle;
	}
	
	public String getOgImage() {
		return ogImage;
	}
	public void setOgImage(String ogImage) {
		this.ogImage = ogImage;
	}
}