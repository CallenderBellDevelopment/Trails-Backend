package com.callenderbell.trails.controllers.user;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.constants.TRSJSONResponseConstants;
import com.callenderbell.trails.controllers.TRSAbstractController;
import com.callenderbell.trails.database.TRSUserDatabase;
import com.callenderbell.trails.json.JSONArray;
import com.callenderbell.trails.json.JSONObject;
import com.callenderbell.trails.model.TRSUser;
import com.google.gson.Gson;

public class TRSGetUsersController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		try
		{
			ArrayList<TRSUser> userList = TRSUserDatabase.getAllUsers();
			
				JSONObject okJson = new JSONObject();
				okJson.put(TRSJSONResponseConstants.JSON_GENERIC_ITEMS, new JSONArray(new Gson().toJson(userList)));
				
				return generateOKJSONResponse(res, okJson);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}	
		
	}

}
