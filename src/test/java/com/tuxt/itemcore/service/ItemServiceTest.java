package com.tuxt.itemcore.service;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;


public class ItemServiceTest extends BaseServiceTest{
	IItemService itemService;
	IGrabItemService grabItemService;
	protected void setUp() throws Exception {
		super.setUp();
		itemService = (IItemService) context.getBean("itemService");
		grabItemService=(IGrabItemService) context.getBean("grabItemService");
	}

	public void testqueryItems() throws Exception 
	{
	InputObject	inputObject=new InputObject();
	inputObject.getParams().put("start", 0);
	inputObject.getParams().put("length", 10);
	
		OutputObject outputObject=new OutputObject();
		List<Map<String, Object>> beans= itemService.queryItems(inputObject, outputObject).getBeans();
		if ("0".equals(outputObject.getReturnCode())) {
			System.out.println(beans);
		}
	}
	public void updateItem(){
		InputObject	inputObject=new InputObject();
		inputObject.getParams().put("title", "title1");
		inputObject.getParams().put("detail", "detail1");
		inputObject.getParams().put("status", "1");
		inputObject.getParams().put("itemId", 1);
		OutputObject outputObject=new OutputObject();
		itemService.updateItem(inputObject, outputObject);
		TestCase.assertEquals("0", outputObject.getReturnCode());
	}
	/*public void useThreadPoolTest() {
		ThreadPoolConfig config = ThreadPoolConfig.initThreadPoolConfig();
		ThreadPoolFactory factory = ThreadPoolFactory.getInstance(config);
		int i = 10;
		while(i>0){
			factory.addTask(new Runnable() {
				InputObject in=new InputObject();
				OutputObject out = new OutputObject();
				public void run() {
					in.getParams().put("personCode", "A");
					grabItemService.queryItemDateFromQueue(in, out);
					InputObject inputObject = new InputObject();
					OutputObject outputObject = new OutputObject();
					inputObject.getParams().put("itemId", out.getBeans().get(0).get("itemId"));
					try {
						itemService.personAudit(inputObject, outputObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			i--;
		}
	}*/
}
