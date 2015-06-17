package com.callenderbell.trails.database;

import com.callenderbell.trails.model.TRSSession;
import com.callenderbell.trails.model.TRSUser;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class TRSUserDatabase {
	
	private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
	
	public static TRSSession getSession(long sessionId, long userId) 
	{
		
		Filter sessionIdFilter = new FilterPredicate("sessionId", FilterOperator.EQUAL,sessionId);
		Filter userIdFilter    = new FilterPredicate("userId", FilterOperator.EQUAL,userId);
		
		Filter compositeFilter = CompositeFilterOperator.and(sessionIdFilter, userIdFilter);
		
		Query query 		   = new Query("Session").setFilter(compositeFilter);
		
		PreparedQuery pq 	   = dataStore.prepare(query);
		
		Entity result          = pq.asSingleEntity();
		
		if (result == null)
		{
			return null;
		}
		
		return new TRSSession(result);

		
	}
	
	public static TRSUser getUser(long userId) throws EntityNotFoundException 
	{
		
		Key k = KeyFactory.createKey("User", userId);
		
		
		Filter keyFilter    = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL,k);
		
		Query query 		   = new Query("User").setFilter(keyFilter);
		
		
		PreparedQuery pq 	   = dataStore.prepare(query);
		
		Entity result          = pq.asSingleEntity();
		
		if (result == null)
		{
			return null;
		}
		
		return new TRSUser(result);
		
	}

	
	public static TRSUser loginUser(String un, String pa)
	{
		
		Filter usernameFilter    = new FilterPredicate("username", FilterOperator.EQUAL,un);
		Filter passwordFilter    = new FilterPredicate("password", FilterOperator.EQUAL,pa);
		
		Filter compositeFilter = CompositeFilterOperator.and(usernameFilter, passwordFilter);
		
		Query query 		   = new Query("User").setFilter(compositeFilter);
		
		PreparedQuery pq 	   = dataStore.prepare(query);
		
		Entity result          = pq.asSingleEntity();
		
		if (result == null)
		{
			return null;
		}
		
		return new TRSUser(result);

		
	}

	public static boolean createSession(long sessionId, long userId) 
	{
				
		Entity e = new Entity("Session");
		
		e.setProperty("sessionId", sessionId);
		e.setProperty("userId",userId);
		
		return (dataStore.put(e) != null);
		
	}
	
	
	
}

