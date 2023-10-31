package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;


public class OrderByRecentTag extends PagingBaseTag {

	private static final long serialVersionUID = -6467680521605739306L;

	private String action;
	private String method;
	private String fieldName;
	private String fieldVal;
	private String sortBy;
	private String sortDirection;
	private String clazz;
	
	public OrderByRecentTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal()<=0) {
			return EVAL_BODY_INCLUDE;
		}
		try {
			JspWriter out = pageContext.getOut();
			
			out.write("<form id=\"sortForm\" action=\"" + action + "\" method=\"" + method + "\">");
			out.write("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + fieldVal + "\" />");
			out.write(getMessageSource().getMessage("global.pager.sort.by.label", null, getLocale()) + " ");
			out.write("<select id=\"sortBy\" name=\"sortBy\" onchange=\"document.getElementById('sortForm').submit();\"");
			if (this.clazz!=null && this.clazz.length()>0) {
				out.write(" class=\"" + this.clazz + "\" ");
			}
			out.write(" >");
			out.write("<option value=\"mostRecent\" " + (sortBy.equals("entryDate")&&sortDirection.equals("desc")?"selected=\"selected\">":">") + 
					getMessageSource().getMessage("global.pager.most.recent.label", null, getLocale()) + "</option>");
			out.write("<option value=\"leastRecent\" " + (sortBy.equals("entryDate")&&sortDirection.equals("asc")?"selected=\"selected\">":">") + 
					getMessageSource().getMessage("global.pager.least.recent.label", null, getLocale()) + "</option>");
			out.write("</select>");
			out.write("</form>");
			
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "LastTag", ex);
		} 
		return EVAL_BODY_INCLUDE;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldVal(String fieldVal) {
		this.fieldVal = fieldVal;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	
}