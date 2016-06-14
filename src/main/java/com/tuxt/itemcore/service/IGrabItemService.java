package com.tuxt.itemcore.service;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;

public interface IGrabItemService {

	public OutputObject markItems(InputObject in, OutputObject out);
	public OutputObject queryItemDateFromQueue(InputObject inputObject,OutputObject outputObject);
}
