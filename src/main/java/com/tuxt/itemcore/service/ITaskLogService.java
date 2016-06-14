package com.tuxt.itemcore.service;

import java.util.Map;

public interface ITaskLogService {
	public int checkTaskLogTableExist(Map<String, Object> map);

	public void createTaskLogTableByCdt(Map<String, Object> map);

	public void insertTaskLog(Map<String, Object> map);
}
