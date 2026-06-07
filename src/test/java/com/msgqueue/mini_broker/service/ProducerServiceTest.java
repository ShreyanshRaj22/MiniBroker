package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.partitioner.HashPartitioner;
import com.msgqueue.mini_broker.dto.response.ProduceMessageResponse;
import com.msgqueue.mini_broker.Constants;

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

	@Test
	void shouldProduceMessage(){
		broker.createTopic(Constants.TOPIC_NAME, Constants.THREE_PARTITIONS);

		ProduceMessageResponse firstResponse = producerService.produce(Constants.TOPIC_NAME, Constants.KEY_1, Constants.PAYLOAD_STRING);
		ProduceMessageResponse secondResponse = producerService.produce(Constants.TOPIC_NAME, Constants.KEY_1, Constants.PAYLOAD_STRING_2);

		assertEquals(0, firstResponse.offset());
		assertEquals(1, secondResponse.offset());
	}


}
