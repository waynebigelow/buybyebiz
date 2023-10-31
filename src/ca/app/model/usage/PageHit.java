package ca.app.model.usage;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.util.DateUtil;
import ca.app.util.ProjectUtil;

@Entity
@Table(name="page_hit")
public class PageHit implements Serializable {
	private static final long serialVersionUID = 549870982098483561L;
	
	@Id
	@SequenceGenerator(name="seqPageHit", sequenceName="seq_page_hit", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqPageHit")
	@Column(name="page_hit_id", unique=true)
	private int pageHitId;
	
	@Column(name="application_id")
	private int applicationId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="session_id")
	private String sessionId;
	
	@Column(name="page_hit_type_id")
	private int pageHitTypeId = PageHitType.ENTRY.getId();
	
	@Column(name="area_id")
	private int areaId;
	
	@Column(name="area_info")
	private String areaInfo;
	
	@Column(name="action_id")
	private int actionId;
	
	@Column(name="date_time")
	private Timestamp dateTime;
	
	@Column(name="time_spent")
	private long timeSpent;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name="referer")
	private String referer;
	
	@Column(name="referer_type_id")
	private int refererTypeId = RefererType.UNKNOWN.getId();
	
	@Column(name="raw_user_agent")
	private String rawUserAgent;
	
	@Column(name="user_agent")
	private String userAgent;
	
	@Column(name="user_agent_os")
	private String userAgentOS;
	
	public int getPageHitId() {
		return pageHitId;
	}
	public void setPageHitId(int pageHitId) {
		this.pageHitId = pageHitId;
	}
	
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public int getPageHitTypeId() {
		return pageHitTypeId;
	}
	public void setPageHitTypeId(int pageHitTypeId) {
		this.pageHitTypeId = pageHitTypeId;
	}
	
	public PageHitType getPageHitType() {
		return PageHitType.get(pageHitTypeId);
	}
	public void setPageHitType(PageHitType pageHitType) {
		this.pageHitTypeId = pageHitType.getId();
	}
	
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	public Area getArea() {
		return Area.get(this.areaId);
	}
	public void setArea(Area area) {
		this.areaId = area.getId();
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
	
	public Action getAction() {
		return Action.get(this.actionId);
	}
	public void setAction(Action action) {
		this.actionId = action.getId();
	}
	
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getDateTimeFormatted() {
		if(dateTime == null) {
			return "";
		}
		String parsedDate = DateUtil.getStringDate(dateTime.getTime(), ProjectUtil.getProperty("standard.date.format"));
		return parsedDate;
	}
	
	public long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	
	public RefererType getRefererType() {
		return RefererType.get(refererTypeId);
	}
	public void setRefererType(RefererType refererType) {
		this.refererTypeId = refererType.getId();
	}
	
	public String getRawUserAgent() {
		return rawUserAgent;
	}
	public void setRawUserAgent(String rawUserAgent) {
		this.rawUserAgent = rawUserAgent;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgentOS() {
		return userAgentOS;
	}
	public void setUserAgentOS(String userAgentOS) {
		this.userAgentOS = userAgentOS;
	}
	
	public String toString() {
		StringBuilder buff = new StringBuilder();
		
		buff.append("USAGE: ");
		buff.append("[" + applicationId + "]");
		buff.append("[" + listingId + "]");
		buff.append("[" + userId + "]");
		buff.append("[" + sessionId + "]");
		if(getArea() != null) {
			buff.append("[" + getArea().name() + "]");
		}
		if(getAction() != null) {
			buff.append("[" + getAction().name() + "]");
		}
		buff.append("[" + dateTime + "]");
		buff.append("[" + ipAddress + "]");
		buff.append("[" + referer + "]");
		buff.append("[" + userAgent + "]");
		buff.append("\n");
		
		return buff.toString();
	}
}