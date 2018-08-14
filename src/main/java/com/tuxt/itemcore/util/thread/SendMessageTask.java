package com.tuxt.itemcore.util.thread;

import com.tuxt.itemcore.service.IItemService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Map;

public class SendMessageTask {

	private ThreadPoolTaskExecutor taskExecutor;

	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	private IItemService itemService;
	public IItemService getItemService() {
		return itemService;
	}
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public void init() {
		itemService.cleanProcessing();
		while (true) {
			List<Map<String, Object>> noProcessDataList =itemService.queryNoProcess();

			if (noProcessDataList!=null&&!noProcessDataList.isEmpty()) {
                for (final Map<String, Object> map : noProcessDataList) {
                    taskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            itemService.processItem(map);
                        }
                    });
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
