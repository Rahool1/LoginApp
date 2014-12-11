package com.example.background;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestPackage {
	private String uri;
	private String method = "GET";
	private String sessionid = "";
	private String br_request_type = "";
	
	
	public String getBr_request_type() {
		return br_request_type;
	}
	public void setBr_request_type(String br_request_type) {
		this.br_request_type = br_request_type;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	private Map<String, String> params = new HashMap<String, String>();
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void setParam(String key, String value) {
		params.put(key, value);
	}
	
	public String getParamValue(String key){
		String value =params.get(key);
		return value;
		}
	
	public String getEncodedParams() {
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			String value = null;
			try {
				value = URLEncoder.encode(params.get(key), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key + "=" + value);
		}
		return sb.toString();
	}

}
