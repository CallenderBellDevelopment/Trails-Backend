package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSUserHistory {
	
	public long id;
	public long userId; 
	public long trackId;
	public long timeOfPlay;
	public boolean currentlyPlaying;
	
	

	public TRSUserHistory(Entity e) {
		id				 = (long) e.getKey().getId();
		trackId  		 = (long) e.getProperty("trackId");
		userId           = (long) e.getProperty("userId");
		timeOfPlay       = (long) e.getProperty("timeOfPlay");
		currentlyPlaying = (boolean) e.getProperty("currentlyPlaying");
	}

}
