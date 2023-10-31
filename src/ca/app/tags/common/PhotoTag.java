package ca.app.tags.common;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ca.app.model.photo.PhotoType;
import ca.app.util.LogUtil;
import ca.app.web.dto.listing.SiteDTO;
import ca.app.web.dto.photo.PhotoDTO;

public class PhotoTag extends TagSupport {
	private static final long serialVersionUID = -6467680521605739306L;
	
	private boolean editable = false;
	private SiteDTO site;
	private PhotoDTO photo;
	private String baseURL;
	private boolean gallery = false;
	private String clazz;

	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			
			if (editable) {
				out.write(buildEditableImage());
			} else {
				out.write(buildImage());
			}
		} catch (Exception ex) {
			LogUtil.logException(this.getClass(), "PhotoTag", ex);
		} 
		
		return EVAL_BODY_INCLUDE;
	}

	public String buildEditableImage() {
		StringBuilder editableImage = new StringBuilder();
		if (!editable) {
			String src = baseURL+"/"+site.getPhotoPath()+"/"+photo.getFileName();
			
			editableImage.append("<a href=\""+src+"\" title=\""+photo.getCaption()+"\">");
		} else {
			PhotoType photoType = photo.getPhotoType();
			String photoParams = 
					"'"+photo.getPhotoTypeId()+"'"+
					",'"+photo.getToken()+"'"+
					","+photoType.getViewPortWidth()+
					","+photoType.getViewPortHeight()+
					","+photoType.getBoundaryWidth()+
					","+photoType.getBoundaryHeight();
			
			editableImage.append("<a href=\"javascript:"+(photo.getPhotoId()>0?"edit":"add")+"Photo("+photoParams+")\" title=\""+photo.getCaption()+"\">");
		}
		editableImage.append(buildImage()); 
		editableImage.append("</a>");
		
		return editableImage.toString();
	}
	
	public String buildImage() {
		if (photo != null) {
			PhotoType photoType = photo.getPhotoType();
			
			String img = baseURL+"/"+(photo.getPhotoId()>0?site.getPhotoPath():site.getApplicationPath()+"/images")+"/"+photo.getFileName();
			String width = (photoType.getDisplayWidth().equals("")?"":"width=\""+photoType.getDisplayWidth()+"\" ");
			String height = (photoType.getDisplayHeight().equals("")?"":"height=\""+photoType.getDisplayHeight()+"\" ");
			String src = "src=\""+img+"\" ";
			clazz = (clazz==null||clazz.equals("")?"":"class=\""+clazz+"\" ");
			
			if (gallery) {
				String anchor ="<a href=\""+img+"\" title=\""+photo.getCaption()+"\">";
				return anchor+"<img "+width+height+src+clazz+"/></a>";
			} else {
				return "<img "+width+height+src+clazz+"/>";
			}
		}
		
		return "";
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
	}
	
	public void setPhoto(PhotoDTO photo) {
		this.photo = photo;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public void setGallery(boolean gallery) {
		this.gallery = gallery;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}