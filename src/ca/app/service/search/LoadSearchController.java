package ca.app.service.search;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.common.CountryType;
import ca.app.model.common.MetaPageSettings;
import ca.app.model.common.ProvinceType;
import ca.app.model.common.StateType;
import ca.app.model.usage.Usage;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.listing.ListingService;
import ca.app.service.usage.PageHitService;
import ca.app.util.LogUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.paging.Page;

@Controller
public class LoadSearchController extends BaseController {
	@Autowired
	private ListingService listingService;
	@Autowired
	private PageHitService pageHitService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		String dir = request.getParameter("dir");
		String categoryId = request.getParameter("category");
		String subCategoryId = request.getParameter("subCategory");
		String minPrice = request.getParameter("minPrice");
		String maxPrice = request.getParameter("maxPrice");
		String province = StringUtil.getString(request.getParameter("province"), ProvinceType.ON.getShortName());
		String country = StringUtil.getString(request.getParameter("country"), CountryType.CA.getShortName());
		int start = StringUtil.convertStringToInt(request.getParameter("start"), 0);
		int limit = 10;
		
		Application application = (Application)request.getAttribute("application");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("applicationId", application.getApplicationId() + "");
		params.put("enabled", "1");
		params.put("categoryId", categoryId + "");
		params.put("subCategoryId", subCategoryId + "");
		params.put("minPrice", minPrice + "");
		params.put("maxPrice", maxPrice + "");
		params.put("province", province);
		params.put("country", country);
		params.put("countryLongName", CountryType.getByShortName(country).getLongName());
		
		if (!province.equals("")) {
			if (CountryType.getByShortName(country) == CountryType.CA) {
				params.put("zoom", ProvinceType.get(province).getMapZoom());
			} else {
				params.put("zoom", StateType.get(province).getMapZoom());
			}
		} else {
			params.put("zoom", CountryType.getByShortName(country).getMapZoom());
		}
		
		Page<ListingDTO> page = new Page<ListingDTO>();
		page.setParams(params);
		page.setStart(start);
		page.setLimit(limit);
		page.setDir(dir == null ? "asc" : dir);
		page.setSort("price");
		listingService.searchListingByPage(page);
		
		if (!appUser.isSuperAdmin()) {
			try {
				pageHitService.logPageHit(null, request, Usage.SEARCH);
			} catch (Exception ex) {
				LogUtil.logInfo(this.getClass(), "Page hit error:" + ex.getMessage());
			}
		}

		ModelAndView mav = new ModelAndView("/listing/search");
		mav.addObject("pageAccess", pageAccess);
		mav.addObject("listings", page);
		mav.addObject("mapHeight", "500px");
		mav.addObject("metaPageSettings", getMetaPageSettings(application));
		return mav;
	}
	
	private MetaPageSettings getMetaPageSettings(Application application) {
		ApplicationDTO applicationDTO = new ApplicationDTO(application);
		
		MetaPageSettings metaPageSettings = new MetaPageSettings();
		metaPageSettings.setPageTitle(applicationDTO.getName() + " - Advertising and Finding Businesses for Sale");
		metaPageSettings.setMetaAuthor(applicationDTO.getName());
		metaPageSettings.setMetaDescription(applicationDTO.getDescription());
		metaPageSettings.setOgTitle(applicationDTO.getName() + " - Advertising and Finding Businesses for Sale");
		metaPageSettings.setOgDescription(applicationDTO.getDescription());
		metaPageSettings.setOgImage(applicationDTO.getSecureApplicationURL()+"/buybyemedia/app/buybyebiz/images/theme.png");
		
		return metaPageSettings;
	}
}