package com.msgqueue.mini_broker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.msgqueue.mini_broker.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(TopicAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(TopicAlreadyExistsException e){
		return ResponseEntity
			.status(400)
			.body(new ErrorResponse(e.getMessage(), "TOPIC_ALREADY_EXISTS"));
	}

	@ExceptionHandler(TopicDoesNotExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(TopicDoesNotExistsException e){
		return ResponseEntity
			.status(404)
			.body(new ErrorResponse(e.getMessage(), "TOPIC_DOES_NOT_EXIST"));
	}

	@ExceptionHandler(Exception.class)
    	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		return ResponseEntity
			.status(500)
			.body(new ErrorResponse(e.getMessage(), "INTERNAL_ERROR"));
	}
}
