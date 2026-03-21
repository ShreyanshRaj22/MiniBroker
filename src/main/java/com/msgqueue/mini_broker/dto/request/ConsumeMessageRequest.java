package com.msgqueue.mini_broker.dto.request;

public class ConsumeMessageRequest {
	private String topic;
	private int partition, offset, limit;

	public ConsumeMessageRequest(){}

	public String getTopic() {return topic;}
	public int getPartition() {return partition;}
	public int getOffset() {return offset;}
	public int getLimit() {return limit;}

	public void setTopic(String topic) { this.topic = topic; }
	public void setPartition(int partition) { this.partition = partition; }
	public void setOffset(int offset) { this.offset = offset; }
	public void setLimit(int limit) { this.limit = limit; }
}
