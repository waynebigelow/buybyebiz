package ca.app.security;

import javax.mail.MessagingException;

import ca.app.model.application.Application;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;

public interface SecurityService {

	public String encodePasswordForUser(User user);
	public void changeUserPassword(User user, String newPassword);
	public void logOldPassword(User user, String oldPassword);
	public String resetUserPassword(User user, Application application) throws MessagingException;
	public void logSuccessfulLogin(int userId);
	public User logFailedLogin(String email, Application application) throws MessagingException;
	public AppUser getUserFromHash(String hash, String key);
}