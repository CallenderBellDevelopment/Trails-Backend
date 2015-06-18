package com.callenderbell.trails.admin;


import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class DatastoreAdmin {
	
	
	public static void populateSongs(){
		
	    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();



		
	}
	
	
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
