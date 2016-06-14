package com.tuxt.itemcore.service.impl;

import java.util.List;
import java.util.Map;

import com.ai.common.xml.util.ControlConstants;
import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.service.ILoginService;

public class LoginServiceImpl extends BaseServiceImpl implements ILoginService{
	private static final Logger logger = LoggerFactory.getApplicationLog(LoginServiceImpl.class);
	private static final String NAMESPACE="user";
	@Override
	public OutputObject login(InputObject inputObject, OutputObject outputObject) {
		Map<String, Object> paramData=inputObject.getParams();
		try {
			Map<String, Object> result=getBaseDao().get(NAMESPACE, "queryUser", paramData);
			if (result!=null) {
				outputObject.setBean(result);
				outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
				outputObject.setReturnMessage("登陆成功");
			}else {
				outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
				outputObject.setReturnMessage("用户名或密码错误");
			}
		} catch (Exception e) {
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("系统出错了");
		}
		return outputObject;
	}
	public OutputObject queryMenu(InputObject in, OutputObject out) {
		try {
			List <Map<String,Object>> beans=getBaseDao().query(NAMESPACE, "query_menu_new", in.getParams());
			for(Map<String, Object> map : beans){
				String tpl = (String) map.get("tpl");
				tpl = tpl.substring(tpl.lastIndexOf('/')+1,tpl.lastIndexOf('.'));
				map.put("tpl", tpl);
			}
			out.setBeans(beans);
			out.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
			out.setReturnMessage("success");
			System.out.println(out.getBeans().toString());
		} catch (Exception e) {
			out.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			out.setReturnMessage("查询用户菜单出现错误");
			logger.error("sendMessage", "查询用户菜单出现错误：", e);
		}
		return out;
	}
}
