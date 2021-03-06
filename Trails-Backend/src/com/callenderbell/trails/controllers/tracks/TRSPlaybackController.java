package com.callenderbell.trails.controllers.tracks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSConstants;
import com.callenderbell.trails.constants.TRSJSONRequestConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSHistoryDatabase;
import com.callenderbell.trails.database.TRSUserDatabase;
import com.callenderbell.trails.model.TRSSession;
import com.callenderbell.trails.utils.TRSUtils;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;

public class TRSPlaybackController extends TRSAbstractController {

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

				// Get track id
				long longTrackId = Long.parseLong(paramsMap
						.get(TRSJSONRequestConstants.JSON_TRACK_ID));

				GcsService gcsService = GcsServiceFactory.createGcsService();
				GcsFilename filename = new GcsFilename("TRSBucket", longTrackId + "");

				// Point the track output stream to the response

				GcsInputChannel readChannel = gcsService
						.openPrefetchingReadChannel(filename, 0, 1024);
				copy(Channels.newInputStream(readChannel),
						res.getOutputStream());
				
				
				// Create new row in history table
				
				// Process request.
	            long longUserId = Long.parseLong( userId );
				
				TRSHistoryDatabase.addTrackToHistory(longUserId, longTrackId);
				
				// Update
				
				

			} catch (Exception e) {
				return generateErrorJSONResponseServerError(res, e.getMessage());
			}

		} else
			return generateErrorJSONResponseAuthorizationNeeded(res);
		
		return null; //TODO investigate this null return

	}

	private void copy(InputStream input, OutputStream output)
			throws IOException {
		try {
			byte[] buffer = new byte[1024];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
		} finally {
			input.close();
			output.close();
		}
	}

}
