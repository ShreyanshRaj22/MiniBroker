package com.msgqueue.mini_broker.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.msgqueue.mini_broker.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(TopicAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(TopicAlreadyExistsException e){

		log.error("Topic already exists");
		return ResponseEntity
			.status(400)
			.body(new ErrorResponse(e.getMessage(), "TOPIC_ALREADY_EXISTS"));
	}

	@ExceptionHandler(TopicDoesNotExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(TopicDoesNotExistsException e){

		log.error("Topic does not exist");
		return ResponseEntity
			.status(404)
			.body(new ErrorResponse(e.getMessage(), "TOPIC_DOES_NOT_EXIST"));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleException(BadRequestException e){

		log.error("Bad request");
		return ResponseEntity
			.status(400)
			.body(new ErrorResponse(e.getMessage(), "BAD_REQUEST"));
	} 

	@ExceptionHandler(Exception.class)
    	public ResponseEntity<ErrorResponse> handleException(Exception e) {

		log.error("Interval server error");
		return ResponseEntity
			.status(500)
			.body(new ErrorResponse(e.getMessage(), "INTERNAL_ERROR"));
	}
}
