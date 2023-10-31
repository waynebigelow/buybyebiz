package ca.app.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import ca.app.util.LogUtil;
import ca.app.util.ProjectUtil;
/**
 * very, very, very basic security. Because spring security only supports a single authentication entry point,
 * we can re-implement the security framework to simply return http 200s or 401s.  Until we upgrade, this will suffice.
 * 
 * big caveat: only authenticates a single user with a known token from the property file, and all it will do is drop
 * the response.  this is not a good long-term solution, but it will be ok for now.
 **/

public class RestTokenBasedSecurityFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		String workerAuth = ProjectUtil.getProperty("aws.worker.auth.key");
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		LogUtil.logDebug(getClass(), "Received Auth Header : " + request.getHeader("X-Worker-Auth"));
		
		if (request.getHeader("X-Worker-Auth") != null && request.getHeader("X-Worker-Auth").equals(workerAuth)) {
			chain.doFilter(req, res);
		}else{
			response.sendError(HttpStatus.SC_FORBIDDEN);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}