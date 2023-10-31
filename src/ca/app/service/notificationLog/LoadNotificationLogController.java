package ca.app.service.notificationLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.configuration.AdminTabType;
import ca.app.model.notification.NotificationLog;
import ca.app.model.user.AppUser;
import ca.app.model.user.PageAccessiblity;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.notification.NotificationLogDTO;
import ca.app.web.paging.Page;
import flexjson.JSONSerializer;

@Controller
public class LoadNotificationLogController extends BaseController {
	@Autowired
	private NotificationLogService notificationLogService;
	private String type;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}
		
		PageAccessiblity pageAccess = new PageAccessiblity();
		pageAccess.configure(appUser);
		
		if (type.equals("page")) {
			String userId = request.getParameter("addressee");
			int start = StringUtil.convertStringToInt(request.getParameter("start"), 0);
			int limit = 10;
			
			Map<String, String> params = new HashMap<String, String>();
			if (!pageAccess.isSystemAdmin()) {
				params.put("userId", "" + appUser.getUserId());
			} else {
				params.put("userId", "" + userId);
			}
			
			Page<NotificationLogDTO> notificationLogPage = new Page<NotificationLogDTO>();
			
			notificationLogPage.setParams(params);
			notificationLogPage.setStart(start);
			notificationLogPage.setLimit(limit);
			notificationLogService.getNotificationLogPage(notificationLogPage);
			
			ModelAndView mav = new ModelAndView("/commonAdmin/notificationLog");
			mav.addObject("notificationLog", notificationLogPage);
			mav.addObject("pageAccess", pageAccess);
			mav.addObject("tabs", AdminTabType.values());
			mav.addObject("tab", AdminTabType.NOTIFICATION_LOG);
			return mav;
		} else {
			String logType = request.getParameter("logType");
			int primaryId  = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.PRIMARY_ID.getAlias()), TokenFieldType.PRIMARY_ID);
			
			List<NotificationLog> notificationLogs = null;
			if (logType.equals("listing")) {
				notificationLogs = notificationLogService.getAllByListingId(primaryId);
			} else {
				notificationLogs = notificationLogService.getAllByUserId(primaryId);
			}
			
			JSONSerializer json = new JSONSerializer();
			json.include("token","subject","sentTimeFormatted","type","primary");
			json.exclude("*");
			
			JsonUtil.setupResponseForJSON(response);
			response.getWriter().print(json.serialize(notificationLogs));
			response.getWriter().flush();
			return null;
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}
}