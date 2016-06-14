package com.tuxt.itemcore.util.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.tuxt.itemcore.service.IGrabItemService;
import com.tuxt.itemcore.service.impl.GrabItemServiceImpl;
import com.tuxt.itemcore.util.PropertiesUtil;

public class QueryItemQueue {
	private static IGrabItemService grabItemService;
	public static void setGrabItemService(GrabItemServiceImpl grabItemService) {
		QueryItemQueue.grabItemService = grabItemService;
	}

	private static ConcurrentLinkedQueue<Map<String, Object>> queue = new ConcurrentLinkedQueue<Map<String, Object>>();
	static boolean flag = true;
	static InputObject in = new InputObject();
	static OutputObject out = new OutputObject();
	static int number = Integer.parseInt(PropertiesUtil.getString("BATCH_NUMBER"));

	public void initQueue(){
		
		new Thread(new Runnable() {
			public void run() {
				while (flag ) {
					try{
						flag = Boolean.parseBoolean(PropertiesUtil.getString("QUEUE_SWITCH"));
					}catch(Exception e){
						flag = true;
					}					
					// 查询
					if (queue.size() < Integer.valueOf(PropertiesUtil.getString("QUERY_ORDER_MIN_SIZE"))) {
						in.getParams().put("number", number);
						grabItemService.markItems(in, out);
						
						if (null != out && null != out.getBeans()&& out.getBeans().size() > 0) {
							setDataToQueue(out.getBeans());
						}
					}					
				}
			}
		}).start();
	}

	public static void setDataToQueue(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			queue.offer(map);
		}

	}

	/**
	 * 生产
	 */
	public static void offer(Map<String, Object> map) {
		queue.offer(map);
	}

	/**
	 * 消费
	 */
	public static Map<String, Object> pool() {
		return queue.poll();
	}	
}
