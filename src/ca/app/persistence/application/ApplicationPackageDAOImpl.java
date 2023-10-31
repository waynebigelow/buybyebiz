package ca.app.persistence.application;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.application.ApplicationPackage;
import ca.app.model.application.ApplicationPackageType;

@Repository(value="applicationPackageDAO")
public class ApplicationPackageDAOImpl implements ApplicationPackageDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ApplicationPackage getByPackageId(int packageId){
		Query query = sessionFactory.getCurrentSession().createQuery("from ApplicationPackage where packageId = :packageId");
		query.setInteger("packageId", packageId);
		return (ApplicationPackage)query.uniqueResult();
	}
	
	public ApplicationPackage getByAppAndTypeId(int applicationId, int typeId){
		Query query = sessionFactory.getCurrentSession().createQuery("from ApplicationPackage where applicationId = :applicationId and typeId = :typeId and enabled = :enabled");
		query.setInteger("applicationId", applicationId);
		query.setInteger("typeId", typeId);
		query.setBoolean("enabled", true);
		return (ApplicationPackage)query.uniqueResult();
	}
	
	public ApplicationPackage getByLinkValueProviderAppCurrencyAndTypeId(String linkValue, int providerId, int applicationId, int currencyTypeId, int typeId){
		StringBuilder sql = new StringBuilder();
		sql.append("select ap.package_id as packageId, ap.name, ap.description, ap.application_id as applicationId, ap.type_id as typeId, ");
		sql.append("ap.time_period_id as timePeriodId, ap.duration, ap.price, ap.currency_type_id as currencyTypeId, ap.enabled, ap.link_id as linkId ");
		sql.append("from application_package ap ");
		sql.append("join provider_package_link ppl on ap.link_id = ppl.link_id and ppl.link_value = :linkValue and ppl.provider_id = :providerId ");
		sql.append("where ap.application_id = :applicationId and ap.currency_type_id = :currencyTypeId and ap.type_id = :typeId and ap.enabled = :enabled");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.setString("linkValue", linkValue);
		query.setInteger("providerId", providerId);
		query.setInteger("applicationId", applicationId);
		query.setInteger("currencyTypeId", currencyTypeId);
		query.setInteger("typeId", typeId);
		query.setBoolean("enabled", true);
		
		query.setResultTransformer(Transformers.aliasToBean(ApplicationPackage.class));
		query.addScalar("packageId", IntegerType.INSTANCE);
		query.addScalar("name");
		query.addScalar("description");
		query.addScalar("applicationId", IntegerType.INSTANCE);
		query.addScalar("typeId", IntegerType.INSTANCE);
		query.addScalar("timePeriodId", IntegerType.INSTANCE);
		query.addScalar("duration", IntegerType.INSTANCE);
		query.addScalar("price", BigDecimalType.INSTANCE);
		query.addScalar("currencyTypeId", IntegerType.INSTANCE);
		query.addScalar("enabled", BooleanType.INSTANCE);
		query.addScalar("linkId", IntegerType.INSTANCE);
		
		return (ApplicationPackage)query.uniqueResult();
	}
	
	public void saveOrUpdate(ApplicationPackage applicationPackage){
		sessionFactory.getCurrentSession().saveOrUpdate(applicationPackage);
	}
	
	public void delete(ApplicationPackage applicationPackage){
		sessionFactory.getCurrentSession().delete(applicationPackage);
	}
	
	public void deleteByPackageId(int packageId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ApplicationPackage where packageId = :packageId");
		query.setInteger("packageId", packageId);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<ApplicationPackage> getAllPackages(){
		Query query = sessionFactory.getCurrentSession().createQuery("from ApplicationPackage");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ApplicationPackage> getByAppIdAndCurrencyId(int applicationId, int currencyTypeId){
		Query query = sessionFactory.getCurrentSession().createQuery("from ApplicationPackage where applicationId = :applicationId and typeId = :typeId and enabled = :enabled");
		query.setInteger("applicationId", applicationId);
		query.setInteger("typeId", ApplicationPackageType.EXTENSION.getId());
		query.setBoolean("enabled", true);
		return query.list();
	}
}