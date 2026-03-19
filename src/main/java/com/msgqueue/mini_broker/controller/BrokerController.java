package com.msgqueue.mini_broker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.model.Message;
import com.msgqueue.mini_broker.service.ConsumerService;
import com.msgqueue.mini_broker.service.ProducerService;

@RestController
@RequestMapping("/api")
public class BrokerController {
	private final Broker broker;
	private final ProducerService producerService;
	private final ConsumerService consumerService;

	public BrokerController(Broker broker, ProducerService producerService, ConsumerService consumerService){
		this.broker = broker;
		this.producerService = producerService;
		this.consumerService = consumerService;
	}

	@PostMapping("/createTopic")
	public String createTopic(@RequestParam String topic, @RequestParam int partitions){
		broker.createTopic(topic, partitions);
		return "Topic Created: " + topic;
	}

	@PostMapping("/produceMessage")
	public Map<String, Object> produceMsg(@RequestBody Map<String, String> request){
		String topic = request.get("topic");
		String key = request.get("partitionKey");
		String payload = request.get("payload");

		long offset = producerService.produce(topic, key, payload);

		Map<String, Object> response = new HashMap<>();
		response.put("offset", offset);

		return response;
	}

	@PostMapping("/consumeMessage")
	public List<Message> consumeMsg(@RequestBody Map<String, Object> request){
		String topicName = (String) request.get("topic");
		int partition = (int) request.get("partition");
		int offset = (int) request.get("offset");
		int limit = (int) request.get("limit");

		List<Message> response = consumerService.consume(topicName, partition, offset, limit);
		return response;
	}
}
