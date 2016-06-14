package com.tuxt.itemcore.service;

import java.util.List;
import java.util.Map;

public interface ICfgTaskService {
	public List<Map<String, Object>> getCfgTaskList(Map<String, Object> map);

	public Map<String, Object> getCfgTaskDtl(Map<String, Object> map);
}
