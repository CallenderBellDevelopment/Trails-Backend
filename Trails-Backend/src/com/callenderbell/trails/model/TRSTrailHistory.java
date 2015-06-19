package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSTrailHistory {
	
	public long id;
	public long trackId; 
	public long trailId;
	public long userId;
	public long date;
	
	

	public TRSTrailHistory(Entity e) {
		id				 = (long) e.getKey().getId();
		trackId  		 = (long) e.getProperty("trackId");
		trackId          = (long) e.getProperty("trailId");
		userId           = (long) e.getProperty("userId");
		date             = (long) e.getProperty("date");
	}

}
