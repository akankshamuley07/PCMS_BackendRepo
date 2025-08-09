package com.pcms.users.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(value = UserAlreadyExixtsException.class)
	public ResponseEntity<APIError> UserAlreadyExixtsException() {
		APIError error = new APIError(409, "USER_ALREADY_EXISTS!!!");
		
		return new ResponseEntity<APIError>(error,HttpStatus.CONFLICT);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = UserDoesNotExistsException.class)
	public ResponseEntity<APIError> UserDoesNotExistsException() {
		APIError error = new APIError(404, "USER_DOES_NOT_EXISTS!!!");
		
		return new ResponseEntity<APIError>(error,HttpStatus.NOT_FOUND);
	}
}
