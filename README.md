# Mini Broker

> A lightweight, Kafka-inspired in-memory message broker built in Java.

## Overview

Mini Broker replicates core Kafka concepts — topics, partitions, and offset-based consumption — entirely in memory. Designed for learning, testing, or lightweight pub/sub use cases without the overhead of a full Kafka setup.

---

## Features

- [x] Topic creation
- [x] Partitioned message storage
- [x] Offset-based consumption
- [x] Consumer Groups

---

## Planned Structure

Below is the planned source structure:

```
src/
├── controller/
│   └── BrokerController
│
├── dto/
│   ├── request/
│   │   ├── CreateTopicRequest
│   │   ├── ProduceMessageRequest
│   │   ├── ConsumeMessageRequest
│   │   ├── RegisterConsumerRequest
│   │   ├── CommitOffsetRequest
│   │   └── HeartbeatRequest
│   └── response/
│       ├── ProduceMessageResponse
│       ├── PollResponse
│       └── AssignmentResponse
│
├── broker/                        # Storage layer
│   └── Broker
│
├── service/
│   ├── ProducerService
│   ├── ConsumerService
│   └── GroupCoordinatorService
│
├── partitioner/
│   ├── Partitioner
│   ├── HashPartitioner
│   ├── PartitionAssignor
│   └── RoundRobinAssignor
│
├── model/
│   ├── Message
│   ├── Topic
│   ├── Partition
│   ├── ConsumerGroup
│   ├── ConsumerMember
│   ├── TopicPartition
│   └── ConsumerAssignment
│
└── exception/
    ├── TopicNotFoundException
    ├── GroupNotFoundException
    ├── ConsumerNotFoundException
    └── PartitionNotFoundException
```

---

## Getting Started

> Documentation and setup instructions coming with the Consumer Groups release.

---

## Contributing

Contributions and feedback are welcome! Feel free to open an issue or submit a pull request.
