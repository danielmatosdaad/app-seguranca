package com.br4dev.imob.rest.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class ClientIpAddress {

	private static Pattern PRIVATE_ADDRESS_PATTERN = Pattern.compile(
			"(^127\\.)|(^192\\.168\\.)|(^10\\.)|(^172\\.1[6-9]\\.)|(^172\\.2[0-9]\\.)|(^172\\.3[0-1]\\.)|(^::1$)|(^[fF][cCdD])",
			Pattern.CANON_EQ);

	private static boolean isPrivateOrLocalAddress(String address) {
		Matcher regexMatcher = PRIVATE_ADDRESS_PATTERN.matcher(address);
		return regexMatcher.matches();
	}

	public static String getFrom(HttpServletRequest request, boolean filterPrivateAddresses) {
		String ip = request.getRemoteAddr();
		String getWay = request.getHeader("VIA");
		String headerClientIp = request.getHeader("Client-IP");
		String headerXForwardedFor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isEmpty(ip) && StringUtils.isNotEmpty(headerClientIp)) {
			ip = headerClientIp;
		} else if (StringUtils.isNotEmpty(headerXForwardedFor)) {
			ip = headerXForwardedFor;
		}

		System.out.println("GetWay: " + getWay);
		System.out.println("Client-IP: " + headerClientIp);
		System.out.println("X-Forwarded-For: " + headerXForwardedFor);
		System.out.println("Ip: " + ip);
		if (filterPrivateAddresses && isPrivateOrLocalAddress(ip)) {
			return StringUtils.EMPTY;
		} else {
			return ip;
		}
	}
}
