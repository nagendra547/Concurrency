package com.nagendra547.concurrent;

/**
 * Sample example showcasing thread updating shared variables.
 * 
 * Incrementing Counter: 0 inside : Thread-1
Incrementing Counter: 0 inside : Thread-0
Increment successfull: 1 inside : Thread-0
Incrementing Counter: 1 inside : Thread-0
Increment successfull: 1 inside : Thread-1
Incrementing Counter: 1 inside : Thread-1
Increment successfull: 3 inside : Thread-0
Increment successfull: 3 inside : Thread-1
Incrementing Counter: 3 inside : Thread-1
Incrementing Counter: 3 inside : Thread-0
Increment successfull: 4 inside : Thread-0
Increment successfull: 4 inside : Thread-1
Incrementing Counter: 4 inside : Thread-0
Incrementing Counter: 4 inside : Thread-1
Increment successfull: 5 inside : Thread-1
Increment successfull: 5 inside : Thread-0
Incrementing Counter: 5 inside : Thread-1
Incrementing Counter: 5 inside : Thread-0
Increment successfull: 6 inside : Thread-1
Increment successfull: 7 inside : Thread-0

It should have been updated to 10.

 * @author nagendra
 *
 */
public class SampleThreadSafety1 {

	public static void main(String[] args) {
		System.out.println("Starting Counter");
		final Counter counter = new Counter();
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<5;i++)
					counter.increment();
			}
		};
		
		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<5;i++)
					counter.increment();
			}
		};
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		thread1.start();
		thread2.start();
		
	}

}

class Counter{
	int count =0;
	
	public void increment() {
		System.out.println("Incrementing Counter: " + count + " inside : " + Thread.currentThread().getName());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count++;
		System.out.println("Increment successfull: " + count + " inside : " + Thread.currentThread().getName());
	}
	
	
}
