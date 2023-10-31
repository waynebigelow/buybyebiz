package ca.app.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address implements Serializable {
	private static final long serialVersionUID = 5408194204048154990L;
	
	@Id
	@SequenceGenerator(name="seqAddress", sequenceName="seq_address", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqAddress")
	@Column(name="address_id", unique=true)
	private int addressId;

	@Column(name="type_id")
	private int typeId;

	@Column(name="address1")
	private String address1;

	@Column(name="address2")
	private String address2;

	@Column(name="city")
	private String city;

	@Column(name="country")
	private String country;

	@Column(name="province")
	private String province;
	
	@Column(name="postal_code")
	private String postalCode;
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getAddress1() {
		if (address1 == null) {
			address1 = "";
		}
		return address1;
	}	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		if (address2 == null) {
			address2 = "";
		}
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getCity() {
		if (city == null) {
			city = "";
		}
		return city;
	}	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		if (country == null) {
			country = "";
		}
		return country;
	}	
	public void setCountry(String country) {
		this.country = country;
	}
	public CountryType getCountryType() {
		return CountryType.getByShortName(country);
	}
	
	public String getProvince() {
		if (province == null) {
			province = "";
		}
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getPostalCode() {
		if (postalCode == null) {
			postalCode = "";
		}
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}