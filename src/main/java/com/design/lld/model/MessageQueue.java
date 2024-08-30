package com.design.lld.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class MessageQueue {
    private final Queue<Message> queue;
    private final Map<String, Consumer> consumers;
    private final Map<String, List<String>> dependencies;

    public MessageQueue(int size) {
        this.queue = new LinkedList<>();
        this.consumers = new ConcurrentHashMap<>();
        this.dependencies = new HashMap<>();
    }

    public void addConsumer(Consumer consumer) {
        consumers.put(consumer.getId(), consumer);
    }

    public void addDependency(String consumerId, List<String> dependentOn) {
        dependencies.put(consumerId, dependentOn);
    }

    public void startProcessing() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Message message = queue.poll();
                processMessage(message);
            }
        }
    }

    private void processMessage(Message message) {
        for (Consumer consumer : consumers.values()) {
            if (consumer.canConsume(message) && canProcess(consumer.getId(), message.getId())) {
                try {
                    consumer.consume(message);
                    System.out.println(consumer.getId() + " processed " + message.getId());
                } catch (Exception e) {
                    retryProcessing(consumer, message, 3);
                }
            }
        }
    }

    private boolean canProcess(String consumerId, String messageId) {
        List<String> deps = dependencies.get(consumerId);
        if (deps == null) return true;

        for (String dep : deps) {
            // Check if dependent consumers have processed the message
            // This needs a mechanism to track message processing history
        }
        return true;
    }

    private void retryProcessing(Consumer consumer, Message message, int retries) {
        while (retries > 0) {
            try {
                consumer.consume(message);
                System.out.println("Retry success: " + consumer.getId() + " processed " + message.getId());
                return;
            } catch (Exception e) {
                retries--;
                System.out.println("Retrying... " + retries + " attempts left.");
            }
        }
        System.out.println("Failed to process: " + consumer.getId() + " for message " + message.getId());
    }

    public Queue  getQueue(){
        return queue;
    }
}
