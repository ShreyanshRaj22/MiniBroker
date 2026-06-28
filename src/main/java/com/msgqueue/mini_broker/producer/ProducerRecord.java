package com.msgqueue.mini_broker.producer;

public record ProducerRecord(String topic, int partition, String key, String payload) {}
