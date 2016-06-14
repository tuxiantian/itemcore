package com.tuxt.itemcore.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TypeUtil extends TestCase{
	public void test1() {
		Map<String, Object> map=new HashMap<String, Object>();
		//map.put("name", "a");
		System.out.println(Integer.parseInt(String.valueOf(map.get("name"))));
	}
}
