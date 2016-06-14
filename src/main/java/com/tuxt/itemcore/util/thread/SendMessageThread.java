package com.tuxt.itemcore.util.thread;

import java.util.Map;
import java.util.concurrent.Callable;

import com.tuxt.itemcore.service.IItemService;

public class SendMessageThread implements Callable<Integer>{

	private IItemService itemService;
	
	public IItemService getItemService() {
		return itemService;
	}
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	private Map<String, Object> dataMap;
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public SendMessageThread(){}
	public SendMessageThread(IItemService itemService,Map<String, Object> dataMap){
		this.itemService=itemService;
		this.dataMap=dataMap;
	}
	@Override
	public Integer call() throws Exception {
		
		return itemService.processItem(dataMap);
	}

}
