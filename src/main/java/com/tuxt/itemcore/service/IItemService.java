package com.tuxt.itemcore.service;

import java.util.List;
import java.util.Map;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;

public interface IItemService {
	 OutputObject queryItems(InputObject inputObject,OutputObject outputObject);
	 OutputObject updateItem(InputObject inputObject,
			OutputObject outputObject);
	 OutputObject queryItemByKey(InputObject inputObject,
			OutputObject outputObject);
	 OutputObject cleanMarkItems(InputObject inputObject,
			OutputObject outputObject);
	/**
	 * 系统启动后首先将上一次处理中的工单标记为未处理
	 */
	 void cleanProcessing();
	 OutputObject personAudit(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	 List<Map<String, Object>> queryNoProcess();
	
	 Integer processItem(Map<String, Object> map);
	 void exportItemByCdt(InputObject inputObject,
			OutputObject outputObject);
}
