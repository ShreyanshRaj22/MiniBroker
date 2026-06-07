package com.msgqueue.mini_broker.flows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.partitioner.HashPartitioner;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.service.ConsumerService;
import com.msgqueue.mini_broker.service.GroupCoordinatorService;
import com.msgqueue.mini_broker.service.ProducerService;

public class ConsumerGroupFlowTest {
	private Broker broker;
	private GroupCoordinatorService coordinatorService;
	private ConsumerService consumerService;
	private ProducerService producerService;
	private Partitioner partitioner;

	@BeforeEach
	void setup(){
		broker = new Broker();
		coordinatorService = new GroupCoordinatorService(broker);
		partitioner = new HashPartitioner();
		producerService = new ProducerService(broker, partitioner);
		consumerService = new ConsumerService(broker, coordinatorService);
	}

	@Test
	void shouldProduceConsumeAndCommitOffsets(){
	}
}
