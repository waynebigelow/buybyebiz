package ca.app.util;

import org.jsoup.safety.Whitelist;

public class JsoupWhiteListUtil {
	
	private static JsoupWhiteListUtil instance = null;
	private static Whitelist whitelist = null;
	
	protected JsoupWhiteListUtil() {
	}
 
	private static JsoupWhiteListUtil initializeInstance() {
		if (instance == null) {
			synchronized (JsoupWhiteListUtil.class) {
				if (instance == null) {
					instance = new JsoupWhiteListUtil();
					instance.initializeWhitelist();
				}
			}
		}
		return instance;
	}
	
	private Whitelist initializeWhitelist() {
		String whiteList = ProjectUtil.getProperty("jsoup.whitelist");
		String[] exceptions = null;
		if (whiteList != null && !whiteList.equals("")) {
			exceptions = ProjectUtil.getProperty("jsoup.whitelist").split(",");
		}
		
		whitelist = Whitelist.none();
		
		if (exceptions != null) {
			for (String tag : exceptions) {
				whitelist.addTags(tag);
			}
		}
		
		return whitelist;
	}
	
	public static Whitelist getWhitelist() {
		if (whitelist == null || instance == null) {
			initializeInstance();
		}
		
		return whitelist;
	}
	
	public static void reload() {
		if(instance != null) {
			instance = null;
		}
		if(whitelist != null) {
			whitelist = null;
		}
		initializeInstance();
	}
}