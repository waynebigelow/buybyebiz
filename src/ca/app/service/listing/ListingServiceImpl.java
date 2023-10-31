package ca.app.service.listing;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.application.ApplicationPackage;
import ca.app.model.category.Category;
import ca.app.model.listing.EnquiryMap;
import ca.app.model.listing.EnquiryPost;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingPackage;
import ca.app.model.listing.MetaDataApproval;
import ca.app.model.listing.MetaDataStatus;
import ca.app.model.listing.Purchase;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.model.user.Roles;
import ca.app.model.user.User;
import ca.app.model.user.UserListingRole;
import ca.app.persistence.listing.EnquiryMapDAO;
import ca.app.persistence.listing.EnquiryPostDAO;
import ca.app.persistence.listing.ListingDAO;
import ca.app.persistence.listing.ListingPackageDAO;
import ca.app.persistence.listing.MetaDataApprovalDAO;
import ca.app.persistence.listing.PurchaseDAO;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.mail.MailService;
import ca.app.service.mail.SupportNotificationType;
import ca.app.service.usage.PageHitService;
import ca.app.service.user.UserService;
import ca.app.util.DateUtil;
import ca.app.util.LogUtil;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.paging.Page;

public class ListingServiceImpl implements ListingService {
	@Autowired
	private ListingDAO listingDAO;
	@Autowired
	private MetaDataApprovalDAO metaDataApprovalDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private EnquiryMapDAO enquiryMapDAO;
	@Autowired
	private EnquiryPostDAO enquiryPostDAO;
	@Autowired
	private ListingPackageDAO listingPackageDAO;
	@Autowired
	private PurchaseDAO purchaseDAO;
	@Autowired
	private MailService mailService;
	@Autowired
	private PageHitService pageHitService;
	
	// ------------------------------------------------------
	// The following methods are for Listing
	// ------------------------------------------------------
	public void create(Listing listing, AppUser appUser) {
		listingDAO.insert(listing);
		
		UserListingRole userListingRole = userService.getUserListingRole(listing.getUser().getUserId(), Roles.LISTING_OWNER.getRoleId(), listing.getApplication().getApplicationId(), 0);
		if (userListingRole == null) {
			userListingRole = userService.saveRoleForUserAndListing(listing.getUser().getUserId(), listing.getListingId(), Roles.LISTING_OWNER.getRoleId(), listing.getApplication().getApplicationId());
			appUser.getRoles().add(userListingRole);
		} else {
			userListingRole.setListingId(listing.getListingId());
			userService.saveUserListingRole(userListingRole);
			
			for (UserListingRole role : appUser.getRoles()) {
				if (role.getRoleId() == Roles.LISTING_OWNER.getRoleId()) {
					if (role.getListingId() == 0) {
						role.setListingId(listing.getListingId());
					}
				}
			}
		}
		
		ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), ActivityType.LISTING_CREATED.getId(), listing.getListingId(), userService.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public void update(Listing listing) {
		listingDAO.update(listing);
	}
	
