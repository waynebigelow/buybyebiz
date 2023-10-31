package ca.app.persistence.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.user.UserAgreement;
import ca.app.model.user.UserAgreementType;

@Repository(value="userAgreementDAO")
public class UserAgreementDAOImpl implements UserAgreementDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public UserAgreement get(int agreementId) {
		return sessionFactory.getCurrentSession().get(UserAgreement.class, agreementId);
	}

	public void save(UserAgreement agreement) {
		sessionFactory.getCurrentSession().save(agreement);
	}

	public void update(UserAgreement agreement) {
		sessionFactory.getCurrentSession().update(agreement);
	}
	
	public void delete(UserAgreement agreement) {
		sessionFactory.getCurrentSession().delete(agreement);
	}

	public List<UserAgreement> getByUserId(int userId) {
		return getByUserId(userId, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAgreement> getByUserId(int userId, UserAgreementType agreementType, Integer version) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserAgreement.class);
		criteria.add(Restrictions.eq("userId",userId));
		if (agreementType!=null) {
			criteria.add(Restrictions.eq("agreementTypeId", agreementType.getId()));
		}
		if (version!=null) {
			criteria.add(Restrictions.eq("version", version));
		}
		criteria.addOrder(Order.desc("agreementDate"));
		return criteria.list();
	}
	
	public void deleteAllAgreementsByUserId(int userId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from UserAgreement where userId = :userId");
		query.setInteger("userId", userId);
		query.executeUpdate();
	}
}