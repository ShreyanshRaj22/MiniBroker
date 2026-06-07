package com.msgqueue.mini_broker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.msgqueue.mini_broker.broker.Broker;

public class ConsumerServiceTest {
	private ConsumerService consumerService;
	private GroupCoordinatorService coordinatorService;
	Broker broker;

	@BeforeEach
	void setup(){
		broker = new Broker();
		coordinatorService = new GroupCoordinatorService(broker);
		consumerService = new ConsumerService(broker, coordinatorService);
	}


}
