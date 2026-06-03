package com.msgqueue.mini_broker.model;

import java.util.HashMap;
import java.util.Map;

public class ConsumerGroup {

	private final String groupId;
	private final Map<String, ConsumerMember> members;
	private final Map<TopicPartition, String> partitionAssignments;
	private final Map<TopicPartition, Long> committedOffsets;

	public ConsumerGroup(String groupId){
		this.groupId = groupId;
		this.members = new HashMap<>();
		this.partitionAssignments = new HashMap<>();
		this.committedOffsets = new HashMap<>();
	}

	// getters
	
	public String getGroupId(){
        	return groupId;
    	}

    	public Map<String, ConsumerMember> getMembers(){
        	return members;
    	}

    	public Map<TopicPartition, String> getPartitionAssignments(){
        	return partitionAssignments;
    	}

    	public Map<TopicPartition, Long> getCommittedOffsets(){
        	return committedOffsets;
    	}

}
