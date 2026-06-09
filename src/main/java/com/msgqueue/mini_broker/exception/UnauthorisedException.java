package com.msgqueue.mini_broker.exception;

public class UnauthorisedException extends RuntimeException {
	public UnauthorisedException(String msg){
		super(msg);
	}
}
