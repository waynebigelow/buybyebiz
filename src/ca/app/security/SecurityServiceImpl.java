package ca.app.security;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;

import ca.app.model.application.Application;
import ca.app.model.usage.Area;
import ca.app.model.user.ActivityLog;
import ca.app.model.user.ActivityType;
import ca.app.model.user.AppUser;
import ca.app.model.user.Password;
import ca.app.model.user.User;
import ca.app.persistence.user.PasswordDAO;
import ca.app.service.activityLog.ActivityLogService;
import ca.app.service.mail.MailService;
import ca.app.service.user.UserService;
import ca.app.util.HashUtil;
import ca.app.util.PasswordUtil;
import ca.app.util.ProjectUtil;

public class SecurityServiceImpl implements SecurityService {
	private SecurityDaoImpl securityDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SaltSource saltSource;
	@Autowired
	private PasswordDAO passwordDAO;
	@Autowired
	private MailService mailService;
	@Autowired
	private ActivityLogService activityLogService;
	
	public String encodePasswordForUser(User user) {
		String password = user.getPassword();
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		AppUser appUser = new AppUser(user.getEmail(), user.getPassword(), user.isEnabled(), auths, user.getEmail(), user.getFirstName(), user.getLastName(), user.getPreferredLocale(), user.getUserId());
		Object salt = saltSource.getSalt(appUser);
		
		return passwordEncoder.encodePassword(password, salt);
	}

	public String resetUserPassword(User user, Application application) throws MessagingException {
		String newPassword = PasswordUtil.generatePassword();
		
		changeUserPassword(user, newPassword);
		
		mailService.sendPasswordReset(application, user, newPassword);
		
		ActivityLog activityLog = new ActivityLog(user, Area.SECURITY.getId(), ActivityType.PWD_RESET.getId(), 0, null);
		activityLogService.insert(activityLog);

		return newPassword;
	}
	
	public void changeUserPassword(User user, String newPassword) {
		user.setPassword(newPassword);
		user.setLastPasswordChange(new Timestamp(System.currentTimeMillis()));
		userService.updateUserWithNewPassword(user);
		
		ActivityLog activityLog = new ActivityLog(user, Area.SECURITY.getId(), ActivityType.PWD_CHANGED.getId(), 0, null);
		activityLogService.insert(activityLog);
	}
	
	public void logSuccessfulLogin(int userId) {
		User user = userService.getByUserId(userId);
		user.setLastLogin(new Timestamp(System.currentTimeMillis()));
		user.setFailedLogins(0);
		userService.update(user);
		
		ActivityLog activityLog = new ActivityLog(user, Area.SECURITY.getId(), ActivityType.SIGNED_IN.getId(), 0, null);
		activityLogService.insert(activityLog);
	}

	public User logFailedLogin(String email, Application application) throws MessagingException {
		User user = userService.getByEmail(email);
		if(user != null) {
			user.setFailedLogins(user.getFailedLogins() + 1);
			//The reason we check to see if the user is enabled is to 
			//prevent a scenario where an intentionally disabled user 
			//forces a lockout after the fact to be able to re-enable the account by resetting the password
			if(user.getFailedLogins() >= ProjectUtil.getIntProperty("max.failed.logins") && user.isEnabled()) {
				user.setEnabled(false);
				user.setLockedOut(true);
				user.setDisabledReason("Login attempts exceeded.");
				
				ActivityLog activityLog = new ActivityLog(user, Area.SECURITY.getId(), ActivityType.FAILED_LOGINS_EXCEEDED.getId(), 0, null);
				activityLogService.insert(activityLog);
				//We only want to send this email once, not every time we fail after the max.
				if(user.getFailedLogins() == ProjectUtil.getIntProperty("max.failed.logins")){
					mailService.sendAccountLocked(application, user);
				}
			}
			
			userService.update(user);
		}
		return user;
	}

	public void logOldPassword(User user, String oldPassword) {
		Password password = new Password();
		password.setChangedDate(new Timestamp(System.currentTimeMillis()));
		password.setUserId(user.getUserId());
		password.setHash(oldPassword);
		passwordDAO.updatePasswordHistory(password);
	}
	
	public AppUser getUserFromHash(String hash, String key) {
		String userName = HashUtil.getValueFromHash(hash, key);
		AppUser user = (AppUser)securityDAO.loadUserByUsername(userName);
		
		return user;
	}
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}
	public void setSecurityDAO(SecurityDaoImpl securityDAO) {
		this.securityDAO = securityDAO;
	}
	public void setPasswordDAO(PasswordDAO passwordDAO) {
		this.passwordDAO = passwordDAO;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
}