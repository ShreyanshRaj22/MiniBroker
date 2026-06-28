package com.msgqueue.mini_broker.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.msgqueue.mini_broker.model.QueueKey;

public class RecordAccumulator {

    private final ConcurrentHashMap<QueueKey, BlockingQueue<ProducerRecord>> queues =
            new ConcurrentHashMap<>();

    // private String queueKey(String topic, int partition) {
    //     return topic + "-" + partition;
    // }

    public void createQueue(String topic, int partition) {
        queues.putIfAbsent(
                new QueueKey(topic, partition),
                new LinkedBlockingQueue<>()
        );
    }

    public void append(ProducerRecord record) {
        BlockingQueue<ProducerRecord> queue =
                queues.get(new QueueKey(record.topic(), record.partition()));

        if (queue == null) {
            throw new IllegalStateException(
                    "Queue not initialized for topic="
                            + record.topic()
                            + ", partition="
                            + record.partition()
            );
        }

        queue.offer(record);
    }

    public BlockingQueue<ProducerRecord> getQueue(
            String topic,
            int partition) {

        return queues.get(new QueueKey(topic, partition));
    }
}
