
package com.callenderbell.trails.utils;

import javax.servlet.http.Cookie;

public class TRSLongLivedCookie extends Cookie {

	private static final long serialVersionUID = 1L;
	private static final int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;

	public TRSLongLivedCookie(String name, String value) {
		super(name, value);

		setMaxAge(SECONDS_PER_YEAR);
		//setHttpOnly(true); TODO - have a look at this
		setPath("/");
		
		
	}
}