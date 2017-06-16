package com.cle.jobtime.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.model.Mission;
import com.cle.jobtime.model.Project;
import com.cle.jobtime.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class MainController {

	@Autowired
	MessageSource messageSource;


	@Autowired
	MainService mainService;


	private ObjectMapper mapper = new ObjectMapper();
	
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	 @RequestMapping("/")
	 public String home() {
		 
	 return "test";
	 }
	 
	@RequestMapping(value = "/getJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getJobs(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
//			mainService.editJob(null);
			
			String jobs=  mainService.getJobs();
			
			return new ResponseEntity<String>(jobs, HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.debug("merde",e );
		}
		
		return null;		
	}
	
	@RequestMapping(value = "/getTaskType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getTaskType(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			String taskTypes=  mainService.getTaskType();
			
			return new ResponseEntity<String>(taskTypes, HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.debug("merde",e );
		}
		
		return null;		
	}
	
	@RequestMapping(value = "/addJobDone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> addJobDone(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			ObjectMapper mapper = new ObjectMapper();

			// String s = mapper.writeValueAsString(user);
			JobDone jd = mapper.readValue(httpEntity.getBody(), JobDone.class);
			if(jd.getDate() == null || jd.getProject() == null || jd.getTaskType() == null || jd.getTimeSpent() == 0)
			{
				return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
			}
//			Project project = mainService.getProject(java.util.UUID uuid);
//Mission mission = jd.getProject().getMission();
			mainService.addJobDone(jd);
			return new ResponseEntity<String>("{}", HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.debug("merde",e );
		}
		return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
	}
}
