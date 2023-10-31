package ca.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import ca.app.util.RequestUtil;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = RequestUtil.doJsoup(values[i], parameter);
		}
		return encodedValues;
	}
	
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		
		return RequestUtil.doJsoup(value, parameter);
	}
	
	public String getHeader(String name) {
		String value = super.getHeader(name);
		
		return RequestUtil.doJsoup(value, name);
	}
}