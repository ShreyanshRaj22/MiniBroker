package com.msgqueue.mini_broker.dto.request;

public record PollRequest(String groupId, String consumerId, String topicName, int limit) {}
