package ca.app.persistence.common;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.common.ListingLead;
import ca.app.web.paging.Page;

@Repository("listingLeadDAO")
public class ListingLeadDAOImpl implements ListingLeadDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void insert(ListingLead listingLead) {
		sessionFactory.getCurrentSession().saveOrUpdate(listingLead);
	}
	
	public void update(ListingLead listingLead) {
		sessionFactory.getCurrentSession().saveOrUpdate(listingLead);
	}
	
	public void delete(ListingLead listingLead) {
		sessionFactory.getCurrentSession().delete(listingLead);
	}
	
	public ListingLead getByListingLeadId(int listingLeadId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ListingLead where listingLeadId = :listingLeadId");
		query.setInteger("listingLeadId", listingLeadId);
		return (ListingLead)query.uniqueResult();
	}
	
	public ListingLead getByEmail(String emailAddress){
		Query query = sessionFactory.getCurrentSession().createQuery("from ListingLead where email = :emailAddress");
		query.setString("email", emailAddress);
		return (ListingLead)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ListingLead> getAvailableLeads() {
		Query query = sessionFactory.getCurrentSession().createQuery("from ListingLead where enablePromotionalEmail = :enablePromotionalEmail");
		query.setBoolean("enablePromotionalEmail", true);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Page<ListingLead> searchLeadsByPage(Page<ListingLead> page) {
		int applicationId = page.getInt("applicationId", 0);
		boolean optOut = page.getBoolean("optOut", false);

		StringBuilder sql = new StringBuilder();
		sql.append(" from listing_lead ll ");
		sql.append(" where (1=1) ");

		if (applicationId > 0) {
			sql.append(" and ll.application_id = :applicationId ");
		}
		if (optOut == true) {
			sql.append(" and ll.opt_out = :optOut ");
		}
		
		String pagedSql = "select ll.* " + sql + buildOrderByClause(page.getSort(), page.getDir());
		SQLQuery pagedQuery = sessionFactory.getCurrentSession().createSQLQuery(pagedSql);

		String countSql = " select count(*) " + sql;
		SQLQuery countQuery = sessionFactory.getCurrentSession().createSQLQuery(countSql);

		if (applicationId > 0) {
			pagedQuery.setInteger("applicationId", applicationId);
			countQuery.setInteger("applicationId", applicationId);
		}
		if (optOut == true) {
			pagedQuery.setBoolean("optOut", optOut);
			countQuery.setBoolean("optOut", optOut);
		}
		
		if (page.getLimit() > 0) {
			pagedQuery.setFirstResult(page.getStart());
			pagedQuery.setMaxResults(page.getLimit());
		}
		
		pagedQuery.addEntity(ListingLead.class);

		page.setItems(pagedQuery.list());
		
		if (page.getLimit() > 0) {
			page.setTotal(((BigInteger) countQuery.uniqueResult()).intValue());
		} else {
			page.setTotal(page.getItems().size());
		}

		return page;
	}
	
	private String buildOrderByClause(String sort, String dir) {
		String orderBy = " order by ";

		if ("desc".equalsIgnoreCase(dir)) {
			dir = "desc nulls last";
		}

		if ("business_name".equals(sort)) {
			orderBy += "business_name " + dir;
		} else {
			orderBy += "business_name " + dir;
		}

		return orderBy;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}