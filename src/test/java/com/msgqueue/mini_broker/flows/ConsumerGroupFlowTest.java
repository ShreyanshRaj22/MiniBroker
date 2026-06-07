package com.msgqueue.mini_broker.flows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.msgqueue.mini_broker.Constants;
import com.msgqueue.mini_broker.broker.Broker;
import com.msgqueue.mini_broker.model.Message;
import com.msgqueue.mini_broker.partitioner.HashPartitioner;
import com.msgqueue.mini_broker.partitioner.Partitioner;
import com.msgqueue.mini_broker.service.ConsumerService;
import com.msgqueue.mini_broker.service.GroupCoordinatorService;
import com.msgqueue.mini_broker.service.ProducerService;

public class ConsumerGroupFlowTest {

    private Broker broker;
    private GroupCoordinatorService coordinatorService;
    private ConsumerService consumerService;
    private ProducerService producerService;
    private Partitioner partitioner;

    @BeforeEach
    void setup() {
        broker = new Broker();

        coordinatorService =
                new GroupCoordinatorService(broker);

        partitioner = new HashPartitioner();

        producerService =
                new ProducerService(
                        broker,
                        partitioner
                );

        consumerService =
                new ConsumerService(
                        broker,
                        coordinatorService
                );
    }

    @Test
    void shouldProducePollCommitAndPollAgain() {

        broker.createTopic(
                Constants.TOPIC_NAME,
                Constants.THREE_PARTITIONS
        );

        // Register consumer
        coordinatorService.registerConsumer(
                Constants.GROUP_1_ID,
                Constants.CONSUMER_1_ID,
                Constants.TOPIC_NAME
        );

        // Produce messages
        producerService.produce(
                Constants.TOPIC_NAME,
                Constants.KEY_1,
                Constants.PAYLOAD_STRING
        );

        producerService.produce(
                Constants.TOPIC_NAME,
                Constants.KEY_2,
                Constants.PAYLOAD_STRING_2
        );

        // First poll
        List<Message> firstPoll =
                consumerService.poll(
                        Constants.GROUP_1_ID,
                        Constants.CONSUMER_1_ID,
                        Constants.TOPIC_NAME,
                        10
                );

        assertEquals(2, firstPoll.size());

        // Commit offsets for all consumed messages
        for (Message message : firstPoll) {

            int partitionId =
                    partitioner.partition(
                            message.getPartitionKey(),
                            Constants.THREE_PARTITIONS
                    );

            consumerService.commit(
                    Constants.GROUP_1_ID,
                    Constants.TOPIC_NAME,
                    partitionId,
                    message.getOffset() + 1
            );
        }

        // Poll again
        List<Message> secondPoll =
                consumerService.poll(
                        Constants.GROUP_1_ID,
                        Constants.CONSUMER_1_ID,
                        Constants.TOPIC_NAME,
                        10
                );

        assertEquals(0, secondPoll.size());
    }
}
