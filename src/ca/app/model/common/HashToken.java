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
@Table(name="hash_token")
public class HashToken implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqHashToken", sequenceName="seq_hashtoken", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqHashToken")
	@Column(name="token_id", unique=true)
	private int tokenId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="type_id")
	private int typeId;
	
	@Column(name="hash")
	private String hash;
	
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="expired_date")
	private Timestamp expiredDate;

	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}
}