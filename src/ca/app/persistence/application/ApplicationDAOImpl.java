package ca.app.persistence.application;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.application.Application;

@Repository(value="applicationDAO")
public class ApplicationDAOImpl implements ApplicationDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Application getByApplicationId(int applicationId){
		Query query = sessionFactory.getCurrentSession().createQuery("from Application where applicationId = :applicationId");
		query.setInteger("applicationId", applicationId);
		return (Application)query.uniqueResult();
	}
	
	public Application getApplicationByListingId(int listingId){
		Query query = sessionFactory.getCurrentSession().createQuery("from Application where listingId = :listingId");
		query.setInteger("listingId", listingId);
		return (Application)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Application> getAllApplications(){
		Query query = sessionFactory.getCurrentSession().createQuery("from Application order by name asc");
		return query.list();
	}
	
	public void saveOrUpdate(Application application){
		sessionFactory.getCurrentSession().saveOrUpdate(application);
	}
	
	public void deleteByApplicationId(int applicationId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Application where applicationId = :applicationId");
		query.setInteger("applicationId", applicationId);
		query.executeUpdate();
	}
	
	public Application getApplicationForHostName(String hostName) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Application where upper(domain) = upper(:host)");
		query.setString("host", hostName);
		return (Application)query.uniqueResult();
	}
}