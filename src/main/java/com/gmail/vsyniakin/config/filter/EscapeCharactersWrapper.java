package com.gmail.vsyniakin.config.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class EscapeCharactersWrapper extends HttpServletRequestWrapper implements EscapeCharactersInterface{

	public EscapeCharactersWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		if (name.equals("encodeImage")) {
			return super.getParameter(name);
		}
		String [] values = getParameterValues(name);
		if (values != null) {
			return values[0];
		} 
		
		return super.getParameter(name);
	}
	
	@Override
	public String[] getParameterValues(String name) {
		if (name.equals("encodeImage")) {
			return super.getParameterValues(name);
		}
		String [] values = getParameterMap().get(name);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				values[i] = findAndReplaceSymbols(values[i]);
			}
			return values;
		}
		
		return super.getParameterValues(name);
	}

}
