package ca.app.persistence.i18n;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.i18n.ListingLocale;

@Repository(value="listingLocaleDAO")
public class ListingLocaleDAOImpl implements ListingLocaleDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<ListingLocale> getListingLocales(int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ListingLocale where listingId = :pid");
		query.setInteger("pid", listingId);
		return query.list();
	}
	
	public void create(ListingLocale listingLocale) {
		sessionFactory.getCurrentSession().delete(listingLocale);
	}
	
	public void update(ListingLocale listingLocale) {
		sessionFactory.getCurrentSession().delete(listingLocale);
	}
	
	public void delete(ListingLocale listingLocale) {
		sessionFactory.getCurrentSession().delete(listingLocale);
	}
	
	public void deleteAll(int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ListingLocale where listing_id = :pid");
		query.setInteger("pid", listingId);
		query.executeUpdate();
	}
}