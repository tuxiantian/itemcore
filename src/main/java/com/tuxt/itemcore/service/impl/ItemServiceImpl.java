package com.tuxt.itemcore.service.impl;


import java.util.List;
import java.util.Map;

import com.ai.common.xml.util.ControlConstants;
import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.service.IAsyncService;
import com.tuxt.itemcore.service.IItemService;
import com.tuxt.itemcore.util.PageUtil;

public class ItemServiceImpl extends BaseServiceImpl implements IItemService{
	private static final Logger logger = LoggerFactory.getServiceLog(ItemServiceImpl.class);
	private final static String NAMESPACE="item";
	private IAsyncService asyncService;
	public void setAsyncService(IAsyncService asyncService) {
		this.asyncService = asyncService;
	}
	@Override
	public OutputObject queryItems(InputObject inputObject,
			OutputObject outputObject) {
		try {
			Map<String, Object> paramData=inputObject.getParams();
			PageUtil.setPageParameter2Map(paramData);
			Integer total=getBaseDao().count(NAMESPACE, "queryItemsCount",paramData);
			if (total>0) {
				outputObject.getBean().put("total", total);
				List<Map<String, Object>> beans=getBaseDao().query(NAMESPACE, "getItems", paramData);
				if (!beans.isEmpty()) {
					outputObject.setBeans(beans);
				}
			}
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
			outputObject.setReturnMessage("success");
		} catch (Exception e) {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("查询出错了");
		}
		return outputObject;
	}
	public void exportItemByCdt(InputObject inputObject,
			OutputObject outputObject) {
		try {
			Map<String, Object> paramData=inputObject.getParams();
			List<Map<String, Object>> beans=getBaseDao().query(NAMESPACE, "exportItemByCdt", paramData);
			String excel_head_cnname="编号,标题,内容,状态,类别",
					excel_data_cellSize="20,20,20,20,20",
					excel_data_enname="itemid,title,detail,status,categoryName";
			outputObject.getBean().put("excel_head_cnname", excel_head_cnname);
			outputObject.getBean().put("excel_data_cellSize", excel_data_cellSize);
			outputObject.getBean().put("excel_data_enname",excel_data_enname );
			if (!beans.isEmpty()) {
				outputObject.setBeans(beans);
			}
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
			outputObject.setReturnMessage("success");
		} catch (Exception e) {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("查询出错了");
		}
	}
	public OutputObject queryItemByKey(InputObject inputObject,
			OutputObject outputObject) {
		try {
			Map<String, Object> paramData=inputObject.getParams();
			Map<String, Object> bean=getBaseDao().get(NAMESPACE, "queryItemByKey", paramData);
			if (!bean.isEmpty()) {
				outputObject.setBean(bean);
				outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
				outputObject.setReturnMessage("success");
			}
		} catch (Exception e) {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("查询出错了");
		}
		return outputObject;
	}
	public OutputObject updateItem(InputObject inputObject,
			OutputObject outputObject) {
		try {
			Map<String, Object> paramData=inputObject.getParams();
			int result=getBaseDao().update(NAMESPACE, "updateItem", paramData);
			if (result>0) {
				outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
				outputObject.setReturnMessage("更新成功了");
			}
		} catch (Exception e) {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("查询出错了");
		}
		return outputObject;
	}	
	public OutputObject cleanMarkItems(InputObject inputObject,
			OutputObject outputObject) {
		try {
			getBaseDao().update(NAMESPACE, "cleanMarkItems", null);
		} catch (Exception e) {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("更新出错了");
		}
		return outputObject;
	}		
	public void cleanProcessing(){
		Integer count = getBaseDao().update(NAMESPACE, "cleanProcessing", null);
		logger.info("cleanProcessing", count.toString());
	}
	public OutputObject personAudit(InputObject inputObject, OutputObject outputObject) throws Exception {
		asyncService.asyncPersonAudit(inputObject, outputObject);
		outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
		outputObject.setReturnMessage("success");
		return outputObject;
	}
	public List<Map<String, Object>> queryNoProcess(){
		List<Map<String, Object>> noProcessList = getBaseDao().query(NAMESPACE, "queryNoProcess");
		InputObject in=new InputObject();
		in.setBeans(noProcessList);
		getBaseDao().batchUpdate(NAMESPACE, "markProcessing", in);
		return noProcessList;
	}
	
	public Integer processItem(Map<String, Object> map) {
		try {
			/*if (map.get("title").equals("title25")) {
				throw new RuntimeException("title25");
			}*/
			getBaseDao().update(NAMESPACE, "processItem", map);
			Thread.sleep(500);
		} catch (Exception e) {
		}
		if (map.get("title").equals("title25")) {
			throw new RuntimeException("title25");
		}
		return  (Integer) map.get("itemid");
	}
}
