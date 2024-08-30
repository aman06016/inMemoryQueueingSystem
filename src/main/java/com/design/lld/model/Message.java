package com.design.lld.model;

class Message {
    private final String id;
    private final String payload; // JSON string

    public Message(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    public String getId() { return id; }
    public String getPayload() { return payload; }
}
