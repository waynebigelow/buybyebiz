package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;


public class AlphaSearchTag extends PagingBaseTag {

	private static final long serialVersionUID = -6467680521605739306L;
	
	private static final String[] alphas = new String[] {"ANY",
		"A","B","C","D","E","F","G","H","I","J","K","L","M",
		"N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public AlphaSearchTag() {
		super();
	}

	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();

			out.write("|&nbsp;");
			for (int i=0; i<alphas.length; i++) {
				String alphaStr = (i==0)?getMessageSource().getMessage("memlist.search.all.label",null,getLocale()):alphas[i];
				out.write("&nbsp;<a href=\"" + buildUrl((i==0)?"":alphaStr) + "\">" + alphaStr + "</a>&nbsp;|");
			}
			
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "FirstTag", ex);
		} 
		return EVAL_BODY_INCLUDE;
    }
	
	protected String buildUrl(String searchText) {
		return this.getUrl()
			+ "?start=0" 
			+ "&limit=" + getPage().getLimit()
			+ "&searchText=" + searchText
			+ "&sw=1"
			+ "&sn=1";
	}

}