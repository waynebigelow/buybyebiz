package ca.app.util;

import java.net.URLDecoder;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.esapi.ESAPI;

import ca.app.model.user.AppUser;
import ca.app.service.common.TokenFieldType;

public class RequestUtil {
	public static String formatSecureUrl(HttpServletRequest request, String uri) {
		return "https://" + request.getServerName() + request.getContextPath() + uri;
	}
	
	public static String formatNonsecureUrl(HttpServletRequest request, String uri) {
		return "http://" + request.getServerName() + request.getContextPath() + uri;
	}
	
	public static Map<String,String> parseQueryStr(String queryStr) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		
		String[] params = queryStr.split("&");
		for (String param : params) {
			String[] tokens = param.split("=");
			map.put(tokens[0], URLDecoder.decode(tokens[1], "UTF-8"));
		}
		
		return map;
	}
	
	public static String getRequestParams(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		buff.append("REQUEST: \n");
		
		@SuppressWarnings("rawtypes")
		Enumeration enm = request.getParameterNames();
		while(enm.hasMoreElements()) {
			String name = (String)enm.nextElement();
			String value = request.getParameter(name);
			buff.append(" " + name + " = " + value + "\n");
		}
		
		return buff.toString();
	}

	public static String getRequestAttributes(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		
		@SuppressWarnings("rawtypes")
		Enumeration enm = request.getAttributeNames();
		while(enm.hasMoreElements()) {
			String name = (String)enm.nextElement();
			Object value = request.getAttribute(name);
			buff.append(" " + name + " = " + value.getClass().getName() + "\n");
		}
		
		return buff.toString();
	}

	public static String getRequestHeaders(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		buff.append("REQUEST HEADERS: \n");
		
		@SuppressWarnings("rawtypes")
		Enumeration enm = request.getHeaderNames();
		while(enm.hasMoreElements()) {
			String name = (String)enm.nextElement();
			
			buff.append(" NAME: " + name + "\n");
			
			@SuppressWarnings("rawtypes")
			Enumeration enm2 = request.getHeaders(name);
			while(enm2.hasMoreElements()) {
				buff.append("VALUE: " + enm2.nextElement() + "\n");
			}
		}
		
		return buff.toString();
	}

	public static String getRequestCookies(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		
		buff.append("REQUEST COOKIES: \n");
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			buff.append(getCookieString(cookie));
			buff.append("\n");
		}
		
		return buff.toString();
	}

	public static String getCookieString(Cookie cookie) {
		StringBuilder buff = new StringBuilder();
		buff.append("[Name:" + cookie.getName() + "]");
		buff.append("[Domain:" + cookie.getDomain() + "]");
		buff.append("[Path:" + cookie.getPath() + "]");
		buff.append("[MaxAge:" + cookie.getMaxAge() + "]");
		buff.append("[Value:" + cookie.getValue() + "]");
		return buff.toString();
	}

	public static String getSessionObjects(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		
		@SuppressWarnings("rawtypes")
		Enumeration enm = request.getSession().getAttributeNames();
		while(enm.hasMoreElements()) {
			String name = (String)enm.nextElement();
			Object value = request.getSession().getAttribute(name);
			buff.append(" " + name + " = " + value.getClass().getName() + "\n");
		}
		
		return buff.toString();
	}

	public static String getAccessLogStr(HttpServletRequest request) {
		Set<String> blackList = new HashSet<String>();
		blackList.add("password");
		blackList.add("old_password");
		blackList.add("new_password1");
		blackList.add("new_password2");
		blackList.add("trnCardNumber");
		blackList.add("trnCardCvd");
		
		HttpServletRequestWrapper wrapped = new HttpServletRequestWrapper(request);
		AppUser user = null;
		Object obj = wrapped.getUserPrincipal();
		if (obj instanceof AppUser) {
			user = (AppUser)obj;
		}
		
		StringBuilder buff = new StringBuilder();
		buff.append("ACCESS_LOG");
		buff.append("[USR=" + ((user==null)?"null":user.getEmail()) + "]");

		buff.append("[" + wrapped.getMethod() + "]");		
		buff.append("[REQ=" + wrapped.getRequestURL() + "]");
		
		buff.append("[PARAMS={");
		
		
		@SuppressWarnings("rawtypes")
		Enumeration enm = wrapped.getParameterNames();
		int cnt = 0;
		while(enm.hasMoreElements()) {
			String name = (String)enm.nextElement();
			String value = wrapped.getParameter(name);
			
			if (blackList.contains(name)) {
				buff.append(((cnt==0)?"":"&") + name + "=[PROTECTED]");
			} else {
				buff.append(((cnt==0)?"":"&") + name + "=" + value);
			}
			cnt++;
		}
		buff.append("}]");
		
		buff.append("[CHARSET=" + wrapped.getCharacterEncoding() + "]");
		buff.append("[LOCALE=" + wrapped.getLocale() + "]");
		buff.append("[REFERER=" + wrapped.getRemoteAddr() + "]");

		return buff.toString();
	}
	
	public static int getPrimaryId(String tokenValue, TokenFieldType tokenFieldType) {
		if (tokenValue == null) {
			return 0;
		}
		
		try {
			String[] decodedToken = getTokenArray(tokenValue);
			if (decodedToken == null || decodedToken.length != 2) {
				throw new Exception();
			}
			
			if (decodedToken[0].equals(tokenFieldType.getKey())) {
				return StringUtil.convertStringToInt(decodedToken[1], 0);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			//LogUtil.lo
		}
		
		return 0;
	}
	
	public static String getEmailAddress(String tokenValue, TokenFieldType tokenFieldType) {
		if (tokenValue == null) {
			return null;
		}
		
		try {
			String[] decodedToken = getTokenArray(tokenValue);
			if (decodedToken == null || decodedToken.length != 2) {
				throw new Exception();
			}
			
			if (decodedToken[0].equals(tokenFieldType.getKey())) {
				return decodedToken[1];
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			//LogUtil.lo
		}
		
		return null;
	}
	
	private static String[] getTokenArray(String token) {
		return HashUtil.getValueFromHash(token, ProjectUtil.getProperty("authentication.hash.key")).split(":");
	}
	
	public static String getToken(String name, int value) {
		String key = ProjectUtil.getProperty("authentication.hash.key");
		String toEncode = name + ":" + value;
		
		return HashUtil.getBase64Hash(toEncode, key, true);
	}
	
	public static String getRequestParamURL(TokenFieldType tokenFieldType, int primaryId) {
		return tokenFieldType.getAlias() + "=" + getToken(tokenFieldType.getKey(), primaryId);
	}
	
	public static String getRequestParamURL(TokenFieldType tokenFieldType, String email) {
		return tokenFieldType.getAlias() + "=" + getToken(tokenFieldType.getKey(), email);
	}
	
	public static String getToken(String name, String value) {
		String key = ProjectUtil.getProperty("authentication.hash.key");
		String toEncode = name + ":" + value;
		
		return HashUtil.getBase64Hash(toEncode, key, true);
	}
	
	public static String stripXSS(String value, String name) {
		if (value != null) {
			value = ESAPI.encoder().canonicalize(value);
			
			// Avoid null characters
			value = value.replaceAll("", "");
			
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		
		return value;
	}
	
	public static String doJsoup(String value, String name) {
		// We'll use Jsoup to remove any other html entities for most request parameters except a limited number
		// of comment/description fields, which will at the controller level be validated against a whitelist
		// of acceptable entities
		if (JsoupExclusions.get(name.toUpperCase()) == null) {
			value = StringUtil.permittedHtml(value);
		} else {
			value = StringUtil.prepMultilineTextForDB(value);
		}
		
		return value;
	}
	
	private enum JsoupExclusions {
		COMMENTTEXT;
		
		private static final Map<String, JsoupExclusions> lookup = new HashMap<String, JsoupExclusions>();
		static {
			for (JsoupExclusions currEnum : EnumSet.allOf(JsoupExclusions.class)) {
				lookup.put(currEnum.name(), currEnum);
			}
		}
		public static JsoupExclusions get(String paramName) {
			return lookup.get(paramName);
		}
	}
}