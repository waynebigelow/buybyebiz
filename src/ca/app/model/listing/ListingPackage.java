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
	@Column(name="listing_package_id", unique=true)
	private int listingPackageId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="purchase_id", nullable=true)
	private Purchase purchase;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="package_id")
	private ApplicationPackage applicationPackage;

	public int getListingPackageId() {
		return listingPackageId;
	}
	public void setListingPackageId(int listingPackageId) {
		this.listingPackageId = listingPackageId;
	}

	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	public ApplicationPackage getApplicationPackage() {
		return applicationPackage;
	}
	public void setApplicationPackage(ApplicationPackage applicationPackage) {
		this.applicationPackage = applicationPackage;
	}
}