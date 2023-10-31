package ca.app.service.user;

import java.util.List;

import ca.app.model.application.Application;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.model.user.UserAgreement;
import ca.app.model.user.UserAgreementType;
import ca.app.model.user.UserListingRole;
import ca.app.web.dto.user.UserDTO;

public interface UserService {
	public void register(User user, Application application);
	public void createUser(User user, Application application, AppUser appUser);
	public void update(User user, boolean encodePassword, AppUser appUser);
	public void update(User user, AppUser appUser);
	public void update(User user);
	public void delete(User user, AppUser appUser);
	public void completeRegistration(User user, Application application);
	public void completeEmailChange(User user, Application application);
	
	public User getByUserId(int userId);
	public User getByEmail(String email);
	public boolean existsEmail(String email);
	public List<UserDTO> getAllUsers();

	public void cascadeDeleteAllForUser(User user);
	public void addUserAgreement(UserAgreement agreement);
	public List<UserAgreement> getUserAgreementList(int userId);
	public List<UserAgreement> getUserAgreementList(int userId, UserAgreementType agreementType, Integer version);
	public UserListingRole getUserListingRole(int userId, int roleId, int applicationId, int listingId);
	public UserListingRole saveRoleForUserAndListing(int userId, int listingId, int roleId, int applicationId);
	public void saveUserListingRole(UserListingRole userListingRole);
	public List<UserListingRole> getUserListingRoles(int applicationId, int listingId, int userId, boolean includeSystemRoles);
	public void updateUserWithNewPassword(User user);
}