# Mini Broker

> A lightweight, Kafka-inspired in-memory message broker built in Java.

## Overview

Mini Broker replicates core Kafka concepts — topics, partitions, and offset-based consumption — entirely in memory. Designed for learning, testing, or lightweight pub/sub use cases without the overhead of a full Kafka setup.

## Update being worked on 

Currently working on removing the in-memory data persistance and move it to a file based structure.

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

1. Clone the repo.
2. mvn clean install
3. mvn spring-boot:run 

---

## Sidenote

Currently the project does not include a docker or an image setup as the current complexity does not demand for it, however will transition to an image based setup as per requirements that may imerge in the future.

## Contributing

Contributions and feedback are welcome! Feel free to open an issue or submit a pull request. Do make sure to write corresponding test cases, mandatory for services and preferred for the rest.
