package com.myoscorp.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler
	public ResponseEntity<ApplicationErrors> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest){
		ApplicationErrors ae= new ApplicationErrors(ex.getMessage(), "404");
		ae.setDate(new Date());
		return new ResponseEntity<ApplicationErrors>(ae,HttpStatus.NOT_FOUND);
	}
}
