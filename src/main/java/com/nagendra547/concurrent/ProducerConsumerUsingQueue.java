package com.nagendra547.concurrent;

import java.util.LinkedList;
import java.util.Random;

/**
 * In Java, the object itself is the entity that is shared between threads which
 * allows them to communicate with each other. The threads have no specific
 * knowledge of each other and they can run asynchronously. They run and they
 * lock, wait, and notify on the object that they want to get access to. They
 * have no knowledge of other threads and don't need to know their status. They
 * don't need to know that it is thread2 which is waiting for the resource –
 * they just notify on the resource and whomever it is that is waiting (if
 * anyone) will be notified.<br>
 * https://stackoverflow.com/questions/17840397/concept-behind-putting-wait-notify-methods-in-object-class
 * <br>
 * 
 * A simple producer and consumer problem showcase.<br>
 * If size is full, wait for consumer to consume it.<br>
 * If size is empty, wait for producer to produce it.<br>
 * 
 * 
 * @author nagendra
 *
 */
public class ProducerConsumerUsingQueue {

	public static void main(String[] args) {
		LinkedList<Integer> sharedQueue = new LinkedList<>();
		Producer producer1 = new Producer(sharedQueue);
		Consumer consumer1 = new Consumer(sharedQueue);
		producer1.start();
		consumer1.start();
	}

}

class Producer extends Thread {
	LinkedList<Integer> sharedQueue;

	public Producer(LinkedList<Integer> queue) {
		this.sharedQueue = queue;
	}

	public void run() {
		while (true) {
			try {
				/**
				 * Double lock on sharedQueue to avoid the deadlock. if size is 10 then only
				 * keep it waiting
				 */
				synchronized (sharedQueue) {
					if (sharedQueue.size() == 10) {
						synchronized (sharedQueue) {
							System.out.println("Queue is Full : Waiting for consumer to consume");
							Thread.sleep(100);
							sharedQueue.wait();
						}
					}
				}
				synchronized (sharedQueue) {
					sharedQueue.notify();
					int x = new Random().nextInt(10000);
					System.out.println("Producer produced: " + x);
					sharedQueue.offer(x);
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread {
	LinkedList<Integer> sharedQueue;

	public Consumer(LinkedList<Integer> queue) {
		this.sharedQueue = queue;
	}

	public void run() {
		while (true) {
			try {
				synchronized (sharedQueue) {
					if (sharedQueue.size() == 0) {
						synchronized (sharedQueue) {
							System.out.println("Queue is Empty : Waiting for Producer to produce");
							Thread.sleep(100);
							sharedQueue.wait();
						}
					}
				}
				synchronized (sharedQueue) {
					sharedQueue.notify();
					int x = sharedQueue.poll();
					System.out.println("Consumer Consumed : " + x);
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}