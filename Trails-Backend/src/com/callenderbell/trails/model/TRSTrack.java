package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSTrack {

	
	public static enum Mood {FEEL_GOOD, SAD, HAPPY, RELAXED};
	
	
	public long id;
	public String title;
	public String artist;
	public String genre; //TODO update genre to be enum type
	public Mood mood;
	public int bpm;

	public TRSTrack(Entity e) {
		id = (long) e.getKey().getId();
		title = (String) e.getProperty("title");
		artist = (String) e.getProperty("artist");
		genre = (String) e.getProperty("genre");
		mood = Mood.valueOf((String)e.getProperty("mood"));
		bpm = Integer.parseInt((String)e.getProperty("bpm"));
	}
	
	public static Mood[] getMoods()
	{
		 return Mood.values();
	}

}
