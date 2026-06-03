package com.msgqueue.mini_broker.model;

public record TopicPartition(String topicName, int partitionId) {}
