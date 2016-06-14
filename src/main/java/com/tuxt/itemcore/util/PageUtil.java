package com.tuxt.itemcore.util;

import java.util.Map;

public class PageUtil {
	/**
	 * 注意分页的键：起始页startPage，页大小pageCount.sql中相应的使用start和length。
	 * 
	 * @param paramData
	 */
	public static void setPageParameter2Map(Map<String, Object> paramData) {
		if (StringUtil.isEmpty((String) paramData.get("startPage"))) {
			paramData.put("startPage", "0");
		}
		if (StringUtil.isEmpty((String) paramData.get("pageCount"))) {
			paramData.put("pageCount", "10");
		}
		int length = Integer.parseInt((String) paramData.get("pageCount"));
		int start = Integer.parseInt((String) paramData.get("startPage")) * length;
		paramData.put("start", String.valueOf(start));
		paramData.put("length", String.valueOf(length));
	}
}
