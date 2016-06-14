package com.tuxt.itemcore.util.thread;

import com.tuxt.itemcore.util.SpringApplicationUtil;

public class ThreadPoolTest{

	public void init() {
		ThreadPoolExecutorHelper threadPoolHelper=	(ThreadPoolExecutorHelper) SpringApplicationUtil.getBean("threadPoolExecutorHelper1");
		threadPoolHelper.addTask(new Runnable() {
			
				@Override
				public void run() {
					int sum=0;
					for (int i = 0; i < 100; i++) {
						sum+=i;
						System.out.println(Thread.currentThread().getName());
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(sum);
				}
			});
	}
	public static void main(String[] args) {
	
	}
}
