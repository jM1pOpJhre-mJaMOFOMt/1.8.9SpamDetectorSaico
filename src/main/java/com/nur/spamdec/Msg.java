package com.nur.spamdec;

public class Msg {
    private final String mmessage;
    private final long mtimestamp;
    public Msg(String message, long l) {
        mmessage=message;
        mtimestamp=l;
    }
    public String getMessage() {
        return mmessage;
    }
    public long getTimestamp() {
        return mtimestamp;
    }
}
