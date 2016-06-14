package com.tuxt.itemcore.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.service.ICfgTaskService;
import com.tuxt.itemcore.util.DateUtil;
import com.tuxt.itemcore.util.SpringApplicationUtil;
import com.tuxt.itemcore.util.StringUtil;

public class ScanAllTaskJob implements Job {
	private static final Logger log = LoggerFactory.getApplicationLog(ScanAllTaskJob.class);
	// 任务
	public static final String TASK_ID = "TASK_ID";
	public static final String TASK_JOB_GROUP = "TASK_JOB_GROUP";
	public static final String TASK_TRIGGER_GROUP = "TASK_TRIGGER_GROUP";
	public static final String JOB_PREFIX = "job_";
	public static final String TRIGGER_PREFIX = "trigger_";
	// 任务方式
	public static final String TASK_METHOD_FIXED = "F";
	public static final String TASK_METHOD_IMMEDIATE = "I";
	public static final String TASK_METHOD_CRON = "C";
	private ICfgTaskService cfgTaskService;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("ScanAllTaskJob", "扫描任务执行");
		cfgTaskService = (ICfgTaskService) SpringApplicationUtil.getBean("cfgTaskService");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		List<Map<String, Object>> rtnList = cfgTaskService.getCfgTaskList(queryMap);
		if (rtnList == null || rtnList.size() == 0) {
			log.info("ScanAllTaskJob", "扫描到符合要求的任务为零,删除所有任务");
		}
		Scheduler scheduler = context.getScheduler();
		List<JobKey> allJobKeyList = new ArrayList<JobKey>();
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			allJobKeyList.addAll(jobKeys);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ScanAllTaskJob", "getCurrentlyExecutingJobs", e);
		}
		List<JobExecutionContext> executeJobList = null;
		try {
			executeJobList = scheduler.getCurrentlyExecutingJobs();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ScanAllTaskJob", "getCurrentlyExecutingJobs", e);
		}
		List<JobKey> runJobKeyList = getAllRunningJobKey(executeJobList);
		removeAllNotRunJob(scheduler, runJobKeyList, allJobKeyList);
		if (rtnList == null || rtnList.size() == 0) {
			return;
		}
		try {
			for (Map<String, Object> map : rtnList) {
				String taskId = map.get("taskId").toString();
				String jobNameStr = JOB_PREFIX + taskId;
				String triggerNameStr = TRIGGER_PREFIX + taskId;
				String runType = null;
				if (map.get("taskRunType") != null) {
					runType = map.get("taskRunType").toString().trim();
				}
				String taskExpr = null;
				if (map.get("taskExpr") != null) {
					taskExpr = map.get("taskExpr").toString();
					taskExpr=taskExpr.replaceAll("　", " ").trim();//替换全角空格
				}

				JobKey jobKey = JobKey.jobKey(jobNameStr, TASK_JOB_GROUP);
				TriggerKey triggerKey = TriggerKey.triggerKey(triggerNameStr, TASK_TRIGGER_GROUP);
				if (isJobKeyRunning(runJobKeyList, jobKey)) {
					continue;
				} else {
					JobDetail jobDetail = JobBuilder.newJob(TaskRealJob.class).withIdentity(jobNameStr, TASK_JOB_GROUP)
							.build();
					jobDetail.getJobDataMap().put(TASK_ID, taskId);
					// 周期执行
					if ("C".equalsIgnoreCase(runType)) {
						if (StringUtil.isEmpty(taskExpr)) {
							continue;
						}
						Trigger oldTrigger = scheduler.getTrigger(triggerKey);
						if (oldTrigger == null) {
							// 表达式调度构建器
							CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskExpr);
							// 按新的cronExpression表达式构建一个新的trigger
							CronTrigger trigger = TriggerBuilder.newTrigger()
									.withIdentity(triggerNameStr, TASK_TRIGGER_GROUP).withSchedule(scheduleBuilder)
									.build();
							scheduler.scheduleJob(jobDetail, trigger);
						} else {
							if (oldTrigger instanceof CronTrigger) {
								CronTrigger trigger = (CronTrigger) oldTrigger;
								CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskExpr);
								// 按新的cronExpression表达式重新构建trigger
								trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
										.withSchedule(scheduleBuilder).build();
								// 按新的trigger重新设置job执行
								scheduler.rescheduleJob(triggerKey, trigger);
							} 
						}
					} else if ("F".equalsIgnoreCase(runType)) {
						if (StringUtil.isEmpty(taskExpr)) {
							continue;
						}
						// 固定时间执行
						Date startDate = DateUtil.string2Date(taskExpr, DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
						if (startDate == null) {
							continue;
						}
						if (startDate.before(new Date())) {
							continue;
						}
						Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
								.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
								.startAt(startDate).build();
						scheduler.scheduleJob(jobDetail, trigger);
					} else if ("I".equalsIgnoreCase(runType)) {
						Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
								.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).startNow()
								.build();
						// 立即执行
						scheduler.scheduleJob(jobDetail, trigger);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeAllNotRunJob(Scheduler scheduler, List<JobKey> runJobKeyList, List<JobKey> allJobKeyList) {
		try {
			if (allJobKeyList == null || allJobKeyList.size() == 0) {
				return;
			}
			if (runJobKeyList == null || runJobKeyList.size() == 0) {
				for (JobKey jobKey : allJobKeyList) {
					scheduler.deleteJob(jobKey);
				}
			} else {
				for (JobKey jobKey : allJobKeyList) {
					if (!isJobKeyRunning(runJobKeyList, jobKey)) {
						scheduler.deleteJob(jobKey);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<JobKey> getAllRunningJobKey(List<JobExecutionContext> executeJobList) {
		List<JobKey> jobKeyList = new ArrayList<JobKey>();
		if (executeJobList == null || executeJobList.size() == 0) {
			return jobKeyList;
		}
		for (JobExecutionContext jobContext : executeJobList) {
			JobDetail jobDetail = jobContext.getJobDetail();
			if (jobDetail == null) {
				continue;
			}
			JobKey jobKey = jobDetail.getKey();
			jobKeyList.add(jobKey);
		}
		return jobKeyList;
	}

	public boolean isJobKeyRunning(List<JobKey> runJobKeyList, JobKey jobKey) {
		if (runJobKeyList == null || runJobKeyList.size() == 0) {
			return false;
		}
		for (JobKey runJobKey : runJobKeyList) {
			if (runJobKey.getName().equals(jobKey.getName()) && runJobKey.getGroup().equals(jobKey.getGroup())) {
				return true;
			}
		}
		return false;
	}

	public void updateCronExp(Scheduler scheduler, String jobName, String groupName, String cronExp) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
