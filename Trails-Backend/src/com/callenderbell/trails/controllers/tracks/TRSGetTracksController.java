package com.callenderbell.trails.controllers.tracks;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSTrackDatabase;
import com.callenderbell.trails.json.JSONArray;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.model.TRSTrack;
import com.google.gson.Gson;

public class TRSGetTracksController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		try
		{
						
			ArrayList<TRSTrack> trackList = TRSTrackDatabase.getAllTracks();
			
			
				JSONObject okJson = new JSONObject();
				okJson.put(TRSJSONResponseConstants.JSON_TRACK_TRACK, new JSONArray(new Gson().toJson(trackList)));
				
				return generateOKJSONResponse(res, okJson);
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}		
	}

}
