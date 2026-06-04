package com.msgqueue.mini_broker.dto.request;

public record RegisterConsumerRequest(String groupId, String consumerId, String topicName) {}
