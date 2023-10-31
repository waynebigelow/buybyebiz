package ca.app.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.app.util.LogUtil;
import ca.app.util.RequestUtil;

public class XSSFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpServletResponse = null;
		HttpServletRequest httpServletRequest = null;
		
		if(request instanceof HttpServletRequest) {
			httpServletRequest = (HttpServletRequest)request;
			httpServletRequest.setCharacterEncoding("UTF-8");
			
			httpServletResponse = (HttpServletResponse)response;
			httpServletResponse.setCharacterEncoding("UTF-8");
			
			boolean xssFailed = false;
			String xssValue = null; 
			String[] values = null;
			
			Map<String,String[]> parameters = httpServletRequest.getParameterMap();
			for(String key : parameters.keySet()) {
				values = parameters.get(key);
				
				for(String paramValue : values) {
					xssValue = RequestUtil.stripXSS(paramValue, key);
					
					if (!paramValue.equals(xssValue)) {
						LogUtil.logInfo(XSSFilter.class, "Field Name: " + key + "; User entered value: " + paramValue + "; XSS Failed Validation");
						xssFailed = true;
						break;
					}
				}
				
				if (xssFailed) {
					break;
				}
			}
			
			if (xssFailed) {
				httpServletResponse.sendError(500);
			}
		}
		
		chain.doFilter(new XSSRequestWrapper(httpServletRequest), httpServletResponse);
	}
}