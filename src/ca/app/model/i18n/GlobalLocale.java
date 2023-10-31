package ca.app.model.i18n;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="global_locale")
public class GlobalLocale implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="code")
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDisplayLanguage(Locale locale) {
		return getLocale().getDisplayLanguage(locale);
	}
	
	public Locale getLocale() {
		if (this.code != null) {
			String language = this.code;
			String country = "";
			int index = this.code.indexOf("_");
			if (index > 0) {
				language = this.code.substring(0, index);
				country = this.code.substring(index + 1);
			}
			return new Locale(language, country);
		}
		
		return Locale.getDefault();
	}
	
	public void setLocale(Locale locale) {
		this.code = locale.getDisplayName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		GlobalLocale other = (GlobalLocale) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}