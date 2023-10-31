package ca.app.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter {
	private FilterConfig filterConfig = null;

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (req.getCharacterEncoding() == null) {
			req.setCharacterEncoding(filterConfig.getInitParameter("encoding"));
		}
		
		resp.setCharacterEncoding(filterConfig.getInitParameter("encoding"));
		
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	public void destroy() {}
}