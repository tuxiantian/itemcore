package com.tuxt.itemcore.util.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorHelper {
		// 线程池
		private ThreadPoolExecutor pool;
		public ThreadPoolExecutor getPool() {
			return pool;
		}

		public void setPool(ThreadPoolExecutor pool) {
			this.pool = pool;
		}
		public ThreadPoolExecutorHelper(){
			
		}
		public ThreadPoolExecutorHelper(ThreadPoolExecutor pool) {
			this.pool=pool;
		}
		/**
		 * 添加线程池任务
		 * 
		 * @param run
		 */
		public synchronized void addTask(Runnable run) {
			pool.execute(run);
		}

		/**
		 * 添加线程池任务
		 * 
		 * @param runs
		 */
		public synchronized void addTask(List<Runnable> runs) {
			if (runs != null) {
				for (Runnable r : runs) {
					this.addTask(r);
				}
			}
		}

		public synchronized List<Future<?>> addFutureTask(List<Callable<?>> tasks) {
			List<Future<?>> futures=new ArrayList<>();
			for (Callable<?> task : tasks) {
				Future<?> future=this.addFutureTask(task);
				futures.add(future);
			}
			return futures;
		}
		public synchronized Future<?> addFutureTask(Callable<?> task) {			
			return	pool.submit(task);
		}
		/**
		 * 关闭线程池
		 */
		public void closePool() {
			pool.shutdown();
		}
}
