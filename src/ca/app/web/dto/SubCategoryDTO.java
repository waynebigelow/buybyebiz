package ca.app.web.dto;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.MessageSource;

import ca.app.model.category.SubCategory;

public class SubCategoryDTO implements Serializable{
	private static final long serialVersionUID = 3983118687958680553L;
	
	private int subCategoryId;
	private int categoryId;
	private String name;
	private String i18n;
	private String text;

	public SubCategoryDTO(SubCategory subCategory, MessageSource messageSource, Locale locale) {
		this.subCategoryId = subCategory.getSubCategoryId();
		this.categoryId = subCategory.getCategoryId();
		this.name = subCategory.getName();
		this.i18n = subCategory.getI18n();
		this.text = messageSource.getMessage(subCategory.getI18n(), null, locale);
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

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}