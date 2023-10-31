package ca.app.persistence.notificationLog;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.common.Option;
import ca.app.model.listing.Listing;
import ca.app.model.notification.NotificationLog;
import ca.app.model.user.User;
import ca.app.web.dto.notification.NotificationLogDTO;
import ca.app.web.paging.Page;

@Repository(value="notificationLogDAO")
public class NotificationLogDAOImpl implements NotificationLogDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void log(NotificationLog notificationLog) {
		sessionFactory.getCurrentSession().save(notificationLog);
	}

	@SuppressWarnings("unchecked")
	public Page<NotificationLogDTO> getNotificationLogPage(Page<NotificationLogDTO> page) {
		int userId = page.getInt("userId", 0);
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from notification_log nl ");
		sql.append(" where (1=1) ");
		if (userId > 0) {
			sql.append(" and nl.user_id = :userId ");
		}
		
		String pagedSql = "select nl.* " + sql + " order by nl.sent_time desc";
		SQLQuery pagedQuery = sessionFactory.getCurrentSession().createSQLQuery(pagedSql);

		String countSql = " select count(*) " + sql;
		SQLQuery countQuery = sessionFactory.getCurrentSession().createSQLQuery(countSql);
		
		if (userId > 0) {
			pagedQuery.setInteger("userId", userId);
			countQuery.setInteger("userId", userId);
		}
		
		if (page.getLimit() > 0) {
			pagedQuery.setFirstResult(page.getStart());
			pagedQuery.setMaxResults(page.getLimit());
		}

		pagedQuery.addEntity(NotificationLog.class);
		
		List<NotificationLog> notificationLogs = pagedQuery.list();
		List<NotificationLogDTO> dtos = new ArrayList<NotificationLogDTO>();
		for (NotificationLog notificationLog : notificationLogs) {
			dtos.add(new NotificationLogDTO(notificationLog));
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

		if ("desc".equalsIgnoreCase(dir)) {
			dir = "desc nulls last";
		}

		if ("price".equals(sort)) {
			orderBy += "price " + dir;
		}

		return orderBy;
	}
	
	public NotificationLog getByNotificationLogId(int notificationLogId){
		Query query = sessionFactory.getCurrentSession().createQuery("from NotificationLog where notificationLogId = :notificationLogId");
		query.setInteger("notificationLogId", notificationLogId);
		return (NotificationLog)query.uniqueResult();
	}
	
	public void deleteByNotificationLogId(int notificationLogId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from NotificationLog where notificationLogId = :notificationLogId");
		query.setInteger("notificationLogId", notificationLogId);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Option> getAllNotifications() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(addressee) as label, user_id as id from notification_log order by label asc");

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(Option.class));
		query.addScalar("label");
		query.addScalar("id", IntegerType.INSTANCE);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NotificationLog> getAllByListing(Listing listing) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(NotificationLog.class);
		criteria.add(Restrictions.eq("listing", listing));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NotificationLog> getAllByUser(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(NotificationLog.class);
		criteria.add(Restrictions.eq("user", user));
		
		return criteria.list();
	}
	
	public boolean isNotificationSentToday(String addressee, int notificationTypeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as counted from notification_log ");
		sql.append("where addressee = :addressee ");
		sql.append("and notification_type_id = :notificationTypeId ");
		sql.append("and sent_time between :startDate and :endDate");
		
		Date startDate = new Date(System.currentTimeMillis() - 86400000);
		Date endDate = new Date(System.currentTimeMillis());
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.setString("addressee", addressee);
		query.setInteger("notificationTypeId", notificationTypeId);
		query.setTimestamp("startDate", startDate);
		query.setTimestamp("endDate", endDate);
		query.addScalar("counted", IntegerType.INSTANCE);
		
		int counted = (int)query.uniqueResult();
		
		return counted > 0;
	}
}