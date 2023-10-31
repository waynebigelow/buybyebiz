package ca.app.model.usage;

import java.io.Serializable;

public class UserAgent implements Serializable {

	private static final long serialVersionUID = 7695466871476503379L;
	
	private int id;
	private String raw;
	private String data;
	private String type;
	private String name;
	private String version;
	private String osName;
	private String osVersionName;
	private String osVersionNum;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRaw() {
		return raw;
	}
	
	public void setRaw(String raw) {
		this.raw = raw;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getOsName() {
		return osName;
	}
	
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getOsVersionName() {
		return osVersionName;
	}
	
	public void setOsVersionName(String osVersionName) {
		this.osVersionName = osVersionName;
	}
	
	public String getOsVersionNum() {
		return osVersionNum;
	}
	
	public void setOsVersionNum(String osVersionNum) {
		this.osVersionNum = osVersionNum;
	}
}