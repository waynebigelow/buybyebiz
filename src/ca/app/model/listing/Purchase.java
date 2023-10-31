package ca.app.model.listing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="purchase")
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="seqPurchase", sequenceName="seq_purchase", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqPurchase")
	@Column(name="purchase_id", unique=true)
	private int purchaseId;
	
	@Column(name="transaction_id")
	private String transactionId;
	
	@Column(name="description")
	private String description;
	
	@Column(name="price")
	private BigDecimal price = new BigDecimal("0.00");
	
	@Column(name="tax")
	private BigDecimal tax = new BigDecimal("0.00");
	
	@Column(name="tax_province")
	private String taxProvince;
	
	@Column(name="discount")
	private BigDecimal discount = new BigDecimal("0.00");
	
	@Column(name="total")
	private BigDecimal total = new BigDecimal("0.00");
	
	@Column(name="currency_type_id")
	private int currencyTypeId;
	
	@Column(name="purchaser_user_id")
	private int purchaserUserId;
	
	@Column(name="purchase_date")
	private Timestamp purchaseDate;

	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	public String getTaxProvince() {
		return taxProvince;
	}
	public void setTaxProvince(String taxProvince) {
		this.taxProvince = taxProvince;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public int getCurrencyTypeId() {
		return currencyTypeId;
	}
	public void setCurrencyTypeId(int currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}
	
	public int getPurchaserUserId() {
		return purchaserUserId;
	}
	public void setPurchaserUserId(int purchaserUserId) {
		this.purchaserUserId = purchaserUserId;
	}

	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}