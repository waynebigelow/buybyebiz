package ca.app.service.usage; 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import ca.app.model.application.Application;
import ca.app.model.listing.Listing;
import ca.app.model.usage.PageHit;
import ca.app.model.usage.PageHitType;
import ca.app.model.usage.RefererType;
import ca.app.model.usage.Usage;
import ca.app.model.usage.UserAgent;
import ca.app.model.user.AppUser;
import ca.app.persistence.usage.PageHitDAO;
import ca.app.util.LogUtil;
import ca.app.web.paging.Page;

public class PageHitServiceImpl implements PageHitService {
	@Autowired
	private RefererParsingService refererParsingService;
	@Autowired
	private UserAgentParsingService userAgentParsingService;
	@Autowired
	private PageHitDAO pageHitDAO;
	
	public void logPageHit(Listing listing, HttpServletRequest request, Usage usage) {
		PageHit pageHit = createBasePageHit(listing, request, usage);
		
		String subArea = request.getParameter("subArea");
		if (subArea != null && subArea.length() > 0) {
			pageHit.setAreaInfo(subArea);
		}
		
		if (pageHit.getRefererType() != RefererType.REDIRECT) {
			save(pageHit, request.getSession());
		}
	}
	
	private PageHit createBasePageHit(Listing listing, HttpServletRequest request, Usage usage) {
		Application application = (Application)request.getAttribute("application");
		
		PageHit pageHit = new PageHit();
		pageHit.setArea(usage.getArea());
		pageHit.setAction(usage.getAction());
		pageHit.setListingId((listing == null?0:listing.getListingId()));
		pageHit.setApplicationId(application.getApplicationId());
		pageHit.setUserId(getAuthenticatedUserId());
		pageHit.setSessionId(request.getSession().getId());
		pageHit.setPageHitType(PageHitType.EXIT);
		pageHit.setDateTime(new Timestamp(System.currentTimeMillis()));
		pageHit.setIpAddress(request.getRemoteAddr());
		
		String rawReferer = request.getHeader("referer");
		if(rawReferer != null && rawReferer.length() > 600) {
			rawReferer = rawReferer.substring(0, 599);
		}
		pageHit.setReferer(rawReferer);
		
		String source = request.getParameter("source");
		String s = request.getParameter("s");
		RefererType refererType = refererParsingService.parseReferer(listing, rawReferer, source, s);
		pageHit.setRefererTypeId((refererType!=null)?refererType.getId():0);
		
		String rawUserAgent = request.getHeader("user-agent");
		pageHit.setRawUserAgent(rawUserAgent);
		
		UserAgent userAgent = parseUserAgent(request, rawUserAgent);
		if (userAgent != null) {
			pageHit.setUserAgent(buildUserAgentName(userAgent));
			pageHit.setUserAgentOS(userAgent.getOsName());
		}
		
		return pageHit;
	}
	
	private void save(PageHit pageHit, HttpSession session) {
		PageHit prevPageHit = (PageHit)session.getAttribute("pageHit");
		if (prevPageHit != null) {
			long timeSpent = pageHit.getDateTime().getTime() - prevPageHit.getDateTime().getTime();
			prevPageHit.setTimeSpent(timeSpent);
			if (prevPageHit.getPageHitType()==PageHitType.EXIT) {
				prevPageHit.setPageHitType(PageHitType.REGULAR);
			}
			
			pageHitDAO.save(prevPageHit);
		} else {
			pageHit.setPageHitType(PageHitType.ENTRY);
		}
		
		pageHitDAO.save(pageHit);
		session.setAttribute("pageHit", pageHit);
		LogUtil.logDebug(this.getClass(), pageHit.toString());
	}
	
	public Page<PageHit> getPageHitsPage(Page<PageHit> page) {
		return pageHitDAO.getPageHitsPage(page);
	}
	
	public List<BigDecimal> getAreaIdsByActionAndIP(int actionId, String ipAddress) {
		return pageHitDAO.getAreaIdsByActionAndIP(actionId, ipAddress);
	}
	
	public List<BigDecimal> getActionIdsByAreaAndIP(int areaId, String ipAddress) {
		return pageHitDAO.getActionIdsByAreaAndIP(areaId, ipAddress);
	}
	
	public List<String> getIPByAreaAndAction(int areaId, int actionId) {
		return pageHitDAO.getIPByAreaAndAction(areaId, actionId);
	}
	
	public int getPageHitCount(int listingId) {
		return pageHitDAO.getPageHitCount(listingId);
	}
	
	private UserAgent parseUserAgent(HttpServletRequest request, String rawUserAgent) {
		UserAgent userAgent = null;
		
		if (request.getSession().getAttribute("UserAgent") != null) {
			userAgent = (UserAgent)request.getSession().getAttribute("UserAgent");
		} else {
			userAgent = userAgentParsingService.parseUserAgent(rawUserAgent);
			
			request.getSession().setAttribute("UserAgent", userAgent);
		}
		
		return userAgent;
	}
	
	private String buildUserAgentName(UserAgent userAgent) {
		String result = userAgent.getName();
		if (userAgent.getVersion() != null && userAgent.getVersion().length() > 0) {
			result += " " + userAgent.getVersion();
		}
		
		if (result != null) {
			int majorIdx = result.indexOf(".");
			if (majorIdx > 0) {
				result = result.substring(0, majorIdx);
			}
		}
		
		return result;
	}
	
	private static int getAuthenticatedUserId() {
		AppUser user = null;
		
		Object obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof AnonymousAuthenticationToken) {
			user = AppUser.getAnonymousUser();
		} else {
			if (SecurityContextHolder.getContext() != null) {
				if (SecurityContextHolder.getContext().getAuthentication() != null) {
					if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
						user = (AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					}
				}
			}
			
			if (user == null) {
				user = AppUser.getAnonymousUser();
			}
		}
		
		return (user != null) ? user.getUserId() : 0;
	}
	
	public void setRefererParsingService(RefererParsingService refererParsingService) {
		this.refererParsingService = refererParsingService;
	}
	public void setUserAgentParsingService(UserAgentParsingService userAgentParsingService) {
		this.userAgentParsingService = userAgentParsingService;
	}
	public void setPageHitDAO(PageHitDAO pageHitDAO) {
		this.pageHitDAO = pageHitDAO;
	}
}