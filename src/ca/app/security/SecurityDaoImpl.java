package ca.app.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import ca.app.model.user.AppUser;
import ca.app.model.user.Role;
import ca.app.model.user.Roles;
import ca.app.model.user.UserListingRole;
import ca.app.service.common.TokenFieldType;
import ca.app.service.user.UserService;
import ca.app.util.RequestUtil;

public class SecurityDaoImpl extends JdbcDaoImpl {

	private UserService userService;

	private static final String DEF_USERS_BY_USERNAME_QUERY = 
		"select " +
		"	user_id, " + 
		"	email as username, " +
		"	email, " +
		"	firstname, " +
		"	lastname, " +
		"	password, " +
		"	enabled, " +
		"	preferred_locale, " +
		"	locked_out " +
		"from " +
		"	user_account " +
		"where " +
		"	lower(email) = lower(?) ";
	
	private static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = 
		"select " +
		"	rls.role_name " +
		"from " +
		"	user_listing_role ulr " +
		"	join roles rls " +
		"	on ulr.role_id = rls.role_id " +
		"where " + 
		"	ulr.user_id = ? " + 
		"	and ulr.application_id = ? ";
	
	private MappingSqlQuery<AppUser> usersByUsernameMapping;
	private MappingSqlQuery<GrantedAuthority> authoritiesByUsernameMapping;
	
	public SecurityDaoImpl() {
		super();
		setUsersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY);
		setAuthoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY);
	}
	
	@Override
	protected void initDao() {
		this.usersByUsernameMapping			= new UsersByUsernameMapping(getDataSource());
		this.authoritiesByUsernameMapping	= new AuthoritiesByUsernameMapping(getDataSource());
	}
	
	public User loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		// The username needs to be broken into parts.
		// It is formatted as: <applicationToken>:<username>
		int applicationId = 0;
		if (username.contains(":")) {
			String[] userNameArray = username.split(":");
			username = userNameArray[1];
			
			applicationId = RequestUtil.getPrimaryId(userNameArray[0], TokenFieldType.APPLICATION);
		}
		String lowercaseUsername = username.toLowerCase();
		
		// Try to find the specified user
		AppUser user = null;
		try {
			user = (AppUser)DataAccessUtils.singleResult(usersByUsernameMapping.execute(new Object[]{lowercaseUsername}));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UsernameNotFoundException("User not found");
		}
		
		if(user == null){
			throw new UsernameNotFoundException("User not found");
		}
		
		// Determine if user must agree to TOS
		
		// Load user roles
		List<GrantedAuthority> dbAuths = authoritiesByUsernameMapping.execute(new Object[]{user.getUserId(), applicationId});
		boolean foundRoleUser = false;
		for (Object obj : dbAuths) {
			Role role = (Role)obj;
			if (role.getRoleName().equals("ROLE_USER")) {
				foundRoleUser = true;
			}
		}
		
		if (!foundRoleUser) {
			// Every authorized user needs to have ROLE_USER
			dbAuths.add(new Role("ROLE_"+Roles.USER.getRoleName()));
		}
		
		addCustomAuthorities(user.getUsername(), dbAuths);
		
		AppUser userWithAuths = new AppUser(
			user.getUsername(), 
			user.getPassword(), 
			user.isEnabled(), 
			dbAuths, 
			user.getEmail(), 
			user.getFirstName(), 
			user.getLastName(), 
			user.getPreferredLocale(), 
			user.getUserId()
		);
		
		// Load roles
		List<UserListingRole> roles = userService.getUserListingRoles(applicationId, 0, user.getUserId(), true);
		userWithAuths.setRoles(roles);
		
		return userWithAuths;
	}

	protected class UsersByUsernameMapping extends MappingSqlQuery<AppUser> {
		protected UsersByUsernameMapping(DataSource ds) {
			super(ds, getUsersByUsernameQuery());
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected AppUser mapRow(ResultSet rs, int rownum) throws SQLException {
			String username = rs.getString(2);
			String password = rs.getString(6);
			boolean enabled = (rs.getBoolean(7) && !rs.getBoolean(9));
			String email = rs.getString(3);
			String firstname = rs.getString(4);
			String lastname = rs.getString(5);
			String preferredLocale = rs.getString(8);
			int userId = rs.getInt(1);
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			auths.add(new GrantedAuthorityImpl("HOLDER"));
			
			AppUser user = new AppUser(
				username,
				password,
				enabled,
				auths,
				email, 
				firstname, 
				lastname,
				preferredLocale,
				userId
			);
			
			return user;
		}
	}
	
	protected class AuthoritiesByUsernameMapping extends MappingSqlQuery<GrantedAuthority> {
		protected AuthoritiesByUsernameMapping(DataSource ds) {
			super(ds, getAuthoritiesByUsernameQuery());
			declareParameter(new SqlParameter(Types.NUMERIC));
			declareParameter(new SqlParameter(Types.NUMERIC));
			compile();
		}
		
		protected GrantedAuthority mapRow(ResultSet rs, int rownum) throws SQLException {
			String roleName = "ROLE_" + rs.getString(1);
			Role authority = new Role(roleName);
			return authority;
		}
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}