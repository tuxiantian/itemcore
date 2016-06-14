package com.tuxt.itemcore.util.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
		super.run();
		while (true) {
			Vector<Integer> todo=new Vector<>();
			ThreadPoolExecutorHelper threadPoolExecutorHelper1=(ThreadPoolExecutorHelper) SpringApplicationUtil.getBean("threadPoolExecutorHelper1");
			List<Map<String, Object>> noProcessDataList =itemService.queryNoProcess();
			
			List<Future<Integer>> toDoRemoveList=new ArrayList<>();
			
			if (noProcessDataList!=null&&!noProcessDataList.isEmpty()) {
				for (Map<String, Object> map : noProcessDataList) {
					if (!todo.contains(map.get("itemid"))) {
						todo.add( (Integer) map.get("itemid"));
					}else {
						continue;
					}
					@SuppressWarnings("unchecked")
					Future<Integer> result=(Future<Integer>) threadPoolExecutorHelper1.addFutureTask(new SendMessageThread(itemService, map));
					toDoRemoveList.add(result);
				}
				Iterator<Future<Integer>> iterator=toDoRemoveList.iterator();
				Future<Integer> tempFuture;
				while (iterator.hasNext()) {
					tempFuture=iterator.next();
					if(tempFuture.isDone()){
						try {
							Integer itemid =tempFuture.get();
							if (todo.contains(itemid)) {
								System.out.println("todo.remove:"+itemid);
								todo.remove(itemid);
							}
							iterator.remove();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
				}
				if (todo.size()>10) {
					System.out.println("startSleep");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else {
				System.out.println("noData startSleep");
				System.out.println(todo.size());
				if (todo.size()>0) {
					System.out.println("-----process error data-----");
					for (Integer itemid : todo) {
						System.out.println(itemid);
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
