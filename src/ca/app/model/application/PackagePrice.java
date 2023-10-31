package ca.app.model.application;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.app.model.common.CurrencyType;

@Entity
@Table(name="package_price")
public class PackagePrice implements Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@SequenceGenerator(name="seqPackagePrice", sequenceName="seq_package_price", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqPackagePrice")
	@Column(name="package_price_id", unique=true)
	private int packagePriceId;
	
	@Column(name="package_id")
	private int packageId;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="tax")
	private BigDecimal tax = new BigDecimal("0.00");
	
	@Column(name="currency_type_id")
	private int currencyTypeId = CurrencyType.CAD.getId();
	
	public int getPackagePriceId() {
		return packagePriceId;
	}
	public void setPackagePriceId(int packagePriceId) {
		this.packagePriceId = packagePriceId;
	}
	
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	
	public int getCurrencyTypeId() {
		return currencyTypeId;
	}
	public void setCurrencyTypeId(int currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}
	public CurrencyType getCurrencyType() {
		return CurrencyType.get(currencyTypeId);
	}
}