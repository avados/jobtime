package com.cle.jobtime.configuration;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMVCWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	// before the initial config was done in AppInitializer.class
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return null;
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters()
	{
		return new Filter[]{new XClacksOverhead()};
	}
}
