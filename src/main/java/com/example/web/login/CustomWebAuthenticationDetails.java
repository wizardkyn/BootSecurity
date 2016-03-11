package com.example.web.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = -3984468376168493070L;
	private final String myField;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		myField = request.getParameter("myfield");
	}

	public String getMyField() {
		return myField;
	}
}
