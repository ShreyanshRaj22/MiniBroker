package com.msgqueue.mini_broker.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.model.ConsumerGroup;
import com.msgqueue.mini_broker.model.ConsumerMember;
import com.msgqueue.mini_broker.model.Partition;
import com.msgqueue.mini_broker.model.Topic;
import com.msgqueue.mini_broker.model.TopicPartition;

@Service
public class GroupCoordinatorService {

	private final Broker broker;
	private final Map<String, ConsumerGroup> groups;

	public GroupCoordinatorService(Broker broker){
		this.broker = broker;
		this.groups = new ConcurrentHashMap<>();
	}

	public void registerConsumer(String groupId, String consumerId, String topicName){
		ConsumerGroup group = groups.computeIfAbsent(groupId, ConsumerGroup::new);

		group.getMembers().put(consumerId, new ConsumerMember(consumerId));

		rebalance(group, topicName);
	}

	public void removeConsumer(String groupId, String consumerId, String topicName){
		ConsumerGroup group = groups.get(groupId);

		if(group == null) return;

		group.getMembers().remove(consumerId);

		rebalance(group, topicName);
	}

	// hardcoded strategy of round robin -> later to move to different assingment strategies
	public void rebalance(ConsumerGroup group, String topicName){
		Topic topic = broker.getTopic(topicName);

		List<String> consumers = new ArrayList<>(group.getMembers().keySet());

		group.getPartitionAssignments().clear();
		
		if(consumers.isEmpty()){
			return;
		}

		for(String consumer: consumers){
			group.getPartitionAssignments().put(consumer, new HashSet<>());
		}

		int idx = 0;

		for(Partition partition: topic.getPartitions()){
			String consumer = consumers.get(idx % consumers.size());

			TopicPartition tp = new TopicPartition(topicName, partition.getPartitionId());

			group.getPartitionAssignments().get(consumer).add(tp);

			idx++;
		}

	}

	public Set<TopicPartition> getAssignedPartitions(String groupId, String consumerId){
		ConsumerGroup group = groups.get(groupId);

		if(group == null) return Collections.emptySet();

		return group.getPartitionAssignments().getOrDefault(consumerId, Collections.emptySet());
	}

	public void commitOffsets(String groupId, TopicPartition topicPartition, long offset){
		ConsumerGroup group = groups.get(groupId);

		if(group == null) return;

		group.getCommittedOffsets().put(topicPartition, offset);
	}

	public long getCommittedOffsets(String groupId, TopicPartition topicPartition){
		ConsumerGroup group = groups.get(groupId);

		if(group == null) return 0L;

		return group.getCommittedOffsets().getOrDefault(topicPartition, 0L);
	}

	// testing purpose
	public Map<String, ConsumerGroup> getGroups(){
		return groups;
	}

	public ConsumerGroup getGroup(String groupId){
		return groups.get(groupId);
	}
}
