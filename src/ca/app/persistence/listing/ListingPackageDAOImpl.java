package ca.app.persistence.listing;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.listing.ListingPackage;

@Repository(value="listingPackageDAO")
public class ListingPackageDAOImpl implements ListingPackageDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void insert(ListingPackage listingPackage){
		sessionFactory.getCurrentSession().saveOrUpdate(listingPackage);
	}
	
	public void delete(ListingPackage listingPackage){
		sessionFactory.getCurrentSession().delete(listingPackage);
	}
	
	public void deleteByPackageId(int packageId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ListingPackage where listingPackageId = :packageId");
		query.setInteger("packageId", packageId);
		query.executeUpdate();
	}
	
	public ListingPackage getByPackageId(int packageId){
		Query query = sessionFactory.getCurrentSession().createQuery("from ListingPackage where listingPackageId = :packageId");
		query.setInteger("packageId", packageId);
		return (ListingPackage)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ListingPackage> getAllPackages(){
		Query query = sessionFactory.getCurrentSession().createQuery("from ListingPackage order by listingId asc");
		return query.list();
	}
	
	
	public int getPromoCount(int applicationId, int typeId, int currencyTypeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as counted from listing_package lp ");
		sql.append("join application_package ap on ap.package_id = lp.package_id and ap.application_id = :applicationId and ap.type_id = :typeId ");
		sql.append("join package_price pp on pp.package_id = ap.package_id and pp.currency_type_id = :currencyTypeId ");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.setInteger("applicationId", applicationId);
		query.setInteger("typeId", typeId);
		query.setInteger("currencyTypeId", currencyTypeId);
		query.addScalar("counted", IntegerType.INSTANCE);
		
		return (int)query.uniqueResult();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}