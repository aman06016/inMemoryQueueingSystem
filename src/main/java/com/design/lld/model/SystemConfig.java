package com.design.lld.model;

import com.design.lld.config.Config;

import java.util.Arrays;

class SystemConfig {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(Config.QUEUE_SIZE);

        // Define consumers
        Consumer consumerA = new JsonConsumer("ConsumerA", ".*payment.*");
        Consumer consumerB = new JsonConsumer("ConsumerB", ".*order.*");
        Consumer consumerC = new JsonConsumer("ConsumerC", ".*refund.*");

        queue.addConsumer(consumerA);
        queue.addConsumer(consumerB);
        queue.addConsumer(consumerC);

        // Define dependencies
        queue.addDependency("ConsumerC", Arrays.asList("ConsumerA", "ConsumerB"));

        Producer producer = new Producer(queue.getQueue());

        // Simulate message production
        producer.produce(new Message("1", "{\"type\":\"payment\"}"));
        producer.produce(new Message("2", "{\"type\":\"order\"}"));
        producer.produce(new Message("3", "{\"type\":\"refund\"}"));

        // Start processing messages
        queue.startProcessing();
    }
}
