package com.nagendra547.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Producer consumer using blocking queue.
 * @author nagendra
 *
 */
public class ProducerConsumerBlockingQueue {

	public static void main(String[] args) {
		BlockingQueue<Integer> bq = new LinkedBlockingDeque<>(10);

		Producer1 t1 = new Producer1(bq);
		Consumer1 t2 = new Consumer1(bq);
		t2.start();
		t1.start();

	}

}

class Producer1 extends Thread {
	BlockingQueue<Integer> bq;

	Producer1(BlockingQueue<Integer> bq) {
		this.bq = bq;
	}

	@Override
	public void run() {
		while (true) {
			int x = new Random().nextInt(10000);
			bq.offer(x);
			System.out.println("Producer produced: " + x+" ,Current Size: "+bq.size());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Consumer1 extends Thread {
	BlockingQueue<Integer> bq;

	Consumer1(BlockingQueue<Integer> bq) {
		this.bq = bq;
	}

	@Override
	public void run() {
		while (true) {
			if (bq.size() != 0) {
				int x = bq.poll();
				System.out.println("Consumer consumed: " + x);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
