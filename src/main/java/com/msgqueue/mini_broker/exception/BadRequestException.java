package com.msgqueue.mini_broker.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String msg){
		super(msg);
	}
}
