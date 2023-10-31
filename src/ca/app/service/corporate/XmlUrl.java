package ca.app.service.corporate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

@XmlAccessorType(value = XmlAccessType.NONE)
@XmlRootElement(name = "url")
public class XmlUrl {
	public enum Priority {
		HIGH("1.0"), MEDIUM("0.7"), LOW("0.3");
	
		private String value;
	
		Priority(String value) {
			this.value = value;
		}
	
		public String getValue() {
			return value;
		}
	}
	
	public enum Frequency {
		YEARLY("yearly"), MONTHLY("monthly"), WEEKLY("weekly"), DAILY("daily");
	
		private String value;
	
		Frequency(String value) {
			this.value = value;
		}
	
		public String getValue() {
			return value;
		}
	}
	
	@XmlElement
	private String loc;
	
	@XmlElement
	private String lastmod = new DateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
	
	@XmlElement
	private String changefreq = Frequency.DAILY.getValue();
	
	@XmlElement
	private String priority;
	
	public XmlUrl() {}

	public XmlUrl(String loc, String lastmod, Priority priority, Frequency changefreq) {
		this.loc = loc;
		this.lastmod = lastmod;
		this.priority = priority.getValue();
		this.changefreq = changefreq.getValue();
	}
	
	public String getLoc() {
		return loc;
	}
	
	public String getLastmod() {
		return lastmod;
	}

	public String getPriority() {
		return priority;
	}

	public String getFrequency() {
		return changefreq;
	}
}