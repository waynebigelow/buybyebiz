package ca.app.persistence.listing;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.listing.Purchase;

@Repository(value="purchaseDAO")
public class PurchaseDAOImpl implements PurchaseDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void insert(Purchase purchase){
		sessionFactory.getCurrentSession().save(purchase);
	}
	
	public void saveOrUpdate(Purchase purchase){
		sessionFactory.getCurrentSession().saveOrUpdate(purchase);
	}
	
	public void delete(Purchase purchase){
		sessionFactory.getCurrentSession().delete(purchase);
	}
	
	public void deleteByPurchaseId(int purchaseId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Purchase where purchaseId = :purchaseId");
		query.setInteger("purchaseId", purchaseId);
		query.executeUpdate();
	}
	
	public Purchase getByPurchaseId(int purchaseId){
		Query query = sessionFactory.getCurrentSession().createQuery("from Purchase where purchaseId = :purchaseId");
		query.setInteger("purchaseId", purchaseId);
		return (Purchase)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Purchase> getAllPurchases(){
		Query query = sessionFactory.getCurrentSession().createQuery("from Purchase order by listingId asc");
		return query.list();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}