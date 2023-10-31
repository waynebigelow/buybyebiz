package ca.app.model.common;

import java.io.Serializable;

public class Option implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String label;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}