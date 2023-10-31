package ca.app.persistence.listing;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.listing.MetaDataApproval;
import ca.app.model.listing.MetaDataStatus;

@Repository(value="metaDataApprovalDAO")
public class MetaDataApprovalDAOImpl implements MetaDataApprovalDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void saveOrUpdate(MetaDataApproval metaData){
		sessionFactory.getCurrentSession().saveOrUpdate(metaData);
	}
	
	public void deleteMetaDataApprovalsByIds(String approvedIds) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("delete from meta_data_approval where meta_data_id in ("+approvedIds+")");
		query.executeUpdate();
	}
	
	public MetaDataApproval getByListingIdTypeId(int listingId, int typeId){
		Query query = sessionFactory.getCurrentSession().createQuery("from MetaDataApproval where listingId = :listingId and typeId = :typeId");
		query.setInteger("listingId", listingId);
		query.setInteger("typeId", typeId);
		return (MetaDataApproval)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<MetaDataApproval> loadMetaDataApprovalByListingId(int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from MetaDataApproval where listingId = :listingId");
		query.setInteger("listingId", listingId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<MetaDataApproval> loadMetaDataRejectionsByListingId(int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from MetaDataApproval where listingId = :listingId and statusId = :statusId");
		query.setInteger("listingId", listingId);
		query.setInteger("statusId", MetaDataStatus.REJECTED.getId());
		return query.list();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}