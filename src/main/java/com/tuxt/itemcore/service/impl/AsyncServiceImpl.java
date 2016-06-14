package com.tuxt.itemcore.service.impl;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.tuxt.itemcore.service.IAsyncService;

public class AsyncServiceImpl extends BaseServiceImpl implements IAsyncService{
	private static final String NAMESPACE = "item";
	@Async
	public OutputObject asyncPersonAudit(InputObject inputObject,
			OutputObject outputObject) throws Exception  {
		Map<String, Object> paramData=inputObject.getParams();
		getBaseDao().update(NAMESPACE, "auditItem", paramData);
		return outputObject;
	}
}
