package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.partitioner.HashPartitioner;
import com.msgqueue.mini_broker.dto.response.ProduceMessageResponse;

public class ProducerServiceTest {
	private Broker broker;
	private Partitioner partitioner;
	private ProducerService producerService;


	private final static String TOPIC_NAME = "orders";
	private final static int THREE_PARTITIONS = 3;
	private final static String KEY_STRING_1 = "key-1";
	private final static String PAYLOAD_STRING_1 = "payload-message-string-1";
	private final static String PAYLOAD_STRING_2 = "payload-message-string-2";

	@BeforeEach
	void setup(){
		broker = new Broker();
		partitioner = new HashPartitioner();
		producerService = new ProducerService(broker, partitioner);
	}

	@Test
	void shouldProduceMessage(){
		broker.createTopic(TOPIC_NAME, THREE_PARTITIONS);

		ProduceMessageResponse firstResponse = producerService.produce(TOPIC_NAME, KEY_STRING_1, PAYLOAD_STRING_1);
		ProduceMessageResponse secondResponse = producerService.produce(TOPIC_NAME, KEY_STRING_1, PAYLOAD_STRING_2);

		assertEquals(0, firstResponse.offset());
		assertEquals(1, secondResponse.offset());
	}


}
