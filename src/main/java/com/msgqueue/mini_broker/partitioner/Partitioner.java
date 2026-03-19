package com.msgqueue.mini_broker.partitioner;

public interface Partitioner {
	int partition(String key, int numPartitions);
}
