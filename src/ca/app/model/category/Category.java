package ca.app.model.category;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.application.ApplicationType;

@Entity
@Table(name="category")
public class Category implements Serializable{
	private static final long serialVersionUID = 3983118687958680553L;
	
	@Id
	@SequenceGenerator(name="seqCategory", sequenceName="seq_category", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqCategory")
	@Column(name="category_id", unique=true)
	private int categoryId;
	
	@Column(name="type_id")
	private int typeId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="i18n")
	private String i18n;
	
	public Category() {}
	
	public Category(int categoryId, int typeId, String name, String i18n) {
		this.categoryId = categoryId;
		this.typeId = typeId;
		this.name = name;
		this.i18n = i18n;
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