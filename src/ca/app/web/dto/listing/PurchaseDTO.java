package ca.app.web.dto.listing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;

import ca.app.model.common.CurrencyType;
import ca.app.model.listing.Purchase;
import ca.app.service.common.TokenFieldType;
import ca.app.util.CurrencyFormatUtil;
import ca.app.util.DateUtil;
import ca.app.util.ProjectUtil;
import ca.app.util.RequestUtil;

public class PurchaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int purchaseId;
	private String transactionId;
	private String description;
	private BigDecimal price = new BigDecimal("0.00");
	private BigDecimal tax = new BigDecimal("0.00");
	private String taxProvince;
	private BigDecimal discount = new BigDecimal("0.00");
	private BigDecimal total = new BigDecimal("0.00");
	private int currencyTypeId;
	private int purchaserUserId;
	private Timestamp purchaseDate;

	public PurchaseDTO(Purchase purchase) {
		this.purchaseId = purchase.getPurchaseId();
		this.transactionId = purchase.getTransactionId();
		this.description = purchase.getDescription();
		this.price = purchase.getPrice();
		this.tax = purchase.getTax();
		this.taxProvince = purchase.getTaxProvince();
		this.discount = purchase.getDiscount();
		this.total = purchase.getTotal();
		this.currencyTypeId = purchase.getCurrencyTypeId();
		this.purchaserUserId = purchase.getPurchaserUserId();
		this.purchaseDate = purchase.getPurchaseDate();
	}
	
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getToken() {
		return RequestUtil.getToken(TokenFieldType.PURCHASE.getKey(), purchaseId);
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
	
	public CurrencyType getCurrencyType() {
		return CurrencyType.get(currencyTypeId);
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
	
	public String getPurchaseDateFormatted() {
		if(purchaseDate == null) {
			return "";
		}
		String parsedDate = DateUtil.getStringDate(purchaseDate.getTime(), ProjectUtil.getProperty("standard.date.format"));
		return parsedDate;
	}
	
	public String getPriceFormatted() {
		Locale locale = Locale.getDefault();
		
		String priceFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(price, locale, getCurrencyType().getShortName());
		return priceFormatted;
	}
	
	public String getTotalPriceFormatted() {
		Locale locale = Locale.getDefault();
		
		String priceFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(total, locale, getCurrencyType().getShortName());
		return priceFormatted;
	}
	
	public String getTaxFormatted() {
		Locale locale = Locale.getDefault();
		
		Double total = price.doubleValue();
		total = price.doubleValue() * (tax.doubleValue()/100);
		BigDecimal totalPrice = new BigDecimal(total);
		
		String priceFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(totalPrice, locale, getCurrencyType().getShortName());
		return priceFormatted + " (" + tax + "%)";
	}
}