package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.model.ConsumerGroup;
import com.msgqueue.mini_broker.model.TopicPartition;


public class GroupCoordinatorServiceTest {
	Broker broker;
	private GroupCoordinatorService coordinatorService;

	private static final String TOPIC_NAME = "orders";

	private static final int THREE_PARTITIONS = 3;
	private static final int FOUR_PARTITIONS = 4;
	
	private static final String GROUP_1_ID = "group-1";
	private static final String GROUP_2_ID = "group-2";

	private static final String CONSUMER_1_ID = "consumer-1";
	private static final String CONSUMER_2_ID = "consumer-2";

	@BeforeEach
	void setup(){
		broker = new Broker();
		coordinatorService = new GroupCoordinatorService(broker);
	}

	@Test
	void shouldRegisterConsumer(){

		broker.createTopic(TOPIC_NAME, THREE_PARTITIONS);

		coordinatorService.registerConsumer(GROUP_1_ID, CONSUMER_1_ID, TOPIC_NAME);

		ConsumerGroup group = coordinatorService.getGroup(GROUP_1_ID);

		assertNotNull(group);

		assertTrue(
				group.getMembers().containsKey(CONSUMER_1_ID)
			  );

	}

	@Test
	void shouldRebalancePartitionsAcrossConsumers(){
		broker.createTopic(TOPIC_NAME, FOUR_PARTITIONS);

		coordinatorService.registerConsumer(GROUP_1_ID, CONSUMER_1_ID, TOPIC_NAME);

		coordinatorService.registerConsumer(GROUP_1_ID, CONSUMER_2_ID, TOPIC_NAME);
		
		Set<TopicPartition> all = new HashSet<>();

		Set<TopicPartition> c1 = coordinatorService.getAssignedPartitions(GROUP_1_ID, CONSUMER_1_ID);
		Set<TopicPartition> c2 = coordinatorService.getAssignedPartitions(GROUP_1_ID, CONSUMER_2_ID);

		all.addAll(c1);

		all.addAll(c2);

		assertEquals(
				FOUR_PARTITIONS,
				all.size()
			  );


	}

	@Test 
	void shouldRebalancePartitionsWhenConsumerLeaves() {

		broker.createTopic(TOPIC_NAME, FOUR_PARTITIONS);

		coordinatorService.registerConsumer(GROUP_1_ID, CONSUMER_1_ID, TOPIC_NAME);
		coordinatorService.registerConsumer(GROUP_1_ID, CONSUMER_2_ID, TOPIC_NAME);

		coordinatorService.removeConsumer(GROUP_1_ID, CONSUMER_2_ID, TOPIC_NAME);

		Set<TopicPartition> assigned = coordinatorService.getAssignedPartitions(GROUP_1_ID, CONSUMER_1_ID);

		assertEquals(FOUR_PARTITIONS, assigned.size());


	}


	@Test
	void shouldCommitOffset() {

		broker.createTopic(TOPIC_NAME, FOUR_PARTITIONS);

		coordinatorService.registerConsumer(GROUP_1_ID, CONSUMER_1_ID, TOPIC_NAME);

		TopicPartition tp = new TopicPartition(TOPIC_NAME, 0);

		coordinatorService.commitOffsets(GROUP_1_ID, tp, 10L);
		long offset = coordinatorService.getCommittedOffsets(GROUP_1_ID, tp);

		assertEquals(offset, 10L);
	}
}
