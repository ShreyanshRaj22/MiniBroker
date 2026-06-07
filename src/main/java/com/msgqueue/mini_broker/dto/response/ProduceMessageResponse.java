package com.msgqueue.mini_broker.dto.response;

public record ProduceMessageResponse(long offset, int partitionId) {}
