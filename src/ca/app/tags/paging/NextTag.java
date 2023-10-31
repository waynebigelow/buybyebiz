package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;

public class NextTag extends PagingBaseTag {
	private static final long serialVersionUID = -6467680521605739306L;
	
	public NextTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal() <= 0) {
			return EVAL_BODY_INCLUDE;
		}
		
		try {
			JspWriter out = pageContext.getOut();
			
			if (getPage().getCurrPage() != getPage().getLastPage()) {
				int start = (getPage().getCurrPage() - 1 + 1) * getPage().getLimit();
				out.write("<li class=\"\"><a href=\"" + buildUrl(start) + "\"><span class=\"glyphicon glyphicon-forward\"></span></a></li>");
			} else {
				out.write("<li class=\"disabled\"><a href=\"#\" aria-label=\"Next\"><span class=\"glyphicon glyphicon-forward\"></span></a></li>");
			}
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "NextTag", ex);
		}
		return EVAL_BODY_INCLUDE;
	}
}