package ca.app.service.mail;

import java.util.Locale;

import org.apache.velocity.app.VelocityEngine;

import ca.app.model.application.Application;
import ca.app.util.AssetFolderUtil;
import ca.app.util.AssetFolderUtil.AssetFolder;
import ca.app.util.LogUtil;

public class TemplateFinder {

	private VelocityEngine velocityEngine;
	private Application application;
	private String templateAppName = "BuyByeMedia";
	private Locale locale = null;
	
	
	public TemplateFinder(VelocityEngine velocityEngine, Application application) {
		this.velocityEngine = velocityEngine;
		this.application = application;
	}
	
	public TemplateFinder(VelocityEngine velocityEngine, Application application, String templateAppName) {
		this(velocityEngine, application);
		this.templateAppName = templateAppName;
	}
	
	public TemplateFinder(VelocityEngine velocityEngine, Application application, Locale locale) {
		this(velocityEngine, application);
		this.locale = locale;
	}
	
	public TemplateFinder(VelocityEngine velocityEngine, Application application, String templateAppName, Locale locale) {
		this(velocityEngine, application, templateAppName);
		this.locale = locale;
	}
	
	public String resolve(String template) {
		String customTemplate = null;
		
		if (!templateAppName.equals("BuyByeMedia")) {
			customTemplate = findTemplate(template, templateAppName, locale);
			if (customTemplate != null) {
				LogUtil.logDebug(this.getClass(), " Found template: [case=1][locale=" + locale + "][result=" + customTemplate + "]");
				return customTemplate;
			}
		} 
		
		if (application != null) {
			customTemplate = findTemplate(template, application.getName(), locale);
			if (customTemplate != null) {
				LogUtil.logDebug(this.getClass(), " Found template: [case=2][locale=" + locale + "][result=" + customTemplate + "]");
				return customTemplate;
			}
		}
		
		
		if (locale != null) {
			customTemplate = getLocaleSpecificTemplate(template, locale);
			if (velocityEngine.resourceExists(customTemplate)) {
				LogUtil.logDebug(this.getClass(), " Found template: [case=4a][locale=" + locale + "][result=" + customTemplate + "]");
				return customTemplate;
			}
		}

		LogUtil.logDebug(this.getClass(), " Found template: [case=4b][locale=" + locale + "][result=" + template + "]");
		return template;
	}
	
	private String findTemplate(String template, String appName, Locale locale) {
		String customTemplate = null;
		
		if (locale != null) {
			customTemplate = AssetFolderUtil.getPath(AssetFolder.APPLICATION, appName, 0, false) + "/" + getLocaleSpecificTemplate(template, locale);
			if (velocityEngine.resourceExists(customTemplate)) {
				return customTemplate;
			}
		}
		
		customTemplate = AssetFolderUtil.getPath(AssetFolder.APPLICATION, appName, 0, false) + "/" + template;
		if (velocityEngine.resourceExists(customTemplate)) {
			return customTemplate;
		}
		
		return null;
	}
	
	private String getLocaleSpecificTemplate(String template, Locale locale) {
		return template.replace(".vm", "_" + locale.toString() + ".vm");
	}
}