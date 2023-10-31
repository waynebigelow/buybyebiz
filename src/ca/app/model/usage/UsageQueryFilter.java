package ca.app.model.usage;

import java.io.Serializable;

public class UsageQueryFilter implements Serializable {

	private static final long serialVersionUID = 3058895127627837153L;
	
	private int usageQueryFilterId = 0;
	private int usageQueryConfigId = 0;
	private String name;
	private String value;
	
	public int getUsageQueryFilterId() {
		return usageQueryFilterId;
	}
	
	public void setUsageQueryFilterId(int usageQueryFilterId) {
		this.usageQueryFilterId = usageQueryFilterId;
	}
	
	public int getUsageQueryConfigId() {
		return usageQueryConfigId;
	}
	
	public void setUsageQueryConfigId(int usageQueryConfigId) {
		this.usageQueryConfigId = usageQueryConfigId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getIntValue() {
		if (this.value!=null) {
			return Integer.parseInt(this.value);
		}
		return 0;
	}
}