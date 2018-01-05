package com.br4dev.imob.rest.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ClienteHttpUtil {

	public static Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

		Map<String, String> result = new HashMap<>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			result.put(key, value);
		}
		result.put("referer", request.getHeader("referer"));
		result.put("cf-ipcountry", request.getHeader("cf-ipcountry"));
		result.put("cf-ray", request.getHeader("cf-ray"));
		result.put("x-forwarded-proto", request.getHeader("x-forwarded-proto"));
		result.put("x-forwarded-for", request.getHeader("x-forwarded-for"));
		result.put("x-real-ip", request.getHeader("x-real-ip"));
		result.put("x-forwarded-serve", request.getHeader("x-forwarded-serve"));
		result.put("cf-visitor", request.getHeader("cf-visitor"));
		result.put("host", request.getHeader("host"));
		result.put("upgrade-insecure-requests", request.getHeader("upgrade-insecure-requests"));
		result.put("connection", request.getHeader("connection"));
		result.put("cf-connecting-ip", request.getHeader("cf-connecting-ip"));
		result.put("accept-encoding", request.getHeader("accept-encoding"));
		result.put("user-agent", request.getHeader("user-agent"));
		return result;
	}

	public static void print(Map<String, String> result) {

		if (result != null) {
			Set<String> key = result.keySet();
			for (String k : key) {
				String value = result.get(k);
				System.out.println("Chave: " + k + " Valor: " + value);
			}
		}
	}
}
