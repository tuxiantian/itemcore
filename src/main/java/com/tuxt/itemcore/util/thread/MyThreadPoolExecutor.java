package com.tuxt.itemcore.util.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyThreadPoolExecutor extends ThreadPoolExecutor {
	private final ThreadLocal<Long> startTime=new ThreadLocal<Long>();
	private final AtomicLong numTasks=new AtomicLong();
	private final AtomicLong totalTime=new AtomicLong();
	
	private static final Log log = LogFactory.getLog(MyThreadPoolExecutor.class);
	public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		startTime.set(System.currentTimeMillis());
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try{
			long taskEndTime=System.currentTimeMillis();
			long taskTime=taskEndTime-startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.info(String.format("taskStartTime:%s,taskEndTime:%s,cost time:%s ms.", startTime.get(),taskEndTime,taskTime));
		}finally{
			super.afterExecute(r, t);
		}
		
	}
	@Override
	protected void terminated() {
		try{
			log.info(String.format("Terminated:avg time=%s ms", totalTime.get()/numTasks.get()));
		}finally{
			super.terminated();
		}
	}
	
}
