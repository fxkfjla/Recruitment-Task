package com.example.recruitmenttask.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
	// TODO: Render views
	
	@ExceptionHandler(value = {Exception.class, IOException.class, JAXBException.class})
	public ResponseEntity<Object> handleException(Exception e)
	{
		return new ResponseEntity<>(generateBody(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e)
	{
		return new ResponseEntity<>(generateBody(e), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e)
	{
		return new ResponseEntity<>(generateBody(e), HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	private Map<String, String> generateBody(Exception e)
	{
		logger.error(e.getMessage(), e);
		
		Map<String, String> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now().toString());
		body.put("message", e.getMessage());
		
		return body;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
}