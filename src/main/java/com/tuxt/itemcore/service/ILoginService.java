package com.tuxt.itemcore.service;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;

public interface ILoginService {
	public OutputObject login(InputObject inputObject,OutputObject outputObject);
	public OutputObject queryMenu(InputObject in, OutputObject out);
}
