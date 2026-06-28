package com.msgqueue.mini_broker.producer;

import java.util.concurrent.BlockingQueue;

import com.msgqueue.mini_broker.model.Partition;

public class Sender implements Runnable {
	private final Partition partition;
	private final BlockingQueue<ProducerRecord> queue;

	public Sender(Partition partition,
			BlockingQueue<ProducerRecord> queue){
		this.queue = queue;
		this.partition = partition;
	}

	@Override
	public void run(){
		while (!Thread.currentThread().isInterrupted()) {
			try {
				ProducerRecord record = queue.take();

				partition.append(
						record.payload(),
						record.key()
						);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
