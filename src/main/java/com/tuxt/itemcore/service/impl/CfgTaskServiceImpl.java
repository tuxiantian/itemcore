package com.tuxt.itemcore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tuxt.itemcore.service.ICfgTaskService;

public class CfgTaskServiceImpl extends BaseServiceImpl implements ICfgTaskService {
	private static final String NAMESPACE = "cfgTaskDao";

	public List<Map<String, Object>> getCfgTaskList(Map<String, Object> map) {
		return getBaseDao().query(NAMESPACE, "getCfgTaskList", map);
	}

	public Map<String, Object> getCfgTaskDtl(Map<String, Object> map) {
		List<Map<String, Object>> rtnList = getBaseDao().query(NAMESPACE, "getCfgTaskDtl", map);
		if (rtnList == null || rtnList.size() == 0) {
			return new HashMap<String, Object>();
		}
		return rtnList.get(0);
	}

}
