package com.msgqueue.mini_broker.service;

import com.msgqueue.mini_broker.model.*;
import com.msgqueue.mini_broker.broker.Broker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	private final Broker broker;

	private final GroupCoordinatorService coordinatorService;
	private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

	public ConsumerService(Broker broker, GroupCoordinatorService coordinatorService){
		this.broker = broker;
		this.coordinatorService = coordinatorService;
	}

	public List<Message> consume(String topicName, int partitionId, long offset, int limit) {

		log.info("topicName: {} partitionId: {} offset: {} limit: {}", topicName, partitionId, offset, limit);
        	Topic topic = broker.getTopic(topicName);
        	Partition partition = topic.getPartition(partitionId);

        	return partition.read(offset, limit);
	}

	public List<Message> poll(String groupId, String consumerId, String topicName, int limit){
		Set<TopicPartition> assigned = coordinatorService.getAssignedPartitions(groupId, consumerId);

		List<Message> result = new ArrayList<>();
		for(TopicPartition tp: assigned){

			if(!tp.topicName().equals(topicName)){
				continue;
			}

			long offset = coordinatorService.getCommittedOffsets(groupId, tp);

			Topic topic = broker.getTopic(tp.topicName());

			Partition partition = topic.getPartition(tp.partitionId());

			List<Message> messages = partition.read(offset, limit);

			result.addAll(messages);
		}

		return result;
	}

	public void commit(String groupId, String topicName, int partitionId, long offset){
		TopicPartition topicPartition = new TopicPartition(topicName, partitionId);

		coordinatorService.commitOffsets(groupId, topicPartition, offset);
	}

}
