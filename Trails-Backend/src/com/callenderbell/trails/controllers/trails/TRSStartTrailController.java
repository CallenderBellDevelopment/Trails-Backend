package com.callenderbell.trails.controllers.trails;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSConstants;
import com.callenderbell.trails.constants.TRSJSONRequestConstants;
import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSTrailDatabase;
import com.callenderbell.trails.database.TRSUserDatabase;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.model.TRSSession;
import com.callenderbell.trails.model.TRSTrail;
import com.callenderbell.trails.utils.TRSUtils;
import com.google.gson.Gson;

public class TRSStartTrailController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {		
		
		Cookie[] cookies = req.getCookies();

		String userId = TRSUtils.getCookieValue(cookies,
				TRSConstants.COOKIE_USERID, null);
		String username = TRSUtils.getCookieValue(cookies,
				TRSConstants.COOKIE_USERNAME, null);
		String sessionId = TRSUtils.getCookieValue(cookies,
				TRSConstants.COOKIE_SESSION_ID, null);

		if (userId != null && username != null && sessionId != null) {

			try {

				// Check session validity.
				TRSSession session = TRSUserDatabase.getSession(
						Long.parseLong(sessionId), Long.parseLong(userId));
				if (session == null) {
					return generateErrorJSONResponseAuthorizationNeeded(res);
				}

				// Get request parameters.
				String params = req.getQueryString();
				HashMap<String, String> paramsMap = TRSUtils
						.getParametersMap(params);

				// Get trail start track id
				long longTrackId = Long.parseLong(paramsMap
						.get(TRSJSONRequestConstants.JSON_TRAIL_TRACK_ID));
				
				long longUserId = Long.parseLong(userId);

				TRSTrail startResult = TRSTrailDatabase.startTrail(longUserId, longTrackId);
				
				JSONObject okJson = new JSONObject();
				okJson.put(TRSJSONResponseConstants.JSON_TRAIL_CURRENT_TRACK_ID, new JSONObject(new Gson().toJson(startResult.currentTrackId)));
				okJson.put(TRSJSONResponseConstants.JSON_TRAIL_PROGRESS, new JSONObject(new Gson().toJson(startResult.progress)));
				
				
				return generateOKJSONResponse(res, okJson);

			} catch (Exception e) {
				return generateErrorJSONResponseServerError(res, e.getMessage());
			}

		} else
			return generateErrorJSONResponseAuthorizationNeeded(res);
		

		
		
		
		
		
		
		
	}

}
