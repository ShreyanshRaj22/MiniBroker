package com.msgqueue.mini_broker.model;

public class Message {
	private final long offset;
	private final String payload, partitionKey;
	private final long timestamp;

	public Message(long offset, String payload, String partitionKey ){
		this.offset = offset;
		this.payload = payload;
		this.partitionKey = partitionKey;
		this.timestamp = System.currentTimeMillis();
	}

	public long getOffset() { return offset; }
	public String getPayload() { return payload; }
	public String getPartitionKey() { return partitionKey; }
	public long getCreatedAt() { return timestamp; }
}
