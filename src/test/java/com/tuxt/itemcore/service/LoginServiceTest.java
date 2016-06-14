package com.tuxt.itemcore.service;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;

public class LoginServiceTest extends BaseServiceTest{
	ILoginService loginService;
	protected void setUp() throws Exception {
		super.setUp();
		loginService = (ILoginService) context.getBean("loginService");
	}

	public void testlogin() throws Exception 
	{
		InputObject inputObject=new InputObject();
		inputObject.getParams().put("username", "admin");
		inputObject.getParams().put("password", "123");
		OutputObject outputObject=new OutputObject();
		loginService.login(inputObject, outputObject);
		assertEquals("0", outputObject.getReturnCode());
	}
}
