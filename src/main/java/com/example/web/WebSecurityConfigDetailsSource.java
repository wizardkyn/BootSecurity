package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.web.login.CustomWebAuthenticationDetailsSource;
import com.example.web.login.LoginService;
import com.example.web.login.LoginSuccessHandler;

//// with CustomWebAuthenticationDetailsSource , CustomWebAuthenticationDetails , LoginSuccessHandler
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfigDetailsSource extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginService loginService;
    
    @Bean
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
    @Autowired
    private CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource;

	@Bean
	public LoginSuccessHandler loginSuccessHandler() throws Exception{
		LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
		loginSuccessHandler.setDefaultTargetUrl("/index.do");
		return loginSuccessHandler;
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
            .authorizeRequests()
                .antMatchers("/resources/**", "/signup.do").permitAll()
                .antMatchers("/web/**").hasRole("USER")
                .antMatchers("/backend/**").access("hasRole('USER') and hasRole('ADMIN')") 
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.authenticationDetailsSource(customWebAuthenticationDetailsSource)
                .loginPage("/login.do")
                .loginProcessingUrl("/authLogin.do")
                .successHandler(loginSuccessHandler())
                .failureUrl("/login.do?error")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout.do")
                .logoutSuccessUrl("/login.do?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        http.exceptionHandling().accessDeniedPage("/login.do?error");
        http.sessionManagement().invalidSessionUrl("/login.do");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }
}