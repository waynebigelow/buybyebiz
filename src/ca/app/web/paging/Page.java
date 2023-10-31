package ca.app.web.paging;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ca.app.util.DateUtil;
import ca.app.util.StringUtil;

public class Page<T> {
	private Locale locale;
	private int start = 0;
	private int limit = 0;
	private String sort;
	private String customSort;
	private String dir = "asc";
	private String searchText = null;
	private int[] ids = null;
	private Map<String,String> params;
	private List<T> items;
	private int total;
	
	public Page() {
		
	}
	
	public Page(int start, int limit, int total, String searchText, List<T> items) {
		this.start = start;
		this.limit = limit;
		this.total = total;
		this.searchText = searchText;
		this.items = items;
	}
	
	public Locale getLocale() {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getCustomSort() {
		return customSort;
	}
	
	public void setCustomSort(String customSort) {
		this.customSort = customSort;
	}

	public String getDir() {
		return dir;
	}
	
	public void setDir(String dir) {
		this.dir = dir;
	}

	public Map<String, String> getParams() {
		if (params == null) {
			params = new HashMap<String,String>();
		}
		return params;
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public String getString(String paramName, String defaultValue) {
		String value = getParams().get(paramName);
		return (value != null) ? value : defaultValue;
	}
	
	public int getInt(String paramName, int defaultValue) {
		return StringUtil.convertStringToInt(getParams().get(paramName), defaultValue);
	}
	
	public boolean getBoolean(String paramName, boolean defaultValue) {
		return StringUtil.convertStringToBoolean(getParams().get(paramName), defaultValue);
	}
	
	public Date getDate(String paramName, String dateFormat) {
		String str = getParams().get(paramName);
		Date dte = DateUtil.getDateFromString(str, dateFormat);
		return dte;
	}
	
	public Timestamp getTimestamp(String paramName, String dateFormat) {
		Date dte = getDate(paramName, dateFormat);
		return (dte != null) ? new Timestamp(dte.getTime()) : null;
	}

	public String getSearchText() {
		return searchText;
	}
	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public int[] getIds() {
		return ids;
	}
	
	public void setIds(int[] ids) {
		this.ids = ids;
	}

	public List<T> getItems() {
		if (items == null) {
			items = new ArrayList<T>();
		}
		return items;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getCurrPage() {
		if (limit == 0) {
			return 1;
		}
		return (start/limit) + 1;
	}
	
	public int getLastPage() {
		if (limit==0) {
			return 1;
		}
		int lastPage = (total/limit) + ((total%limit>0) ? 1 : 0);
		return (lastPage>0) ? lastPage : 1;
	}

	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append(" start: " + start + "\n");
		buff.append(" limit: " + limit + "\n");
		buff.append(" sort: " + sort + "\n");
		buff.append(" dir: " + dir + "\n");
		buff.append(" size: " + ((items!=null)?items.size():0) + "\n");
		buff.append(" total: " + total + "\n");
		buff.append(" curr: " + getCurrPage() + "\n");
		buff.append(" last: " + getLastPage() + "\n");
		return buff.toString();
	}
}