	public void update(Listing listing, AppUser appUser, ActivityType activityType) {
		update(listing);
		
		ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), activityType.getId(), listing.getListingId(), (appUser==null?listing.getUser():userService.getByUserId(appUser.getUserId())));
		activityLogService.insert(activityLog);
	}
	
	public void delete(Listing listing, AppUser appUser) {
		listingDAO.update(listing);
		
		ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), ActivityType.LISTING_DELETED.getId(), listing.getListingId(), userService.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public Listing getByListingId(int listingId){
		return listingDAO.getByListingId(listingId);
	}
	
	public Listing getByListingURI(String listingURI) {
		return listingDAO.getByListingURI(listingURI);
	}
	
	public List<Listing> getAllActiveListings() {
		return listingDAO.getAllActiveListings();
	}
	
	public List<Category> getListedCategories(int applicationId) {
		return listingDAO.getListedCategories(applicationId);
	}
	
	public List<String> getLocationsByCategoryId(int categoryId) {
		return listingDAO.getLocationsByCategoryId(categoryId);
	}
	
	public List<ListingDTO> getAllListingsByUserId(int userId, boolean isPreview){
		return getListingDTO(listingDAO.getAllListingsByUserId(userId), isPreview);
	}
	
	public List<ListingDTO> getActiveListingsByUserId(int userId, boolean isPreview){
		return getListingDTO(listingDAO.getActiveListingsByUserId(userId), isPreview);
	}
	
	public Page<ListingDTO> searchListingByPage(Page<ListingDTO> page) {
		return listingDAO.searchListingByPage(page);
	}
	
	public Timestamp calculateExpiryDate(List<ListingPackage> packages) {
		int numDays = 0;
		int numMonths = 0;
		int numYears = 0;
		
		Calendar date = Calendar.getInstance();
		date.setTime(new Date(System.currentTimeMillis()));

		LogUtil.logDebug(getClass(), "Listing Base Date: " + DateUtil.format(date));
		
		for (ListingPackage listingPackage : packages) {
			ApplicationPackage appPackage = listingPackage.getApplicationPackage();
			
			LogUtil.logDebug(getClass(), "Processing listing package - duration = " + appPackage.getDuration() + " " + appPackage.getTimePeriod());
				
			switch(appPackage.getTimePeriod()) {
				case DAY:
					numDays += appPackage.getDuration(); break;
				case MONTH:
					numMonths += appPackage.getDuration(); break;
				case YEAR:
					numYears  += appPackage.getDuration(); break;
			}
			
			LogUtil.logDebug(getClass(), "Time aggregation: [numDays = "+numDays+"][numMonths="+numMonths+"][numYears="+numYears+"]");
		}
		
		date.add(Calendar.YEAR, numYears);
		date.add(Calendar.MONTH, numMonths);
		date.add(Calendar.DATE, numDays);
		
		LogUtil.logDebug(getClass(), "Final Expiry Date: " + DateUtil.format(date));
		
		return new Timestamp(date.getTimeInMillis());
	}
	
	private List<ListingDTO> getListingDTO(List<Listing> listings, boolean isPreview) {
		List<ListingDTO> dtos = new ArrayList<ListingDTO>();
		for (Listing listing : listings) {
			ListingDTO dto = new ListingDTO(listing, isPreview);
			dto.setPageHitCount(pageHitService.getPageHitCount(dto.getListingId()));
			
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	// ------------------------------------------------------
	// The following methods are for MetaDataApproval
	// ------------------------------------------------------
	public MetaDataApproval getByListingIdTypeId(int listingId, int typeId) {
		return metaDataApprovalDAO.getByListingIdTypeId(listingId, typeId);
	}
	
	public void processApprovals(Map<Integer, String> approvalMap, Listing listing) throws Exception {
		MetaDataApproval metaData = null;
		
		Iterator<Entry<Integer, String>> it = approvalMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> pair = (Map.Entry<Integer, String>)it.next();
			
			metaData = getByListingIdTypeId(listing.getListingId(), pair.getKey());
			if (metaData == null) {
				metaData = new MetaDataApproval();
				metaData.setListingId(listing.getListingId());
			}
			metaData.setStatusId(MetaDataStatus.PENDING_REVIEW.getId());
			metaData.setTypeId(pair.getKey());
			metaData.setValue(pair.getValue());
			metaData.setSubmitDate(new Timestamp(System.currentTimeMillis()));
			saveOrUpdate(metaData);
			
			it.remove();
		}
		
		if (listing.getExpirationDate() != null) {
			mailService.sendAdminEmail(listing, SupportNotificationType.CONTENT_MODIFIED);
		}
	}
	
	public void saveOrUpdate(MetaDataApproval metaData) {
		metaDataApprovalDAO.saveOrUpdate(metaData);
	}
	
	public void deleteMetaDataApprovalsByIds(String approvedIds) {
		metaDataApprovalDAO.deleteMetaDataApprovalsByIds(approvedIds);
	}
	
	public List<MetaDataApproval> loadMetaDataApprovalByListingId(int listingId) {
		return metaDataApprovalDAO.loadMetaDataApprovalByListingId(listingId);
	}
	
	public List<MetaDataApproval> loadMetaDataRejectionsByListingId(int listingId) {
		return metaDataApprovalDAO.loadMetaDataRejectionsByListingId(listingId);
	}
	
	// ------------------------------------------------------
	// The following methods are for EnquiryMap
	// ------------------------------------------------------
	public void create(EnquiryMap enquiryMap) {
		enquiryMapDAO.create(enquiryMap);
		
		ActivityLog activityLog = new ActivityLog(enquiryMap.getPoster(), Area.LISTING.getId(), ActivityType.ENQUIRY_POSTED.getId(), enquiryMap.getListingId(), enquiryMap.getPoster());
		activityLogService.insert(activityLog);
	}

	public EnquiryMap getByEnquiryMapId(int enquiryMapId) {
		return enquiryMapDAO.getByEnquiryMapId(enquiryMapId);
	}
	
	public EnquiryMap getByListingIdPosterId(int listingId, int posterId) {
		return enquiryMapDAO.getByListingIdPosterId(listingId, posterId);
	}
	
	public List<EnquiryMap> getByPosterId(User poster) {
		return enquiryMapDAO.getByPosterId(poster);
	}
	
	public List<EnquiryMap> getAllByListingId(int listingId) {
		return enquiryMapDAO.getAllByListingId(listingId);
	}
	
	// ------------------------------------------------------
	// The following methods are for EnquiryPost
	// ------------------------------------------------------
	
	public void create(EnquiryPost enquiry, Listing listing) {
		create(enquiry);
		
		User poster = userService.getByUserId(enquiry.getAuthorId());
		
		ActivityLog activityLog = new ActivityLog(poster, Area.LISTING.getId(), ActivityType.ENQUIRY_REPLIED.getId(), listing.getListingId(), poster);
		activityLogService.insert(activityLog);
	}
	
	public void create(EnquiryPost enquiry) {
		enquiryPostDAO.create(enquiry);
	}
	
	public void markReadByEnquiryMapId(int enquiryMapId, int userId) {
		enquiryPostDAO.markReadByEnquiryMapId(enquiryMapId, userId);
	}
	
	// ------------------------------------------------------
	// The following methods are for ListingPackage
	// ------------------------------------------------------
	public void create(ListingPackage listingPackage) {
		listingPackageDAO.insert(listingPackage);
	}
	public void delete(ListingPackage listingPackage) {
		listingPackageDAO.delete(listingPackage);
	}
	public void deleteByPackageId(int packageId) {
		listingPackageDAO.deleteByPackageId(packageId);
	}
	public ListingPackage getByPackageId(int packageId) {
		return listingPackageDAO.getByPackageId(packageId);
	}
	public List<ListingPackage> getAllPackages() {
		return listingPackageDAO.getAllPackages();
	}
	public int getPromoCount(int applicationId, int typeId, int currencyTypeId) {
		return listingPackageDAO.getPromoCount(applicationId, typeId, currencyTypeId);
	}
	// ------------------------------------------------------
	// The following methods are for Purchase
	// ------------------------------------------------------
	public Purchase getByPurchaseId(int purchaseId) {
		return purchaseDAO.getByPurchaseId(purchaseId);
	}
	public void create(Purchase purchase) {
		purchaseDAO.insert(purchase);
	}
	public void saveOrUpdate(Purchase purchase) {
		purchaseDAO.saveOrUpdate(purchase);
	}
	public void delete(Purchase purchase) {
		purchaseDAO.delete(purchase);
	}
	public void deleteByPurchaseId(int purchaseId){
		purchaseDAO.deleteByPurchaseId(purchaseId);
	}
	public List<Purchase> getAllPurchases(){
		return purchaseDAO.getAllPurchases();
	}
	
	// ------------------------------------------------------
	// The following are field setters
	// ------------------------------------------------------
	public void setListingDAO(ListingDAO listingDAO) {
		this.listingDAO = listingDAO;
	}
	public void setMetaDataApprovalDAO(MetaDataApprovalDAO metaDataApprovalDAO) {
		this.metaDataApprovalDAO = metaDataApprovalDAO;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setActivityLogService(ActivityLogService activityLogService) {
		this.activityLogService = activityLogService;
	}
	public void setEnquiryMapDAO(EnquiryMapDAO enquiryMapDAO) {
		this.enquiryMapDAO = enquiryMapDAO;
	}
	public void setEnquiryPostDAO(EnquiryPostDAO enquiryPostDAO) {
		this.enquiryPostDAO = enquiryPostDAO;
	}
	public void setListingPackageDAO(ListingPackageDAO listingPackageDAO) {
		this.listingPackageDAO = listingPackageDAO;
	}
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}

	public PageHitService getPageHitService() {
		return pageHitService;
	}
	public void setPageHitService(PageHitService pageHitService) {
		this.pageHitService = pageHitService;
	}
}