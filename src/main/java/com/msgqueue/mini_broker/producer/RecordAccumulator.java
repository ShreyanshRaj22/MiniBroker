package com.msgqueue.mini_broker.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class RecordAccumulator {
	private final ConcurrentHashMap<Integer, BlockingQueue<ProducerRecord>> queues;
	public RecordAccumulator(int partitions){
		queues = new ConcurrentHashMap<>();

		for(int i=0;i<partitions;i++){
			queues.put(i, new LinkedBlockingQueue<>());
		}
	}

	public void append(ProducerRecord record){
		queues.get(record.partition()).offer(record);
	}

	public BlockingQueue<ProducerRecord> getQueue(int partition){
		return queues.get(partition);
	}
}
