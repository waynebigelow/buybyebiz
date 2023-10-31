package ca.app.web.dto.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.app.model.common.Address;
import ca.app.model.common.CountryType;

public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 5408194204048154990L;
	
	private int addressId;
	private int typeId;
	private String address1;
	private String address2;
	private String city;
	private String country;
	private String province;
	private String postalCode;
	
	public AddressDTO() {}
	
	public AddressDTO(Address address) {
		this.addressId = address.getAddressId();
		this.typeId = address.getTypeId();
		this.address1 = address.getAddress1();
		this.address2 = address.getAddress2();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.province = address.getProvince();
		this.postalCode = address.getPostalCode();
	}
	
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
	public String getCountryCode() {
		CountryType type = CountryType.get(country);
		if (type != null) {
			return type.getShortName();
		}
		return "";
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
	
	public boolean isAddressEmpty() {
		if(getAddress1().length() > 0){
			return false;
		}
		if(getAddress2().length() > 0){
			return false;
		}
		if(getCity().length() > 0){
			return false;
		}
		if(getProvince().length() > 0){
			return false;
		}
		if(getCountry().length() > 0){
			return false;
		}
		if(getPostalCode().length() > 0){
			return false;
		}
		return true;
	}
	
	@JsonIgnore
	public String getFormattedString() {
		return getFormattedString(false);
	}
	
	@JsonIgnore
	public String getFormattedStringNoCountry() {
		return getFormattedString(true);
	}
	
	@JsonIgnore
	public String getFormattedString(boolean suppressCountry) {
		StringBuilder addrFormatted = new StringBuilder();
		
		addrFormatted.append(getAddress1());
		
		if(getAddress2().length() > 0) {
			addrFormatted.append((addrFormatted.toString().equals("") ? "" : ", ") + getAddress2());
		}
		
		if(getCity().length() > 0) {
			addrFormatted.append((addrFormatted.toString().equals("") ? "" : ", ") + getCity());
		}
		
		if(getProvince().length() > 0) {
			addrFormatted.append((addrFormatted.toString().equals("") ? "" : ", ") + getProvince());
		}
		
		if(!suppressCountry && getCountry().length() > 0) {
			addrFormatted.append((addrFormatted.toString().equals("") ? "" : ", ") + CountryType.getByShortName(country).getLongName());
		}
		
		if(getPostalCode().length() > 0) {
			addrFormatted.append((addrFormatted.toString().equals("") ? "" : ", ") + getPostalCode());
		}
		
		if (addrFormatted.toString().equals("")) {
			addrFormatted.append("North America");
		}
		
		return addrFormatted.toString();
	}
	
	@JsonIgnore
	public String getPrintFormattedString() {
		return getPrintFormattedString(false);
	}
	
	@JsonIgnore
	public String getPrintFormattedStringNoCountry() {
		return getPrintFormattedString(true);
	}
	
	@JsonIgnore
	public String getPrintFormattedString(boolean suppressCountry) {
		StringBuilder addrFormatted = new StringBuilder();
		
		addrFormatted.append(getAddress1());
		
		if(getAddress2() != null && getAddress2().length() > 0){
			addrFormatted.append(", " + getAddress2());
		}
		
		if(getCity() != null && getCity().length() > 0){
			addrFormatted.append(", " + getCity());
		}
		
		if(getProvince() != null && getProvince().length() > 0){
			addrFormatted.append(", " + getProvince());
		}
		
		if(!suppressCountry && getCountry() != null && getCountry().length() > 0){
			addrFormatted.append(", " + getCountry());
		}
		
		if(getPostalCode() != null && getPostalCode().length() > 0){
			addrFormatted.append(", " + getPostalCode());
		}
		
		return addrFormatted.toString();
	}
	
	public int getZoomForAddress() {
		if (!address1.equals("") && !city.equals("") && !province.equals("")) {
			return 15;
		} else if (address1.equals("") && !city.equals("") && !province.equals("")) {
			return 11;
		} else if (!postalCode.equals("")) {
			return 11;
		}
		
		return 4;
	}
}