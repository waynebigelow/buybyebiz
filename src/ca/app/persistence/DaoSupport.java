package ca.app.persistence;

import java.lang.reflect.Method;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import ca.app.model.user.ActivityLog;
import ca.app.model.user.AppUser;
import ca.app.model.user.User;
import ca.app.util.LogUtil;

public abstract class DaoSupport extends HibernateDaoSupport {

	public Object cloneObject(Object srcObject, Object trgObject) {
		if (srcObject == null) {
			return trgObject;
		}
		
		ClassMetadata meta = getSessionFactory().getClassMetadata(srcObject.getClass());
		if (meta == null) {
			meta = getSessionFactory().getClassMetadata(trgObject.getClass());
		}
	
		Type[] types = meta.getPropertyTypes();
		String[] names = meta.getPropertyNames();
		Object[] values = meta.getPropertyValues(srcObject);
		Method[] methods = trgObject.getClass().getMethods();
		
		for (int i=0; i < names.length; i++) {
			if (!types[i].isEntityType() && !types[i].isCollectionType()) {
				String attributeName = names[i];
				String setterName = "set" + StringUtils.capitalize(attributeName);
			
				try {
					for (Method method : methods) {
						if (method.getName().equals(setterName)) {
							method.invoke(trgObject, values[i]);
						}
					}
				} catch (Exception ex) {
					LogUtil.logException(this.getClass(), "Cloning exception", ex);
				}
			}
		}
		
		return trgObject;
	}
	
	protected void saveActivityLog(int userId, int primaryId, int areaId, int typeId) {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setUser(getHibernateTemplate().get(User.class, userId));
		activityLog.setPrimaryId(primaryId);
		activityLog.setAreaId(areaId);
		activityLog.setTypeId(typeId);
		activityLog.setActivityDate(new Timestamp(System.currentTimeMillis()));
		getHibernateTemplate().save(activityLog);
	}
	
	public void delete() {
		
	}
	
	protected int getAuthenticatedUserId() {
		AppUser user = null;
		int userId = 0;
		Object obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof AnonymousAuthenticationToken) {
			return userId;
		} else {
			if (SecurityContextHolder.getContext() != null) {
				if (SecurityContextHolder.getContext().getAuthentication() != null) {
					if (SecurityContextHolder.getContext().getAuthentication().getPrincipal()!=null) {
						user = (AppUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						userId = user.getUserId();
					}
				}
			}
		}
		return userId;
	}
}