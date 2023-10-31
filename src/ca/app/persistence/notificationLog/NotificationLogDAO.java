package ca.app.persistence.notificationLog;

import java.util.List;

import ca.app.model.common.Option;
import ca.app.model.listing.Listing;
import ca.app.model.notification.NotificationLog;
import ca.app.model.user.User;
import ca.app.web.dto.notification.NotificationLogDTO;
import ca.app.web.paging.Page;

public interface NotificationLogDAO {
	public void log(NotificationLog notificationLog);
	public Page<NotificationLogDTO> getNotificationLogPage(Page<NotificationLogDTO> page);
	public void deleteByNotificationLogId(int notificationLogId);
	public List<Option> getAllNotifications();
	public NotificationLog getByNotificationLogId(int notificationLogId);
	public List<NotificationLog> getAllByListing(Listing listing);
	public List<NotificationLog> getAllByUser(User user);
	public boolean isNotificationSentToday(String addressee, int notificationTypeId);
}