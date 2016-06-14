package com.tuxt.itemcore.util.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
/**
 * 线程池异常处理类
 * @author 
 *
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
	private static final Logger logger = LoggerFactory.getUtilLog(RejectedExecutionHandler.class);
	private int errorHandleSleepTime;
    public int getErrorHandleSleepTime() {
		return errorHandleSleepTime;
	}
	public void setErrorHandleSleepTime(int errorHandleSleepTime) {
		this.errorHandleSleepTime = errorHandleSleepTime;
	}
	public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        System.out.println("Begin exception handler-----------");
        try {
        	//Integer.valueOf(ThreadPoolConfigPropertiesUtil.getString("ERROR_HANDLE_SLEEP_TIME"))
			Thread.sleep(this.errorHandleSleepTime);
		} catch (Exception e) {
			logger.error("MyRejectedExecutionHandler", "", e);
		}
        //执行失败任务
        new Thread(task,"exception by pool").start();
        //打印线程池的对象
        //System.out.println("The pool RejectedExecutionHandler = "+executor.toString());
    }
}