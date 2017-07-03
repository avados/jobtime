package com.cle.jobtime.configuration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cle.jobtime")
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Autowired
	private Environment env;

	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//
	// registry.addResourceHandler("/images/**").setCachePeriod(31556926);
	// }

	// add
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}

	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Bean
	public MessageSource messageSource()
	{
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Bean
	public MultipartResolver multipartResolver()
	{
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();

		return cmr;
		// return new StandardServletMultipartResolver();
	}

	@PostConstruct
	public void showEnv()
	{
		String profile = ArrayUtils.toString(env.getActiveProfiles());

		logger.info("################################################");
		logger.info("#                                              #");
		logger.info("#   The application is running in {} mode   #", profile);
		logger.info("#                                              #");
		logger.info("################################################");
	}

	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor()
	{
		return new DeviceResolverHandlerInterceptor();
	}

	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver()
	{
		return new DeviceHandlerMethodArgumentResolver();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(deviceResolverHandlerInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
	{
		argumentResolvers.add(deviceHandlerMethodArgumentResolver());
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Resources without Spring Security. No cache control response headers.
//        registry.addResourceHandler("/libs/**")
//            .addResourceLocations("/libs/");

		registry.addResourceHandler("/imgs/**")
        .addResourceLocations("/imgs/")
        .resourceChain(true)
        .addResolver(new GzipResourceResolver());
        // Resources controlled by Spring Security, which
        // adds "Cache-Control: must-revalidate".
        registry.addResourceHandler("/libs/**")
            .addResourceLocations("/libs/")
            .setCachePeriod(3600*24)
            .resourceChain(true)
            .addResolver(new GzipResourceResolver());
    }
	
	@Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        return executor;
    }
}
