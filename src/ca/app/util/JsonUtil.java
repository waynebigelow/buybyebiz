package ca.app.util;

import javax.servlet.http.HttpServletResponse;

public class JsonUtil {

	public static void setupResponseForJSON(HttpServletResponse response) {
		setupResponseForJSON(response, null);
	}
		
	public static void setupResponseForJSON(HttpServletResponse response, String contentType) {
		if (contentType==null || contentType.length()==0) {
			contentType = "text/json";
		}
		LogUtil.logDebug(JsonUtil.class, "Response content-type: " + contentType);
		
		response.setContentType(contentType + "; charset=utf-8");
		response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
	}
}