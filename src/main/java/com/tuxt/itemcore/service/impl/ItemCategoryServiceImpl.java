package com.tuxt.itemcore.service.impl;

import java.util.List;
import java.util.Map;

import com.ai.common.xml.util.ControlConstants;
import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.tuxt.itemcore.service.IItemCategoryService;

public class ItemCategoryServiceImpl extends BaseServiceImpl implements IItemCategoryService{
	private final static String NAMESPACE="itemcategory";
	public OutputObject queryItemCategory(InputObject inputObject,OutputObject outputObject) {
		List<Map<String, Object>> beans=getBaseDao().query(NAMESPACE, "queryItemCategory");
		if (!beans.isEmpty()) {
			outputObject.setBeans(beans);
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
		}
		return outputObject;
	}
}
