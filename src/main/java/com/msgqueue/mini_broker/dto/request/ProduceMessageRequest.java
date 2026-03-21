package com.msgqueue.mini_broker.dto.request;

public class ProduceMessageRequest {
	private String topic, key, payload;

	public ProduceMessageRequest() {}

	public String getTopic() {return topic;}
	public String getKey() {return key;}
	public String getPayload() {return payload;}

	public void setTopic(String topic) { this.topic = topic; }
	public void setKey(String key) { this.key= key; }
	public void setPayload(String payload) { this.payload= payload; }
}
