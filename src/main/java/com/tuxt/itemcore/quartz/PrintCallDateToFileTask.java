package com.tuxt.itemcore.quartz;

import java.io.IOException;
import java.util.Date;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.util.DateUtil;
import com.tuxt.itemcore.util.fileUtil.WriteFileUtil;

public class PrintCallDateToFileTask {
	private static final Logger logger = LoggerFactory.getApplicationLog(PrintCallDateToFileTask.class);
	public void task(String taskcode) {
		logger.info("PrintCallDateToFileTask任务开始执行", "");
		String fileName="J:/taskdate.txt";
		String content=DateUtil.date2String(new Date());
		try {
			WriteFileUtil.appendFile(fileName, content+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("PrintCallDateToFileTask任务执行完毕", "");
	}
}
