package com.tuxt.itemcore.util.thread;

import com.tuxt.itemcore.service.IItemService;

public class SendMessageTask {

	private IItemService itemService;
	public IItemService getItemService() {
		return itemService;
	}
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	public void init() {
		SendMsgInitThread sendMsgInitThread=new SendMsgInitThread(itemService);
		sendMsgInitThread.start();
	}
}
