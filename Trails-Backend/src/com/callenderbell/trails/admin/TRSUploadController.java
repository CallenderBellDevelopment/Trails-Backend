package com.callenderbell.trails.admin;

import java.nio.ByteBuffer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSJSONRequestConstants;
import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSTrackDatabase;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.utils.TRSUtils;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;

public class TRSUploadController extends TRSAbstractController {


	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		try {
			String jsonString = TRSUtils.postDataToString(request);
			JSONObject json = new JSONObject(jsonString);

			String title = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_TITLE);
			String artist = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_ARTIST);
			String genre = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_GENRE);
			String mood = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_MOOD);
			String bpm = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_BPM);
			String base64 = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_BASE64_MP3);

			byte[] decoded = null;
			if (base64 != null) {
				decoded = TRSUtils.base64ImageDecode(base64);
			}

			// First add the track to datastore

			long tId = TRSTrackDatabase.addTrack(title, artist, genre, mood, bpm);

			if (tId > 0) {
				// Now upload mp3 to cloud store
				
				GcsService gcsService = GcsServiceFactory.createGcsService();
				GcsFilename filename = new GcsFilename("TRSBucket", tId+"");
				GcsFileOptions options = new GcsFileOptions.Builder()
						.mimeType("audio/mpeg").acl("public-read")
						.build();
								
				GcsOutputChannel writeChannel = gcsService.createOrReplace(filename,
				options);
				
				writeChannel.write(ByteBuffer.wrap(decoded));
				writeChannel.close();
				
				
		
				JSONObject okJson = new JSONObject();
				okJson.put(TRSJSONResponseConstants.JSON_TRACK_TRACK, tId);
				return generateOKJSONResponse(response, okJson);

			} else
				return generateErrorJSONResponseDatabaseError(response);

		} catch (Exception e) {
			e.printStackTrace();
			return generateErrorJSONResponseServerError(response,
					e.getMessage());
		}

		

	}

}
