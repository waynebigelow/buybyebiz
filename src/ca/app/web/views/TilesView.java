package ca.app.web.views;

import java.net.URL;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.Definition;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.servlet.context.ServletTilesApplicationContext;
import org.apache.tiles.servlet.context.ServletTilesRequestContext;
import org.apache.tiles.servlet.context.ServletUtil;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class TilesView extends AbstractUrlBasedView {

	private volatile boolean exposeForwardAttributes = false;
	
	@Override
	protected void initServletContext(ServletContext sc) {
		if (sc.getMajorVersion() == 2 && sc.getMinorVersion() < 5) {
			this.exposeForwardAttributes = true;
		}
	}

	@Override
	public boolean checkResource(final Locale locale) throws Exception {
		TilesContainer container = ServletUtil.getContainer(getServletContext());
		if (!(container instanceof BasicTilesContainer)) {
			return true;
		}
		BasicTilesContainer basicContainer = (BasicTilesContainer)container;
		
		TilesApplicationContext appContext = new ServletTilesApplicationContext(getServletContext());
		TilesRequestContext requestContext = new ServletTilesRequestContext(appContext, null, null) {
			
			@Override
			public Locale getRequestLocale() {
				return locale;
			}
		};

		Definition def = basicContainer.getDefinitionsFactory().getDefinition(getUrl(), requestContext);
		
		if (def != null) {
			String pathToRealJsp = def.getTemplateAttribute().getValue().toString();
			// TODO: look into the difference between how the 'template' and the 'value' attributes are handled from the xml.
			// Specifically, why defining a rule for "proto:memlist" doesn't return a pathToRealJsp of "/layouts/proto/memlist.jsp"
			
			if (logger.isDebugEnabled()) {
				logger.debug("Checking for resource [view=" + getUrl() + "][pathToRealJsp=" + pathToRealJsp + "]");
			}
			
			if (checkForRealJsp(pathToRealJsp)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkForRealJsp(String pathToRealJsp) throws Exception {
		boolean jspFound = false;
		URL jspUrl = getServletContext().getResource(pathToRealJsp);
		if (jspUrl != null) {
			jspFound = true;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Resource was " + ((jspFound)?"":"NOT ") + " found at [" + pathToRealJsp + "]");
		}
		
		return jspFound;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext servletContext = getServletContext();
		TilesContainer container = ServletUtil.getContainer(servletContext);
		if (container == null) {
			throw new ServletException("Tiles container is not initialized. Have you added a TilesConfigurer to your web application context?");
		}

		exposeModelAsRequestAttributes(model, request);
		JstlUtils.exposeLocalizationContext(new RequestContext(request, servletContext));

		if (!response.isCommitted()) {
			// Tiles is going to use a forward, but some web containers (e.g. OC4J 10.1.3)
			// do not properly expose the Servlet 2.4 forward request attributes... However,
			// must not do this on Servlet 2.5 or above, mainly for GlassFish compatibility.
			if (this.exposeForwardAttributes) {
				try {
//					WebUtils.exposeForwardRequestAttributes(request);
				}
				catch (Exception ex) {
					this.exposeForwardAttributes = false;
				}
			}
		}

		container.render(getUrl(), request, response);
	}
}