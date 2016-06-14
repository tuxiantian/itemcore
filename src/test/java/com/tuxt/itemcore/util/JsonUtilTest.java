package com.tuxt.itemcore.util;

import com.ai.frame.bean.OutputObject;
import com.ai.frame.util.JsonUtil;

import junit.framework.TestCase;

public class JsonUtilTest extends TestCase{
	public void str2OutputObject(){
		String str="{\"returnCode\":\"0000\",\"returnMessage\":\"SUCCESS\"}";
		OutputObject out=JsonUtil.json2OutputObject(str);
		System.out.println(out.getReturnCode());
	}
}
