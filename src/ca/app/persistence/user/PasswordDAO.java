package ca.app.persistence.user;

import java.util.List;

import ca.app.model.user.Password;

public interface PasswordDAO {
	
	public void updatePasswordHistory(Password password);
	public List<Password> getPasswordHistory(int userId, int size);
	public void deleteAllPasswordsByUserId(int userId);
}