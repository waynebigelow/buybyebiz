package ca.app.persistence.listing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.category.Category;
import ca.app.model.listing.Listing;
import ca.app.model.listing.ListingStatus;
import ca.app.util.ProjectUtil;
import ca.app.util.StringUtil;
import ca.app.web.dto.listing.ListingDTO;
import ca.app.web.paging.Page;

@Repository(value="listingDAO")
public class ListingDAOImpl implements ListingDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void insert(Listing listing) {
		sessionFactory.getCurrentSession().save(listing);
	}
	
	public void update(Listing listing) {
		sessionFactory.getCurrentSession().update(listing);
	}
	
	public void delete(Listing listing) {
		sessionFactory.getCurrentSession().delete(listing);
	}
	
	public Listing getByListingId(int listingId){
		Query query = sessionFactory.getCurrentSession().createQuery("from Listing where listingId = :listingId");
		query.setInteger("listingId", listingId);
		return (Listing)query.uniqueResult();
	}

	public Listing getByListingURI(String listingURI){
		Query query = sessionFactory.getCurrentSession().createQuery("from Listing where upper(listingURI) like upper(:listingURI)");
		query.setString("listingURI", "%"+listingURI+"%");
		return (Listing)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getAllActiveListings() {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select * from listing where status_id in ("+ListingStatus.ACTIVE.getId()+","+ListingStatus.SUSPENDED.getId()+")");
		query.addEntity(Listing.class);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getListedCategories(int applicationId) {
		int demoId = StringUtil.convertStringToInt(ProjectUtil.getProperty("system.demo.listingId"), 0);
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct(c.category_id), c.* from category c ");
		sb.append("join listing l on l.category_id = c.category_id ");
		if (demoId > 0) {
			sb.append("and l.listing_id <> :demoId ");
		}
		sb.append("where l.application_id = :applicationId order by name ASC");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		
		if (demoId > 0) {
			query.setInteger("demoId", demoId);
		}
		query.setInteger("applicationId", applicationId);
		query.addEntity(Category.class);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLocationsByCategoryId(int categoryId) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select distinct(a.country) as country from address a join listing l on l.address_id = a.address_id where l.category_id = :categoryId order by country ASC");
		query.setInteger("categoryId", categoryId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getAllListingsByUserId(int userId){
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select lst.*, app.* from listing lst inner join application app on " + 
			" lst.application_id = app.application_id where user_id = :userId");
		query.addEntity(Listing.class);
		query.setInteger("userId", userId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Listing> getActiveListingsByUserId(int userId){
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select * from listing where user_id = :userId and status_id != :statusId");
		query.addEntity(Listing.class);
		query.setInteger("userId", userId);
		query.setInteger("statusId", ListingStatus.DELETED.getId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Page<ListingDTO> searchListingByPage(Page<ListingDTO> page) {
		int demoId = StringUtil.convertStringToInt(ProjectUtil.getProperty("system.demo.listingId"), 0);
		int applicationId = page.getInt("applicationId", 0);
		boolean enabled = page.getBoolean("enabled", false);
		int categoryId = page.getInt("categoryId", 0);
		int subCategoryId = page.getInt("subCategoryId", 0);
		int minPrice = page.getInt("minPrice", 0);
		int maxPrice = page.getInt("maxPrice", 0);
		String province = page.getString("province", null);
		String country = page.getString("country", null);

		// Build the base query
		StringBuilder sql = new StringBuilder();
		sql.append(" from listing lst ");
		sql.append(" join address ad on ad.address_id = lst.address_id ");
		sql.append(" where (1=1) ");
		//sql.append(" and status = " + Status.APPROVED.getId() + " ");
		//sql.append(" and deleted = :del ");

		if (applicationId > 0) {
			sql.append(" and lst.application_id = :applicationId ");
		}
		if (demoId > 0) {
			sql.append(" and lst.listing_id <> :demoId ");
		}
		if (enabled == true) {
			sql.append(" and lst.enabled = :enabled ");
		}
		if (categoryId > 0) {
			sql.append(" and lst.category_id = :categoryId ");
		}
		if (subCategoryId > 0) {
			sql.append(" and lst.sub_category_id = :subCategoryId ");
		}
		if (minPrice > 0) {
			sql.append(" and lst.price >= :minPrice ");
		}
		if (maxPrice > 0) {
			sql.append(" and lst.price <= :maxPrice ");
		}
		if (province != null && !province.equals("")) {
			sql.append(" and ad.province = :province ");
		}
		if (country != null && !country.equals("")) {
			sql.append(" and ad.country = :country ");
		}
		
		// Build the paging query
		String pagedSql = "select lst.*, ad.* " + sql + buildOrderByClause(page.getSort(), page.getDir());
		SQLQuery pagedQuery = sessionFactory.getCurrentSession().createSQLQuery(pagedSql);

		// Build the counting query
		String countSql = " select count(*) " + sql;
		SQLQuery countQuery = sessionFactory.getCurrentSession().createSQLQuery(countSql);
		
		//pagedQuery.setBoolean("del", false);
		//countQuery.setBoolean("del", false);

		if (applicationId > 0) {
			pagedQuery.setInteger("applicationId", applicationId);
			countQuery.setInteger("applicationId", applicationId);
		}
		if (demoId > 0) {
			pagedQuery.setInteger("demoId", demoId);
			countQuery.setInteger("demoId", demoId);
		}
		if (enabled == true) {
			pagedQuery.setBoolean("enabled", enabled);
			countQuery.setBoolean("enabled", enabled);
		}
		if (categoryId > 0) {
			pagedQuery.setInteger("categoryId", categoryId);
			countQuery.setInteger("categoryId", categoryId);
		}
		if (subCategoryId > 0) {
			pagedQuery.setInteger("subCategoryId", subCategoryId);
			countQuery.setInteger("subCategoryId", subCategoryId);
		}
		if (minPrice > 0) {
			pagedQuery.setInteger("minPrice", minPrice);
			countQuery.setInteger("minPrice", minPrice);
		}
		if (maxPrice > 0) {
			pagedQuery.setInteger("maxPrice", maxPrice);
			countQuery.setInteger("maxPrice", maxPrice);
		}
		if (province != null && !province.equals("")) {
			pagedQuery.setString("province", province);
			countQuery.setString("province", province);
		}
		if (country != null && !country.equals("")) {
			pagedQuery.setString("country", country);
			countQuery.setString("country", country);
		}
		
		// Init paging parameters
		if (page.getLimit() > 0) {
			pagedQuery.setFirstResult(page.getStart());
			pagedQuery.setMaxResults(page.getLimit());
		}

		// Configure how the paged result will be handled
		pagedQuery.addEntity(Listing.class);

		// Execute the queries
		List<Listing> listings = pagedQuery.list();
		
		List<ListingDTO> dtos = new ArrayList<ListingDTO>();
		for (Listing listing : listings) {
			dtos.add(new ListingDTO(listing, false));
		}
		page.setItems(dtos);
		
		if (page.getLimit() > 0) {
			page.setTotal(((BigInteger) countQuery.uniqueResult()).intValue());
		} else {
			page.setTotal(page.getItems().size());
		}

		return page;
	}
	
	private String buildOrderByClause(String sort, String dir) {
		String orderBy = " order by ";
		
		if (!dir.equals("asc") && !dir.equals("desc")) {
			dir = "asc";
		}
 		
		if ("desc".equalsIgnoreCase(dir)) {
			dir = "desc nulls last";
		}

		if ("price".equals(sort)) {
			orderBy += "price " + dir;
		} else if ("title".equals(sort)) {
			orderBy += "title " + dir;
		}

		return orderBy;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}