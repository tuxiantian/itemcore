package com.tuxt.itemcore.quartz;

import java.util.Date;
import java.util.Random;

/**
 * <pre> 在未手动加锁的情况下，下一个周期的执行计划开始时，若当前的执行计划未完成则继续当前的执行计划，下一个执行计划废弃。
 * 对于任务类型是固定时间或立即执行的任务，该规则依然使用。因此task类中不必再写手动加锁的代码。
 * 实现方法见com.tuxt.itemcore.quartz.ScanAllTaskJob(L93-L95)</pre>
 * @author Administrator
 *
 */
public class TestTask {

	public void doTask(String taskcode) {
		try {
			System.out.println(String.format("current date is:%s", new Date().toLocaleString()));
			
			Thread.currentThread().sleep(new Random().nextInt(10)*1000);
			for (int i = 0; i < 10; i++) {
				System.out.println(i);
				System.out.println(Thread.currentThread().getId());
			}
		} catch (Exception e) {
		}
	}
}
