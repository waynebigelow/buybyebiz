package ca.app.util;

import java.util.Map;

public class ThreadLocalUtil {

	private final static ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>();

	public static void set(Map<String, Object> map) {
		context.set(map);
	}

	public static Map<String, Object> get() {
		return (Map<String, Object>) context.get();
	}
	
	public static void cleanup() {
		context.remove();
	}
}
