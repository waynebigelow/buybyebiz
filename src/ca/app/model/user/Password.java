package ca.app.model.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="password")
public class Password {
	
	@Id
	@SequenceGenerator(name="seqPassword", sequenceName="seq_password", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqPassword")
	@Column(name="password_id", unique=true)
	private int passwordId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="hash")
	private String hash;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	public int getPasswordId() {
		return passwordId;
	}
	public void setPasswordId(int passwordId) {
		this.passwordId = passwordId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Timestamp getChangedDate() {
		return changedDate;
	}
	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}
}