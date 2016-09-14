package com.tuxt.itemcore.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestClass {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		try {
			String busiClass="com.tuxt.itemcore.quartz.PrintCallDateToFileTask";
			Class taskClass = Class.forName(busiClass.trim());
			Object obj = taskClass.newInstance();
			String busiMethod="task";
			Method meth = obj.getClass().getDeclaredMethod(busiMethod,String.class);
			meth.setAccessible(true);
			Object taskCode = "12";
			meth.invoke(obj,taskCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
