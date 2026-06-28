package com.msgqueue.mini_broker.model;

public record QueueKey(String topic, int partition){}
