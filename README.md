# Mini Broker

A Kafka-like in-memory message broker built in Java.

## Features (WIP)
- Topic creation
- Partitioned message storage
- Offset-based consumption


## src code structure planned for release with Consumer Groups

├── controller
│   └── BrokerController

├── dto
│   ├── request
│   │   ├── CreateTopicRequest
│   │   ├── ProduceMessageRequest
│   │   ├── ConsumeMessageRequest
│   │   ├── RegisterConsumerRequest
│   │   ├── CommitOffsetRequest
│   │   └── HeartbeatRequest
│   │
│   └── response
│       ├── ProduceMessageResponse
│       ├── PollResponse
│       └── AssignmentResponse
│
├── broker - this is just storage layer
│   └── Broker
│
├── service
│   ├── ProducerService
│   ├── ConsumerService
│   └── GroupCoordinatorService
│
├── partitioner
│   ├── Partitioner
│   ├── HashPartitioner
│   ├── PartitionAssignor
│   └── RoundRobinAssignor
│
├── model
│   ├── Message
│   ├── Topic
│   ├── Partition
│   │
│   ├── ConsumerGroup
│   ├── ConsumerMember
│   ├── TopicPartition
│   └── ConsumerAssignment
│
└── exception
    ├── TopicNotFoundException
    ├── GroupNotFoundException
    ├── ConsumerNotFoundException
    └── PartitionNotFoundException
