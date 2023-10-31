package ca.app.web.dto.listing;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.listing.EnquiryPost;
import ca.app.service.common.TokenFieldType;
import ca.app.util.DateUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;

public class EnquiryPostDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private int postId;
	private int enquiryMapId;
	private String comment;
	private Timestamp postDate;
	private int authorId;
	private String ipAddress;
	private boolean isRead = false;
	private Timestamp readDate;

	public EnquiryPostDTO(EnquiryPost enquiryPost) {
		this.postId = enquiryPost.getPostId();
		this.enquiryMapId = enquiryPost.getEnquiryMapId();
		this.comment = enquiryPost.getComment();
		this.postDate = enquiryPost.getPostDate();
		this.authorId = enquiryPost.getAuthorId();
		this.ipAddress = enquiryPost.getIpAddress();
		this.isRead = enquiryPost.isRead();
		this.readDate = enquiryPost.getReadDate();
	}
	
	@JsonIgnore
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.ENQUIRY_POST.getKey(), postId);
	}
	
	public int getEnquiryMapId() {
		return enquiryMapId;
	}
	public void setEnquiryMapId(int enquiryMapId) {
		this.enquiryMapId = enquiryMapId;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCommentFormatted() {
		return DateUtil.getStringDate(postDate.getTime(), ProjectUtil.getProperty("standard.date.format")) + ": " + comment;
	}
	
	public Timestamp getPostDate() {
		return postDate;
	}
	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public Timestamp getReadDate() {
		return readDate;
	}
	public void setReadDate(Timestamp readDate) {
		this.readDate = readDate;
	}
}