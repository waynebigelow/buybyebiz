package ca.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;

public class CurrencyFormatUtil {

	public static void main(String[] args) {
		/**
		 * Run this method to see examples of how prices will be displayed for all permutations of 
		 * user locales and currencies.
		 */
		
		BigDecimal price = new BigDecimal("9999.99");
		
		String[] regions = new String[]{"en_US","en_CA","fr_CA","en_GB"};
		String[] currencies = new String[]{"USD","CAD","GBP"};
		
		for (String region : regions) {
			Locale userLocale = LocaleUtils.toLocale(region);
			for (String currency : currencies) {
				System.out.println("[User locale=" + region + "][Currency=" + currency + "]: " + formatMoneyWithCurrency(price, userLocale, currency));
			}
			System.out.println("");
		}
	}
	
	public static String formatMoney(BigDecimal value) {
		DecimalFormat df = new DecimalFormat("0.00");
		value.setScale(2, RoundingMode.HALF_EVEN);
		return df.format(value.doubleValue());
	}
	
	public static String formatMoneyWithCurrency(BigDecimal value, Locale userLocale, String currencyCode) {
		// A little hack so that we don't need to change server and DB locales from en_US to en_CA in 
		// order to properly format currencies for Canadians.
		if ("CAD".equals(currencyCode) && "en_US".equals(userLocale.toString())) {
			userLocale = LocaleUtils.toLocale("en_CA");
		} else if ("USD".equals(currencyCode) && "en_CA".equals(userLocale.toString())) {
			userLocale = LocaleUtils.toLocale("en_US");
		}
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance(userLocale);
		formatter.setCurrency(Currency.getInstance(currencyCode));
		String formattedValue = formatter.format(value.doubleValue());
		return formattedValue;
	}
}