package com.tuxt.itemcore.service;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class CacheServiceTest  extends BaseServiceTest{
	ICacheService cacheService;
	protected void setUp() throws Exception {
		super.setUp();
		cacheService=(ICacheService) context.getBean("cacheService");
	}
	public void convertSqlData(){
		Map<String, Object> param=new HashMap<>();
		param.put("provCode", "100");
		cacheService.convertSqlData(param);
		String expected="北京";
		String actual=String.valueOf(param.get("provValue"));
		TestCase.assertEquals(expected, actual);
	}
}
