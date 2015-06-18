package com.callenderbell.trails.database;

import java.util.ArrayList;

import com.callenderbell.trails.model.TRSTrack;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class TRSTrackDatabase {
	
	private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
	
	public static long addTrack (String title, String artist, String genre) throws EntityNotFoundException, InterruptedException
	{
		
		Entity e = new Entity("Track");
		
		
		e.setProperty("title", title);
		e.setProperty("artist",artist);
		e.setProperty("genre",genre);
								
		return (long)(dataStore.put(e).getId());
					
	}
		
	public static TRSTrack getTrack(long trackId) throws EntityNotFoundException 
	{
		
		Key k = KeyFactory.createKey("Track", trackId);
		
		
		Filter keyFilter    = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL,k);
		
		Query query 		   = new Query("Track").setFilter(keyFilter);
		
		
		PreparedQuery pq 	   = dataStore.prepare(query);
		
		Entity result          = pq.asSingleEntity();
		
		if (result == null)
		{
			return null;
		}
		
		return new TRSTrack(result);
		
	}
	
	public static ArrayList<TRSTrack> getAllTracks()
	{
		
		Query query 		   = new Query("Track");
		
		PreparedQuery pq 	   = dataStore.prepare(query);
		
		ArrayList<TRSTrack> returnList = new ArrayList<TRSTrack>();
		
		for (Entity e: pq.asIterable())		
		{
			if (e != null){
				returnList.add(new TRSTrack(e));
			}			  			
		}		
		
		return returnList;
		
	}
	
	
}
