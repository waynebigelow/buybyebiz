package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;


public class FirstTag extends PagingBaseTag {
	private static final long serialVersionUID = -6467680521605739306L;
	
	public FirstTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal()<=0) {
			return EVAL_BODY_INCLUDE;
		}
		try {
			JspWriter out = pageContext.getOut();
			
			if (getPage().getCurrPage() != 1) {
				out.write("<li class=\"\"><a href=\"" + buildUrl(0) + "\"><span class=\"glyphicon glyphicon-fast-backward\"></span></a></li>");
			} else {
				out.write("<li class=\"disabled\"><a href=\"#\"><span class=\"glyphicon glyphicon-fast-backward\"></span></a></li>");
			}
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "FirstTag", ex);
		}
		return EVAL_BODY_INCLUDE;
	}
}