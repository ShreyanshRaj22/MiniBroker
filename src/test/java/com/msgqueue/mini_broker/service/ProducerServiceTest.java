package com.msgqueue.mini_broker.service;

import org.junit.jupiter.api.BeforeEach;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.partitioner.HashPartitioner;

public class ProducerServiceTest {
	private Broker broker;
	private Partitioner partitioner;
	private ProducerService producerService;

	@BeforeEach
	void setup(){
		broker = new Broker();
		partitioner = new HashPartitioner();
		producerService = new ProducerService(broker, partitioner);
	}


}
