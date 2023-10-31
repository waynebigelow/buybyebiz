package ca.app.security.channel;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.ChannelDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class AppChannelProcessingFilter extends GenericFilterBean {

	private ChannelDecisionManager channelDecisionManager;
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(securityMetadataSource, "securityMetadataSource must be specified");
		Assert.notNull(channelDecisionManager, "channelDecisionManager must be specified");

		Collection<ConfigAttribute> attrDefs = this.securityMetadataSource.getAllConfigAttributes();

		if (attrDefs == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not validate configuration attributes as the FilterInvocationSecurityMetadataSource did not return any attributes");
			}
			return;
		}

		Set<ConfigAttribute> unsupportedAttributes = new HashSet<ConfigAttribute>();

		for (ConfigAttribute attr : attrDefs) {
			if (!this.channelDecisionManager.supports(attr)) {
				unsupportedAttributes.add(attr);
			}
		}

		if (unsupportedAttributes.size() == 0) {
			if (logger.isInfoEnabled()) {
				logger.info("Validated configuration attributes");
			}
		} else {
			throw new IllegalArgumentException("Unsupported configuration attributes: " + unsupportedAttributes);
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		FilterInvocation fi = new FilterInvocation(request, response, chain);
		Collection<ConfigAttribute> attr = this.securityMetadataSource.getAttributes(fi);

		if (attr != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Request: " + fi.toString() + "; ConfigAttributes: " + attr);
			}
			
			System.out.println("Request: " + fi.toString() + "; ConfigAttributes: " + attr);

			channelDecisionManager.decide(fi, attr);

			if (fi.getResponse().isCommitted()) {
				return;
			}
		}

		chain.doFilter(request, response);
	}

	
	protected ChannelDecisionManager getChannelDecisionManager() {
		return channelDecisionManager;
	}
	
	public void setChannelDecisionManager(ChannelDecisionManager channelDecisionManager) {
		this.channelDecisionManager = channelDecisionManager;
	}

	protected FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}
	
	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
		this.securityMetadataSource = filterInvocationSecurityMetadataSource;
	}
}