package ca.app.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.w3c.tidy.Tidy;

public class StringUtil {
	public static String convertJSEscape(String text) {
		if (text == null)
			return "";
		
		char[] originalText = text.trim().toCharArray();
		StringBuffer buffer = new StringBuffer();
		int charCounter = originalText.length;

		for(int i = 0; i < charCounter; i++) {
			String acceptedChar = null;
			char character = originalText[i];
				
			if (character == '"')
				acceptedChar = "'";
			else
				acceptedChar = String.valueOf(character);
				
			if (acceptedChar != null)	
				buffer.append(acceptedChar);
		}
		
		String s = buffer.toString();
		s = s.replaceAll("\n","<br/>");
		s = s.replaceAll("\r","");
		return s;
	}
	
	public static String getString(String value, String defaultValue) {
		return (value != null) ? value : defaultValue;
	}
	
	public static int convertStringToInt(String value, int defaultValue) {
		int convertedValue = defaultValue;
		
		if(value != null) {
			try {
				convertedValue = Integer.valueOf(value);
			} catch(NumberFormatException e) {
				
			}
		}
		
		return convertedValue;
	}
	
	public static int[] convertDelimittedStringToIntArray(String value, String delimitter) {
		if (value != null) {
			String[] strArray = value.split(delimitter);
			int[] intArray = new int[strArray.length];
			for (int i=0; i < strArray.length; i++) {
				int intValue = Integer.parseInt(strArray[i]);
				intArray[i] = intValue;
			}
			
			return intArray;
		}
		
		return new int[]{};
	}
	
	public static Integer[] convertDelimittedStringToIntegerArray(String value, String delimitter) {
		int[] intArray = convertDelimittedStringToIntArray(value, delimitter);
		Integer[] result = new Integer[intArray.length];
		for (int i=0; i < intArray.length; i++) {
			result[i] = Integer.valueOf(intArray[i]);
		}
		
		return result;
	}
	
	public static long convertStringToLong(String value, long defaultValue) {
		long convertedValue = defaultValue;
		
		if(value != null) {
			try {
				convertedValue = Long.valueOf(value);
			} catch(NumberFormatException e) {
				
			}
		}
		
		return convertedValue;
	}
	
	public static boolean convertStringToBoolean(String value, boolean defaultValue) {
		boolean convertedValue = defaultValue;
		
		if(value != null) {
			if (value.equalsIgnoreCase("on") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("y")) {
				convertedValue = true;
			} else if (value.equalsIgnoreCase("off") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0") || value.equalsIgnoreCase("n")) {
				convertedValue = false;
			}
		}
		
		return convertedValue;
	}
	
	public static float convertStringToFloat(String value, float defaultValue) {
		float convertedValue = defaultValue;
		
		if(value != null) {
			try {
				convertedValue = Float.valueOf(value);
			} catch(NumberFormatException e) {
			
			}
		}
		
		return convertedValue;
	}
	
	public static Whitelist getWhitelist(String ...exceptions) {
		Whitelist whitelist = Whitelist.none();
		for (String tag : exceptions) {
			whitelist.addTags(tag);
		}
		
		return whitelist;
	}
	
	public static String noHtml(String value) {
		if (value == null) {
			return value;
		}
		
		return Jsoup.clean(value, Whitelist.none());
	}

	public static String prepMultilineTextForDB(String value) {
		if (value == null) {
			return value;
		}
		
		value = nl2br(value);
		value = Jsoup.clean(value, getWhitelist("br"));
		
		return value;
	}
	
	public static String prepMultilineTextForHtml(String value) {
		if (value == null) {
			return "";
		}
		
		value = Jsoup.clean(value, getWhitelist("br", "p"));
		
		return value;
	}
	
	public static String prepMultilineTextForEdit(String value, boolean escapeHtml, boolean escapeJavaScript) {
		if (value == null) {
			return "";
		}
		
		value = Jsoup.clean(value, getWhitelist("br", "p"));
		value = br2nl(value);
		
		if (escapeHtml) value = StringEscapeUtils.escapeHtml(value);
		if (escapeJavaScript) value = StringEscapeUtils.escapeJavaScript(value);
		
		return value;
	}
	
	public static String permittedHtml(String value) {
		if (value == null) {
			return value;
		}
		
		value = Jsoup.clean(value, JsoupWhiteListUtil.getWhitelist());
		value = resetAmpersand(value);
		
		return value;
	}
	
	public static String resetAmpersand(String text) {
		return text.replaceAll("&amp;", "&");
	}
	
	public static String nl2br(String text) {
		return text.replaceAll("\n", "<br>");
	}
	
	public static String br2nl(String html) {
		Document document = Jsoup.parseBodyFragment(html);
		document.select("br").append("\\n");
		document.select("p").prepend("\\n\\n");
		
		return document.text().replaceAll("\\\\n", "\n");
	}

	public static boolean isInteger(String i){
		try {
			Integer.parseInt(i);
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	public static boolean isLong(String l){
		try {
			Long.parseLong(l);
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	public static String convertHtmlToXhtml(String html) {
		StringReader reader = new StringReader(html);
		StringWriter writer = new StringWriter(html.length());
		
		Tidy tidy = new Tidy();
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		tidy.setXHTML(true);
		tidy.setTrimEmptyElements(false);
		
		tidy.parse(reader, writer);
		String xhtml = writer.toString();

		return xhtml;
	}
	
	public static String truncate(final String str, final int length) {
		String out;
		if (length < 0) {
			return "";
		}
		if (str.length() <= length) {
			out = str;
		} else {
			out = str.substring(0, length);
		}
		
		return out;
	}

	public static String ellipsis(String input, int maxLength) {
		StringBuilder output = new StringBuilder(StringUtil.truncate(input, maxLength));
		if (input.length() > maxLength) {
			output.append("&hellip;");
		}
		
		return output.toString();
	}

	public static String getStacktrace(StackTraceElement[] stack, boolean html) {
		StringBuilder sb = new StringBuilder();
		for(StackTraceElement el: stack) {
			sb.append(el.toString());
			sb.append(html?"<br/>":System.getProperty("line.separator"));
		}
		
		return sb.toString();
	}

	public static String getCommaSeparatedString(Collection<String> c) {
		StringBuilder output = new StringBuilder();
		if(c != null) {
			Iterator<String> i = c.iterator();
			while(i.hasNext()) {
				output.append(i.next());
				if(i.hasNext()) {
					output.append(",");
				}
			}
		}
		
		return output.toString();
	}
}