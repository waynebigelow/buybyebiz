package ca.app.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ca.app.model.application.Application;
import ca.app.service.application.ApplicationService;
import ca.app.util.AssetFolderUtil;
import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;

public class ApplicationCheckFilter implements Filter {
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		if(arg0 instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest)arg0;
			HttpServletResponse response = (HttpServletResponse)arg1;
			
			if (!request.getRequestURI().endsWith("/status.jsp")) {
				if (!checkApplication(request,response)) {
					return;
				}
			}
		}
		
		arg2.doFilter(arg0, arg1);
	}
	
	public static boolean checkApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ApplicationService applicationService = (ApplicationService)getWAC(request).getBean("applicationService");
		Application application = applicationService.getApplicationForHostName(request.getServerName());
		
		if (application == null) {
			LogUtil.logDebug(ApplicationCheckFilter.class, "application was null, sending user to baseURL");
			response.sendRedirect(getBaseURL(request));
			return false;
		} else {
			String baseDir = ProjectUtil.getProperty("upload.location");
			String applicationPath = AssetFolderUtil.getPath(AssetFolder.APPLICATION, application.getName(), 0, true);
			new File(baseDir + File.separator + applicationPath).mkdirs();
		}
		
		request.setAttribute("application", application);
		
		return true;
	}
	
	private static WebApplicationContext getWAC(HttpServletRequest request) {
		return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	}
	
	private static String getBaseURL(HttpServletRequest request) {
		//return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/home.html";
		return "https://" + request.getServerName() + ":" + request.getServerPort() + ProjectUtil.getProperty("webapp.home.url");
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	public void destroy() {}
}