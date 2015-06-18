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

		// Cookie[] cookies = request.getCookies();

		// String id = TRSUtils.getCookieValue( cookies,
		// TRSConstants.COOKIE_USERID, null );
		// String username = TRSUtils.getCookieValue( cookies,
		// TRSConstants.COOKIE_USERNAME, null );
		// String sessionId = TRSUtils.getCookieValue( cookies,
		// TRSConstants.COOKIE_SESSION_ID, null );

		try {
			String jsonString = TRSUtils.postDataToString(request);
			JSONObject json = new JSONObject(jsonString);

			String title = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_TITLE);
			String artist = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_ARTIST);
			String genre = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_GENRE);
			String base64 = json
					.getString(TRSJSONRequestConstants.JSON_TRACK_BASE64_MP3);

			byte[] decoded = null;
			if (base64 != null) {
				decoded = TRSUtils.base64ImageDecode(base64);
			}

			// First add the track to datastore

			long tId = TRSTrackDatabase.addTrack(title, artist, genre);

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
				
				
				//Test read
				
				System.out.println(filename.toString());
				

				

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

		// resp.setContentType("audio/mpeg");
		// resp.getWriter().println("Hello, world from java");
		// GcsService gcsService = GcsServiceFactory.createGcsService();
		// GcsFilename filename = new GcsFilename(BUCKETNAME, FILENAME);
		// GcsFileOptions options = new GcsFileOptions.Builder()
		// .mimeType("audio/mpeg")
		// .acl("public-read")
		// .addUserMetadata("myfield1", "my field value")
		// .build();

		// GcsOutputChannel writeChannel = gcsService.createOrReplace(filename,
		// options);
		// // You can write to the channel using the standard Java methods.
		// // Here we use a PrintWriter:
		// PrintWriter writer = new PrintWriter(Channels.newWriter(writeChannel,
		// "UTF8"));
		// writer.println("The woods are lovely dark and deep.");
		// writer.println("But I have promises to keep.");
		// writer.flush();
		//
		// // Note that the writeChannel is Serializable, so it is possible to
		// store it somewhere and write
		// // more to the file in a separate request. To make the object as
		// small as possible call:
		// writeChannel.waitForOutstandingWrites();
		//
		// // This time we write to the channel directly
		// writeChannel.write(ByteBuffer.wrap("And miles to go before I sleep.".getBytes("UTF8")));
		//
		// // If you want partial content saved in case of an exception, close
		// the
		// // GcsOutputChannel in a finally block. See the GcsOutputChannel
		// interface
		// // javadoc for more information.
		// writeChannel.close();
		// resp.getWriter().println("Done writing...");
		//
		// // At this point, the file is visible to anybody on the Internet
		// through Cloud Storage as:
		// // (http://storage.googleapis.com/BUCKETNAME/FILENAME)
		//
		// GcsInputChannel readChannel = null;
		// BufferedReader reader = null;
		// try {
		// // We can now read the file through the API:
		// readChannel = gcsService.openReadChannel(filename, 0);
		// // Again, different standard Java ways of reading from the channel.
		// reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));
		// String line;
		// // Prints "The woods are lovely, dark, and deep."
		// // "But I have promises to keep."
		// // "And miles to go before I sleep."
		// while ((line = reader.readLine()) != null) {
		// resp.getWriter().println("READ:" + line);
		// }
		// } finally { if (reader != null) { reader.close(); }
		//
		// }

	}

}
