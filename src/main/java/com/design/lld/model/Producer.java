package com.design.lld.model;

import com.design.lld.config.Config;

import java.util.Queue;

class Producer {
    private final Queue<Message> queue;

    public Producer(Queue<Message> queue) {
        this.queue = queue;
    }

    public void produce(Message message) {
        synchronized (queue) {
            if (queue.size() == Config.QUEUE_SIZE) {
                System.out.println("Queue is full. Waiting to produce...");
            }
            queue.add(message);
            queue.notifyAll();
        }
    }
}
