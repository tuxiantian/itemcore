package com.tuxt.itemcore.service;

import java.util.Map;

public interface IFtpService {

	public abstract Map queryCfgFtpPath(String ftpPathCode);

	public abstract Map queryCfgFtpPart(String cfgFtpCode);

}