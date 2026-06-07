package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.dto.response.ProduceMessageResponse;
import com.msgqueue.mini_broker.model.Message;
import com.msgqueue.mini_broker.model.TopicPartition;
import com.msgqueue.mini_broker.partitioner.HashPartitioner;
import com.msgqueue.mini_broker.partitioner.Partitioner;

public class ConsumerServiceTest {
	private ConsumerService consumerService;
	private ProducerService producerService;
	private Partitioner partitioner;
	private GroupCoordinatorService coordinatorService;
	Broker broker;

	private final static String TOPIC_NAME = "orders";
	
	private final static int THREE_PARTITIONS = 3;

	private final static String GROUP_ID_STRING = "group-1";
	private final static String CONSUMER_ID_STRING = "consumer-1";

	private final static String KEY_1 = "24";
	private final static String PAYLOAD_STRING = "payload-1";
	private final static int LIMIT = 3;

	@BeforeEach
	void setup(){
		broker = new Broker();
		partitioner = new HashPartitioner();
		producerService = new ProducerService(broker, partitioner);
		coordinatorService = new GroupCoordinatorService(broker);
		consumerService = new ConsumerService(broker, coordinatorService);
	}


	@Test
	void shouldPoll(){
		broker.createTopic(TOPIC_NAME, THREE_PARTITIONS);

		coordinatorService.registerConsumer(GROUP_ID_STRING, CONSUMER_ID_STRING, TOPIC_NAME);

		producerService.produce(TOPIC_NAME, KEY_1, PAYLOAD_STRING);
		producerService.produce(TOPIC_NAME, KEY_1, PAYLOAD_STRING);
		producerService.produce(TOPIC_NAME, KEY_1, PAYLOAD_STRING);

		List<Message> result = consumerService.poll(GROUP_ID_STRING, CONSUMER_ID_STRING, TOPIC_NAME, LIMIT);

		assertEquals(3, result.size());

	}

	@Test
	void shouldCommitOffset(){
		broker.createTopic(TOPIC_NAME, THREE_PARTITIONS);

		coordinatorService.registerConsumer(GROUP_ID_STRING, CONSUMER_ID_STRING, TOPIC_NAME);

		ProduceMessageResponse response = producerService.produce(TOPIC_NAME, KEY_1, PAYLOAD_STRING);
		coordinatorService.registerConsumer(GROUP_ID_STRING, CONSUMER_ID_STRING, TOPIC_NAME);

		consumerService.commit(GROUP_ID_STRING, TOPIC_NAME, response.partitionId(), response.offset());
		TopicPartition tp = new TopicPartition(TOPIC_NAME, response.partitionId());

		assertEquals(response.offset(), coordinatorService.getCommittedOffsets(GROUP_ID_STRING, tp));
	}

}
