package com.callenderbell.trails.controllers.tracks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.json.JSONArray;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.model.TRSTrack;
import com.callenderbell.trails.model.TRSTrack.Mood;
import com.google.gson.Gson;

public class TRSGetMoodsController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		try
		{
						
			Mood[] moodList = TRSTrack.getMoods();
			
				JSONObject okJson = new JSONObject();
				okJson.put(TRSJSONResponseConstants.JSON_GENERIC_ITEMS, new JSONArray(new Gson().toJson(moodList)));
				
				return generateOKJSONResponse(res, okJson);
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}	
		
		
	}

}
