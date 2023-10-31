package ca.app.model.listing;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtil {

	public static URLParseResult extractSiteURI(String contextPath, String requestURI) throws IOException {
		requestURI = requestURI.toLowerCase();
		if (requestURI.endsWith("/")) {
			requestURI = requestURI.substring(0, requestURI.length()-1);
		}
		
		String listingURI = doPatternMatch(requestURI, "^.*/listingAdmin/(.*)$");
		if (listingURI != null && listingURI.contains(".html")) {
			listingURI = listingURI.substring(0, listingURI.indexOf(".html"));
		}
		
		if (listingURI == null || listingURI.equals("")) {
			listingURI = doPatternMatch(requestURI, "^.*/(.*)$");
			
			if (listingURI != null && listingURI.contains(".html")) {
				listingURI = listingURI.substring(0, listingURI.indexOf(".html"));
			}
		}
		
		URLParseResult result = new URLParseResult();
		result.setListingURI(true);
		result.setListingURI(listingURI);

		return result;
	}
	
	private static String doPatternMatch(String uri, String re) {
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(uri);
		if (m.matches()) {
			return m.group(1);
		}
		
		return null;
	}
	
	public static String buildListingURI(String listingURI) {
		return "/listing/" + listingURI + ".html";
	}
}