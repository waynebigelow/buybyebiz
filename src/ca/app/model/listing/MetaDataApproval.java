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
@Table(name="meta_data_approval")
public class MetaDataApproval implements Serializable {
	private static final long serialVersionUID = 6369716355745105638L;

	@Id
	@SequenceGenerator(name="seqMetaDataApproval", sequenceName="seq_meta_data_approval", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqMetaDataApproval")
	@Column(name="meta_data_id", unique=true)
	private int metaDataId;

	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="type_id")
	private int typeId;
	
	@Column(name="status_id")
	private int statusId;
	
	@Column(name="value")
	private String value;
	
	@Column(name="submit_date")
	private Timestamp submitDate;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="rejected_date")
	private Timestamp rejectedDate;
	
	public int getMetaDataId() {
		return metaDataId;
	}
	public void setMetaDataId(int metaDataId) {
		this.metaDataId = metaDataId;
	}

	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public MetaDataType getMetaDataType() {
		return MetaDataType.get(typeId);
	}
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Timestamp getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Timestamp getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(Timestamp rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
}