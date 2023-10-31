package ca.app.tags.paging;

import javax.servlet.jsp.JspWriter;

import ca.app.util.LogUtil;


public class GotoTag extends PagingBaseTag {

	private static final long serialVersionUID = -6467680521605739306L;

	private String pageLabel;
	private String ofLabel;
	
	public GotoTag() {
		super();
	}

	public int doStartTag() {
		if (getPage().getTotal()<=0) {
			return EVAL_BODY_INCLUDE;
		}
		try {
			JspWriter out = pageContext.getOut();
			
			String uid = "" + System.currentTimeMillis();
			
			out.write("<script>");
			out.write("function gotoPage" + uid + "() {");
			out.write("   var frm = document.getElementById('gotoForm" + uid + "');");
			out.write("   var newPage = parseInt(frm.page.value);");
			out.write("   if (isNaN(newPage) || newPage<1 || newPage>" + getPage().getLastPage() + ") {");
			out.write("      frm.page.value = '" + getPage().getCurrPage() + "';");
			out.write("      return;");
			out.write("   }");
			out.write("   frm.start.value = (frm.page.value-1)*" + getPage().getLimit() + ";");
			out.write("   frm.submit();");
			out.write("}");
			out.write("</script>");
			
			out.write("<form id=\"gotoForm" + uid + "\" action=\"" + getUrl() + "\" method=\"get\" style=\"display:inline;\" >");
			out.write("<input type=\"hidden\" id=\"start\" name=\"start\" value=\"" + getPage().getStart() + "\" />");
			out.write("<input type=\"hidden\" id=\"limit\" name=\"limit\" value=\"" + getPage().getLimit() + "\" />");
			out.write("<input type=\"hidden\" id=\"searchText\" name=\"searchText\" value=\"" + ((getPage().getSearchText()!=null)?getPage().getSearchText():"") + "\" />");
			
			out.write(getMessageSource().getMessage(pageLabel,null,getLocale()) + "&nbsp;");
			out.write("<input type=\"text\" ");
			out.write(" id=\"page\" name=\"page\" ");
			out.write(" value=\"" + getPage().getCurrPage() + "\" ");
			out.write(" style=\"width:35px;\" ");
			out.write(" onchange=\"gotoPage" + uid + "();\" ");
			out.write("/>");
			out.write("&nbsp;" + getMessageSource().getMessage(ofLabel,null,getLocale()) + "&nbsp;" + getPage().getLastPage());
			
			out.write("</form>");
			
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "IndexesTag", ex);
		} 
		return EVAL_BODY_INCLUDE;
    }
	
	
	public void setPageLabel(String pageLabel) {
		this.pageLabel = pageLabel;
	}
	public void setOfLabel(String ofLabel) {
		this.ofLabel = ofLabel;
	}
	

}