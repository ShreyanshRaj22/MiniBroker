package com.msgqueue.mini_broker.exception;

public class TopicAlreadyExistsException extends RuntimeException {
	public TopicAlreadyExistsException(String msg){
		super(msg);
	}
}
