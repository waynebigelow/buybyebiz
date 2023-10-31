package ca.app.persistence.photo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.photo.Photo;
import ca.app.model.photo.PhotoStatus;
import ca.app.model.photo.PhotoType;

@Repository(value="photoDAO")
public class PhotoDAOImpl implements PhotoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Photo get(int photoId) {
		return (Photo) sessionFactory.getCurrentSession().get(Photo.class, photoId);
	}
	
	public void save(Photo photo) {
		sessionFactory.getCurrentSession().saveOrUpdate(photo);
		if (photo.getStatusId() == PhotoStatus.APPROVED.getId()) {
			//updateChangeLog(photo.getListingId(), MemorialContentType.PHOTO);
		}
	}
	
	public void delete(Photo photo) {
		sessionFactory.getCurrentSession().delete(photo);
	}

	@SuppressWarnings("unchecked")
	public List<Photo> getApprovedPhotos(int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Photo where listingId = :listingId and statusId = :statusId and photoTypeId = :photoTypeId");
		query.setInteger("listingId", listingId);
		query.setInteger("statusId", PhotoStatus.APPROVED.getId());
		query.setInteger("photoTypeId", PhotoType.GALLERY.getId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Photo> getPhotosByListingIdAndStatusId(int listingId, int statusId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Photo where listingId = :listingId and statusId = :statusId");
		query.setInteger("listingId", listingId);
		query.setInteger("statusId", statusId);
		return query.list();
	}
	
	public Photo getProfilePhoto(int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Photo where listingId = :listingId and photoTypeId = :photoTypeId");
		query.setInteger("listingId", listingId);
		query.setInteger("photoTypeId", PhotoType.PROFILE.getId());
		return (Photo)query.uniqueResult();
	}

}