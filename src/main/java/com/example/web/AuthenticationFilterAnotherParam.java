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

public class AuthenticationFilterAnotherParam extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		final String myField = request.getParameter("myfield");
        request.getSession().setAttribute("myfield", myField);

        return super.attemptAuthentication(request, response);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/index.do"));
		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		request.getSession().removeAttribute("myfield");
		setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login.do?error"));
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
