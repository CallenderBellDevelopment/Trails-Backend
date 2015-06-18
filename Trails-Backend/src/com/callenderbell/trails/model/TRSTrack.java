package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSTrack {

	public long id;
	public String title;
	public String artist;
	public String genre;

	public TRSTrack(Entity e) {
		id = (long) e.getKey().getId();
		title = (String) e.getProperty("title");
		artist = (String) e.getProperty("artist");
		genre = (String) e.getProperty("genre");
	}

}
