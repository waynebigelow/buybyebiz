package ca.app.service.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import ca.app.model.user.AppUser;
import ca.app.util.RequestUtil;

public abstract class BaseMultiActionController extends MultiActionController implements MessageSourceAware {

	private MessageSource messageSource;

	protected AppUser getAuthenticatedUser(HttpServletRequest request) {
		AppUser user = null;
		Object obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof AnonymousAuthenticationToken) {
			user = AppUser.getAnonymousUser();
		} else {
			user = (AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}
	
	protected AppUser getAuthenticatedUser() {
		AppUser user = null;
		Object obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof AnonymousAuthenticationToken) {
			user = AppUser.getAnonymousUser();
		} else {
			user = (AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}
	
	public String getMsg(Locale locale, String code) {
		return messageSource.getMessage(code, null, locale);
	}
	
	public String getMsg(Locale locale, String code, Object[] args) {
		return messageSource.getMessage(code, args, locale);
	}

	public Locale getLocale(HttpServletRequest request) {
		Locale locale = (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if (locale==null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	protected boolean requiresUpdate(String newValue, String oldValue) {
		if (newValue==null && oldValue==null) {
			return false;
		} else if (newValue==null && "".equals(oldValue)) {
			return false;
		} else if (oldValue==null && "".equals(newValue)) {	
			return false;
		} else if (newValue==null) {
			return true;
		} else {
			return !newValue.equals(oldValue);
		}
	}
	
	public String getToken(TokenFieldType tokenFieldType, int id) {
		return RequestUtil.getToken(tokenFieldType.getKey(), id);
	}
	
	public void setMessageSource (MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public MessageSource getMessageSource() {
		return this.messageSource;
	}
}