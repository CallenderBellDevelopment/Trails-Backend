package com.callenderbell.trails;

import java.io.IOException;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.callenderbell.trails.admin.DatastoreAdmin;

@SuppressWarnings("serial")
public class TRSDispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		System.out.println("TITS");
		
//		DatastoreAdmin.populate();
		
	}
}
