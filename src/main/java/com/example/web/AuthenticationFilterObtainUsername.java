package com.example.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilterObtainUsername extends UsernamePasswordAuthenticationFilter {
	
	private String myField;
	private final String delimiter = ":===:";

	public String getMyField() { return myField; }
	public void setMyField(String myField) { this.myField = myField; }

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = request.getParameter(getUsernameParameter());
		String myField = request.getParameter("myfield");
	    String combinedUsername = username + delimiter + myField;
	    setMyField(myField);
	    System.out.println("Combined username = " + combinedUsername);
	    return combinedUsername;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
	    System.out.println("successfulAuthentication myField = " + myField);
		setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/index.do"));
		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

	    System.out.println("unsuccessfulAuthentication myField = " + myField);
		setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login.do?error"));
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
