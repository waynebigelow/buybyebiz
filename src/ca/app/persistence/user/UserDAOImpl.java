package ca.app.persistence.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.user.User;
import ca.app.model.user.UserListingRole;

@Repository(value="userDAO")
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void insert(User user){
		sessionFactory.getCurrentSession().save(user);
	}
	
	public void update(User user){
		sessionFactory.getCurrentSession().update(user);
	}
	
	public void delete(User user){
		sessionFactory.getCurrentSession().delete(user);
	}
	
	public User getByUserId(int userId){
		Query query = sessionFactory.getCurrentSession().createQuery("from User where userId = :userId");
		query.setInteger("userId", userId);
		return (User)query.uniqueResult();
	}

	public User getByEmail(String email){
		Query query = sessionFactory.getCurrentSession().createQuery("from User where email = :email");
		query.setString("email", email);
		return (User)query.uniqueResult();
	}

	public boolean existsEmail(String email){
		Query query = sessionFactory.getCurrentSession().createQuery("from User where email = :email");
		query.setString("email", email);
		return (User)query.uniqueResult() == null;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		Query getEntries = sessionFactory.getCurrentSession().createQuery("from User order by lastName asc, firstName asc");
		return getEntries.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserListingRole> getUserListingRoles(int applicationId, int listingId, int userId, boolean includeSystemRoles) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserListingRole.class);
		
		if (applicationId > 0 && !includeSystemRoles) {
			criteria.add(Restrictions.eq("applicationId", applicationId));
		} else if (applicationId > 0 && includeSystemRoles) {
			criteria.add(Restrictions.disjunction()
				.add(Restrictions.eq("applicationId", applicationId))
				.add(Restrictions.eq("applicationId", 0))
			);
		} else if (!includeSystemRoles) {
			criteria.add(Restrictions.gt("applicationId", 0));
		}
		
		if (listingId > 0) {
			criteria.add(Restrictions.eq("listingId", listingId));
		}
		
		if (userId>0) {
			criteria.add(Restrictions.eq("userId", userId));
		}
		
		return (List<UserListingRole>)criteria.list();
	}

	public UserListingRole saveRoleForUserAndListing(int userId, int listingId, int roleId, int applicationId) {
		UserListingRole userListingRole = new UserListingRole();
		userListingRole.setUserId(userId);
		userListingRole.setListingId(listingId);
		userListingRole.setRoleId(roleId);
		userListingRole.setApplicationId(applicationId);
		sessionFactory.getCurrentSession().save(userListingRole);
		
		return userListingRole;
	}
	
	public UserListingRole getUserListingRole(int userId, int roleId, int applicationId, int listingId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserListingRole where userId = :userId and roleId = :roleId and applicationId = :applicationId and listingId = :listingId");
		query.setInteger("userId", userId);
		query.setInteger("roleId", roleId);
		query.setInteger("applicationId", applicationId);
		query.setInteger("listingId", listingId);
		
		return (UserListingRole)query.uniqueResult();
	}
	
	public void saveUserListingRole(UserListingRole userListingRole) {
		sessionFactory.getCurrentSession().save(userListingRole);
	}
	
	public void deleteAllRolesByUserId(int userId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from UserListingRole where userId = :userId");
		query.setInteger("userId", userId);
		query.executeUpdate();
	}
	
	public void deleteUserAddressUserId(int addressId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Address where addressId = :addressId");
		query.setInteger("addressId", addressId);
		query.executeUpdate();
	}
}