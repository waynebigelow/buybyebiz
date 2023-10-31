package ca.app.worker;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingStatus;
import ca.app.service.listing.ListingService;
import ca.app.service.mail.GlobalEmailType;
import ca.app.service.mail.MailService;
import ca.app.service.notificationLog.NotificationLogService;
import ca.app.util.DateUtil;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.StringUtil;

public class ScheduledTaskHandler implements Runnable {
	@Autowired
	private ListingService listingService;
	@Autowired
	private MailService mailService;
	@Autowired
	private NotificationLogService notificationLogService;
	
	@Override
	public void run() {
		LogUtil.logInfo(this.getClass(), "Scheduled task handler started.");
		
		try {
			handleExpiringListings();
		} catch(Exception ex) {
			LogUtil.logException(this.getClass(), "", ex);
			
			String to = ProjectUtil.getProperty("support.distribution.list");
			String from = ProjectUtil.getProperty("system.email.address");
			String subject = "UNEXPECTED EXCEPTION - ScheduledTaskHandler";
			String body = ex.getClass().getName() + "<br/>" + ex.getMessage() + "<br/>" + StringUtil.getStacktrace(ex.getStackTrace(), true);
			
			mailService.sendSystemEmail(to, from, subject, body);
		}
		
		LogUtil.logInfo(this.getClass(), "Scheduled task handler finished.");
	}

	private void handleExpiringListings() throws MessagingException {
		List<Listing> listings = listingService.getAllActiveListings();
		
		Date currentDate = new Date(System.currentTimeMillis());
		for (Listing listing : listings) {
			if (listing.getUser().isEnableExpirationEmail()) {
				Date baseDate = new Date(listing.getExpirationDate().getTime());
				if (baseDate.before(currentDate) || baseDate.equals(currentDate)) {
					listing.setEnabled(false);
					listing.setStatusId(ListingStatus.EXPIRED.getId());
					listingService.update(listing);
					
					if (!notificationLogService.isNotificationSentToday(listing.getUser().getEmail(), GlobalEmailType.SITE_EXPIRED.getId())) {
						mailService.sendCommonSiteEmail(listing, GlobalEmailType.SITE_EXPIRED);
						LogUtil.logInfo(this.getClass(), "Listing: " + listing.getListingId() + " has expired. Expiration: " + baseDate + ", System Date: " + currentDate);
					}
				} else {
					baseDate = DateUtil.incrementByDays(listing.getExpirationDate(), -2);
					
					if (baseDate.before(currentDate) || baseDate.equals(currentDate)) {
						if (!notificationLogService.isNotificationSentToday(listing.getUser().getEmail(), GlobalEmailType.SITE_EXPIRY_PENDING.getId())) {
							mailService.sendPendingExpiry(listing);
							LogUtil.logInfo(this.getClass(), "Listing: " + listing.getListingId() + " is approaching expiration. Approaching: " + baseDate + ", System Date: " + currentDate);
						}
					}
				}
			}
		}
	}
	
	public void setListingService(ListingService listingService) {
		this.listingService = listingService;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	public void setNotificationLogService(NotificationLogService notificationLogService) {
		this.notificationLogService = notificationLogService;
	}
}