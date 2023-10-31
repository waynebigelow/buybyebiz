package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;

public class IndexesTag extends PagingBaseTag {
	private static final long serialVersionUID = -6467680521605739306L;

	private int numIndexes = 10;
	private String selectedClass;
	
	public IndexesTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal() <= 0) {
			return EVAL_BODY_INCLUDE;
		}
		
		try {
			JspWriter out = pageContext.getOut();
			
			// If the result set contains fewer pages than the given numIndexes,
			// then reset numIndexes
			numIndexes = (numIndexes <= getPage().getLastPage()) ? numIndexes:getPage().getLastPage();
			
			// Determine how many pages to show before and after the current page
			int pre, post = 0;
			if (numIndexes / 2 >= getPage().getCurrPage()) {
				pre = getPage().getCurrPage() - 1;
				post = numIndexes - 1 - pre;
			} else if (numIndexes / 2 > (getPage().getLastPage() - getPage().getCurrPage())) {
				post = getPage().getLastPage() - getPage().getCurrPage();
				pre = numIndexes - 1 - post;
			} else {
				pre = numIndexes / 2;
				post = numIndexes - 1 - pre;
			}
			
			String selectedDefaultClass = "active";
			String objectType = "li";

			String selectedClasses = selectedDefaultClass;
			if (this.selectedClass!=null && this.selectedClass.length() > 0) {
				selectedClasses += " " + this.selectedClass;
			}
			
			// Show pages before the current page
			for (int i = 0; i < pre; i++) {
				int page = getPage().getCurrPage()-pre+i;
				int start = (page - 1) * getPage().getLimit();
				out.write("<" + objectType + "><a href=\"" + buildUrl(start) + "\">" + page + "</a></" + objectType + ">");
			}
			
			// Show the current page
			out.write("<" + objectType + " class=\"" + selectedClasses + "\"><a href=\"#\">" + getPage().getCurrPage() + "</a></" + objectType + ">");
			
			// Show pages after the current page
			for (int i = 0; i < post; i++) {
				int page = getPage().getCurrPage() + 1 + i;
				int start = (page -1 ) * getPage().getLimit();
				out.write("<" + objectType + "><a href=\"" + buildUrl(start) + "\">" + page + "</a></" + objectType + ">");
			}
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "IndexesTag", ex);
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	public void setNumIndexes(int numIndexes) {
		this.numIndexes = numIndexes;
	}
	
	public void setSelectedClass(String selectedClass) {
		this.selectedClass = selectedClass;
	}
}