package ca.app.service.activityLog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ca.app.model.user.AppUser;
import ca.app.service.common.BaseController;
import ca.app.service.common.TokenFieldType;
import ca.app.util.JsonUtil;
import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;
import ca.app.web.dto.user.ActivityLogDTO;
import flexjson.JSONSerializer;

@Controller
public class LoadActivityLogJSONController extends BaseController {
	@Autowired
	private ActivityLogService activityLogService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUser appUser = getAuthenticatedUser();
		if (appUser.getUserId() == 0 || !appUser.hasRole("ROLE_SUPER_ADMIN")) {
			response.sendRedirect(getBaseURL(request));
			LogUtil.logDebug(this.getClass(), "Unauthenticated or insufficient role provided for " + appUser.toString());
			return null;
		}

		int primaryId = RequestUtil.getPrimaryId(request.getParameter(TokenFieldType.PRIMARY_ID.getAlias()), TokenFieldType.PRIMARY_ID);
		String logType = request.getParameter("logType");

		List<ActivityLogDTO> activityLogs = null;
		if (logType.equals("l")) {
			activityLogs = activityLogService.getAllByListingId(primaryId);
		} else {
			activityLogs = activityLogService.getAllByUserId(primaryId);
		}
		
		JSONSerializer json = new JSONSerializer();
		json.include("area","type","activityDateFormatted","modifyUser.displayName");
		json.exclude("*");
		
		JsonUtil.setupResponseForJSON(response);
		response.getWriter().print(json.serialize(activityLogs));
		response.getWriter().flush();
		return null;
	}

	public void setActivityLogService(ActivityLogService activityLogService) {
		this.activityLogService = activityLogService;
	}
}