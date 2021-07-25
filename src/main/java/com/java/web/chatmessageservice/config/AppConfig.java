package com.java.web.chatmessageservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


	
	@Configuration
	public class AppConfig implements WebMvcConfigurer {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	    	
	    	System.out.println("Inside App Config... ");
	        registry.addMapping("/**")
	                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
	                .allowedOrigins("*")
	                .allowedHeaders("*");
	                
	        		
	    }
	}


