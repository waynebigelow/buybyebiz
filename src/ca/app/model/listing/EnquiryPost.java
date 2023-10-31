package ca.app.model.listing;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="enquiry_post")
public class EnquiryPost implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqEnquiryPost", sequenceName="seq_enquiry_post", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqEnquiryPost")
	@Column(name="post_id", unique=true)
	private int postId;

	@Column(name="enquiry_map_id")
	private int enquiryMapId;

	@Column(name="comment")
	private String comment;
	
	@Column(name="post_date")
	private Timestamp postDate;

	@Column(name="author_id")
	private int authorId;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name="is_read")
	private boolean isRead = false;
	
	@Column(name="read_date")
	private Timestamp readDate;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
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