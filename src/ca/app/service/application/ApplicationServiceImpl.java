package ca.app.service.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.app.model.application.Application;
import ca.app.model.application.ApplicationPackage;
import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;
import ca.app.persistence.application.ApplicationDAO;
import ca.app.persistence.application.ApplicationPackageDAO;
import ca.app.persistence.category.CategoryDAO;
import ca.app.web.dto.application.ApplicationDTO;
import ca.app.web.dto.application.ApplicationPackageDTO;
import ca.app.web.dto.category.CategoryDTO;

public class ApplicationServiceImpl implements ApplicationService {
	@Autowired
	private ApplicationDAO applicationDAO;
	@Autowired
	private ApplicationPackageDAO applicationPackageDAO;
	@Autowired
	private CategoryDAO categoryDAO;

	public Application getByApplicationId(int applicationId){
		return applicationDAO.getByApplicationId(applicationId);
	}
	
	public Application getApplicationByListingId(int listingId){
		return applicationDAO.getApplicationByListingId(listingId);
	}
	
	public List<ApplicationDTO> getAllApplications(){
		List<Application> applications = applicationDAO.getAllApplications();
		
		List<ApplicationDTO> dtos = new ArrayList<ApplicationDTO>();
		for (Application application : applications) {
			dtos.add(new ApplicationDTO(application));
		}
		
		return dtos;
	}
	
	public void saveOrUpdate(Application application){
		applicationDAO.saveOrUpdate(application);
	}
	
	public void deleteByApplicationId(int applicationId){
		applicationDAO.deleteByApplicationId(applicationId);
	}
	
	public Application getApplicationForHostName(String hostName) {
		return applicationDAO.getApplicationForHostName(hostName);
	}
	
	public ApplicationPackage getByPackageId(int packageId){
		return applicationPackageDAO.getByPackageId(packageId);
	}
	
	public ApplicationPackage getByAppAndTypeId(int applicationId, int typeId) {
		return applicationPackageDAO.getByAppAndTypeId(applicationId, typeId);
	}
	
	public ApplicationPackage getByLinkValueProviderAppCurrencyAndTypeId(String linkValue, int providerId, int applicationId, int currencyTypeId, int typeId) {
		return applicationPackageDAO.getByLinkValueProviderAppCurrencyAndTypeId(linkValue, providerId, applicationId, currencyTypeId, typeId);
	}
	
	public void saveOrUpdate(ApplicationPackage applicationPackage) {
		applicationPackageDAO.saveOrUpdate(applicationPackage);
	}
	
	public void delete(ApplicationPackage applicationPackage) {
		applicationPackageDAO.delete(applicationPackage);
	}
	
	public void deleteByPackageId(int packageId){
		applicationPackageDAO.deleteByPackageId(packageId);
	}
	
	public List<ApplicationPackageDTO> getByAppIdAndCurrencyId(int applicationId, int currencyTypeId){
		return getAppPackageDTO(applicationPackageDAO.getByAppIdAndCurrencyId(applicationId, currencyTypeId));
	}
	
	public List<ApplicationPackageDTO> getAllPackages(){
		return getAppPackageDTO(applicationPackageDAO.getAllPackages());
	}
	
	private List<ApplicationPackageDTO> getAppPackageDTO(List<ApplicationPackage> packages) {
		List<ApplicationPackageDTO> dtos = new ArrayList<ApplicationPackageDTO>();
		for (ApplicationPackage appPackage : packages) {
			dtos.add(new ApplicationPackageDTO(appPackage));
		}
		
		return dtos;
	}
	
	public Category getByCategoryId(int categoryId) {
		return categoryDAO.getByCategoryId(categoryId);
	}
	
	public SubCategory getBySubCategoryId(int subCategoryId) {
		return categoryDAO.getBySubCategoryId(subCategoryId);
	}
	
	public void saveOrUpdate(Category category) {
		categoryDAO.saveOrUpdate(category);
	}
	
	public void saveOrUpdate(SubCategory subCategory) {
		categoryDAO.saveOrUpdate(subCategory);
	}
	
	public void deleteCategory(int categoryId) {
		deleteSubCategories(categoryId);
		categoryDAO.deleteCategoryByCategoryId(categoryId);
	}
	
	public void deleteSubCategories(int categoryId) {
		categoryDAO.deleteSubCategoriesByCategoryId(categoryId);
	}
	
	public void deleteSubCategory(int subCategoryId) {
		categoryDAO.deleteSubCategoryBySubCategoryId(subCategoryId);
	}
	
	public List<CategoryDTO> getAllCategories() {
		return getCategoryDTO(categoryDAO.getAllCategories());
	}
	
	public List<SubCategory> getAllByCategoryId(int categoryId) {
		return categoryDAO.getAllByCategoryId(categoryId);
	}

	public List<CategoryDTO> getAllByTypeId(int typeId) {
		return getCategoryDTO(categoryDAO.getAllByTypeId(typeId));
	}
	
	private List<CategoryDTO> getCategoryDTO(List<Category> categories) {
		List<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
		for (Category category : categories) {
			dtos.add(new CategoryDTO(category));
		}
		
		return dtos;
	}
	
	public void setApplicationDAO(ApplicationDAO applicationDAO) {
		this.applicationDAO = applicationDAO;
	}
	public void setApplicationPackageDAO(ApplicationPackageDAO applicationPackageDAO) {
		this.applicationPackageDAO = applicationPackageDAO;
	}
	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
}