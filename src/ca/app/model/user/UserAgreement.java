package ca.app.model.user;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.application.Application;

@Entity
@Table(name="user_agreement")
public class UserAgreement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqUserAgreement", sequenceName="seq_user_agreement", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqUserAgreement")
	@Column(name="user_agreement_id", unique=true)
	private int userAgreementId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="application_id")
	private int applicationId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="user_agreement_type_id")
	private int userAgreementTypeId;
	
	@Column(name="version")
	private int version;
	
	@Column(name="agreement_date")
	private Timestamp agreementDate;
	
	public UserAgreement() {}
	
	public UserAgreement(User user, Application application, UserAgreementType userAgreementType) {
		this.userId = user.getUserId();
		this.applicationId = application.getApplicationId();
		this.userAgreementTypeId = userAgreementType.getId();
		this.version = userAgreementType.getVersion();
		this.agreementDate = new Timestamp(System.currentTimeMillis());
	}

	public int getUserAgreementId() {
		return userAgreementId;
	}
	public void setUserAgreementId(int userAgreementId) {
		this.userAgreementId = userAgreementId;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getUserAgreementTypeId() {
		return userAgreementTypeId;
	}
	public void setUserAgreementTypeId(int userAgreementTypeId) {
		this.userAgreementTypeId = userAgreementTypeId;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public Timestamp getAgreementDate() {
		return agreementDate;
	}
	public void setAgreementDate(Timestamp agreementDate) {
		this.agreementDate = agreementDate;
	}
}