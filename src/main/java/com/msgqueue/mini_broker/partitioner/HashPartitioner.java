package com.msgqueue.mini_broker.partitioner;

import org.springframework.stereotype.Component;

@Component
public class HashPartitioner implements Partitioner {

	@Override
	public int partition(String key, int numPartitions){
		if(key == null){
			return (int) (Math.random() * numPartitions);
		}

		return Math.abs(key.hashCode()) % numPartitions;
	}
}
