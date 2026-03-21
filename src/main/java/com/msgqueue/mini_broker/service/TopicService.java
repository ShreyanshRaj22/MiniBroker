package com.msgqueue.mini_broker.service;

import org.springframework.stereotype.Service;
import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.model.Topic;

@Service
public class TopicService {
	private final Broker broker;
	public TopicService(Broker broker){
		this.broker = broker;
	}

	public CreateTopicResponse createTopic(String topicName, int numPartitions){
		 	broker.createTopic(topicName, numPartitions);
			return (new CreateTopicResponse(topicName));
	}
	public Topic getTopic(String topicName){
		return broker.getTopic(topicName);
	}
}


class CreateTopicResponse {
	private final String topicName;

	public CreateTopicResponse(String topicName){
		this.topicName = topicName;
	}

	public String getTopicName() {return topicName;}
}
