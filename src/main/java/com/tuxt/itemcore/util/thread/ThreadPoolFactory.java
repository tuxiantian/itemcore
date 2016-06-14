package com.tuxt.itemcore.util.thread;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolFactory {
	

	public static ThreadPoolExecutor getThreadPool(ThreadPoolConfig config) {
		ThreadPoolExecutor pool;
		if (config.getHandler() == null) {
			pool = new ThreadPoolExecutor(config.getCorePoolSize(),
					config.getMaximumPoolSize(), config.getKeepAliveTime(),
					config.getUnit(), config.getWorkQueue());
		} else {
			pool = new ThreadPoolExecutor(config.getCorePoolSize(),
					config.getMaximumPoolSize(), config.getKeepAliveTime(),
					config.getUnit(), config.getWorkQueue(),
					config.getHandler());
		}
		return pool;
	}
	 	
}
