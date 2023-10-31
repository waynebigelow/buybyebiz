package ca.app.web.dto.category;

import java.io.Serializable;

import ca.app.model.application.ApplicationType;
import ca.app.model.category.Category;

public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 3983118687958680553L;

	private int categoryId;
	private int typeId;
	private String name;
	private String i18n;
	
	public CategoryDTO() {}
	
	public CategoryDTO(Category category) {
		this.categoryId = category.getCategoryId();
		this.typeId = category.getTypeId();
		this.name = category.getName();
		this.i18n = category.getI18n();
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public ApplicationType getType() {
		return ApplicationType.get(typeId);
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