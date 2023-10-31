package ca.app.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ReauthSecurityDaoImpl extends SecurityDaoImpl {
	/**
	 * Problem: There are times when we need to update data associated with the logged-in user.
	 *  An example of this is when the user accepts the Terms of Service. The problem is that
	 *  Spring makes the SecurityContextHolder immutable and they say that the best practice is
	 *  to force the user to login again to refresh everything. That's not a great user experience
	 *  so this class gives us the ability to auto re-authenticate.
	 * 
	 * Solution 1: One way to do this is to define an additional authentication-provider in the 
	 *  applicationContext-security.xml file which does not perform a hash or salt the password.
	 *  Then we can pass in the hashed version of the password, it will be tested against the
	 *  hashed version of the password coming out of the database and the user will be re-authenticated.
	 *  
	 *  The thing that I don't like about this is that if anyone ever gained access to the Users
	 *  table, they would have the hashed versions of the passwords and those would authenticate
	 *  successfully at the login form.
	 * 
	 * Solution 1a: My solution is to salt the hashed passwords in the re-authentication process.
	 *  Then someone would need both a copy of the Users table as well as our code base to learn
	 *  enough to gain access.
	 */
	
	private static final String DEF_USERS_BY_USERNAME_QUERY = 
			"select " +
			"	user_id, " +
			"	email as username, " +
			"	email, " +
			"	firstname, " +
			"	lastname, " +
			"	user_id || password, " +
			"	enabled, " +
			"	preferred_locale, " +
			"	locked_out " +
			" from " +
			"	user_account " +
			" where " +
			"	lower(email) = lower(?) ";
	
	public ReauthSecurityDaoImpl() {
		super();
		setUsersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY);
	}
	
	public User loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User user = super.loadUserByUsername(username);
		return user;
	}
}