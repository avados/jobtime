package com.cle.jobtime.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestException extends Exception {

	final private String status;
	final private String message;
	
	private static final Logger logger = LoggerFactory.getLogger(RestException.class);
	
	public RestException(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public String getStatus()
	{
		return status;
	}


	public String getMessage()
	{
		return message;
	}
	
	public static ResponseEntity<String> errorResponseEntity(RestException e)
	{
		String message = "{}";
		try
		{
			ObjectMapper om = new ObjectMapper();
			message = om.writeValueAsString(e);
		}
		catch (Exception e2)
		{
			logger.error("Error while creating error Response entity.", e);
		}

		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}
}
