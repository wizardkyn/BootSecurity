package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.web.login.LoginService;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginService loginService;
    
    @Bean
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationFilterAnotherParam authenticationFilterAnotherParam() throws Exception {
		AuthenticationFilterAnotherParam authenticationFilterAnotherParam = new AuthenticationFilterAnotherParam();
		authenticationFilterAnotherParam.setAuthenticationManager(this.authenticationManagerBean());
		authenticationFilterAnotherParam.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/authLogin.do","POST"));
		return authenticationFilterAnotherParam;
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
                .defaultSuccessUrl("/index.do",true)  // true : always-use-default-target
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
    	http.addFilterBefore(authenticationFilterAnotherParam(),UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }
}