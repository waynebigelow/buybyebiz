package ca.app.model.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FileMetadata implements Serializable {
	private static final long serialVersionUID = -535454327335205749L;

	private String name;
	private Map<String, String> info;
	private String pathToFile;
	
	public Map<String, String> getInfo() {
		if (info == null) {
			info = new HashMap<String, String>();
		}
		return info;
	}
	public void setInfo(Map<String, String> info) {
		this.info = info;
	}

	public String getPathToFile() {
		return pathToFile;
	}
	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}