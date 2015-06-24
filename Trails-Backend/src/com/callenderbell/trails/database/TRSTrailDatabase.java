package com.callenderbell.trails.database;

import com.callenderbell.trails.model.TRSTrail;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class TRSTrailDatabase {
	
	private static DatastoreService dataStore = DatastoreServiceFactory
			.getDatastoreService();
	
	
	public static TRSTrail startTrail(long userId, long trackId)
	{
		
		// Create new entity for track with all required properties
		
		Entity e = new Entity("Trail");
		
		e.setProperty("trackId", trackId);
		e.setProperty("progress",0);
		
		// If the data store add is success then return the TRSTrail added
		
		if (dataStore.put(e) == null)
		{
			return null;
		}
		
		return (new TRSTrail(e));
		
	}
	
	public static TRSTrail joinTrail(long userId, long leaderId)
	{
		// Create a key used in order to query the data store for the given trail via  user id
		
		Key k = KeyFactory.createKey("Trail", leaderId);

		// We want to filter on that key		
		
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.EQUAL, k);

		Query query = new Query("Trail").setFilter(keyFilter);
		
		PreparedQuery pq = dataStore.prepare(query);
		
		// We should only get back a single trail as a user can only be in one trail at a time
		
		Entity trailResult          = pq.asSingleEntity();
				
		if (trailResult == null)
		{
			return null;
		}
		
		// Now we create a new TrailUser entity for the user joining
		
		Entity e = new Entity("TrailUser");
		e.setProperty("userId", userId);
		e.setProperty("trailId", trailResult.getKey().getId());
		
		
		if (dataStore.put(e) == null)
		{
			return null;
		}
		
		return new TRSTrail(trailResult);
		
				
	}
	
}
