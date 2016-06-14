package com.tuxt.itemcore.quartz;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 对周期执行的任务加锁，避免数据重复处理
 * @author tuxiantian@163.com
 *
 */
public abstract class CycleProcessTask {
	protected static final ConcurrentHashMap<String,String> lockMap = new ConcurrentHashMap<String,String>();
	protected static final String LOCK_FLAG = "Y";
	protected static final String UN_LOCK_FLAG = "N";

	protected synchronized boolean isLock(String busiCode) {
		if (lockMap.containsKey(busiCode)) {
			if (LOCK_FLAG.equals(lockMap.get(busiCode)))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	public void	doTask(String taskcode){
		if (!isLock(taskcode)) {// 判断锁
			setLock(taskcode, LOCK_FLAG);// 设置锁
		}else{
			return;
		}
		try {
			doProcess();
			setLock(taskcode, UN_LOCK_FLAG);//释放锁
		} catch (Exception e) {
			
			setLock(taskcode, UN_LOCK_FLAG);//释放锁
		}
	}
	/**
	 * 处理具体的业务逻辑
	 * @throws Exception
	 */
	public abstract void doProcess() throws Exception;
	protected synchronized void setLock(String busiCode, String flag) {
		lockMap.put(busiCode, flag);
	}
}
