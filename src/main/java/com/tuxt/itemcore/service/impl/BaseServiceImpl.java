package com.tuxt.itemcore.service.impl;

import com.tuxt.itemcore.dao.impl.BaseDaoImpl;
import com.tuxt.itemcore.service.IBaseService;

/**
 * @author shilei6@asiainfo.com
 * @since 2015-04-20
 */
public class BaseServiceImpl implements IBaseService  {
	private BaseDaoImpl baseDao;

	public BaseDaoImpl getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDaoImpl baseDao) {
		this.baseDao = baseDao;
	}
}
