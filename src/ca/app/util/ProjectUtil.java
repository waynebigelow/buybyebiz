package ca.app.util;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ProjectUtil {
	private static ProjectUtil instance = null;
	private static Properties props = null;
	private static Random random = null;
	
	private ProjectUtil() {}
	
	private static void initializeInstance() {
		instance = new ProjectUtil();
		instance.initializeProperties();
	}
	
	private void initializeProperties() {
		try {
			props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
			random = new Random();
		} catch (IOException ex) {
			LogUtil.logException(this.getClass(), "Error loading application properties", ex);
		}
	}
	
	public static void reload() {
		if(instance != null) {
			instance = null;
		}
		if(props != null) {
			props = null;
		}
		initializeInstance();
	}
	
	public static String getProperty(String key) {
		if (props == null || instance == null) {
			initializeInstance();
		}
		String value = props.getProperty(key);
		if (value != null) {
			return value.trim();
		}
		return value;
	}
	
	public static Properties getProperties() {
		if (props == null || instance == null) {
			initializeInstance();
		}
		return props;
	}
	
	public static Random getRandom() {
		if (props==null || instance==null) {
			initializeInstance();
		}
		return random;
	}
	
	public static int getIntProperty(String key) {
		return StringUtil.convertStringToInt(getProperty(key),0);
	}
	
	public static long getLongProperty(String key) {
		return StringUtil.convertStringToLong(getProperty(key),0L);
	}
	
	public static boolean getBooleanProperty(String key) {
		return getBooleanProperty(key,false);
	}
	
	public static boolean getBooleanProperty(String key, boolean defaultValue) {
		return StringUtil.convertStringToBoolean(getProperty(key),defaultValue);
	}
	
	public static boolean isEnforceMaxPasswordAge() {
		return StringUtil.convertStringToBoolean(getProperty("enforce.max.password.age"), false);
	}
	
	public static int getMaxPasswordAge() {
		return StringUtil.convertStringToInt(getProperty("max.password.age"), 50);
	}
	
	public static boolean isEnforceMinPasswordRepeat() {
		return StringUtil.convertStringToBoolean(getProperty("enforce.no.password.repeat"), false);
	}
	
	public static int getMinPasswordRepeat() {
		return StringUtil.convertStringToInt(getProperty("min.password.repeat"), 50);
	}
}