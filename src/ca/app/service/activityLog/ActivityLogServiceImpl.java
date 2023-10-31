package ca.app.service.activityLog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.user.ActivityLog;
import ca.app.model.user.User;
import ca.app.persistence.activityLog.ActivityLogDAO;
import ca.app.service.user.UserService;
import ca.app.web.dto.user.ActivityLogDTO;
import ca.app.web.paging.Page;

public class ActivityLogServiceImpl implements ActivityLogService {
	@Autowired
	private ActivityLogDAO activityLogDAO;
	@Autowired
	private UserService userService;
	
	public void insert(ActivityLog activityLog) {
		activityLogDAO.insert(activityLog);
	}

	public void deleteByActivityLogId(int activityLogId) {
		activityLogDAO.deleteByActivityLogId(activityLogId);
	}
	
	public void deleteAllActivityByUserId(int userId){
		activityLogDAO.deleteAllActivityByUserId(userId);
	}
	
	public ActivityLog getByActivityLogId(int activityLogId) {
		return activityLogDAO.getByActivityLogId(activityLogId);
	}
	
	public Page<ActivityLogDTO> getActivityLogPage(Page<ActivityLogDTO> page) {
		return activityLogDAO.getActivityLogPage(page);
	}
	
	public List<BigDecimal> getAreaIdsByUserId(int typeId, int userId) {
		return activityLogDAO.getAreaIdsByUserId(typeId, userId);
	}
	
	public List<BigDecimal> getTypeIdsByUserId(int areaId, int userId) {
		return activityLogDAO.getTypeIdsByUserId(areaId, userId);
	}
	
	public List<User> getAllActivityAuthors(int areaId, int typeId) {
		return activityLogDAO.getAllActivityAuthors(areaId, typeId);
	}
	
	public List<ActivityLogDTO> getAllByListingId(int listingId) {
		List<ActivityLog> activityLogs = activityLogDAO.getAllByListingId(listingId);
		
		List<ActivityLogDTO> dtos = new ArrayList<ActivityLogDTO>();
		for (ActivityLog activityLog : activityLogs) {
			dtos.add(new ActivityLogDTO(activityLog));
		}
		
		return dtos;
	}
	
	public List<ActivityLogDTO> getAllByUserId(int userId) {
		List<ActivityLog> activityLogs = activityLogDAO.getAllByUser(userService.getByUserId(userId));
		
		List<ActivityLogDTO> dtos = new ArrayList<ActivityLogDTO>();
		for (ActivityLog activityLog : activityLogs) {
			dtos.add(new ActivityLogDTO(activityLog));
		}
		
		return dtos;
	}
	
	public void setActivityLogDAO(ActivityLogDAO activityLogDAO) {
		this.activityLogDAO = activityLogDAO;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}