package ca.app.i18n;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ca.app.util.LogUtil;
import ca.app.util.ThreadLocalUtil;

public class CustomizableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
	
	public String getMessageInternal(String code, Object[] args, Locale locale) {
		String i18nOverrideKey = null;
		
		Map<String,Object> map = ThreadLocalUtil.get();
		if (map != null) {
			i18nOverrideKey = (String)map.get("i18nOverrideKey");
			LogUtil.logTrace(getClass(), "Got override key: " + i18nOverrideKey);
		}
		
		String msg = null;
		if (i18nOverrideKey!=null && i18nOverrideKey.length() > 0) {
			LogUtil.logTrace(getClass(), "Looking for: " + i18nOverrideKey + "." + code + " for locale " + locale);
			msg = super.getMessageInternal(i18nOverrideKey + "." + code, args, locale);
		}
		
		if (msg == null) {
			// Fall back on the core messages
			LogUtil.logTrace(getClass(), "Didn't find a message for " + code + " : " + locale + ", falling back");
			msg = super.getMessageInternal(code, args, locale);
		}

		return msg;
	}
}