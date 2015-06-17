package com.callenderbell.trails.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

public class TRSUtils {
	
	public static byte[] base64ImageDecode(String base64) {
		return Base64.decodeBase64(base64);
    }
	
	public static long generateLongID() {
		return Math.abs( (new Random()).nextLong() );
	}
	
	public static String getCookieValue( Cookie[] cookies, String cookieName, String defaultValue ) {
		if ( cookies == null || cookies.length <= 0 )
			return defaultValue;
		
		for ( int i = 0; i < cookies.length; i++ ) {
			Cookie cookie = cookies[ i ];
			
			if ( cookieName.equals( cookie.getName() ) )
				return cookie.getValue();
		}

		return defaultValue;
	}
	
	public static String postDataToString( HttpServletRequest request ) throws IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;

		BufferedReader reader = request.getReader();
		
		while ((line = reader.readLine()) != null)
			jb.append(line);
		
		return jb.toString();
	}
	
	public static HashMap<String,String> getParametersMap(String data) throws UnsupportedEncodingException {
		if ( data == null || data.length() <= 0 )
			return new HashMap<String, String>();
		
		String[] d = data.split( "&" );
		HashMap<String, String> map = new HashMap<String, String>();
		
		for ( int i = 0; i < d.length; i++ ) {
			String[] param = d[ i ].split("=");
			
			if (param.length > 1)
				map.put(param[0], param[1]);
		}
		
		return map;
	}
	
}