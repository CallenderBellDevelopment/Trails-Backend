package com.callenderbell.trails.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.callenderbell.trails.json.JSONObject;

public class ResponseUtils {
	
	public static final String MIME_TEXT = "text/html";
	public static final String MIME_JSON = "application/json";
	public static final String MIME_IMAGE_JPEG = "image/jpeg";
	
	public static void sendOKJSONResponse( HttpServletResponse out, JSONObject json ) {
		try {
			out.setStatus(HttpServletResponse.SC_OK);
			out.setHeader("Cache-Control", "max-age=604800, public");
			out.setContentType(MIME_JSON);
			out.setCharacterEncoding( "UTF-8" );
			out.getWriter().print(json);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void sendOKJSONResponse( HttpServletResponse out, String json ) {
		try {
			out.setStatus(HttpServletResponse.SC_OK);
			out.setHeader("Cache-Control", "max-age=604800, public");
			out.setContentType(MIME_JSON);
			out.setCharacterEncoding( "UTF-8" );
			out.getWriter().print(json);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void sendOKHtmlResponse( HttpServletResponse out, String html ) {
		try {
			out.setStatus(HttpServletResponse.SC_OK);
			out.setContentType( MIME_TEXT );
			out.setCharacterEncoding( "UTF-8" );
			out.setContentLength( (html != null ? html.getBytes().length : 0) );
			
			ServletOutputStream outputStream = out.getOutputStream();
		    outputStream.write( html.getBytes() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void sendOKByteResponse( HttpServletResponse out, byte[] contents ) {
		try {
			if (contents == null)
				contents = new byte[0];
			
			out.setStatus(HttpServletResponse.SC_OK);
			out.setHeader("Cache-Control", "max-age=604800, public");
			out.setContentType( MIME_IMAGE_JPEG );
			out.setContentLength( (contents != null ? contents.length : 0) );
			
			ServletOutputStream outputStream = out.getOutputStream();
		    outputStream.write( contents );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void sendErrorJSONResponse( HttpServletResponse out, JSONObject json, int statusCode ) {
		try {
			out.setStatus(statusCode);
			out.setContentType(MIME_JSON);
			out.setCharacterEncoding( "UTF-8" );
			
			if (statusCode == HttpServletResponse.SC_UNAUTHORIZED)
				out.setHeader("WWW-Authenticate", "OAuth realm=\"users\"");
			
			if (json != null)
				out.getWriter().print(json);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void sendErrorHtmlResponse( HttpServletResponse out,String contents, int statusCode ) {
		try {
			out.setStatus(statusCode);
			out.setContentType( MIME_TEXT );
			out.setCharacterEncoding( "UTF-8" );
			
			if (statusCode == HttpServletResponse.SC_UNAUTHORIZED)
				out.setHeader("WWW-Authenticate", "OAuth realm=\"users\"");
			
			if (contents != null) {
				out.setContentLength( (contents != null ? contents.getBytes().length : 0) );
				
				ServletOutputStream outputStream = out.getOutputStream();
			    outputStream.write( contents.getBytes() );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}