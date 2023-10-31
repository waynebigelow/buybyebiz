package ca.app.persistence.category;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.app.model.category.Category;
import ca.app.model.category.SubCategory;

@Repository(value="categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Category getByCategoryId(int categoryId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Category where categoryId = :categoryId");
		query.setInteger("categoryId", categoryId);
		return (Category)query.uniqueResult();
	}
	
	public SubCategory getBySubCategoryId(int subCategoryId){
		Query query = sessionFactory.getCurrentSession().createQuery("from SubCategory where subCategoryId = :subCategoryId");
		query.setInteger("subCategoryId", subCategoryId);
		return (SubCategory)query.uniqueResult();
	}
	
	public void saveOrUpdate(Category category){
		sessionFactory.getCurrentSession().saveOrUpdate(category);
	}
	
	public void saveOrUpdate(SubCategory subCategory){
		sessionFactory.getCurrentSession().saveOrUpdate(subCategory);
	}
	
	public void deleteCategoryByCategoryId(int categoryId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Category where categoryId = :categoryId");
		query.setInteger("categoryId", categoryId);
		query.executeUpdate();
	}
	
	public void deleteSubCategoriesByCategoryId(int categoryId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from SubCategory where categoryId = :categoryId");
		query.setInteger("categoryId", categoryId);
		query.executeUpdate();
	}
	
	public void deleteSubCategoryBySubCategoryId(int subCategoryId){
		Query query = sessionFactory.getCurrentSession().createQuery("delete from SubCategory where subCategoryId = :subCategoryId");
		query.setInteger("subCategoryId", subCategoryId);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getAllCategories(){
		Query query = sessionFactory.getCurrentSession().createQuery("from Category order by name asc");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SubCategory> getAllByCategoryId(int categoryId){
		Query query = sessionFactory.getCurrentSession().createQuery("from SubCategory where categoryId = :categoryId order by name asc");
		query.setInteger("categoryId", categoryId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getAllByTypeId(int typeId){
		Query query = sessionFactory.getCurrentSession().createQuery("from Category where typeId = :typeId order by name asc");
		query.setInteger("typeId", typeId);
		return query.list();
	}
}