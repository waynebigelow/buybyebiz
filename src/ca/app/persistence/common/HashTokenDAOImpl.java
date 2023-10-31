package ca.app.persistence.common;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.common.HashToken;

@Repository("hashTokenDAO")
public class HashTokenDAOImpl implements HashTokenDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void insert(HashToken token) {
		sessionFactory.getCurrentSession().save(token);
	}
	
	public void update(HashToken token) {
		sessionFactory.getCurrentSession().saveOrUpdate(token);
	}
	
	public void delete(HashToken token) {
		sessionFactory.getCurrentSession().delete(token);
	}

	public void deleteByUserIdTypeId(int userId, int typeId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from HashToken where userId = :userId and typeId = :typeId");
		query.setInteger("userId", userId);
		query.setInteger("typeId", typeId);
		query.executeUpdate();
	}
	
	public HashToken getByHash(String hash) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HashToken.class);
		criteria.add(Restrictions.eq("hash", hash));
		return (HashToken) criteria.uniqueResult();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}