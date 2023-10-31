package ca.app.model.common;

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
@Table(name="support_issue")
public class SupportIssue implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqSupportIssue", sequenceName="seq_support_issue", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqSupportIssue")
	@Column(name="issue_id", unique=true)
	private int issueId;
	
	@Column(name="application_id")
	private int applicationId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="summary")
	private String summary;
	
	@Column(name="description")
	private String description;
	
	@Column(name="post_date")
	private Timestamp postDate;
	
	@Column(name="is_read")
	private boolean isRead = false;
	
	@Column(name="read_date")
	private Timestamp readDate;

	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getPostDate() {
		return postDate;
	}
	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
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