package com.msgqueue.mini_broker.dto.request;

public record RemoveConsumerRequest(String groupId, String consumerId, String topicName) {}
