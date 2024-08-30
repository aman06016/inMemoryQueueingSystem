package com.design.lld.model;

class JsonConsumer implements Consumer {
    private final String id;
    private final String pattern; // Could be regex or any other condition

    public JsonConsumer(String id, String pattern) {
        this.id = id;
        this.pattern = pattern;
    }

    @Override
    public void consume(Message message) throws Exception {
        // Processing logic here
        if (!message.getPayload().matches(pattern)) {
            throw new Exception("Message doesn't match the pattern");
        }
        // Processing logic
        System.out.println(id + " consumed message: " + message.getId());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean canConsume(Message message) {
        return message.getPayload().matches(pattern);
    }
}
