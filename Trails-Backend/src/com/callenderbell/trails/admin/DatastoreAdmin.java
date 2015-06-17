package com.callenderbell.trails.admin;


import com.google.appengine.api.datastore.FetchOptions.Builder.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DatastoreAdmin {
	
	
	public static void populate(){
		
		
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		
		Entity user = new Entity("User");		
		
		user.setProperty("username", "steve");
		user.setProperty("password", "tits");
		user.setProperty("email", "test@tits.com");
		user.setProperty("firstname", "steve");
		user.setProperty("lastname", "callender");
		
		Entity user1 = new Entity("User");		
		
		user1.setProperty("username", "kenneth");
		user1.setProperty("password", "tits1");
		user1.setProperty("email", "test@tits.com");
		user1.setProperty("firstname", "kenneth");
		user1.setProperty("lastname", "bell");
		
		Entity user2 = new Entity("User");		
		
		user2.setProperty("username", "georgi");
		user2.setProperty("password", "tits2");
		user2.setProperty("email", "test@tits.com");
		user2.setProperty("firstname", "georgi");
		user2.setProperty("lastname", "christov");
		
		
		

		dataStore.put(user);
		dataStore.put(user1);
		dataStore.put(user2);
		
	}
	
	
	public static Query getUser(String name){


		Filter nameFilter =
				new FilterPredicate("username",
						FilterOperator.EQUAL,
						name);

		return new Query("User").setFilter(nameFilter);

	}

}
