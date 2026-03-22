package com.msgqueue.mini_broker.service;

import com.msgqueue.mini_broker.model.*;
import com.msgqueue.mini_broker.broker.Broker;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	private final Broker broker;
	private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

	public ConsumerService(Broker broker){
		this.broker = broker;
	}

	public List<Message> consume(String topicName, int partitionId, long offset, int limit) {

		log.info("topicName: {} partitionId: {} offset: {} limit: {}", topicName, partitionId, offset, limit);
        	Topic topic = broker.getTopic(topicName);
        	Partition partition = topic.getPartition(partitionId);

        	return partition.read(offset, limit);
    }
}
