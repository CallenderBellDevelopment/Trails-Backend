package com.callenderbell.trails.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.callenderbell.trails.constants.*;
import com.callenderbell.trails.json.JSONArray;
import com.callenderbell.trails.json.JSONException;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.utils.ResponseUtils;

public abstract class TRSAbstractController extends AbstractController {
	
	public ModelAndView generateOKByteResponse( HttpServletResponse response, byte[] contents ) {
    	if (contents == null)
    		return generateErrorJSONResponseNotFound(response);
    	
		ResponseUtils.sendOKByteResponse( response, contents );
    	
    	return null;
    }
	
	// API specific methods.
    public ModelAndView generateOKJSONResponse( HttpServletResponse response, JSONObject json ) {
    	ResponseUtils.sendOKJSONResponse( response, json );
    	
    	return null;
    }
    
    public ModelAndView generateOKJSONResponse( HttpServletResponse response, String json ) {
    	ResponseUtils.sendOKJSONResponse( response, json );
    	
    	return null;
    }
    
    public ModelAndView generateErrorJSONResponse( HttpServletResponse response, JSONObject json, int statusCode ) {
    	ResponseUtils.sendErrorJSONResponse( response, json, statusCode );
    	
    	return null;
    }
    
    public ModelAndView generateErrorJSONResponseDatabaseError(HttpServletResponse response) {
    	JSONObject errorJson = new JSONObject();
		errorJson.put(TRSJSONResponseConstants.JSON_GENERIC_ERROR_MESSAGE, "There was an error while processing your request.");
		
		return generateErrorJSONResponse(response, errorJson, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    public ModelAndView generateErrorJSONResponseServerError(HttpServletResponse response, String message) {
    	JSONObject errorJson = new JSONObject();
		errorJson.put(TRSJSONResponseConstants.JSON_GENERIC_ERROR_MESSAGE, message);
		
		return generateErrorJSONResponse(response, errorJson, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    
    public ModelAndView generateErrorJSONResponseAuthorizationNeeded(HttpServletResponse response) {
    	JSONObject errorJson = new JSONObject();
		errorJson.put(TRSJSONResponseConstants.JSON_GENERIC_ERROR_MESSAGE, "You need to be logged in to make this request.");
		
		return generateErrorJSONResponse(response, errorJson, HttpServletResponse.SC_UNAUTHORIZED);
    }
    
    public ModelAndView generateErrorJSONResponseNotFound(HttpServletResponse response) {
    	JSONObject errorJson = new JSONObject();
		errorJson.put(TRSJSONResponseConstants.JSON_GENERIC_ERROR_MESSAGE, "The item was not found.");
		
		return generateErrorJSONResponse(response, errorJson, HttpServletResponse.SC_NOT_FOUND);
    }
    
    public String tryGetField(JSONObject json, String fieldKey) {
    	try {
    		return json.getString(fieldKey);
    	} catch (JSONException e) {
    		return null;
    	}
    }
    
    public int tryGetIntField(JSONObject json, String fieldKey) {
    	try {
    		return json.getInt(fieldKey);
    	} catch (JSONException e) {
    		return 0;
    	}
    }
    
    public JSONArray tryGetArrayField(JSONObject json, String fieldKey) {
    	try {
    		return json.getJSONArray(fieldKey);
    	} catch (JSONException e) {
    		return null;
    	}
    }
}