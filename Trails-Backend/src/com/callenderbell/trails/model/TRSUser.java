package com.callenderbell.trails.model;

import com.google.appengine.api.datastore.Entity;

public class TRSUser {
	
	public long id;
	public String username;
	public String firstname;
	public String lastname;
	public String email;
	
	
	
	public TRSUser (Entity e)
	{
		id = (long)e.getKey().getId();
		
		System.out.println(e.getKey().getId());
		
		username = (String) e.getProperty("username");
	    firstname = (String) e.getProperty("firstname");
	    lastname =  (String) e.getProperty("lastname");
        email = (String) e.getProperty("email");
	}
}
