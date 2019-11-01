package com.nagendra547.concurrent;

import java.util.concurrent.Semaphore;

/**
 * Semaphores – Restrict the number of threads that can access a resource.
 * Example, limit max 3 connections to access a resource simultaneously.
 * 
 * @author nagendra
 *
 */
public class SemaphoreExample {
	static Semaphore semaphore = new Semaphore(3);

	public static void main(String[] args) {
		MyThread t1 = new MyThread(semaphore, "t1");
		MyThread t2 = new MyThread(semaphore, "t2");
		MyThread t3 = new MyThread(semaphore, "t3");
		MyThread t4 = new MyThread(semaphore, "t4");
		MyThread t5 = new MyThread(semaphore, "t5");

		Thread t11 = new Thread(t1);
		Thread t22 = new Thread(t2);
		Thread t33 = new Thread(t3);
		Thread t44 = new Thread(t4);
		Thread t55 = new Thread(t5);

		t11.start();
		t22.start();
		t33.start();
		t44.start();
		t55.start();
	}

}

class MyThread implements Runnable {

	private Semaphore semaphore;
	private String name;

	MyThread(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("Acquaring keys for " + name + " ,Keys remaining " + semaphore.availablePermits());
		try {
			semaphore.acquire();
			System.out.println("Acquared Keys for " + name + " ,Keys remaining " + semaphore.availablePermits());
			for (int i = 0; i < 10; i++) {
				System.out.println(name + " performing ,Keys remaining " + semaphore.availablePermits());
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
			System.out.println("Keys released for " + name + " ,Keys remaining " + semaphore.availablePermits());
		}

	}

}
