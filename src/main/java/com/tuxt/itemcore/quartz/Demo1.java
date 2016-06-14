package com.tuxt.itemcore.quartz;

import java.util.List;
import java.util.Map;

public class Demo1 extends CycleProcessTask{
	

	public  void doProcess() throws Exception {
		try {
			List<Map<String, Object>> beans=null;
			for (int i = 0; i < 1000; i++) {
				try {
					System.out.println("######################"+i);
				} catch (Exception e) {
					// 异常数据特殊处理
				}
			}
		} catch (Exception e) {
			
		}finally{
			//执行清理资源的操作
		}
	}

	@Override
	public void doTask(String taskcode) {
		super.doTask(taskcode);
	}

}
