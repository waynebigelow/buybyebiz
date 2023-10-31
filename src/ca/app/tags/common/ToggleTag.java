package ca.app.tags.common;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ca.app.util.LogUtil;

public class ToggleTag extends TagSupport {
	private static final long serialVersionUID = -6467680521605739306L;
	
	private boolean enabled;
	private boolean includeLabel = false;
	
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			
			if (enabled) {
				out.write("<span class=\"glyphicon glyphicon-thumbs-"+(includeLabel?"down":"up")+"\"></span>"+(includeLabel?" Disable":""));
			} else {
				out.write("<span class=\"glyphicon glyphicon-thumbs-"+(includeLabel?"up":"down")+"\"></span>"+(includeLabel?" Enable":""));
			}
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "ToggleTag", ex);
		} 
		
		return EVAL_BODY_INCLUDE;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setIncludeLabel(boolean includeLabel) {
		this.includeLabel = includeLabel;
	}
}