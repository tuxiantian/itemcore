package com.tuxt.itemcore.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.tuxt.itemcore.service.IFtpService;

public class FtpServiceImpl extends BaseServiceImpl implements IFtpService{
	private final static String NAMESPACE="ftp",IN_USE="2";
	@Override
	public Map queryCfgFtpPath(String ftpPathCode) {
		
		Map<String, Object> paramData=new HashMap<>(1);
		paramData.put("ftpPathCode", ftpPathCode);
		return getBaseDao().query(NAMESPACE, "queryCfgFtpPath", paramData).get(0);
	}
	@Override
	public Map queryCfgFtpPart(String cfgFtpCode) {
		Map<String, Object> paramData=new HashMap<>(2);
		paramData.put("cfgFtpCode", cfgFtpCode);
		paramData.put("state", IN_USE);
		return getBaseDao().query(NAMESPACE, "queryCfgFtpPart", paramData).get(0);
	}
}
