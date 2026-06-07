package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.msgqueue.mini_broker.Constants;
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
		broker.createTopic(Constants.TOPIC_NAME, Constants.THREE_PARTITIONS);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);

		producerService.produce(Constants.TOPIC_NAME, Constants.KEY_1, Constants.PAYLOAD_STRING);
		producerService.produce(Constants.TOPIC_NAME, Constants.KEY_1, Constants.PAYLOAD_STRING);
		producerService.produce(Constants.TOPIC_NAME, Constants.KEY_1, Constants.PAYLOAD_STRING);

		List<Message> result = consumerService.poll(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME, Constants.LIMIT);

		assertEquals(3, result.size());

	}

	@Test
	void shouldCommitOffset(){
		broker.createTopic(Constants.TOPIC_NAME, Constants.THREE_PARTITIONS);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);

		ProduceMessageResponse response = producerService.produce(Constants.TOPIC_NAME, Constants.KEY_1, Constants.PAYLOAD_STRING);
		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);

		consumerService.commit(Constants.GROUP_1_ID, Constants.TOPIC_NAME, response.partitionId(), response.offset());
		TopicPartition tp = new TopicPartition(Constants.TOPIC_NAME, response.partitionId());

		assertEquals(response.offset(), coordinatorService.getCommittedOffsets(Constants.GROUP_1_ID, tp));
	}

}
