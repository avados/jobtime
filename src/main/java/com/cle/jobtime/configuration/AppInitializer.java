package com.cle.jobtime.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class AppInitializer implements WebApplicationInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);

	 public void onStartup(ServletContext servletContext) throws ServletException {
	      FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
	      encodingFilter.setInitParameter("encoding", "UTF-8");
	      encodingFilter.setInitParameter("forceEncoding", "false");
	      encodingFilter.addMappingForUrlPatterns(null, false, "/*");

	      final String activeProfile = System.getenv("spring.profiles.active");
	      if(activeProfile == null)
	      {
	    	  logger.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    	  logger.error("! NO PROFILE DETECTED, FALLING BACK TO DEFAULT DEV ENV !");
	    	  logger.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    	  servletContext.setInitParameter("spring.profiles.active", "DEV"); 
	      }
	      
      }
	 
 }

