package com.tuxt.itemcore.util.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
	//池中所保存的线程数，包括空闲线程。
    private int corePoolSize;
    //池中允许的最大线程数。
    private int maximumPoolSize;
    //当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
    private long keepAliveTime; 
    //参数的时间单位。默认为5秒
    private TimeUnit unit;
    //执行前用于保持任务的队列。此队列仅由保持 execute 方法提交的 Runnable 任务。
    private LinkedBlockingQueue<Runnable> workQueue;
    //由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。 
    private RejectedExecutionHandler handler;
   
    private int workQueueCapacity;
    
  

	public int getWorkQueueCapacity() {
		return workQueueCapacity;
	}

	public void setWorkQueueCapacity(int workQueueCapacity) {
		this.workQueueCapacity = workQueueCapacity;
	}

	public ThreadPoolConfig(){
         
    }
     
    public ThreadPoolConfig(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, int workQueueCapacity,RejectedExecutionHandler handler
			) {
		super();
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.unit = TimeUnit.MILLISECONDS;
		this.workQueue = new LinkedBlockingQueue<Runnable>(workQueueCapacity);
		this.handler = handler;
		this.workQueueCapacity=workQueueCapacity;
	}

	public ThreadPoolConfig(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			int workQueueCapacity
			) {
		super();
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.unit = unit;
		this.workQueue = new LinkedBlockingQueue<Runnable>(workQueueCapacity);
		this.handler = new MyRejectedExecutionHandler();
		this.workQueueCapacity=workQueueCapacity;
	}

    public int getCorePoolSize() {
        return corePoolSize;
    }
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }
    public long getKeepAliveTime() {
        return keepAliveTime;
    }
    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
    public TimeUnit getUnit() {
        return unit;
    }
    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
    public BlockingQueue<Runnable> getWorkQueue() {
        return workQueue;
    }
    public void setWorkQueue(LinkedBlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
    }
    public RejectedExecutionHandler getHandler() {
        return handler;
    }
    public void setHandler(RejectedExecutionHandler handler) {
        this.handler = handler;
    }  
}
