package com.msgqueue.mini_broker.model;

import java.util.HashMap;
import java.util.Map;

public class Topic {
	private final String name;
	private final Map<Integer, Partition> partitions;

	public Topic(String name, int numPartitions){
		partitions = new HashMap<>();
		this.name = name;
		for(int i=0;i<numPartitions;i++){
			partitions.put(i, new Partition(i));
		}
	}

	public Partition getPartition(int partitionId){
		return partitions.get(partitionId);
	}

	public int getNumPartitions() { return partitions.size(); }
	public String getName() {return name;}
}
