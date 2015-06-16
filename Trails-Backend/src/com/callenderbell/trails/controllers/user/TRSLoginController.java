package com.callenderbell.trails.controllers.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.callenderbell.trails.controllers.TRSAbstractController;

public class TRSLoginController extends TRSAbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest in,
			HttpServletResponse out) throws Exception {

		out.setStatus(HttpServletResponse.SC_OK);
		
		out.setContentType("text/html");
		
		out.setCharacterEncoding("UTF-8");
		
		out.getWriter().print("Hello TITS");
		
		
		return null;
	}

}
