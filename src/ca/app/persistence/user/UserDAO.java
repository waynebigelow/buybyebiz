package ca.app.persistence.user;

import java.util.List;

import ca.app.model.user.User;
import ca.app.model.user.UserListingRole;


public interface UserDAO {
	public void insert(User user);
	public void update(User user);
	public void delete(User user);
	
	public User getByUserId(int userId);
	public User getByEmail(String email);
	public boolean existsEmail(String email);
	public List<User> getAllUsers();

	public List<UserListingRole> getUserListingRoles(int applicationId, int listingId, int userId, boolean includeSystemRoles);
	public UserListingRole getUserListingRole(int userId, int roleId, int applicationId, int listingId);
	public void saveUserListingRole(UserListingRole userListingRole);
	public UserListingRole saveRoleForUserAndListing(int userId, int listingId, int roleId, int applicationId);
	public void deleteAllRolesByUserId(int userId);
	public void deleteUserAddressUserId(int userId);
}