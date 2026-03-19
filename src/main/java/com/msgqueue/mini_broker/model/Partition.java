package com.msgqueue.mini_broker.model;

import java.util.ArrayList;
import java.util.List;

public class Partition {
	private final int partitionId;
	private final List<Message> log;

	public Partition(int partitionId){
		log = new ArrayList<>();
		this.partitionId = partitionId;
	}

	public int getPartitionId() { return partitionId; }

	public synchronized long append(String payload, String partitionKey){
		long offset = log.size();
		Message msg = new Message(offset, payload, partitionKey);
		log.add(msg);
		return offset;
	}

	public synchronized List<Message> read(long offset, int limit){
		int start = (int) offset;
		int end = Math.min(limit+start, log.size());
		return log.subList(start, end);
	}
}
