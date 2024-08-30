package com.design.lld.model;

interface Consumer {
    void consume(Message message) throws Exception;
    String getId();
    boolean canConsume(Message message);
}
