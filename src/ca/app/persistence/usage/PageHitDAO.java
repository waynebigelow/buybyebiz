package ca.app.persistence.usage;

import java.math.BigDecimal;
import java.util.List;

import ca.app.model.usage.PageHit;
import ca.app.web.paging.Page;

public interface PageHitDAO {
	public void save(PageHit pageHit);
	public int getPageHitCount(int listingId);
	public Page<PageHit> getPageHitsPage(Page<PageHit> page);
	public List<BigDecimal> getAreaIdsByActionAndIP(int actionId, String ipAddress);
	public List<BigDecimal> getActionIdsByAreaAndIP(int areaId, String ipAddress);
	public List<String> getIPByAreaAndAction(int areaId, int actionId);
}