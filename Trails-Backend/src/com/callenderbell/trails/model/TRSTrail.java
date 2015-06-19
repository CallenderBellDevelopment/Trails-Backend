package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSTrail {
	
	public long id;
	public long currentTrackId; 
	public long progress;
	

	public TRSTrail(Entity e) {
		id				 = (long) e.getKey().getId();
		currentTrackId   = (long) e.getProperty("currentTrackId");
		progress         = (long) e.getProperty("progress");
	}

}
