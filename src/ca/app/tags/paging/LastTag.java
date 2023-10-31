package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;


public class LastTag extends PagingBaseTag {
	private static final long serialVersionUID = -6467680521605739306L;
	
	public LastTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal()<=0) {
			return EVAL_BODY_INCLUDE;
		}
		try {
			JspWriter out = pageContext.getOut();
			
			if (getPage().getCurrPage() != getPage().getLastPage()) {
				int start = (getPage().getLastPage() - 1) * getPage().getLimit();
				out.write("<li class=\"\"><a href=\"" + buildUrl(start) + "\"><span class=\"glyphicon glyphicon-fast-forward\"></span></a></li>");
			} else {
				out.write("<li class=\"disabled\"><a href=\"#\"><span class=\"glyphicon glyphicon-fast-forward\"></span></a></li>");
			}
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "LastTag", ex);
		}
		return EVAL_BODY_INCLUDE;
	}
}