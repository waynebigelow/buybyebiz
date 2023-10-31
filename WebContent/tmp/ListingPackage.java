package ca.app.model.listing;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.application.ApplicationPackage;

@Entity
@Table(name="listing_package")
public class ListingPackage implements Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@SequenceGenerator(name="seqListingPackage", sequenceName="seq_listing_package", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqListingPackage")
	@Column(name="package_id", unique=true)
	private int packageId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="app_package_id")
	private ApplicationPackage applicationPackage;
	
	@Column(name="pp_transaction_id")
	private String payPalTransactionId;
	
	@Column(name="listing_id")
	private String listingId;
	
	@Column(name="user_id")
	private String userId;
}