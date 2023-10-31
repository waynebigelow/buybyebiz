package ca.app.persistence.listing;

import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.listing.EnquiryPost;

@Repository(value="enquiryPostDAO")
public class EnquiryPostDAOImpl implements EnquiryPostDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void create(EnquiryPost enquiry) {
		sessionFactory.getCurrentSession().save(enquiry);
	}
	
	public void markReadByEnquiryMapId(int enquiryMapId, int userId) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("update enquiry_post set is_read = true, read_date = :readDate where enquiry_map_id = :enquiryMapId and author_id != :userId and is_read = :isRead");
		query.setTimestamp("readDate", new Date(System.currentTimeMillis()));
		query.setInteger("enquiryMapId", enquiryMapId);
		query.setInteger("userId", userId);
		query.setBoolean("isRead", false);
		
		query.executeUpdate();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}