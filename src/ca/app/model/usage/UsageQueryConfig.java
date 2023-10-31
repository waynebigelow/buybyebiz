package ca.app.model.usage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ca.app.model.common.TimePeriod;

public class UsageQueryConfig implements Serializable {

	private static final long serialVersionUID = 3058895127627837153L;
	
	private int usageQueryConfigId = 0;
	private UsageReport usageReport;
	private String name;
	private String description;
	private int metricTypeId;
	private boolean groupByVendor;
	private boolean groupByPerson;
	private boolean groupByArea;
	private boolean groupByAction;
	private boolean groupByReferer;
	private boolean groupByRefererType;
	private boolean groupByUserAgent;
	private List<UsageQueryFilter> filters;
	private String filterByReferer;
	private String filterByUserAgent;
	private Timestamp startDateTime;
	private Timestamp endDateTime;
	private int timePeriodId;
	private int chartTypeId;
	private int numRowsInChart;
	
	public int getUsageQueryConfigId() {
		return usageQueryConfigId;
	}
	
	public void setUsageQueryConfigId(int usageQueryConfigId) {
		this.usageQueryConfigId = usageQueryConfigId;
	}
	
	public UsageReport getUsageReport() {
		return usageReport;
	}
	
	public void setUsageReport(UsageReport usageReport) {
		this.usageReport = usageReport;
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
	
	public int getMetricTypeId() {
		return metricTypeId;
	}
	
	public void setMetricTypeId(int metricTypeId) {
		this.metricTypeId = metricTypeId;
	}
	
	public ReportMetricType getMetricType() {
		return ReportMetricType.get(this.metricTypeId);
	}
	
	public void setMetricType(ReportMetricType reportMetricType) {
		this.metricTypeId = reportMetricType.getId();
	}
	
	public boolean isGroupByVendor() {
		return groupByVendor;
	}
	
	public void setGroupByVendor(boolean isGroupByVendor) {
		this.groupByVendor = isGroupByVendor;
	}
	
	public boolean isGroupByPerson() {
		return groupByPerson;
	}
	
	public void setGroupByPerson(boolean isGroupByPerson) {
		this.groupByPerson = isGroupByPerson;
	}
	
	public boolean isGroupByArea() {
		return groupByArea;
	}
	
	public void setGroupByArea(boolean isGroupByArea) {
		this.groupByArea = isGroupByArea;
	}
	
	public boolean isGroupByAction() {
		return groupByAction;
	}
	
	public void setGroupByAction(boolean isGroupByAction) {
		this.groupByAction = isGroupByAction;
	}
	
	public boolean isGroupByReferer() {
		return groupByReferer;
	}
	
	public void setGroupByReferer(boolean isGroupByReferer) {
		this.groupByReferer = isGroupByReferer;
	}
	
	public boolean isGroupByRefererType() {
		return groupByRefererType;
	}
	
	public void setGroupByRefererType(boolean isGroupByRefererType) {
		this.groupByRefererType = isGroupByRefererType;
	}
	
	public boolean isGroupByUserAgent() {
		return groupByUserAgent;
	}
	
	public void setGroupByUserAgent(boolean isGroupByUserAgent) {
		this.groupByUserAgent = isGroupByUserAgent;
	}
	
	public List<UsageQueryFilter> getFilters() {
		if (filters==null) {
			filters = new ArrayList<UsageQueryFilter>();
		}
		return filters;
	}
	
	public void setFilters(List<UsageQueryFilter> filters) {
		this.filters = filters;
	}
	
	public List<UsageQueryFilter> getFiltersByName(String name) {
		List<UsageQueryFilter> list = new ArrayList<UsageQueryFilter>();
		for (UsageQueryFilter filter : getFilters()) {
			if (filter.getName().equalsIgnoreCase(name)) {
				list.add(filter);
			}
		}
		return list;
	}
	
	public String getFilterByReferer() {
		return filterByReferer;
	}
	
	public void setFilterByReferer(String filterByReferer) {
		this.filterByReferer = filterByReferer;
	}
	
	public String getFilterByUserAgent() {
		return filterByUserAgent;
	}
	
	public void setFilterByUserAgent(String filterByUserAgent) {
		this.filterByUserAgent = filterByUserAgent;
	}
	
	public Timestamp getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public Timestamp getEndDateTime() {
		return endDateTime;
	}
	
	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public int getTimePeriodId() {
		return timePeriodId;
	}
	
	public void setTimePeriodId(int timePeriodId) {
		this.timePeriodId = timePeriodId;
	}
	
	public TimePeriod getTimePeriod() {
		return TimePeriod.get(timePeriodId);
	}
	
	public void setTimePeriodId(TimePeriod timePeriod) {
		this.timePeriodId = timePeriod.getId();
	}
	
	public int getChartTypeId() {
		return chartTypeId;
	}
	
	public void setChartTypeId(int chartTypeId) {
		this.chartTypeId = chartTypeId;
	}
	
	public int getNumRowsInChart() {
		return numRowsInChart;
	}
	
	public void setNumRowsInChart(int numRowsInChart) {
		this.numRowsInChart = numRowsInChart;
	}
}