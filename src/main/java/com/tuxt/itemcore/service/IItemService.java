package com.tuxt.itemcore.service;

import java.util.List;
import java.util.Map;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;

public interface IItemService {
	public OutputObject queryItems(InputObject inputObject,OutputObject outputObject);
	public OutputObject updateItem(InputObject inputObject,
			OutputObject outputObject);
	public OutputObject queryItemByKey(InputObject inputObject,
			OutputObject outputObject);
	public OutputObject cleanMarkItems(InputObject inputObject,
			OutputObject outputObject);
	public OutputObject personAudit(InputObject inputObject, OutputObject outputObject) throws Exception ;
	
	public List<Map<String, Object>> queryNoProcess();
	
	public Integer processItem(Map<String, Object> map);
}
