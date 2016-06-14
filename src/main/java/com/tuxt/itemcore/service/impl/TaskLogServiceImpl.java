package com.tuxt.itemcore.service.impl;

import java.util.Map;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.service.ITaskLogService;

public class TaskLogServiceImpl extends BaseServiceImpl implements ITaskLogService {
	private static final String MNAMESPACE = "taskLogDao";
	private static final Logger logger = LoggerFactory.getServiceLog(TaskLogServiceImpl.class);

	public int checkTaskLogTableExist(Map<String, Object> map) {
		try {
			getBaseDao().count(MNAMESPACE, "checkTaskLogTableExist", map);
			return 1;
		} catch (Exception e) {
			logger.error("表db_ap_rgsh_tasklog不存在", "",e);
		}
		return 0;
	}

	public void createTaskLogTableByCdt(Map<String, Object> map) {
		try {
			getBaseDao().insert(MNAMESPACE, "createTaskLogTable", map);
		} catch (Exception e) {
			logger.error("新建表db_ap_rgsh_tasklog发生错误", "",e);
		}
	}

	//写日志 不能影响其他业务,使用try catch捕获异常
	public void insertTaskLog(Map<String, Object> map) {
		try {
			getBaseDao().insert(MNAMESPACE, "insertTaskLogRecord", map);
		} catch (Exception e) {
			logger.error("往表db_ap_rgsh_tasklog插入数据发生错误", "",e);
		}

	}

}
