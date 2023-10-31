package ca.app.model.listing;

import java.io.Serializable;

public class URLParseResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String contextPath;
	private String requestURI;
	private String listingURI;
	private boolean isListingURI = false;
	private boolean isNoncanonical = false;
	
	public String getContextPath() {
		return contextPath;
	}
	
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public String getRequestURI() {
		return requestURI;
	}
	
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public String getListingURI() {
		return listingURI;
	}

	public void setListingURI(String listingURI) {
		this.listingURI = listingURI;
	}

	public boolean isListingURI() {
		return isListingURI;
	}

	public void setListingURI(boolean isListingURI) {
		this.isListingURI = isListingURI;
	}

	public boolean isNoncanonical() {
		return isNoncanonical;
	}
	
	public void setNoncanonical(boolean isNoncanonical) {
		this.isNoncanonical = isNoncanonical;
	}
}