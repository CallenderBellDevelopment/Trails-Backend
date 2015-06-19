package com.callenderbell.trails.controllers.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSConstants;
import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSUserDatabase;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.model.TRSSession;
import com.callenderbell.trails.model.TRSUser;
import com.callenderbell.trails.utils.TRSUtils;
import com.google.gson.Gson;

public class TRSCheckLoginController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		

		Cookie[] cookies = req.getCookies();
        
    	String id = TRSUtils.getCookieValue( cookies, TRSConstants.COOKIE_USERID, null );
        String username = TRSUtils.getCookieValue( cookies, TRSConstants.COOKIE_USERNAME, null );
        String sessionId = TRSUtils.getCookieValue( cookies, TRSConstants.COOKIE_SESSION_ID, null );
    	
        if ( id != null && username != null && sessionId != null )
        {
		
	        try {
	        	// Check session validity.
	            TRSSession session = TRSUserDatabase.getSession(Long.parseLong(sessionId), Long.parseLong(id));
	            if ( session == null )
	            {
	                return generateErrorJSONResponseAuthorizationNeeded(res);
	            }
	            // Process request.
	            long myUserId = Long.parseLong( id );
	            
	            TRSUser u = TRSUserDatabase.getUser(myUserId);
	             if ( u != null ) {
	                 JSONObject okJson = new JSONObject();
	                 okJson.put(TRSJSONResponseConstants.JSON_USER_USER, new JSONObject(new Gson().toJson(u)));
	                 
	                 return generateOKJSONResponse(res, okJson);
	             }
	             else
	                 return generateErrorJSONResponseDatabaseError(res);
	        } catch (Exception e)
	        {
	        	return generateErrorJSONResponseServerError(res, e.getMessage());
	        }	
	       
        }
        else
            return generateErrorJSONResponseAuthorizationNeeded(res);
		
	}

}
