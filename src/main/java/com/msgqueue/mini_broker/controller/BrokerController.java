package com.msgqueue.mini_broker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.dto.request.CommitOffsetRequest;
import com.msgqueue.mini_broker.dto.request.ConsumeMessageRequest;
import com.msgqueue.mini_broker.dto.request.CreateTopicRequest;
import com.msgqueue.mini_broker.dto.request.PollRequest;
import com.msgqueue.mini_broker.dto.request.ProduceMessageRequest;
import com.msgqueue.mini_broker.dto.request.RegisterConsumerRequest;
import com.msgqueue.mini_broker.dto.request.RemoveConsumerRequest;
import com.msgqueue.mini_broker.model.Message;
import com.msgqueue.mini_broker.model.Topic;
import com.msgqueue.mini_broker.service.ConsumerService;
import com.msgqueue.mini_broker.service.GroupCoordinatorService;
import com.msgqueue.mini_broker.service.ProducerService;

@RestController
@RequestMapping("/api")
public class BrokerController {
	private final Broker broker;
	private final ProducerService producerService;
	private final ConsumerService consumerService;
	private final GroupCoordinatorService coordinatorService;

	public BrokerController(Broker broker, ProducerService producerService, ConsumerService consumerService, GroupCoordinatorService coordinatorService){
		this.broker = broker;
		this.producerService = producerService;
		this.consumerService = consumerService;
		this.coordinatorService = coordinatorService;
	}

	@PostMapping("/createTopic")
	public String createTopic(@RequestBody CreateTopicRequest request){
		broker.createTopic(request.getTopic(), request.getPartitions());
		return "Topic Created: "+request.getTopic();
	}

	@PostMapping("/produceMessage")
	public Map<String, Object> produceMsg(@RequestBody ProduceMessageRequest request){
		String topic = request.getTopic();
		String key = request.getKey();
		String payload = request.getPayload();

		long offset = producerService.produce(topic, key, payload);

		Map<String, Object> response = new HashMap<>();
		response.put("offset", offset);

		return response;
	}

	@PostMapping("/consumeMessage")
	public List<Message> consumeMsg(@RequestBody ConsumeMessageRequest request){
		String topicName = request.getTopic();
		int partition = request.getPartition();
		int offset = request.getOffset();
		int limit = request.getLimit();

		List<Message> response = consumerService.consume(topicName, partition, offset, limit);
		return response;
	}

	@PostMapping("/registerConsumer")
	public String registerConsumer(@RequestBody RegisterConsumerRequest request){
		coordinatorService.registerConsumer(request.groupId(), request.consumerId(), request.topicName());

		return "Consumer Registered";
	}

	@PostMapping("/removeConsumer")
	public String removeConsumer(@RequestBody RemoveConsumerRequest request){
		coordinatorService.removeConsumer(request.groupId(), request.consumerId(), request.topicName());

		return "Consumer Removed";
	}

	@PostMapping("/poll")
	public List<Message> poll(@RequestBody PollRequest request){
		return consumerService.poll(request.groupId(), request.consumerId(), request.topicName(), request.limit());
	}

	@PostMapping("/commitOffset")
	public String commitOffset(@RequestBody CommitOffsetRequest request){
		consumerService.commit(
				request.groupId(),
				request.topicName(),
				request.partitionId(),
				request.offset()
				);
		return "Offset Committed";
	}

	// testing purposes 
	@GetMapping("/allTopics")
	public List<Topic> getAllTopics(){
		return broker.getAllTopicsTest();
	}

	@GetMapping("/groups")
	public Object getGroups(){
		return coordinatorService.getGroups();
	}
}
