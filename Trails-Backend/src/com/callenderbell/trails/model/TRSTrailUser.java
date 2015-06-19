package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSTrailUser {
	
	public long id;
	public long userId; 
	public long trailId;

	public TRSTrailUser(Entity e) {
		id				 = (long) e.getKey().getId();
		userId  		 = (long) e.getProperty("userId");
		trailId          = (long) e.getProperty("trailId");
	}

	

}
