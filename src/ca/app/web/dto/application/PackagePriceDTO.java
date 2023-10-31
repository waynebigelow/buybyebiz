package ca.app.web.dto.application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;

import ca.app.model.application.PackagePrice;
import ca.app.model.common.CurrencyType;
import ca.app.util.CurrencyFormatUtil;

public class PackagePriceDTO implements Serializable {
	private static final long serialVersionUID = 2L;

	private int packagePriceId;
	private int packageId;
	private BigDecimal price;
	private BigDecimal tax;
	private int currencyTypeId;
	
	public PackagePriceDTO(PackagePrice packagePrice) {
		this.packagePriceId = packagePrice.getPackagePriceId();
		this.packageId = packagePrice.getPackageId();
		this.price = packagePrice.getPrice();
		this.tax = packagePrice.getTax();
		this.currencyTypeId = packagePrice.getCurrencyTypeId();
	}
	
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
	public String getCurrencyTypeShortName() {
		return getCurrencyType().getShortName();
	}
	
	public String getPriceFormatted() {
		Locale locale = Locale.getDefault();
		String priceFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(price, locale, getCurrencyType().getShortName());
		return priceFormatted;
	}
	
	public String getTotalPriceFormatted() {
		Locale locale = Locale.getDefault();
		
		Double total = price.doubleValue();
		total = total + (price.doubleValue() * new Double(tax.doubleValue()));
		BigDecimal totalPrice = new BigDecimal(total);
		
		String priceFormatted = CurrencyFormatUtil.formatMoneyWithCurrency(totalPrice, locale, getCurrencyType().getShortName());
		return priceFormatted;
	}
	
	public String getTaxFormatted() {
		return tax + "%";
	}
}