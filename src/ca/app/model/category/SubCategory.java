package ca.app.model.category;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sub_category")
public class SubCategory implements Serializable{
	private static final long serialVersionUID = 3983118687958680553L;
	
	@Id
	@SequenceGenerator(name="seqSubCategory", sequenceName="seq_sub_category", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqSubCategory")
	@Column(name="sub_category_id", unique=true)
	private int subCategoryId;
	
	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="i18n")
	private String i18n;
	
	public SubCategory() {}
	
	public SubCategory(int subCategoryId, int categoryId, String name, String i18n) {
		this.subCategoryId = subCategoryId;
		this.categoryId = categoryId;
		this.name = name;
		this.i18n = i18n;
	}
	
	public int getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}