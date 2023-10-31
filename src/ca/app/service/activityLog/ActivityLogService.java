package ca.app.service.activityLog;

import java.math.BigDecimal;
import java.util.List;

import ca.app.model.user.ActivityLog;
import ca.app.model.user.User;
import ca.app.web.dto.user.ActivityLogDTO;
import ca.app.web.paging.Page;

public interface ActivityLogService {
	public void insert(ActivityLog activityLog);
	public void deleteByActivityLogId(int activityLogId);
	public void deleteAllActivityByUserId(int userId);
	
	public ActivityLog getByActivityLogId(int activityLogId);
	
	public Page<ActivityLogDTO> getActivityLogPage(Page<ActivityLogDTO> page);
	
	public List<BigDecimal> getAreaIdsByUserId(int typeId, int userId);
	public List<BigDecimal> getTypeIdsByUserId(int areaId, int userId);
	public List<User> getAllActivityAuthors(int areaId, int typeId);
	public List<ActivityLogDTO> getAllByListingId(int listingId);
	public List<ActivityLogDTO> getAllByUserId(int userId);
}