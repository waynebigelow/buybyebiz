package ca.app.persistence.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.user.Password;

@Repository(value="passwordDAO")
public class PasswordDAOImpl implements PasswordDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void updatePasswordHistory(Password password) {
		sessionFactory.getCurrentSession().save(password);
	}

	@SuppressWarnings("unchecked")
	public List<Password> getPasswordHistory(int userId, int size) {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Password.class);
		cr.add(Restrictions.eq("userId", userId));
		cr.addOrder(Order.desc("changedDate"));
		cr.setMaxResults(size);
		return cr.list();
	}
	
	public void deleteAllPasswordsByUserId(int userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Password where userId = :userId");
		query.setInteger("userId", userId);
		query.executeUpdate();
	}
}