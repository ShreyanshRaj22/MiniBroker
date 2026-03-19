package com.msgqueue.mini_broker.service;

import org.springframework.stereotype.Service;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.model.Topic;
import com.msgqueue.mini_broker.model.Partition;

@Service
public class ProducerService {
	private final Broker broker;
	private final Partitioner partitioner;

	public ProducerService(Broker broker, Partitioner partitioner){
		this.broker = broker;
		this.partitioner = partitioner;
	}

	public ProduceResponse produce(String topicName, String key, String payload){
		Topic topic = broker.getTopic(topicName);
		int partitionId = partitioner.partition(topicName, topic.getNumPartitions());
		Partition partition = topic.getPartition(partitionId);
		long offset = partition.append(payload, key);
		return new ProduceResponse(topicName, partitionId, offset);
	}
}

class ProduceResponse {
	public String topicName;
	public int partitionId;
	public long offset;
	public ProduceResponse(String topicName, int partitionId, long offset){
		this.topicName = topicName;
		this.partitionId = partitionId;
		this.offset = offset;
	}
}
