package com.msgqueue.mini_broker.exception;

public class TopicDoesNotExistsException extends RuntimeException {
	public TopicDoesNotExistsException(String msg){
		super(msg);
	}
}
