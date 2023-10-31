package ca.app.model.i18n;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="listing_locale")
public class ListingLocale implements Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@SequenceGenerator(name="seqListingLocale", sequenceName="seq_listing_locale", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqListingLocale")
	@Column(name="listing_locale_id", unique=true)
	private int listingLocaleId;
	
	@Column(name="listing_id")
	private int listingId;
	
	@Column(name="code")
	private String code;
	
	public int getListingLocaleId() {
		return listingLocaleId;
	}
	public void setListingLocaleId(int listingLocaleId) {
		this.listingLocaleId = listingLocaleId;
	}
	
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Locale getLocale() {
		if (this.code!=null) {
			String language = this.code;
			String country = "";
			int index = this.code.indexOf("_");
			if (index>0) {
				language = this.code.substring(0,index);
				country = this.code.substring(index+1);
			}
			return new Locale(language,country);
		}
		
		return Locale.getDefault();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + listingId;
		result = prime * result + listingLocaleId;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListingLocale other = (ListingLocale) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (listingId != other.listingId)
			return false;
		if (listingLocaleId != other.listingLocaleId)
			return false;
		return true;
	}
}