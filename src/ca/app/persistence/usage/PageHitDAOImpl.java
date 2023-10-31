package ca.app.persistence.usage; 

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.usage.PageHit;
import ca.app.model.usage.Usage;
import ca.app.web.paging.Page;

@Repository(value="pageHitDAO")
public class PageHitDAOImpl implements PageHitDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(PageHit pageHit) {
		sessionFactory.getCurrentSession().saveOrUpdate(pageHit);
	}
	
	@SuppressWarnings("unchecked")
	public int getPageHitCount(int listingId) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select distinct(ip_address) from page_hit where listing_id = :listingId and area_id = :areaId");
		query.setInteger("listingId", listingId);
		query.setInteger("areaId", Usage.SITE.getId());
		List<Integer> pageHits = query.list();
		
		return pageHits.size();
	}
	
	@SuppressWarnings("unchecked")
	public Page<PageHit> getPageHitsPage(Page<PageHit> page) {
		String ipAddress = page.getString("ipAddress", null);
		int areaId = page.getInt("area", 0);
		int actionId = page.getInt("action", 0);
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from page_hit ph ");
		sql.append(" where (1=1) ");
		if (ipAddress != null && !ipAddress.equals("")) {
			sql.append(" and ph.ip_address = :ipAddress ");
		}
		if (areaId > 0) {
			sql.append(" and ph.area_id = :areaId ");
		}
		if (actionId > 0) {
			sql.append(" and ph.action_id = :actionId ");
		}
		
		String pagedSql = "select ph.* " + sql + " order by ph.date_time desc";
		SQLQuery pagedQuery = sessionFactory.getCurrentSession().createSQLQuery(pagedSql);

		String countSql = " select count(*) " + sql;
		SQLQuery countQuery = sessionFactory.getCurrentSession().createSQLQuery(countSql);
		
		if (ipAddress != null && !ipAddress.equals("")) {
			pagedQuery.setString("ipAddress", ipAddress);
			countQuery.setString("ipAddress", ipAddress);
		}
		if (areaId > 0) {
			pagedQuery.setInteger("areaId", areaId);
			countQuery.setInteger("areaId", areaId);
		}
		if (actionId > 0) {
			pagedQuery.setInteger("actionId", actionId);
			countQuery.setInteger("actionId", actionId);
		}
		if (page.getLimit() > 0) {
			pagedQuery.setFirstResult(page.getStart());
			pagedQuery.setMaxResults(page.getLimit());
		}
		pagedQuery.addEntity(PageHit.class);
		
		List<PageHit> pageHits = pagedQuery.list();

		page.setItems(pageHits);
		if (page.getLimit() > 0) {
			page.setTotal(((BigInteger) countQuery.uniqueResult()).intValue());
		} else {
			page.setTotal(page.getItems().size());
		}

		return page;
	}
	
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getAreaIdsByActionAndIP(int actionId, String ipAddress) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(area_id) from page_hit ");
		sql.append("where (1=1) ");
		
		if (actionId > 0) {
			sql.append("and action_id = :actionId ");
		}
		if (ipAddress != null && !ipAddress.equals("")) {
			sql.append("and ip_address = :ipAddress ");
		}
		sql.append("order by area_id asc ");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		if (actionId > 0) {
			query.setInteger("actionId", actionId);
		}
		if (ipAddress != null && !ipAddress.equals("")) {
			query.setString("ipAddress", ipAddress);
		}

		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getActionIdsByAreaAndIP(int areaId, String ipAddress) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(action_id) from page_hit ");
		sql.append("where (1=1) ");
		
		if (areaId > 0) {
			sql.append("and area_id = :areaId ");
		}
		if (ipAddress != null && !ipAddress.equals("")) {
			sql.append("and ip_address = :ipAddress ");
		}
		sql.append("order by action_id asc ");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		if (areaId > 0) {
			query.setInteger("areaId", areaId);
		}
		if (ipAddress != null && !ipAddress.equals("")) {
			query.setString("ipAddress", ipAddress);
		}
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getIPByAreaAndAction(int areaId, int actionId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(ip_address) from page_hit ");
		sql.append("where (1=1) ");
		
		if (areaId > 0) {
			sql.append("and area_id = :areaId ");
		}
		if (actionId > 0) {
			sql.append("and action_id = :actionId ");
		}
		sql.append("order by ip_address asc");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		if (areaId > 0) {
			query.setInteger("areaId", areaId);
		}
		if (actionId > 0) {
			query.setInteger("actionId", actionId);
		}
		
		return query.list();
	}
}