package ca.app.persistence.application;

import java.util.List;

import ca.app.model.application.ApplicationPackage;

public interface ApplicationPackageDAO {
	public ApplicationPackage getByPackageId(int packageId);
	public ApplicationPackage getByAppAndTypeId(int applicationId, int typeId);
	public ApplicationPackage getByLinkValueProviderAppCurrencyAndTypeId(String linkValue, int providerId, int applicationId, int currencyTypeId, int typeId);
	public void saveOrUpdate(ApplicationPackage applicationPackage);
	public void delete(ApplicationPackage applicationPackage);
	public void deleteByPackageId(int packageId);
	public List<ApplicationPackage> getAllPackages();
	public List<ApplicationPackage> getByAppIdAndCurrencyId(int applicationId, int currencyTypeId);
}