package ca.app.service.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.application.Application;
import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;
import ca.app.model.common.CountryType;
import ca.app.model.common.Option;
import ca.app.model.common.ProvinceType;
import ca.app.model.common.StateType;
import ca.app.model.listing.Listing;
import ca.app.model.usage.Action;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.application.ApplicationService;
import ca.app.service.listing.ListingService;
import ca.app.service.notificationLog.NotificationLogService;
import ca.app.service.usage.PageHitService;
import ca.app.util.JsonUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.application.ApplicationPackageDTO;
import ca.app.web.dto.category.CategoryDTO;

@Controller
public class CommonInfoController extends BaseMultiActionController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ListingService listingService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private PageHitService pageHitService;
	@Autowired
	private NotificationLogService notificationLogService;

	public ModelAndView listCountries(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		StringBuilder buff = new StringBuilder();
		buff.append("{\"rows\" : [");
		buff.append("{");
		buff.append("\"id\":\"\"");
		buff.append(", ");
		buff.append("\"name\":\""+"--"+getMsg(locale, "0.txt.select")+" "+getMsg(locale, "25.lbl.country")+"--"+"\"");
		buff.append("}");
		int cnt = 1;
		for (CountryType type : CountryType.getTypes()) {
			buff.append((cnt > 0) ? "," : "");
			buff.append("{");
			buff.append("\"id\":\"" + type.getShortName() + "\"");
			buff.append(", ");
			buff.append("\"name\":\"" + type.getLongName() + "\"");
			buff.append("}");
			cnt++;
		}
		buff.append("]}");

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(buff.toString());
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listProvincesOrStates(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		String aType = ServletRequestUtils.getStringParameter(request, "type");
		if (aType == null) {
			aType = "";
		}
		
		String country = ServletRequestUtils.getStringParameter(request, aType + "country");
		if (country == null) {
			country = "CA";
		}
		
		boolean isChained = StringUtil.convertStringToBoolean(request.getParameter("c"), false);
		
		StringBuilder buff = new StringBuilder();
		int cnt = 1;
		if (country.equals(CountryType.CA.getShortName()) || country.equals(CountryType.CA.getLongName())){
			buff.append(commonJSONEntry(isChained, "", "--"+getMsg(locale, "0.txt.select")+" "+getMsg(locale, "25.lbl.province")+"--"));
			for (ProvinceType type : ProvinceType.values()) {
				buff.append((cnt > 0) ? "," : "");
				buff.append(commonJSONEntry(isChained, type.getShortName(), type.getLongName(locale)));
				cnt++;
			}
		} else {
			buff.append(commonJSONEntry(isChained, "", "--"+getMsg(locale, "0.txt.select")+" "+getMsg(locale, "25.lbl.state")+"--"));
			for (StateType type : StateType.values()) {
				buff.append((cnt > 0) ? "," : "");
				buff.append(commonJSONEntry(isChained, type.getShortName(), type.getLongName(locale)));
				cnt++;
			}
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print((isChained ? "["+buff.toString()+"]" : "{\"rows\" : ["+buff.toString()+"]}"));
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listCategories(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		boolean isChained = StringUtil.convertStringToBoolean(request.getParameter("c"), false);
		
		Application application = (Application)request.getAttribute("application");
		List<CategoryDTO> categories = applicationService.getAllByTypeId(application.getTypeId());
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(isChained, "", "--"+getMsg(locale, "0.txt.select")+" "+getMsg(locale, "20.lbl.category")+"--"));
			
		int cnt = 1;
		for (CategoryDTO category : categories) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(isChained, String.valueOf(category.getCategoryId()) , getMsg(locale, category.getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print((isChained ? "["+buff.toString()+"]" : "{\"rows\" : ["+buff.toString()+"]}"));
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listSubCategories(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		int categoryId = StringUtil.convertStringToInt(request.getParameter("category"), 0);
		boolean isChained = StringUtil.convertStringToBoolean(request.getParameter("c"), false);
		
		List<SubCategory> subCategories = applicationService.getAllByCategoryId(categoryId);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(isChained, "", "--"+getMsg(locale, "0.txt.select")+" "+getMsg(locale, "20.lbl.sub.category")+"--"));
			
		int cnt = 1;
		for (SubCategory subCategory : subCategories) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(isChained, String.valueOf(subCategory.getSubCategoryId()) , getMsg(locale, subCategory.getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print((isChained ? "["+buff.toString()+"]" : "{\"rows\" : ["+buff.toString()+"]}"));
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listedCategories(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		boolean isChained = StringUtil.convertStringToBoolean(request.getParameter("c"), false);
		
		Application application = (Application)request.getAttribute("application");
		List<Category> categories = listingService.getListedCategories(application.getApplicationId());
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(isChained, "", "--"+getMsg(locale, "20.lbl.category")+"--"));
			
		int cnt = 1;
		for (Category category : categories) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(isChained, String.valueOf(category.getCategoryId()) , getMsg(locale, category.getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print((isChained ? "["+buff.toString()+"]" : "{\"rows\" : ["+buff.toString()+"]}"));
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listedLocations(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		int categoryId = StringUtil.convertStringToInt(request.getParameter("category"), 0);
		boolean isChained = StringUtil.convertStringToBoolean(request.getParameter("c"), false);
		
		List<String> countries = listingService.getLocationsByCategoryId(categoryId);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(isChained, "", "--"+getMsg(locale, "25.lbl.country")+"--"));
			
		int cnt = 1;
		for (String country : countries) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(isChained, country , CountryType.getByShortName(country).getLongName()));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print((isChained ? "["+buff.toString()+"]" : "{\"rows\" : ["+buff.toString()+"]}"));
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listAppPackages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--"+getMsg(locale, "0.txt.select")+" "+"Package"+"--"));
		
		int listingId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.LISTING.getAlias()), TokenFieldType.LISTING);
		Listing listing = listingService.getByListingId(listingId);
		
		if (listing != null) {
			int applicationId = listing.getApplication().getApplicationId();
			int currencyId = listing.getCurrencyTypeId();
			List<ApplicationPackageDTO> applicationPackages = applicationService.getByAppIdAndCurrencyId(applicationId, currencyId);
	
			int cnt = 1;
			for (ApplicationPackageDTO applicationPackage : applicationPackages) {
				buff.append((cnt > 0) ? "," : "");
				buff.append(commonJSONEntry(false, applicationPackage.getToken() , applicationPackage.getName()));
				cnt++;
			}
		}
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listUserActivityAreas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		Locale locale = BaseController.getLocaleStatic(request);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--All Areas"+"--"));
		
		int typeId = StringUtil.convertStringToInt(request.getParameter("type"), 0);
		int authorId = StringUtil.convertStringToInt(request.getParameter("author"), 0);
		
		List<BigDecimal> areaIds = null;
		if (appUser.hasRole("ROLE_SUPER_ADMIN")) {
			areaIds = activityLogService.getAreaIdsByUserId(typeId, authorId);
		} else {
			areaIds = activityLogService.getAreaIdsByUserId(typeId, appUser.getUserId());
		}

		int cnt = 1;
		for (BigDecimal areaId : areaIds) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ""+areaId , getMsg(locale, Area.get(areaId.intValue()).getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listUserActivityTypes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		Locale locale = BaseController.getLocaleStatic(request);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--All Types"+"--"));
		
		int areaId = StringUtil.convertStringToInt(request.getParameter("area"), 0);
		int authorId = StringUtil.convertStringToInt(request.getParameter("author"), 0);
		
		List<BigDecimal> typeIds = null;
		if (appUser.hasRole("ROLE_SUPER_ADMIN")) {
			typeIds = activityLogService.getTypeIdsByUserId(areaId, authorId);
		} else {
			typeIds = activityLogService.getTypeIdsByUserId(areaId, appUser.getUserId());
		}

		int cnt = 1;
		for (BigDecimal typeId : typeIds) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ""+typeId , getMsg(locale, ActivityType.get(typeId.intValue()).getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listActivityAuthors(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--All Authors"+"--"));
		
		int areaId = StringUtil.convertStringToInt(request.getParameter("area"), 0);
		int typeId = StringUtil.convertStringToInt(request.getParameter("type"), 0);
		
		List<User> authors = activityLogService.getAllActivityAuthors(areaId, typeId);

		int cnt = 1;
		for (User author : authors) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ""+author.getUserId(), author.getLastName()+", "+author.getFirstName()));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listPageHitAreas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--All Areas"+"--"));
		
		int actionId = StringUtil.convertStringToInt(request.getParameter("action"), 0);
		String ipAddress = request.getParameter("ipAddress");
		
		List<BigDecimal> areaIds = pageHitService.getAreaIdsByActionAndIP(actionId, ipAddress);

		int cnt = 1;
		for (BigDecimal areaId : areaIds) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ""+areaId , getMsg(locale, Area.get(areaId.intValue()).getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listPageHitActions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale locale = BaseController.getLocaleStatic(request);
		
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--All Actions"+"--"));
		
		int areaId = StringUtil.convertStringToInt(request.getParameter("area"), 0);
		String ipAddress = request.getParameter("ipAddress");
		
		List<BigDecimal> actionIds = pageHitService.getActionIdsByAreaAndIP(areaId, ipAddress);
		
		int cnt = 1;
		for (BigDecimal actionId : actionIds) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ""+actionId , getMsg(locale, Action.get(actionId.intValue()).getI18n())));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listPageHitIPAddresses(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--All IP Addresses"+"--"));
		
		int areaId = StringUtil.convertStringToInt(request.getParameter("area"), 0);
		int actionId = StringUtil.convertStringToInt(request.getParameter("action"), 0);
		
		List<String> ipAddresses = pageHitService.getIPByAreaAndAction(areaId, actionId);

		int cnt = 1;
		for (String ipAddress : ipAddresses) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ipAddress, ipAddress));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public ModelAndView listAddressees(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuilder buff = new StringBuilder();
		buff.append(commonJSONEntry(false, "", "--Filter by Addressee"+"--"));
		
		List<Option> options = notificationLogService.getAllNotifications();

		int cnt = 1;
		for (Option log : options) {
			buff.append((cnt > 0) ? "," : "");
			buff.append(commonJSONEntry(false, ""+log.getId(), log.getLabel()));
			cnt++;
		}

		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print("{\"rows\" : ["+buff.toString()+"]}");
		response.getWriter().flush();

		return null;
	}
	
	public String commonJSONEntry(boolean isChained, String id, String name){
		StringBuilder buff = new StringBuilder();
		
		if (!isChained){
			buff.append("{");
			buff.append("\"id\":\"" + id + "\"");
			buff.append(", ");
			buff.append("\"name\":\"" + name + "\"");
			buff.append("}");
		} else {
			buff.append("{");
			buff.append("\"" + id + "\"");
			buff.append(":");
			buff.append("\"" + name + "\"");
			buff.append("}");
		}
		
		return buff.toString();
	}
}