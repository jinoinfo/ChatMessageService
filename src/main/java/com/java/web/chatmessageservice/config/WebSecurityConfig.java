package com.java.web.chatmessageservice.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
    	System.out.println("Inside  WebSecurityConfig..");
    	http.cors();
    	http
        // your security config here
        .authorizeRequests()
        .antMatchers(HttpMethod.TRACE, "/**").denyAll()
        .antMatchers("/admin/**").authenticated()
        .anyRequest().permitAll()
        .and().httpBasic()
        .and().headers().frameOptions().disable()
        .and().csrf().disable()
        .headers()
        // the headers you want here. This solved all my CORS problems! 
        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"))
        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
        .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"));

    }
}
