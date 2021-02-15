package com.nur.spamdec;

public class Message {
    private final String message;
    private final long timestamp;

    public Message(String message, long l) {
        this.message = message;
        timestamp = l;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
