package com.callenderbell.trails.controllers.tracks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSJSONRequestConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.utils.TRSUtils;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;

public class TRSPlaybackController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		try {
						
			// Get request parameters.
            String params = req.getQueryString();
            HashMap<String, String> paramsMap = TRSUtils.getParametersMap(params);
            
            long id = Long.parseLong(paramsMap.get(TRSJSONRequestConstants.JSON_TRACK_ID));

			GcsService gcsService = GcsServiceFactory.createGcsService();
			GcsFilename filename = new GcsFilename("TRSBucket", id+"");

			GcsInputChannel readChannel = gcsService
					.openPrefetchingReadChannel(filename, 0, 1024);
			copy(Channels.newInputStream(readChannel), res.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
