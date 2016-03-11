package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.web.login.HttpsLoginSuccessHandler;
import com.example.web.login.LoginService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigSwicthHttps extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginService loginService;
    
	@Bean
	public HttpsLoginSuccessHandler httpsLoginSuccessHandler() throws Exception{
		HttpsLoginSuccessHandler httpsLoginSuccessHandler = new HttpsLoginSuccessHandler();
		httpsLoginSuccessHandler.setDefaultTargetUrl("http://localhost:8080/BootSecurity/index.do");
		return httpsLoginSuccessHandler;
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
                .loginPage("/login.do")
                .loginProcessingUrl("/authLogin.do")
                .successHandler(httpsLoginSuccessHandler())
                .failureUrl("/login.do?error")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout.do")
                .logoutSuccessUrl("/login.do?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    	
    	http.requiresChannel().antMatchers("/login.do","/authLogin.do","/logout.do").requiresSecure();
    	http.sessionManagement().sessionFixation().none();
        http.exceptionHandling().accessDeniedPage("/login.do?error");
        http.sessionManagement().invalidSessionUrl("/login.do");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }
}