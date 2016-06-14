package com.tuxt.itemcore.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.ai.frame.util.BeanUtil;

/**
 * @author shilei6@asiainfo.com
 * @since 2015-04-20
 */
public class Entity implements Serializable {
	private static final long serialVersionUID = -9131680166367906057L;
	private static final Logger logger = LoggerFactory.getUtilLog(Entity.class);
	
	public String toString() {
		Map<String, Object> map = BeanUtil.convertBean2Map(this);
		return map.toString();
	}

	/**
	 * 深度克隆
	 * 
	 * @return 克隆的类
	 */
	@SuppressWarnings("unchecked")
	public <T> T deepClone() {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			// Return Target Object
			return (T) oi.readObject();
		} catch (Exception e) {
			logger.error("deepClone", "", e);
			return null;
		}
	}
}
