package com.tuxt.itemcore.util.thread;

import java.util.List;
import java.util.Map;

import com.tuxt.itemcore.service.IItemService;
import com.tuxt.itemcore.util.SpringApplicationUtil;

public class SendMsgInitThread extends Thread{

	private IItemService itemService;
	public SendMsgInitThread(){}
	public SendMsgInitThread(IItemService itemService){
		this.itemService=itemService;
	}
	@Override
	public void run() {
		while (true) {
			ThreadPoolExecutorHelper threadPoolExecutorHelper1=(ThreadPoolExecutorHelper) SpringApplicationUtil.getBean("threadPoolExecutorHelper1");
			List<Map<String, Object>> noProcessDataList =itemService.queryNoProcess();
			
			if (noProcessDataList!=null&&!noProcessDataList.isEmpty()) {
				for (Map<String, Object> map : noProcessDataList) {
					threadPoolExecutorHelper1.addTask(new SendMessageThread(itemService, map));
				}
			}else {
				System.out.println("noData startSleep");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
