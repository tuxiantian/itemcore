package com.tuxt.itemcore.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ThreadPoolConfigPropertiesUtil {
	private static final String BUNDLE_NAME = "config/threadpoolconfig";
	private static ResourceBundle RESOURCE= ResourceBundle.getBundle(BUNDLE_NAME);
	private  ThreadPoolConfigPropertiesUtil() {
		
	}
	/**
	 * Get a value based on key , if key does not exist , null is returned
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}
}
