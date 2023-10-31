
package ca.app.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.application.Application;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.model.user.Roles;
import ca.app.model.user.User;
import ca.app.model.user.UserAgreement;
import ca.app.model.user.UserAgreementType;
import ca.app.model.user.UserListingRole;
import ca.app.persistence.user.PasswordDAO;
import ca.app.persistence.user.UserAgreementDAO;
import ca.app.persistence.user.UserDAO;
import ca.app.security.SecurityService;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.web.dto.user.UserDTO;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserAgreementDAO userAgreementDAO;
	@Autowired
	private PasswordDAO passwordDAO;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private ActivityLogService activityLogService;
	
	public void register(User user, Application application) {
		handleRegistration(user, application);
		
		userDAO.saveRoleForUserAndListing(user.getUserId(), 0, Roles.USER.getRoleId(), application.getApplicationId());
		userDAO.saveRoleForUserAndListing(user.getUserId(), 0, Roles.ACCOUNT_OWNER.getRoleId(), application.getApplicationId());
	}
	
	private void handleRegistration(User user, Application application) {
		user.setPassword(securityService.encodePasswordForUser(user));
		userDAO.insert(user);
		ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.REGISTRATION_REQUESTED.getId(), 0, null);
		activityLogService.insert(activityLog);
		
		addUserAgreement(new UserAgreement(user, application, UserAgreementType.TERMS_OF_USE));
		activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.TOU_ACCEPTED.getId(), 0, null);
		activityLogService.insert(activityLog);
		
		addUserAgreement(new UserAgreement(user, application, UserAgreementType.PRIVACY_POLICY));
		activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.PP_ACCEPTED.getId(), 0, null);
		activityLogService.insert(activityLog);
	}
	
	public void update(User user, boolean encodePassword, AppUser appUser) {
		if (encodePassword && user.getPassword() != null && !user.getPassword().equals("")) {
			user.setPassword(securityService.encodePasswordForUser(user));
		}
		
		update(user, appUser);
	}
	
	public void update(User user, AppUser appUser) {
		userDAO.update(user);
		
		ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.USER_UPDATED.getId(), 0, userDAO.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public void update(User user) {
		userDAO.update(user);
	}
	
	public void createUser(User user, Application application, AppUser appUser) {
		user.setPassword(securityService.encodePasswordForUser(user));
		userDAO.insert(user);
		
		userDAO.saveRoleForUserAndListing(user.getUserId(), 0, Roles.USER.getRoleId(), application.getApplicationId());
		userDAO.saveRoleForUserAndListing(user.getUserId(), 0, Roles.ACCOUNT_OWNER.getRoleId(), application.getApplicationId());
		userDAO.saveRoleForUserAndListing(user.getUserId(), 0, Roles.LISTING_OWNER.getRoleId(), application.getApplicationId());
		
		ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.USER_CREATED.getId(), 0, userDAO.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public void delete(User user, AppUser appUser) {
		userDAO.delete(user);
		
		ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.USER_DELETED.getId(), 0, userDAO.getByUserId(appUser.getUserId()));
		activityLogService.insert(activityLog);
	}
	
	public void completeRegistration(User user, Application application) {
		update(user);
		
		userDAO.saveRoleForUserAndListing(user.getUserId(), 0, Roles.LISTING_OWNER.getRoleId(), application.getApplicationId());
		
		ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.REGISTRATION_COMPLETED.getId(), 0, user);
		activityLogService.insert(activityLog);
	}
	
	public void completeEmailChange(User user, Application application) {
		update(user);
		
		ActivityLog activityLog = new ActivityLog(user, Area.USER_ACCOUNT.getId(), ActivityType.USER_EMAIL_CHANGED.getId(), 0, user);
		activityLogService.insert(activityLog);
	}
	
	public User getByUserId(int userId) {
		return userDAO.getByUserId(userId);
	}
	
	public User getByEmail(String email) {
		return userDAO.getByEmail(email);
	}
	
	public boolean existsEmail(String email) {
		return userDAO.existsEmail(email);
	}
	
	public List<UserDTO> getAllUsers(){
		List<User> users = userDAO.getAllUsers();
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for (User user : users) {
			dtos.add(new UserDTO(user));
		}
		
		return dtos;
	}
	
	public void cascadeDeleteAllForUser(User user){
		userDAO.deleteAllRolesByUserId(user.getUserId());
		
		userAgreementDAO.deleteAllAgreementsByUserId(user.getUserId());
		
		passwordDAO.deleteAllPasswordsByUserId(user.getUserId());
		
		activityLogService.deleteAllActivityByUserId(user.getUserId());
		
		userDAO.delete(user);
	}
	
	public void addUserAgreement(UserAgreement userAgreement){
		userAgreementDAO.save(userAgreement);
	}
	
	public List<UserAgreement> getUserAgreementList(int userId){
		return userAgreementDAO.getByUserId(userId);
	}
	
	public List<UserAgreement> getUserAgreementList(int userId, UserAgreementType agreementType, Integer version){
		return userAgreementDAO.getByUserId(userId, agreementType, version);
	}
	
	public List<UserListingRole> getUserListingRoles(int applicationId, int listingId, int userId, boolean includeSystemRoles) {
		return userDAO.getUserListingRoles(applicationId, listingId, userId, includeSystemRoles);
	}
	
	public UserListingRole getUserListingRole(int userId, int roleId, int applicationId, int listingId) {
		return userDAO.getUserListingRole(userId, roleId, applicationId, listingId);
	}
	
	public UserListingRole saveRoleForUserAndListing(int userId, int listingId, int roleId, int applicationId) {
		return userDAO.saveRoleForUserAndListing(userId, listingId, roleId, applicationId);
	}
	
	public void saveUserListingRole(UserListingRole userListingRole) {
		userDAO.saveUserListingRole(userListingRole);
	}
	
	public void updateUserWithNewPassword(User user) {
		user.setPassword(securityService.encodePasswordForUser(user));
		if(user.isLockedOut()){
			user.setEnabled(true);
			user.setLockedOut(false);
			user.setFailedLogins(0);
		}
		
		userDAO.update(user);
	}
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public void setUserAgreementDAO(UserAgreementDAO userAgreementDAO) {
		this.userAgreementDAO = userAgreementDAO;
	}
	public void setPasswordDAO(PasswordDAO passwordDAO) {
		this.passwordDAO = passwordDAO;
	}
	public void setActivityLogService(ActivityLogService activityLogService) {
		this.activityLogService = activityLogService;
	}
}