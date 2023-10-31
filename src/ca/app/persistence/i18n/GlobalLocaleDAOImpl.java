package ca.app.persistence.i18n;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.i18n.GlobalLocale;

@Repository(value="globalLocaleDAO")
public class GlobalLocaleDAOImpl implements GlobalLocaleDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<GlobalLocale> getAllGlobalLocales() {
		Query query = sessionFactory.getCurrentSession().createQuery("from GlobalLocale");
		query.setCacheable(true);
		return query.list();
	}
}