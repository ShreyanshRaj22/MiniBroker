package com.msgqueue.mini_broker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.msgqueue.mini_broker.Constants;
import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.model.ConsumerGroup;
import com.msgqueue.mini_broker.model.TopicPartition;


public class GroupCoordinatorServiceTest {
	Broker broker;
	private GroupCoordinatorService coordinatorService;

	@BeforeEach
	void setup(){
		broker = new Broker();
		coordinatorService = new GroupCoordinatorService(broker);
	}

	@Test
	void shouldRegisterConsumer(){

		broker.createTopic(Constants.TOPIC_NAME, Constants.THREE_PARTITIONS);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);

		ConsumerGroup group = coordinatorService.getGroup(Constants.GROUP_1_ID);

		assertNotNull(group);

		assertTrue(
				group.getMembers().containsKey(Constants.CONSUMER_1_ID)
			  );

	}

	@Test
	void shouldRebalancePartitionsAcrossConsumers(){
		broker.createTopic(Constants.TOPIC_NAME, Constants.FOUR_PARTITIONS);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_2_ID, Constants.TOPIC_NAME);
		
		Set<TopicPartition> all = new HashSet<>();

		Set<TopicPartition> c1 = coordinatorService.getAssignedPartitions(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID);
		Set<TopicPartition> c2 = coordinatorService.getAssignedPartitions(Constants.GROUP_1_ID, Constants.CONSUMER_2_ID);

		all.addAll(c1);

		all.addAll(c2);

		assertEquals(
				Constants.FOUR_PARTITIONS,
				all.size()
			  );


	}

	@Test 
	void shouldRebalancePartitionsWhenConsumerLeaves() {

		broker.createTopic(Constants.TOPIC_NAME, Constants.FOUR_PARTITIONS);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);
		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_2_ID, Constants.TOPIC_NAME);

		coordinatorService.removeConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_2_ID, Constants.TOPIC_NAME);

		Set<TopicPartition> assigned = coordinatorService.getAssignedPartitions(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID);

		assertEquals(Constants.FOUR_PARTITIONS, assigned.size());


	}


	@Test
	void shouldCommitOffset() {

		broker.createTopic(Constants.TOPIC_NAME, Constants.FOUR_PARTITIONS);

		coordinatorService.registerConsumer(Constants.GROUP_1_ID, Constants.CONSUMER_1_ID, Constants.TOPIC_NAME);

		TopicPartition tp = new TopicPartition(Constants.TOPIC_NAME, 0);

		coordinatorService.commitOffsets(Constants.GROUP_1_ID, tp, 10L);
		long offset = coordinatorService.getCommittedOffsets(Constants.GROUP_1_ID, tp);

		assertEquals(offset, 10L);
	}
}
