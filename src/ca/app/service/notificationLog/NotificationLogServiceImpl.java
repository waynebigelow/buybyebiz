package ca.app.service.notificationLog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.common.Option;
import ca.app.model.notification.NotificationLog;
import ca.app.persistence.notificationLog.NotificationLogDAO;
import ca.app.service.listing.ListingService;
import ca.app.service.user.UserService;
import ca.app.web.dto.notification.NotificationLogDTO;
import ca.app.web.paging.Page;

public class NotificationLogServiceImpl implements NotificationLogService {
	@Autowired
	private NotificationLogDAO notificationLogDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private ListingService listingService;
	
	public void log(NotificationLog notificationLog) {
		notificationLogDAO.log(notificationLog);
	}

	public Page<NotificationLogDTO> getNotificationLogPage(Page<NotificationLogDTO> page) {
		return notificationLogDAO.getNotificationLogPage(page);
	}

	public void deleteByNotificationLogId(int notificationLogId) {
		notificationLogDAO.deleteByNotificationLogId(notificationLogId);
	}
	
	public NotificationLog getByNotificationLogId(int notificationLogId) {
		return notificationLogDAO.getByNotificationLogId(notificationLogId);
	}
	
	public List<Option> getAllNotifications() {
		return notificationLogDAO.getAllNotifications();
	}
	
	public List<NotificationLog> getAllByListingId(int listingId) {
		return notificationLogDAO.getAllByListing(listingService.getByListingId(listingId));
	}
	
	public List<NotificationLog> getAllByUserId(int userId) {
		return notificationLogDAO.getAllByUser(userService.getByUserId(userId));
	}
	
	public boolean isNotificationSentToday(String addressee, int notificationTypeId) {
		return notificationLogDAO.isNotificationSentToday(addressee, notificationTypeId);
	}
	
	public void setNotificationLogDAO(NotificationLogDAO notificationLogDAO) {
		this.notificationLogDAO = notificationLogDAO;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}	
}