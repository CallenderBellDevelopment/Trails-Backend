package com.callenderbell.trails.database;

import java.util.ArrayList;

import com.callenderbell.trails.constants.TRSJSONRequestConstants;
import com.callenderbell.trails.model.TRSTrack;
import com.callenderbell.trails.model.TRSTrail;
import com.callenderbell.trails.model.TRSTrack.Mood;
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

	private static DatastoreService dataStore = DatastoreServiceFactory
			.getDatastoreService();

	public static TRSTrack addTrack(String title, String artist, String genre,
			String mood, String bpm) throws EntityNotFoundException,
			InterruptedException {

		// Create new entity for track with all required properties 
		
		Entity e = new Entity("Track");

		e.setProperty("title", title);
		e.setProperty("artist", artist);
		e.setProperty("genre", genre);
		e.setProperty("mood", mood);
		e.setProperty("bpm", bpm);
		
		// If the data store add is success then return the TRSTrack added
		if (dataStore.put(e) == null)
		{
			return null;
		}
		
		return (new TRSTrack(e));

	}

	public static TRSTrack getTrack(long trackId)
			throws EntityNotFoundException {

		// Create a key used in order to query the data store for the given track via track id
		
		Key k = KeyFactory.createKey("Track", trackId);

		// We want to filter on that key
		
		Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.EQUAL, k);

		// Use the filter to create a data store query
		
		Query query = new Query("Track").setFilter(keyFilter);

		PreparedQuery pq = dataStore.prepare(query);

		// Because track id is a unique attribute we should receive only one entity
		
		Entity result = pq.asSingleEntity();

		if (result == null) {
			return null;
		}

		// If not null then create a TRSTrack object and return it
		
		return new TRSTrack(result);

	}

	public static ArrayList<TRSTrack> getAllTracks() {

		// Simply create and execute a query on Tracks to get em all
		
		Query query = new Query("Track");

		PreparedQuery pq = dataStore.prepare(query);

		// Create an array list and add in each returned track 
		
		ArrayList<TRSTrack> returnList = new ArrayList<TRSTrack>();

		for (Entity e : pq.asIterable()) {
			if (e != null) {
				returnList.add(new TRSTrack(e));
			}
		}

		// Return the (possibly empty) list
		
		return returnList;

	}

	public static ArrayList<Mood> getAllMoods() {
		
		// Simply create and execute a query on Tracks to get em all

		Query query = new Query("Track");

		PreparedQuery pq = dataStore.prepare(query);
		
		// Create an array list and add in each returned mood

		ArrayList<Mood> returnList = new ArrayList<Mood>();

		Mood m = null;
		
		// Only add in moods once and then return the list
		
		for (Entity e : pq.asIterable()) {
			if (e != null) {
				m = Mood.valueOf((String)e.getProperty(TRSJSONRequestConstants.JSON_TRACK_MOOD));
				if (!returnList.contains(m))
				{
					returnList.add(m);
				}								
			}
		}
		
		return returnList;

	}
	
	
}
