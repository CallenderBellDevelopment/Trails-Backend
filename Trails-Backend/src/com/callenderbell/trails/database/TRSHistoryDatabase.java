package com.callenderbell.trails.database;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class TRSHistoryDatabase {
	
	private static DatastoreService dataStore = DatastoreServiceFactory
			.getDatastoreService();
	
	public static void addTrackToHistory(long userId, long trackId)
	{
		Entity e = new Entity("History");
		

		e.setProperty("userId", userId);
		e.setProperty("trackId", trackId);
		e.setProperty("timeOfPlay", new Date().getTime());
		e.setProperty("currentlyPlaying", true);

				

//		return (long) (dataStore.put(e).getId());
		dataStore.put(e);
		
	}

}
