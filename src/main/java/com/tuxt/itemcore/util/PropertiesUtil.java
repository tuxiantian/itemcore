package com.tuxt.itemcore.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;

/**
 * Parsing The Configuration File
 * 
 * @author shilei6@asiainfo.com
 * @since 2015-01-05
 */
public final class PropertiesUtil {
	private static final Logger logger = LoggerFactory.getUtilLog(PropertiesUtil.class);
	private static final String BUNDLE_NAME = "config/system";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static final ResourceBundle RESOURCE_LOCAL = ResourceBundle.getBundle("config/system-local");
	private static final ResourceBundle RESOURCE_RUN = ResourceBundle.getBundle("config/system-run");
	private static final ResourceBundle RESOURCE_TEST = ResourceBundle.getBundle("config/system-test");
	private static ResourceBundle RESOURCE;
	static String systemMode;
	interface SYSTEM_MODE
	 {
		 public static final String LOCAL="local";//使用本地配置
		 public static final String RUN="run";//使用生产配置
		 public static final String TEST="test";//使用测试环境配置
	 }
	static{
		systemMode=RESOURCE_BUNDLE.getString("SYSTEM_MODE");
		if (SYSTEM_MODE.RUN.equals(systemMode)) {
			 RESOURCE=RESOURCE_RUN;
		}else if (SYSTEM_MODE.LOCAL.equals(systemMode)) {
			 RESOURCE=RESOURCE_LOCAL;
		}else if (SYSTEM_MODE.TEST.equals(systemMode)) {
			 RESOURCE=RESOURCE_TEST;
		}else {
			
		}
	}
	private PropertiesUtil() {
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

	public static String getString(String key,String defaultValue) {
		try {
			return RESOURCE.getString(key);
		} catch (MissingResourceException e) {
			logger.error("PropertiesUtil#getString",key+" not found in system.properties", e);
			return defaultValue;
		}
	}
	public static int getInt(String key,int defaultValue) {
		try {
			return Integer.parseInt(RESOURCE.getString(key));
		} catch (MissingResourceException e) {
			logger.error("PropertiesUtil#getInt",key+" not found in system.properties", e);
			return defaultValue;
		}
	}
	public static boolean getBoolean(String key,boolean defaultValue) {
		try {
			return Boolean.parseBoolean(RESOURCE.getString(key));
		} catch (MissingResourceException e) {
			logger.error("PropertiesUtil#getBoolean",key+" not found in system.properties", e);
			return defaultValue;
		}
	}
}