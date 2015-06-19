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
		Entity e = new Entity("Trail");
		
		e.setProperty("trackId", trackId);
		e.setProperty("progress",0);
		
		if (dataStore.put(e) == null)
		{
			return null;
		}
		
		return (new TRSTrail(e));
		
	}
	
	public static TRSTrail joinTrail(long userId)
	{
		
		Key k = KeyFactory.createKey("Trail", userId);

		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.EQUAL, k);

		Query query = new Query("Trail").setFilter(keyFilter);
		
		PreparedQuery pq = dataStore.prepare(query);
		
		Entity trailResult          = pq.asSingleEntity();
				
		if (trailResult == null)
		{
			return null;
		}
		
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
