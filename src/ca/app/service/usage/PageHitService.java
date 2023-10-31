package ca.app.service.usage;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ca.app.model.listing.Listing;
import ca.app.model.usage.PageHit;
import ca.app.model.usage.Usage;
import ca.app.web.paging.Page;

public interface PageHitService {
	public void logPageHit(Listing listing, HttpServletRequest request, Usage usage);
	public int getPageHitCount(int listingId);
	public Page<PageHit> getPageHitsPage(Page<PageHit> page);
	public List<BigDecimal> getAreaIdsByActionAndIP(int actionId, String ipAddress);
	public List<BigDecimal> getActionIdsByAreaAndIP(int areaId, String ipAddress);
	public List<String> getIPByAreaAndAction(int areaId, int actionId);
}