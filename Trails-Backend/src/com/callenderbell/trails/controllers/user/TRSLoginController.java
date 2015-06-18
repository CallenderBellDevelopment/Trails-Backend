package com.callenderbell.trails.controllers.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSConstants;
import com.callenderbell.trails.constants.TRSJSONRequestConstants;
import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSUserDatabase;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.model.TRSSession;
import com.callenderbell.trails.model.TRSUser;
import com.callenderbell.trails.utils.TRSLongLivedCookie;
import com.callenderbell.trails.utils.TRSUtils;
import com.google.gson.Gson;

public class TRSLoginController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		Cookie[] cookies = request.getCookies();
        
    	String id = TRSUtils.getCookieValue( cookies, TRSConstants.COOKIE_USERID, null );
        String username = TRSUtils.getCookieValue( cookies, TRSConstants.COOKIE_USERNAME, null );
        String sessionId = TRSUtils.getCookieValue( cookies, TRSConstants.COOKIE_SESSION_ID, null );
    	
		
        try {
			String jsonString = TRSUtils.postDataToString(request);
			JSONObject json = new JSONObject(jsonString);

			String un = json.getString(TRSJSONRequestConstants.JSON_USER_USERNAME);
			String pa = json.getString(TRSJSONRequestConstants.JSON_USER_PASSWORD);
			
			// Check if this user is already logged in.
	        if ( id != null && username != null && sessionId != null ) {
	        	// Check session validity.
				TRSSession session = TRSUserDatabase.getSession(Long.parseLong(sessionId), Long.parseLong(id));
				if ( session != null ) {
					long myId = Long.parseLong(id);
					
					TRSUser u = TRSUserDatabase.getUser(myId); 
					
					
					if (u.username.equals(un)) { // Check if the user that is logged in is the same as user that is trying to login.				
						JSONObject okJson = new JSONObject();
						okJson.put(TRSJSONResponseConstants.JSON_USER_USER, new JSONObject(new Gson().toJson(u)));
						
						return generateOKJSONResponse(response, okJson);
					}
				}
	        }
			
			TRSUser u = TRSUserDatabase.loginUser(un,pa);
			if ( u != null ) {
				TRSLongLivedCookie userIdCookie = new TRSLongLivedCookie( TRSConstants.COOKIE_USERID, u.id + "" );
				response.addCookie( userIdCookie );
				
				TRSLongLivedCookie usernameCookie = new TRSLongLivedCookie( TRSConstants.COOKIE_USERNAME, un );
				response.addCookie( usernameCookie );
				
				long sId = TRSUtils.generateLongID();
				TRSLongLivedCookie sessionIdCookie = new TRSLongLivedCookie( TRSConstants.COOKIE_SESSION_ID, sId + "" );
				response.addCookie( sessionIdCookie );
				
				boolean sCreated = TRSUserDatabase.createSession(sId, u.id);
				if ( sCreated ) {
					JSONObject okJson = new JSONObject();
					okJson.put(TRSJSONResponseConstants.JSON_USER_USER, new JSONObject(new Gson().toJson(u)));
					
					return generateOKJSONResponse(response, okJson);
				}
				else
					return generateErrorJSONResponseDatabaseError(response);
			}
			else
				return generateErrorJSONResponseAuthorizationNeeded(response);
		} catch ( Exception e ) {
			
			return generateErrorJSONResponseServerError(response, e.getMessage());
		}
		
	}		

}
