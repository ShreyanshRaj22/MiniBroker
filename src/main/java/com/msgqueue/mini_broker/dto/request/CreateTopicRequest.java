package com.msgqueue.mini_broker.dto.request;


public class CreateTopicRequest {
	private String topic;
	private int partitions;

	public CreateTopicRequest() {}

	public String getTopic() {return topic;}
	public int getPartitions() {return partitions;}

	public void setTopic(String topic) {this.topic = topic;}
	public void setPartitions(int partitions) {this.partitions = partitions;}
}
