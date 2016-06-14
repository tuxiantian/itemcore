package com.tuxt.itemcore.quartz;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.service.ICfgTaskService;
import com.tuxt.itemcore.service.ITaskLogService;
import com.tuxt.itemcore.util.DateUtil;
import com.tuxt.itemcore.util.DateUtil.DATE_PATTERN;
import com.tuxt.itemcore.util.SpringApplicationUtil;
import com.tuxt.itemcore.util.StringUtil;

public class TaskRealJob implements Job {
	private static final Logger log = LoggerFactory.getApplicationLog(TaskRealJob.class);
	private ICfgTaskService cfgTaskService;
	private ITaskLogService taskLogService;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String taskIdStr = null;
		Map<String, Object> logMap = new HashMap<String, Object>();
		long beginTime = System.currentTimeMillis();
		String taskName=null;
		try {
			logMap.put("taskEnName", "TaskRealJob");
			JobDataMap data = context.getJobDetail().getJobDataMap();
			taskIdStr = data.getString(ScanAllTaskJob.TASK_ID);
			if (StringUtil.isEmpty(taskIdStr)) {
				log.error("TaskRealJob", "获取Id失败");
			}
			taskLogService = (ITaskLogService) SpringApplicationUtil.getBean("taskLogService");
			cfgTaskService = (ICfgTaskService) SpringApplicationUtil.getBean("cfgTaskService");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("id", taskIdStr);
			Map<String, Object> taskMap = cfgTaskService.getCfgTaskDtl(queryMap);
			if (queryMap == null || queryMap.size() == 0) {
				if (StringUtil.isEmpty(taskIdStr)) {
					log.error("TaskRealJob", "获取任务信息失败");
				}
			}
			
			String busiClass = taskMap.get("busiClass").toString();
			String busiMethod = taskMap.get("busiMethod").toString();
			String taskCode = taskMap.get("taskCode").toString();
			
			taskName =busiClass.substring(busiClass.lastIndexOf(".")+1); 
			logMap.put("taskEnName", taskName);
			logMap.put("taskResult", taskName+" 任务开始");
			logMap.put("taskParam", taskCode);
			logMap.put("createDate", DateUtil.date2String(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
			logMap.put("state", "C");
			taskLogService.insertTaskLog(logMap);
					
			Class taskClass = Class.forName(busiClass.trim());
			Object obj = taskClass.newInstance();
			Method meth;
			if (!StringUtil.isEmpty(taskCode)) {
				meth = obj.getClass().getDeclaredMethod(busiMethod,String.class);
				meth.setAccessible(true);
				meth.invoke(obj, taskCode);
			}else {
				meth = obj.getClass().getDeclaredMethod(busiMethod);
				meth.setAccessible(true);
				meth.invoke(obj);
			}
			logMap.put("taskResult", String.format("%s执行完成,总共耗时%s", taskName,(System.currentTimeMillis() - beginTime)));
			logMap.put("taskParam", taskCode);
			logMap.put("createDate", DateUtil.date2String(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
			logMap.put("state", "F");
			taskLogService.insertTaskLog(logMap);
		} catch (Exception e) {
			log.error("TaskRealJob Error", "execute", e);
			String errorMsg = ExceptionUtils.getStackTrace(e);
			logMap.put("taskResult", StringUtil.delStrAllQuo(errorMsg, 1200));
			logMap.put("taskParam", taskIdStr);
			logMap.put("createDate", DateUtil.date2String(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
			logMap.put("state", "X");
			taskLogService.insertTaskLog(logMap);
			e.printStackTrace();
		}
	}

}
