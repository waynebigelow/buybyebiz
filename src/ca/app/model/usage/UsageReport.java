package ca.app.model.usage;

import java.io.Serializable;
import java.util.List;

public class UsageReport implements Serializable {

	private static final long serialVersionUID = 4892476749533596005L;
	
	private int usageReportId = 0;
	private int vendorId;
	private int userId;
	private String name;
	private String description;
	private List<UsageQueryConfig> queryConfigs;
	
	public int getUsageReportId() {
		return usageReportId;
	}
	
	public void setUsageReportId(int usageReportId) {
		this.usageReportId = usageReportId;
	}
	
	public int getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<UsageQueryConfig> getQueryConfigs() {
		return queryConfigs;
	}
	
	public void setQueryConfigs(List<UsageQueryConfig> queryConfigs) {
		this.queryConfigs = queryConfigs;
	}
}