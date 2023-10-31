package ca.app.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.usage.Area;

@Entity
@Table(name="activity_log")
public class ActivityLog implements Serializable {
	private static final long serialVersionUID = -5670990567387480041L;

	@Id
	@SequenceGenerator(name="seqActivityLog", sequenceName="seq_activity_log", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqActivityLog")
	@Column(name="activity_log_id", unique=true)
	private int activityLogId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="primary_id")
	private int primaryId;
	
	@Column(name="area_id")
	private int areaId;
	
	@Column(name="type_id")
	private int typeId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="modify_id")
	private User modifyUser;
	
	@Column(name="activity_date")
	private Timestamp activityDate;
	
	public ActivityLog() {};
	
	public ActivityLog(User user, int areaId, int typeId, int primaryId, User modifyUser) {
		this.user = user;
		this.areaId = areaId;
		this.typeId = typeId;
		this.primaryId = primaryId;
		this.activityDate = new Timestamp(System.currentTimeMillis());
		this.modifyUser = (modifyUser == null ? modifyUser = user : modifyUser);
	};
	
	public int getActivityLogId() {
		return activityLogId;
	}
	public void setActivityLogId(int activityLogId) {
		this.activityLogId = activityLogId;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
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
	
	public User getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(User modifyUser) {
		this.modifyUser = modifyUser;
	}
}