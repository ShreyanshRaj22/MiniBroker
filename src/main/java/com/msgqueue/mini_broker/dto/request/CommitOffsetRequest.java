package com.msgqueue.mini_broker.dto.request;


public record CommitOffsetRequest(String groupId, String topicName, int partitionId, long offset) {}
