package ca.app.persistence.listing;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.listing.BusinessDetails;

@Repository(value="businessDetailsDAO")
public class BusinessDetailsDAOImpl implements BusinessDetailsDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public BusinessDetails getByDetailsId(int detailsId){
		Query query = sessionFactory.getCurrentSession().createQuery("from BusinessDetails where detailsId = :detailsId");
		query.setInteger("detailsId", detailsId);
		return (BusinessDetails)query.uniqueResult();
	}
	
	public void saveOrUpdate(BusinessDetails details){
		sessionFactory.getCurrentSession().saveOrUpdate(details);
	}
}