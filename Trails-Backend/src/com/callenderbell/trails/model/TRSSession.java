package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSSession {
	
	public long id;
	public long userId;
	public long sessionId;
	
	
	public TRSSession (Entity e)
	{
		
		id 			= (long)e.getKey().getId();
		sessionId   = (long)e.getProperty("sessionId");
		userId      = (long)e.getProperty("userId");
				
	}
	

}
