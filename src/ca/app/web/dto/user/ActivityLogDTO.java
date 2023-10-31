package ca.app.web.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.Roles;
import ca.app.service.common.TokenFieldType;
import ca.app.util.DateUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;

public class ActivityLogDTO implements Serializable {
	private static final long serialVersionUID = -5670990567387480041L;

	private int activityLogId;
	private UserDTO user;
	private int primaryId;
	private int areaId;
	private int typeId;
	private UserDTO modifyUser;
	private Timestamp activityDate;
	
	public ActivityLogDTO() {}
	
	public ActivityLogDTO(ActivityLog activityLog) {
		this.activityLogId = activityLog.getActivityLogId();
		this.user = new UserDTO(activityLog.getUser());
		this.primaryId = activityLog.getPrimaryId();
		this.areaId = activityLog.getAreaId();
		this.typeId = activityLog.getTypeId();
		this.modifyUser = new UserDTO(activityLog.getModifyUser());
		this.activityDate = activityLog.getActivityDate();
	}
	
	@JsonIgnore
	public int getActivityLogId() {
		return activityLogId;
	}
	public void setActivityLogId(int activityLogId) {
		this.activityLogId = activityLogId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.ACTIVITY_LOG.getKey(), activityLogId);
	}
	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public int getPrimaryId() {
		return primaryId;
	}
	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}
	
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public Area getArea() {
		return Area.get(areaId);
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public ActivityType getType() {
		return ActivityType.get(typeId);
	}
	
	public Timestamp getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Timestamp activityDate) {
		this.activityDate = activityDate;
	}
	public String getActivityDateFormatted() {
		if(activityDate == null) {
			return "";
		}
		String parsedDate = DateUtil.getStringDate(activityDate.getTime(), ProjectUtil.getProperty("standard.date.format"));
		return parsedDate;
	}
	
	public UserDTO getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(UserDTO modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	public String getModifyUserNameFormatted() {
		if (modifyUser.hasRole(Roles.SUPER_ADMIN.name()) && modifyUser.getUserId() != user.getUserId()) {
			return "System Administrator";
		} else {
			return modifyUser.getDisplayName();
		}
	}
}