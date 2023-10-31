package ca.app.persistence.application;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.application.ProviderPackageLink;

@Repository(value="providerPackageLinkDAO")
public class ProviderPackageLinkDAOImpl implements ProviderPackageLinkDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ProviderPackageLink getByLinkId(int linkId){
		Query query = sessionFactory.getCurrentSession().createQuery("from ProviderPackageLink where linkId = :linkId");
		query.setInteger("linkId", linkId);
		return (ProviderPackageLink)query.uniqueResult();
	}
	
	public void saveOrUpdate(ProviderPackageLink packageProviderLink){
		sessionFactory.getCurrentSession().saveOrUpdate(packageProviderLink);
	}
	
	public void delete(ProviderPackageLink packageProviderLink){
		sessionFactory.getCurrentSession().delete(packageProviderLink);
	}
	
	public void deleteByLinkId(int linkId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ProviderPackageLink where linkId = :linkId");
		query.setInteger("linkId", linkId);
		query.executeUpdate();
	}
}