package ca.app.service.notificationLog;

import java.util.List;

import ca.app.model.common.Option;
import ca.app.model.notification.NotificationLog;
import ca.app.web.dto.notification.NotificationLogDTO;
import ca.app.web.paging.Page;

public interface NotificationLogService {
	public void log(NotificationLog notificationLog);
	public Page<NotificationLogDTO> getNotificationLogPage(Page<NotificationLogDTO> page);
	public void deleteByNotificationLogId(int notificationLogId);
	public NotificationLog getByNotificationLogId(int notificationLogId);
	public List<Option> getAllNotifications();
	public List<NotificationLog> getAllByListingId(int listingId);
	public List<NotificationLog> getAllByUserId(int userId);
	public boolean isNotificationSentToday(String addressee, int notificationTypeId);
}