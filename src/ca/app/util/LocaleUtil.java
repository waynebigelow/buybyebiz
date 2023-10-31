package ca.app.util;

import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;


public class LocaleUtil {
	
	public static Locale toLocale(String localeCode) {
		Locale locale = null;
		if (localeCode!=null && localeCode.trim().length()>0) {
			try {
				locale = LocaleUtils.toLocale(localeCode);
			} catch (IllegalArgumentException ex) {}
		}
		if (locale==null || locale.toString()==null || locale.toString().trim().length()==0) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
}
