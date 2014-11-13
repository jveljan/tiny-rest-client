package com.jveljan.tools.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

//TODO: more features, constants for common headers
public class HttpHeaders {
	private Map<String, String> headers = new HashMap<String, String>();
	public String set(String key, String value) {
		return headers.put(key, value);
	}
	
	public Set<Entry<String, String>> entrySet() {
		return headers.entrySet();
	}
}
