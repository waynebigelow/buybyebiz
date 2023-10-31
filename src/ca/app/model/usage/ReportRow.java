package ca.app.model.usage;

import java.io.Serializable;
import java.util.List;

public class ReportRow implements Serializable {

	private static final long serialVersionUID = 5642139979577284370L;
	
	private int applicationId;
	private String applicationName = null;
	private int listingId;
	private String listingTitle = null;
	private int areaId;
	private String areaName = null;
	private String areaInfo = null;
	private int actionId;
	private String actionName = null;
	private String referer = null;
	private int refererTypeId;
	private String refererTypeName = null;
	private String userAgent = null;
	private int metric;
	private List<Integer> metrics;

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public String getListingTitle() {
		return listingTitle;
	}

	public void setListingTitle(String listingTitle) {
		this.listingTitle = listingTitle;
	}

	public int getAreaId() {
		return areaId;
	}
	
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getAreaInfo() {
		return areaInfo;
	}
	
	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}
	
	public int getActionId() {
		return actionId;
	}
	
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getReferer() {
		return referer;
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	public int getRefererTypeId() {
		return refererTypeId;
	}
	
	public void setRefererTypeId(int refererTypeId) {
		this.refererTypeId = refererTypeId;
	}
	
	public String getRefererTypeName() {
		return refererTypeName;
	}
	
	public void setRefererTypeName(String refererTypeName) {
		this.refererTypeName = refererTypeName;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public int getMetric() {
		return metric;
	}
	
	public void setMetric(int metric) {
		this.metric = metric;
	}
	
	public List<Integer> getMetrics() {
		return metrics;
	}
	
	public void setMetrics(List<Integer> metrics) {
		this.metrics = metrics;
	}
}