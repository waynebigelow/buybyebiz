package ca.app.service.search;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.common.CountryType;
import ca.app.model.common.MetaPageSettings;
import ca.app.model.common.ProvinceType;
import ca.app.model.listing.Listing;
import ca.app.model.photo.PhotoType;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.service.listing.ListingService;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.dto.listing.SiteDTO;
import ca.app.web.dto.photo.PhotoDTO;
import ca.app.web.dto.user.ContactDTO;
import ca.app.web.paging.Page;

@Controller
public class LoadSiteController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		Listing listing = null;
		
		int listingId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		if (listingId == 0) {
			String listingURI = request.getRequestURI().substring(0, request.getRequestURI().indexOf(".html"));
			listingURI = listingURI.substring((request.getContextPath().length()+1), listingURI.length());
			listing = listingService.getByListingURI(listingURI);
		} else {
			listing = listingService.getByListingId(listingId);
		}
		
		ListingDTO listingDTO = new ListingDTO(listing, false);
		
		PhotoDTO profilePhoto = null;
		PhotoDTO themePhoto = null;
		List<PhotoDTO> photoList = new ArrayList<PhotoDTO>();
		for (PhotoDTO photo : listingDTO.getPhotos()) {
			if (photo.getPhotoType() == PhotoType.PROFILE) {
				profilePhoto = photo;
			} else if (photo.getPhotoType() == PhotoType.GALLERY) {
				photoList.add(photo);
			} else if  (photo.getPhotoType() == PhotoType.THEME) {
				themePhoto = photo;
			}
		}
		
		SiteDTO siteDTO = new SiteDTO(listingDTO, new ContactDTO(listingDTO.getUser()), photoList, profilePhoto, themePhoto);
		
		Page<ListingDTO> page = new Page<ListingDTO>();
		page.getItems().add(siteDTO.getListing());
		
		if (!appUser.isSuperAdmin()) {
			try {
				pageHitService.logPageHit(listing, request, Usage.SITE);
			} catch (Exception ex) {
				LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
			}
		}
		
		ModelAndView mav = new ModelAndView("/listing/business");
		mav.addObject("site", siteDTO);
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("listings", page);
		mav.addObject("mapHeight", "200px");
		mav.addObject("demo", listing.getListingId() == StringUtil.convertStringToInt(ProjectUtil.getProperty("system.demo.listingId"), 0));
		mav.addObject("metaPageSettings", getMetaPageSettings(request, siteDTO));
		return mav;
	}
	
	private MetaPageSettings getMetaPageSettings(HttpServletRequest request, SiteDTO site) {
		ApplicationDTO applicationDTO = new ApplicationDTO((Application)request.getAttribute("application"));
		
		StringBuilder sb = new StringBuilder();
		sb.append(site.getListing().getTitle());
		sb.append(" - " + getMsg(request, site.getListing().getSubCategory().getI18n()) + " - For Sale ");
		sb.append("in " + site.getListing().getAddress().getCity());
		sb.append(", " + ProvinceType.get(site.getListing().getAddress().getProvince()).getLongName(getLocale(request)));
		sb.append(", " + CountryType.getByShortName(site.getListing().getAddress().getCountry()).getLongName());
		
		MetaPageSettings metaPageSettings = new MetaPageSettings();
		metaPageSettings.setPageTitle(site.getListing().getTitle() + " For Sale");
		metaPageSettings.setMetaAuthor(applicationDTO.getName());
		metaPageSettings.setMetaDescription(sb.toString() + ". "+ site.getListing().getDescription());
		metaPageSettings.setOgTitle(sb.toString());
		metaPageSettings.setOgDescription(site.getListing().getDescription());
		if (site.getThemePhoto() != null && site.getThemePhoto().getFileName() != null) {
			metaPageSettings.setOgImage(applicationDTO.getSecureApplicationURL()+"/"+site.getPhotoPath()+"/"+site.getThemePhoto().getFileName());
		}
		
		return metaPageSettings;
	}
}