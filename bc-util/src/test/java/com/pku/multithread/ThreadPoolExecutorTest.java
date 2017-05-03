package com.pku.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 固定线程池
 */
public class ThreadPoolExecutorTest {

	public static void main(String[] args) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}