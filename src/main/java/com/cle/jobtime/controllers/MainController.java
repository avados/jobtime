package com.cle.jobtime.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.model.Mission;
import com.cle.jobtime.model.Project;
import com.cle.jobtime.model.RestException;
import com.cle.jobtime.model.TaskType;
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
			String jobs=  mainService.getJobsAsJson();
			
			return new ResponseEntity<String>(jobs, HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.debug("getJobs",e );
		}
		
		return null;		
	}
	
	
	@RequestMapping(value = "/getDateJobDone/{sdate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getDateJobDone(HttpEntity<String> httpEntity, WebRequest request, @PathVariable String sdate)
	{
		try
		{	  
			DateFormat format = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
			Date date = format.parse(sdate);
			
			String dateJobTimes = mainService.getJobDoneOnAsJson(date);
			
			return new ResponseEntity<String>(dateJobTimes, HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.debug("getTaskType",e );
		}
		
		return null;		
	}
	
	
	@RequestMapping(value = "/getTaskType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getTaskType(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			String taskTypes=  mainService.getTaskTypeAsJson();
			
			return new ResponseEntity<String>(taskTypes, HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.debug("getTaskType",e );
		}
		
		return null;		
	}
	
	@RequestMapping(value = "/addJobDone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> addJobDone(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			ObjectMapper mapper = new ObjectMapper();

//			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			mapper.setDateFormat(df);
			// String s = mapper.writeValueAsString(user);
			JobDone jd = mapper.readValue(httpEntity.getBody(), JobDone.class);
			
//			Project project = mainService.getProject(java.util.UUID uuid);
//Mission mission = jd.getProject().getMission();
			mainService.addJobDone(jd);
			return new ResponseEntity<String>("{}", HttpStatus.OK);
		}
		catch (RestException e)
		{
			logger.debug("addJobDone",e );
			return RestException.errorResponseEntity(e);
		}
		catch (Exception e)
		{
			logger.debug("addJobDone",e );
		}
		return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/editJob", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> editJob(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			ObjectMapper mapper = new ObjectMapper();

			Job job = mapper.readValue(httpEntity.getBody(), Job.class);
			
			mainService.editJob(job);
			
			
			return new ResponseEntity<String>(mainService.getJobsAsJson(), HttpStatus.OK);
		}
		catch (RestException e)
		{
			logger.debug("addJob",e );
			return RestException.errorResponseEntity(e);
		}
		catch (Exception e)
		{
			logger.debug("addJob",e );
		}
		return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/editMission", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> editMission(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			ObjectMapper mapper = new ObjectMapper();

			Mission mission = mapper.readValue(httpEntity.getBody(), Mission.class);
			
			mainService.editMission(mission);
			
			
			return new ResponseEntity<String>(mainService.getJobsAsJson(), HttpStatus.OK);
		}
		catch (RestException e)
		{
			logger.debug("editMission",e );
			return RestException.errorResponseEntity(e);
		}
		catch (Exception e)
		{
			logger.debug("editMission",e );
		}
		
		return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/editProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> editProject(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			ObjectMapper mapper = new ObjectMapper();

			Project project= mapper.readValue(httpEntity.getBody(), Project.class);
			
			mainService.editProject(project);
			
			
			return new ResponseEntity<String>(mainService.getJobsAsJson(), HttpStatus.OK);
		}
		catch (RestException e)
		{
			logger.debug("editMission",e );
			return RestException.errorResponseEntity(e);
		}
		catch (Exception e)
		{
			logger.debug("editMission",e );
		}
		return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/editTaskType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> editTaskType(HttpEntity<String> httpEntity, WebRequest request)
	{
		try
		{	  
			ObjectMapper mapper = new ObjectMapper();

			TaskType taskType= mapper.readValue(httpEntity.getBody(), TaskType.class);
			
			mainService.editTaskType(taskType);
			
			
			return new ResponseEntity<String>(mainService.getTaskTypeAsJson(), HttpStatus.OK);
		}
		catch (RestException e)
		{
			logger.debug("editTaskType",e );
			return RestException.errorResponseEntity(e);
		}
		catch (Exception e)
		{
			logger.debug("editTaskType",e );
		}
		return new ResponseEntity<String>("{}", HttpStatus.BAD_REQUEST);
	}
	
}
