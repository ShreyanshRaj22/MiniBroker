package com.msgqueue.mini_broker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.exception.BadRequestException;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.model.Topic;
import com.msgqueue.mini_broker.model.Partition;

@Service
public class ProducerService {
	private final Broker broker;
	private final Partitioner partitioner;
	private static final Logger log = LoggerFactory.getLogger(ProducerService.class);

	public ProducerService(Broker broker, Partitioner partitioner){
		this.broker = broker;
		this.partitioner = partitioner;
	}

	public long produce(String topicName, String key, String payload){

		if(topicName == "" || topicName == null || payload == null || payload == ""){
			String errorMsg = "Invalid req: topicName: {"+topicName+"} payload: {"+payload+"}";
			log.error(errorMsg);
			throw new BadRequestException(errorMsg);
		}
		Topic topic = broker.getTopic(topicName);
		int partitionId = partitioner.partition(topicName, topic.getNumPartitions());
		Partition partition = topic.getPartition(partitionId);
		long offset = partition.append(payload, key);

		log.info("Offset: {} topicName: {} payload: {}", offset, topicName, payload);
		return offset;
	}
}
