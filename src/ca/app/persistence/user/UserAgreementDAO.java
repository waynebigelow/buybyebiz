package ca.app.persistence.user;

import java.util.List;

import ca.app.model.user.UserAgreement;
import ca.app.model.user.UserAgreementType;

public interface UserAgreementDAO {
	
	public UserAgreement get(int agreementId);
	public void save(UserAgreement agreement);
	public void update(UserAgreement agreement);
	public void delete(UserAgreement agreement);
	public List<UserAgreement> getByUserId(int userId);
	public List<UserAgreement> getByUserId(int userId, UserAgreementType agreementType, Integer version);
	public void deleteAllAgreementsByUserId(int userId);
}