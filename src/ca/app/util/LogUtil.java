package ca.app.util;

 import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
	
public class LogUtil {
	
	public static void logInfo(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
		if(isConfigured()){
			log.info(message);	
		}else{
			System.out.println(message);
		}
	}
	
	public static void logDebug(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
		if(isConfigured()){
			log.debug(message);
		}else{
			System.out.println(message);
		}
	}
	
	public static void logWarn(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
	 	if(isConfigured()){
	 		log.warn(message);
	 	}else{
			System.out.println(message);
		}
	}
	
	public static void logError(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
		if(isConfigured()){
			log.error(message);
		}else{
			System.out.println(message);
		}
	}
	
	public static void logFatal(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
		if(isConfigured()){
			log.fatal(message);
		}else{
			System.out.println(message);
		}
	}
	
	public static void logTrace(Class<?> clazz, String message) {
		Log log = LogFactory.getLog(clazz);
		if(isConfigured()){
			log.trace(message);
		}else{
			System.out.println(message);
		}
	}
	
	public static void logException(Class<?> clazz, String message, Throwable throwable) {
		Log log = LogFactory.getLog(clazz);
		if(isConfigured()){
			log.error(message, throwable);
		}else{
			System.out.println(message);
			throwable.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static boolean isConfigured() {
		Enumeration appenders = LogManager.getRootLogger().getAllAppenders();
		if (appenders.hasMoreElements()) {
			return true;
		} else {
			Enumeration loggers = LogManager.getCurrentLoggers() ;
			while (loggers.hasMoreElements()) {
				Logger c = (Logger) loggers.nextElement();
				if (c.getAllAppenders().hasMoreElements()) {
					return true;
				}
			}
		}
		return false;
	}
}