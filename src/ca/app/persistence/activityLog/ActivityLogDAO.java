package ca.app.persistence.activityLog;

import java.math.BigDecimal;
import java.util.List;

import ca.app.model.user.ActivityLog;
import ca.app.model.user.User;
import ca.app.web.dto.user.ActivityLogDTO;
import ca.app.web.paging.Page;

public interface ActivityLogDAO {
	public void insert(ActivityLog entry);
	public void deleteByActivityLogId(int activityLogId);
	public void deleteAllActivityByUserId(int userId);
	
	public ActivityLog getByActivityLogId(int activityLogId);
	
	public Page<ActivityLogDTO> getActivityLogPage(Page<ActivityLogDTO> page);
	
	public List<BigDecimal> getAreaIdsByUserId(int typeId, int userId);
	public List<BigDecimal> getTypeIdsByUserId(int areaId, int userId);
	public List<User> getAllActivityAuthors(int areaId, int typeId);
	public List<ActivityLog> getAllByListingId(int listingId);
	public List<ActivityLog> getAllByUser(User user);
}