package ca.app.service.admin;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingStatus;
import ca.app.model.listing.MetaDataApproval;
import ca.app.model.listing.MetaDataStatus;
import ca.app.model.photo.Photo;
import ca.app.model.photo.PhotoStatus;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.service.mail.GlobalEmailType;
import ca.app.service.mail.MailService;
import ca.app.service.user.UserService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

@Controller
public class SaveApprovalsController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private MailService mailService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private UserService userService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		int listingId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		int approvedCount = 0;
		int rejectedCount = 0;
		String approvedIds = null;
		String approved = null;
		for (MetaDataApproval metaData : listing.getMetaDataApprovals()) {
			approved = request.getParameter("approveMeta"+metaData.getMetaDataId());
			if (approved != null) {
				if (approvedIds == null){
					approvedIds = ""+metaData.getMetaDataId();
				} else {
					approvedIds = approvedIds+","+metaData.getMetaDataId();
				}
				
				switch (metaData.getMetaDataType()) {
					case TITLE : listing.setTitle(metaData.getValue()); break;
					case DESCRIPTION : listing.setDescription(metaData.getValue()); break;
					case SELLING_REASON : listing.getBusinessDetails().setSellingReason(metaData.getValue()); break;
					case HOURS_OF_OPERATION : listing.getBusinessDetails().setHoursOfOperation(metaData.getValue()); break;
					case YEAR_ESTABLISHED : listing.getBusinessDetails().setYearEstablished(metaData.getValue()); break;
					case NUMBER_OF_EMPLOYEES : listing.getBusinessDetails().setNumberOfEmployees(metaData.getValue()); break;
					case FRONTAGE : listing.getBusinessDetails().setFrontage(metaData.getValue()); break;
					case SQUARE_FOOTAGE : listing.getBusinessDetails().setSquareFootage(metaData.getValue()); break;
					case ACREAGE : listing.getBusinessDetails().setAcreage(metaData.getValue()); break;
					case SUPPORT : listing.getBusinessDetails().setSupport(metaData.getValue()); break;
					case OWNER_FINANCING : listing.getBusinessDetails().setOwnerFinancing(metaData.getValue()); break;
					case WEBSITE_URL : listing.getBusinessDetails().setWebsiteURL(metaData.getValue()); break;
					case FACEBOOK_URL : listing.getBusinessDetails().setFacebookURL(metaData.getValue()); break;
					case TWITTER_URL : listing.getBusinessDetails().setTwitterURL(metaData.getValue()); break;
					case TRIP_ADVISOR_URL : listing.getBusinessDetails().setTripAdvisorURL(metaData.getValue()); break;
					case FINANCIAL_OTHER : listing.getBusinessDetails().setFinancialOther(metaData.getValue()); break;
					case PROPERTY_OTHER : listing.getBusinessDetails().setPropertyOther(metaData.getValue()); break;
					case PROPERTY_TAX : listing.getBusinessDetails().setPropertyTax(metaData.getValue()); break;
					case OWNERS_RESIDENCE : listing.getBusinessDetails().setOwnersResidence(metaData.getValue()); break;
					case OPERATION_OTHER : listing.getBusinessDetails().setOperationOther(metaData.getValue()); break;
					case MULTIMEDIA_LINK : listing.getBusinessDetails().setMultiMediaLink(metaData.getValue()); break;
					case AGENT_LISTING_LINK : listing.getBusinessDetails().setAgentListingLink(metaData.getValue()); break;
				}
				
				approvedCount++;
			} else {
				metaData.setStatusId(MetaDataStatus.REJECTED.getId());
				metaData.setReason(request.getParameter("metaReason"+metaData.getMetaDataId()));
				metaData.setRejectedDate(new Timestamp(System.currentTimeMillis()));
				listingService.saveOrUpdate(metaData);
				
				rejectedCount++;
			}
		}
		
		for (Photo photo : listing.getPhotos()) {
			if (photo.getStatus() == PhotoStatus.PENDING_REVIEW) {
				approved = request.getParameter("approvePhoto"+photo.getPhotoId());
				if (approved != null) {
					photo.setStatusId(PhotoStatus.APPROVED.getId());
					
					approvedCount++;
				} else {
					photo.setStatusId(PhotoStatus.REJECTED.getId());
					photo.setReason(request.getParameter("photoReason"+photo.getPhotoId()));
					photo.setRejectedDate(new Timestamp(System.currentTimeMillis()));
					
					rejectedCount++;
				}
			}
		}
		
		if (approvedCount > 0 || rejectedCount > 0) {
			if (approvedCount > 0) {
				listingService.deleteMetaDataApprovalsByIds(approvedIds);
			}
			
			if (rejectedCount > 0){
				// If expiration date hasn't been set then its still in Draft
				if (listing.getExpirationDate() == null) {
					listing.setStatusId(ListingStatus.DRAFT.getId());
				}
				
				ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), ActivityType.LISTING_CONTENT_REJECTED.getId(), listing.getListingId(), userService.getByUserId(appUser.getUserId()));
				activityLogService.insert(activityLog);
				
				mailService.sendCommonSiteEmail(listing, GlobalEmailType.SITE_CONTENT_REJECTED);
			} else {
				ActivityLog activityLog = new ActivityLog(listing.getUser(), Area.LISTING.getId(), ActivityType.LISTING_CONTENT_APPROVED.getId(), listing.getListingId(), userService.getByUserId(appUser.getUserId()));
				activityLogService.insert(activityLog);
			}
			
			listingService.update(listing);
		}
		return null;
	}
}