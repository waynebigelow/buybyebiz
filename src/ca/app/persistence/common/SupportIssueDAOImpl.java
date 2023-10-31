package ca.app.persistence.common;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.common.SupportIssue;

@Repository("supportIssueDAO")
public class SupportIssueDAOImpl implements SupportIssueDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void insert(SupportIssue issue) {
		sessionFactory.getCurrentSession().save(issue);
	}
	
	public void update(SupportIssue issue) {
		sessionFactory.getCurrentSession().saveOrUpdate(issue);
	}
	
	public void delete(SupportIssue issue) {
		sessionFactory.getCurrentSession().delete(issue);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}