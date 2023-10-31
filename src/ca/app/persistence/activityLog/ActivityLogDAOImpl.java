package ca.app.persistence.activityLog;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.User;
import ca.app.web.dto.user.ActivityLogDTO;
import ca.app.web.paging.Page;

@Repository(value="activityLogDAO")
public class ActivityLogDAOImpl implements ActivityLogDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void insert(ActivityLog entry) {
		sessionFactory.getCurrentSession().save(entry);
	}
	
	public void deleteByActivityLogId(int activityLogId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ActivityLog where activityLogId = :activityLogId");
		query.setInteger("activityLogId", activityLogId);
		query.executeUpdate();
	}
	
	public void deleteAllActivityByUserId(int userId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ActivityLog where userId = :userId");
		query.setInteger("userId", userId);
		query.executeUpdate();
	}
	
	public ActivityLog getByActivityLogId(int activityLogId)  {
		Query query = sessionFactory.getCurrentSession().createQuery("from ActivityLog where activityLogId = :activityLogId");
		query.setInteger("activityLogId", activityLogId);
		return (ActivityLog)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Page<ActivityLogDTO> getActivityLogPage(Page<ActivityLogDTO> page) {
		int userId = page.getInt("userId", 0);
		int areaId = page.getInt("areaId", 0);
		int typeId = page.getInt("typeId", 0);
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from activity_log al ");
		sql.append(" where (1=1) ");
		if (userId > 0) {
			sql.append(" and al.user_id = :userId ");
		}
		if (areaId > 0) {
			sql.append(" and al.area_id = :areaId ");
		}
		if (typeId > 0) {
			sql.append(" and al.type_id = :typeId ");
		}
		
		String pagedSql = "select al.* " + sql + " order by al.activity_date desc";
		SQLQuery pagedQuery = sessionFactory.getCurrentSession().createSQLQuery(pagedSql);

		String countSql = " select count(*) " + sql;
		SQLQuery countQuery = sessionFactory.getCurrentSession().createSQLQuery(countSql);
		
		if (userId > 0) {
			pagedQuery.setInteger("userId", userId);
			countQuery.setInteger("userId", userId);
		}
		if (areaId > 0) {
			pagedQuery.setInteger("areaId", areaId);
			countQuery.setInteger("areaId", areaId);
		}
		if (typeId > 0) {
			pagedQuery.setInteger("typeId", typeId);
			countQuery.setInteger("typeId", typeId);
		}
		if (page.getLimit() > 0) {
			pagedQuery.setFirstResult(page.getStart());
			pagedQuery.setMaxResults(page.getLimit());
		}
		pagedQuery.addEntity(ActivityLog.class);
		
		List<ActivityLog> activityLogs = pagedQuery.list();
		List<ActivityLogDTO> dtos = new ArrayList<ActivityLogDTO>();
		for (ActivityLog activityLog : activityLogs) {
			dtos.add(new ActivityLogDTO(activityLog));
		}
		
		page.setItems(dtos);
		if (page.getLimit() > 0) {
			page.setTotal(((BigInteger) countQuery.uniqueResult()).intValue());
		} else {
			page.setTotal(page.getItems().size());
		}

		return page;
	}
	
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getAreaIdsByUserId(int typeId, int userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(area_id) from activity_log ");
		sql.append("where (1=1) ");
		
		if (typeId > 0) {
			sql.append("and type_id = :typeId ");
		}
		if (userId > 0) {
			sql.append("and user_id = :userId ");
		}
		sql.append("order by area_id asc ");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		if (typeId > 0) {
			query.setInteger("typeId", typeId);
		}
		if (userId > 0) {
			query.setInteger("userId", userId);
		}

		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getTypeIdsByUserId(int areaId, int userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(type_id) from activity_log ");
		sql.append("where (1=1) ");
		
		if (areaId > 0) {
			sql.append("and area_id = :areaId ");
		}
		if (userId > 0) {
			sql.append("and user_id = :userId ");
		}
		sql.append("order by type_id asc ");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		if (areaId > 0) {
			query.setInteger("areaId", areaId);
		}
		if (userId > 0) {
			query.setInteger("userId", userId);
		}
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllActivityAuthors(int areaId, int typeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from user_account where ");
		sql.append("user_id in (select user_id from activity_log ");
		sql.append("where (1=1) ");
		
		if (areaId > 0) {
			sql.append("and area_id = :areaId ");
		}
		if (typeId > 0) {
			sql.append("and type_id = :typeId ");
		}
		sql.append(") order by lastname asc, firstname asc");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.addEntity(User.class);
		
		if (areaId > 0) {
			query.setInteger("areaId", areaId);
		}
		if (typeId > 0) {
			query.setInteger("typeId", typeId);
		}
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivityLog> getAllByListingId(int listingId) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(ActivityLog.class);
		c.add(Restrictions.eq("primaryId", listingId));
		
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivityLog> getAllByUser(User user) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(ActivityLog.class);
		c.add(Restrictions.eq("user", user));
		c.add(Restrictions.eq("areaId", Area.USER_ACCOUNT.getId()));
		
		return c.list();
	}
	
	private String buildOrderByClause(String sort, String dir) {
		String orderBy = " order by ";

		if ("desc".equalsIgnoreCase(dir)) {
			dir = "desc nulls last";
		}

		if ("price".equals(sort)) {
			orderBy += "price " + dir;
		}

		return orderBy;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}