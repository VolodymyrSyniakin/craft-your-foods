package com.gmail.vsyniakin.config.filter;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public interface EscapeCharactersInterface {
	public static final Map<String, String> REPLACE = initReplace();
	public static final Set<String> KEYS = initSymbols();
	
	static TreeMap<String,String> initReplace (){
		TreeMap <String, String> map = new TreeMap<>();
		map.put(">", "&gt;");
		map.put("<", "&lt;");
		map.put("&", "&amp;");
		map.put("\"", "&#34;");
		map.put("'", "&#x27;");
		map.put("/", "&#x2F;");
		
		return map;
	}
	static TreeSet<String> initSymbols (){
		return new TreeSet<String>(REPLACE.keySet());
	}
	
	default String replaceSymbol (String str, String symbol) {
		if (symbol.equals("&")) {
			return str.replaceAll("&(?!gt|lt|amp|#34|#x27|#x2F)", REPLACE.get(symbol));
		}
		return str.replaceAll(symbol, REPLACE.get(symbol));
	}
	
	default String findAndReplaceSymbols (String str) {
		if (str != null) {
			for (String symbol : KEYS) {
				if (str.contains(symbol)){
					str = replaceSymbol(str, symbol);
				}
			}
		}
		return str;
	}
	
	
}
