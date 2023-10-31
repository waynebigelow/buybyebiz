package ca.app.model.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="provider_package_link")
public class ProviderPackageLink implements Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@SequenceGenerator(name="seqProviderPackageLink", sequenceName="seq_provider_package_link", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqProviderPackageLink")
	@Column(name="link_id", unique=true)
	private int linkId;
	
	@Column(name="provider_id")
	private int providerId = Provider.PAYPAL.getProviderId();
	
	@Column(name="link_value")
	private String linkValue;
	
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	
	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	
	public String getLinkValue() {
		return linkValue;
	}
	public void setLinkValue(String linkValue) {
		this.linkValue = linkValue;
	}
}