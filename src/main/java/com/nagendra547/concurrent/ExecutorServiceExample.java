package com.nagendra547.concurrent;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author nagendra
 * 
 *         ExecutorService example.
 */
public class ExecutorServiceExample {
	public static void main(String[] args) {
		System.out.println("Inside : " + Thread.currentThread().getName());

		System.out.println("Creating Executor Service with a thread pool of Size 5");
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		int n = 100;
		Runnable[] tasks = new Runnable[n];
		for (int i = 0; i < n; i++) {
			final int count = i;
			tasks[i] = () -> {
				System.out.println("Executing Task" + count + " inside : " + Thread.currentThread().getName());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ex) {
					throw new IllegalStateException(ex);
				}
			};
		}

//		System.out.println("Simple way of Submitting the tasks for execution...");
//		for (int i = 0; i < n; i++) {
//			executorService.submit(tasks[i]);
//		}
		System.out.println("All Tasks submitted.");
		CompletableFuture<?>[] futures = Arrays.stream(tasks)
				.map(task -> CompletableFuture.runAsync(task, executorService)).toArray(CompletableFuture[]::new);

		CompletableFuture.allOf(futures).join();
		System.out.println(
				"Now let's join it with current thread, below codes would be executed only when all tasks are done");
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Successfull sleeping for 5 sec");

		System.out.println("Shutting Down executor services now");
		executorService.shutdown();
		System.out.println("Done");
	}
}
