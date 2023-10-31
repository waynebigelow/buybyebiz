package ca.app.persistence.listing;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.listing.EnquiryMap;
import ca.app.model.user.User;

@Repository(value="enquiryMapDAO")
public class EnquiryMapDAOImpl implements EnquiryMapDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void create(EnquiryMap enquiryMap) {
		sessionFactory.getCurrentSession().save(enquiryMap);
	}
	
	public EnquiryMap getByEnquiryMapId(int enquiryMapId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from EnquiryMap where enquiryMapId = :enquiryMapId");
		query.setInteger("enquiryMapId", enquiryMapId);
		return (EnquiryMap)query.uniqueResult();
	}
	
	public EnquiryMap getByListingIdPosterId(int listingId, int posterId) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select * from enquiry_map where listing_id = :listingId and poster_id = :posterId");
		query.setResultTransformer(Transformers.aliasToBean(EnquiryMap.class));
		query.setInteger("listingId", listingId);
		query.setInteger("posterId", posterId);
		return (EnquiryMap)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<EnquiryMap> getByPosterId(User poster) {
		Query query = sessionFactory.getCurrentSession().createQuery("from EnquiryMap where poster = :poster");
		query.setEntity("poster", poster);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<EnquiryMap> getAllByListingId(int listingId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EnquiryMap.class);
		criteria.add(Restrictions.eq("listingId", listingId));
		return criteria.list();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}