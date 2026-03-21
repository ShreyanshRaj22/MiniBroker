package com.msgqueue.mini_broker.dto.response;

public class ErrorResponse {
    private String message;
    private String code;
    private long timestamp;

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() { return message; }
    public String getCode() { return code; }
    public long getTimestamp() { return timestamp; }
}
