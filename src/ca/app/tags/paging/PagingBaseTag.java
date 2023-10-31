package ca.app.tags.paging;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import ca.app.web.paging.Page;

public class PagingBaseTag extends TagSupport {
	private static final long serialVersionUID = -6467680521605739306L;

	private Page<Object> page;
	private String url;
	private String hash = null;

	public PagingBaseTag() {
		super();
	}

	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}
	
	protected String buildUrl(int start) {
		String searchUrl = this.url
			+ "&start=" + start
			+ "&limit=" + page.getLimit()
			+ "&sort=" + page.getSort()
			+ "&dir=" + page.getDir()
			+ "&searchText=" + ((page.getSearchText()!=null)?page.getSearchText():"");
		
		if (page.getParams() != null) {
			for (String key : page.getParams().keySet()) {
				searchUrl += "&" + key + "=" + page.getParams().get(key);
			}
		}
		
		if (hash != null && hash.length() > 0) {
			searchUrl += "#" + hash;
		}
		
		return searchUrl;
	}
	
	public int doEndTag() {
		return TagSupport.EVAL_PAGE;
	}
	
	protected Locale getLocale() {
		final HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		LocaleResolver lc = RequestContextUtils.getLocaleResolver(request);
		return lc.resolveLocale(request);
	}
	
	protected MessageSource getMessageSource() {
		final ServletContext servletContext = pageContext.getServletContext();
		final ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return (MessageSource)context.getBean("messageSource");
	}
	
	public void setPage(Page<Object> page) {
		this.page = page;
	}
	public Page<Object> getPage() {
		return this.page;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return this.url;
	}

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
}