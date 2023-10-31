package ca.app.web.views;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class ViewResolver extends UrlBasedViewResolver {

	protected String getLayoutName() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String layoutName = (String)request.getAttribute("template");
		return (layoutName != null) ? layoutName : "version1";
	}

	@Override
	protected Object getCacheKey(String viewName, Locale locale) {
		String cacheKey = getLayoutName() + ":" + viewName + "_" + locale;
		return cacheKey;
	}
	
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Attempting to resolve view for: [" + viewName + "]");
		}
		
		String layoutName = getLayoutName();
		String layoutViewName = layoutName + ((layoutName == null || layoutName.length() == 0) ? "" : ":") + viewName;
		AbstractUrlBasedView view = buildView(layoutViewName);
		View result = (View)getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, layoutViewName);
		
		return result;
	}
}