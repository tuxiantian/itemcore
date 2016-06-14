package com.tuxt.itemcore.service;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;

public interface IAsyncService {
	public OutputObject asyncPersonAudit(InputObject inputObject,
			OutputObject outputObject) throws Exception;
}
