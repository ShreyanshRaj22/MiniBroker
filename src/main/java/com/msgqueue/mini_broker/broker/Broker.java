package com.msgqueue.mini_broker.broker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.msgqueue.mini_broker.exception.TopicAlreadyExistsException;
import com.msgqueue.mini_broker.exception.TopicDoesNotExistsException;
import com.msgqueue.mini_broker.model.Topic;

@Component
public class Broker {
	private final Map<String, Topic> topics = new ConcurrentHashMap<>();

	public void createTopic(String topicName, int numPartitions){
		if(topics.containsKey(topicName)){
			throw new TopicAlreadyExistsException("Topic already exists.");
		}

		Topic topic = new Topic(topicName, numPartitions);
		topics.put(topicName, topic);
	}

	public Topic getTopic(String topicName){
		Topic topic = topics.get(topicName);

		if(topic == null){
			throw new TopicDoesNotExistsException("Topic does not exist");
		}

		return topic;
	}
}
