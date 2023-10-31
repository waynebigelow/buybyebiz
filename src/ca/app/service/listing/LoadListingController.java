package ca.app.service.listing;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.listing.Listing;
import ca.app.model.photo.PhotoStatus;
import ca.app.model.photo.PhotoType;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.dto.listing.SiteDTO;
import ca.app.web.dto.photo.PhotoDTO;
import ca.app.web.dto.user.ContactDTO;
import ca.app.web.paging.Page;

@Controller
public class LoadListingController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || (!appUser.hasRole("ROLE_SUPER_ADMIN") && !appUser.hasRole("ROLE_LISTING_OWNER"))) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		Listing listing = null;
		
		int listingId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		if (listingId == 0) {
			Application application = (Application)request.getAttribute("application");
			listing = new Listing();
			listing.setApplication(application);
		} else {
			listing = listingService.getByListingId(listingId);
		}
		
		ListingDTO listingDTO = new ListingDTO(listing, true);
		
		PhotoDTO profilePhoto = null;
		PhotoDTO themePhoto = null;
		List<PhotoDTO> photoList = new ArrayList<PhotoDTO>();
		for (PhotoDTO photo : listingDTO.getPhotos()) {
			if (photo.getPhotoType() == PhotoType.PROFILE) {
				profilePhoto = photo;
				profilePhoto.setCaption("Click to edit photo.");
			} else if (photo.getPhotoType() == PhotoType.GALLERY) {
				photo.setCaption("Click to edit photo.");
				photoList.add(photo);
			} else if  (photo.getPhotoType() == PhotoType.THEME) {
				themePhoto = photo;
				themePhoto.setCaption("Click to edit photo.");
			}
		}
		
		if (photoList.size() < 8) {
			int x = photoList.size();
			PhotoDTO photo = null;
			for(int y = x; y <= 7; y++) {
				photo = new PhotoDTO(-1, PhotoType.GALLERY.getId(), "add-image.png", "Click to add a gallery photo.", PhotoStatus.PLACEHOLDER.getId());
				photoList.add(photo);
			}
		}
		
		if (profilePhoto == null) {
			profilePhoto = new PhotoDTO(-1, PhotoType.PROFILE.getId(), "add-profile.png", "Click to add a contact photo.", PhotoStatus.PLACEHOLDER.getId());
			profilePhoto.setPhotoTypeId(PhotoType.PROFILE.getId());
		}
		
		if (themePhoto == null) {
			themePhoto = new PhotoDTO(-1, PhotoType.THEME.getId(), "add-theme.png", "Click to add a theme photo.", PhotoStatus.PLACEHOLDER.getId());
			themePhoto.setPhotoId(-1);
		}
		
		SiteDTO site = null;
		if (listingId == 0) {
			site = new SiteDTO(listingDTO, new ContactDTO(listingDTO.getUser()), photoList, profilePhoto, themePhoto);
		} else {
			site = new SiteDTO(listingDTO, new ContactDTO(listingDTO.getUser()), photoList, profilePhoto, themePhoto);
		}
		
		Page<ListingDTO> page = new Page<ListingDTO>();
		page.getItems().add(site.getListing());
		
		try {
			pageHitService.logPageHit(listing, request, Usage.LISTING_PREVIEW);
		} catch (Exception ex) {
			LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
		}
		
		ModelAndView mav = new ModelAndView("/listingAdmin/business/preview");
		mav.addObject("site", site);
		mav.addObject("listings", page);
		mav.addObject("mapHeight", "200px");
		mav.addObject("pageAccess", pageAccess);
		return mav;
	}
}