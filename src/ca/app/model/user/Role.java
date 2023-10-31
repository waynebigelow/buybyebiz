package ca.app.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import ca.app.util.LogUtil;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = -2450632075221035422L;
	
	@Id
	@SequenceGenerator(name="seqRoles", sequenceName="seq_roles", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqRoles")
	@Column(name="role_id", unique=true)
	private int roleId;
	
	@Column(name="role_name")
	private String roleName;
	
	@Deprecated
	public Role() {
		LogUtil.logWarn(getClass(),"Roles should not be instantiated this way.  The no-arg constructor only exists for AMF serialization");
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Role(String roleName){
		this.roleName = roleName;
	}
	
	public String getModifiedRoleName() {
		return roleName;
	}
	
	@Override
	public String getAuthority() {
		return this.roleName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() == String.class)
			return obj.equals(roleName);
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}

	public int compareTo(Object o) {
		if (this == o)
			return 0;
		if (o == null)
			return -1;
		if (getClass() != o.getClass())
			return -1;
		final Role other = (Role) o;
		if (roleName == null) {
			if (other.roleName != null)
				return 1;
		} else {
			int i = getModifiedRoleName().compareTo(other.getModifiedRoleName());
			return i;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
	}
}