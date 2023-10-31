package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;

public class InfoTag extends PagingBaseTag {
	private static final long serialVersionUID = -6467680521605739306L;

	private String label;
	
	public InfoTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal()<=0) {
			return EVAL_BODY_INCLUDE;
		}
		try {
			JspWriter out = pageContext.getOut();

			int first = getPage().getCurrPage() * getPage().getLimit() - getPage().getLimit() + 1;
			int last = getPage().getCurrPage() * getPage().getLimit();
			if (last > getPage().getTotal()) {
				last = getPage().getTotal();
			}
			out.write(getMessageSource().getMessage(label, new Object[] {first, last, getPage().getTotal()}, getLocale()));
			
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "FirstTag", ex);
		} 
		
		return EVAL_BODY_INCLUDE;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}