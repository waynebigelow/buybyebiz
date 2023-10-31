package ca.app.service.application;

import java.util.List;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationPackage;
import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.application.ApplicationPackageDTO;
import ca.app.web.dto.category.CategoryDTO;

public interface ApplicationService {

	public Application getByApplicationId(int applicationId);
	public Application getApplicationByListingId(int listingId);
	public List<ApplicationDTO> getAllApplications();
	public void saveOrUpdate(Application application);
	public void deleteByApplicationId(int applicationId);
	public Application getApplicationForHostName(String hostName);
	
	public ApplicationPackage getByPackageId(int packageId);
	public ApplicationPackage getByAppAndTypeId(int applicationId, int typeId);
	public ApplicationPackage getByLinkValueProviderAppCurrencyAndTypeId(String linkValue, int providerId, int applicationId, int currencyTypeId, int typeId);
	public void saveOrUpdate(ApplicationPackage applicationPackage);
	public void delete(ApplicationPackage applicationPackage);
	public void deleteByPackageId(int packageId);
	public List<ApplicationPackageDTO> getAllPackages();
	public List<ApplicationPackageDTO> getByAppIdAndCurrencyId(int applicationId, int currencyTypeId);
	
	public Category getByCategoryId(int categoryId);
	public SubCategory getBySubCategoryId(int subCategoryId);
	public void saveOrUpdate(Category category);
	public void saveOrUpdate(SubCategory subCategory);
	public void deleteCategory(int categoryId);
	public void deleteSubCategories(int categoryId);
	public void deleteSubCategory(int subCategoryId);
	public List<CategoryDTO> getAllCategories();
	public List<SubCategory> getAllByCategoryId(int categoryId);
	public List<CategoryDTO> getAllByTypeId(int typeId);
}