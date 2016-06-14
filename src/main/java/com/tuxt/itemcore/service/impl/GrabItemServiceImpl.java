package com.tuxt.itemcore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ai.common.xml.util.ControlConstants;
import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.tuxt.itemcore.service.IGrabItemService;
import com.tuxt.itemcore.util.DateUtil;
import com.tuxt.itemcore.util.thread.QueryItemQueue;

public class GrabItemServiceImpl extends BaseServiceImpl implements IGrabItemService {
	private static final String NAMESPACE = "item";
	public OutputObject queryItemDateFromQueue(InputObject inputObject,OutputObject outputObject){
		try{

			Map<String, Object> paraMap = QueryItemQueue.pool();
			
			if(null != paraMap){
				outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
				outputObject.getBeans().add(paraMap);
			}else{
				outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
				outputObject.setReturnMessage("暂无工单可抢");
			}
			
		}catch(Exception e){
			outputObject.setReturnCode("0");
			outputObject.setReturnMessage("抢单失败");
		}
		return outputObject;
	}
	@Override
	public OutputObject markItems(InputObject in, OutputObject out) {
		Map<String, Object> paramData=in.getParams();
		List<Map<String, Object>> items=getBaseDao().query(NAMESPACE, "queryItemForMake",paramData);
		for (Map<String, Object> map : items) {
			map.put("personCode", "sys");
			map.put("makeDate",DateUtil.date2String(new Date()));
		}
		if (!items.isEmpty()) {
			in.setBeans(items);
			getBaseDao().batchUpdateOrm(NAMESPACE, "markItems", in);
			out.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
			out.setReturnMessage("预占成功");
		}else{
			out.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			out.setReturnMessage("没有可以预占的信息");
		}
		out.setBeans(items);
		return out;
	}
	
}
