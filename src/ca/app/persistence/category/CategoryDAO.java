package ca.app.persistence.category;

import java.util.List;

import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;


public interface CategoryDAO {

	public Category getByCategoryId(int categoryId);
	public SubCategory getBySubCategoryId(int subCategoryId);
	public void saveOrUpdate(Category category);
	public void saveOrUpdate(SubCategory subCategory);
	public void deleteCategoryByCategoryId(int categoryId);
	public void deleteSubCategoriesByCategoryId(int categoryId);
	public void deleteSubCategoryBySubCategoryId(int subCategoryId);
	public List<Category> getAllCategories();
	public List<SubCategory> getAllByCategoryId(int categoryId);
	public List<Category> getAllByTypeId(int typeId);
}