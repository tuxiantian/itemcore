package com.tuxt.itemcore.control;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.ai.common.xml.util.ControlConstants;
import com.ai.frame.IControlService;
import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.ai.frame.util.JsonUtil;

/**
 * @author shilei6@asiainfo.com
 * @since 2015-04-20
 */
public class ControlServiceImpl implements IControlService, BeanFactoryAware {
	private static final Logger logger = LoggerFactory
			.getApplicationLog(ControlServiceImpl.class);

	private BeanFactory factory;

	public void setBeanFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public String execute(String json) {
		InputObject inObj = null;
		OutputObject outObj = new OutputObject();
		try {
			inObj = JsonUtil.json2InputObject(json);
			//outObj.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
			if (inObj != null) {
				long start = System.currentTimeMillis();
				
				Object object = factory.getBean(inObj.getService());
				Method mth = object.getClass().getMethod(inObj.getMethod(), InputObject.class, OutputObject.class);
				mth.invoke(object, inObj ,outObj);
				
				logger.info("INVOKE SECCESS!", "COST="+(System.currentTimeMillis() - start)+"ms");
			} else {
				throw new Exception("InputObject can't be null !!!");
			}
		} catch (Exception e) {
			logger.error("execute", "INVOKE ERROR!", e);
			// 异常处理
			outObj.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outObj.setReturnMessage(e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
		}
		return JsonUtil.outputObject2Json(outObj);
	}
}
