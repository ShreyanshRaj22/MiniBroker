package com.msgqueue.mini_broker.service;

import com.msgqueue.mini_broker.model.*;
import com.msgqueue.mini_broker.broker.Broker;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	private final Broker broker;

	public ConsumerService(Broker broker){
		this.broker = broker;
	}

	public List<Message> consume(String topicName, int partitionId, long offset, int limit) {
        	Topic topic = broker.getTopic(topicName);
        	Partition partition = topic.getPartition(partitionId);

        	return partition.read(offset, limit);
    }
}
