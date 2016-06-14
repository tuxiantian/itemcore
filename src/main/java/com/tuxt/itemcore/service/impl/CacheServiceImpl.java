package com.tuxt.itemcore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.tuxt.itemcore.service.ICacheService;
import com.tuxt.itemcore.util.JedisUtil;
import com.tuxt.itemcore.util.StringUtil;

public class CacheServiceImpl extends BaseServiceImpl implements ICacheService{
	private final static String NAMESPACE="dictionary";

	public void	init(){
		//省编码
		Map<String,String> provinceMap=new HashMap<String,String>();
		//工单状态
		Map<String,String> workStatus = new HashMap<String, String>();
		//业务类型
		Map<String,String> businessType = new HashMap<String, String>();
		//审核意见
		Map<String,String> auditOpitionType = new HashMap<String, String>();
		// 业务类型
		Map<String,String> busiType = new HashMap<String, String>();
		// 人工审核类型(app)
		Map<String,String> opinionType = new HashMap<String, String>();
		// 人工审核类型(微信和网页)
		Map<String,String> wechatAndWebOpinionType = new HashMap<String, String>();
		// 机器审核状态
		Map<String,String> robotAudit = new HashMap<String, String>();
		//受理渠道
		Map<String,String> acceptChannel= new HashMap<String, String>();
		List<Map<String, Object>> dictionary=getBaseDao().query(NAMESPACE, "queryDictionary");
		for (Map<String, Object> map : dictionary) {
			String type=String.valueOf(map.get("TYPE"));
			if ("-1".equals(type)) {
				continue;
			}
			switch (type) {
			case "work_status":
				workStatus.put(String.valueOf(map.get("CODE_KEY")), String.valueOf(map.get("CODE_VALUE")));
				break;
			case "business_type":
				businessType.put(String.valueOf(map.get("CODE_KEY")), String.valueOf(map.get("CODE_VALUE")));
				break;
			case "audit_opition_type":
				auditOpitionType.put(String.valueOf(map.get("CODE_KEY")), String.valueOf(map.get("CODE_VALUE")));
				break;
			case "province_code":
				provinceMap.put(String.valueOf(map.get("CODE_KEY")), String.valueOf(map.get("CODE_VALUE")));
				break;
			case "robot_audit":
				robotAudit.put(String.valueOf(map.get("CODE_KEY")), String.valueOf(map.get("CODE_VALUE")));
				break;
			case "accept_channel":
				acceptChannel.put(String.valueOf(map.get("CODE_KEY")), String.valueOf(map.get("CODE_VALUE")));
				break;
			default:
				break;
			}
		}
		JedisUtil.hmset("workStatus",workStatus );
		JedisUtil.hmset("businessType", businessType);
		JedisUtil.hmset("auditOpitionType",auditOpitionType );
		JedisUtil.hmset("provinceMap",provinceMap );
		JedisUtil.hmset("robotAudit", robotAudit);
		JedisUtil.hmset("acceptChannel",acceptChannel );
	}
	public void convertSqlData(Map<String, Object> param) {
		// 省份
		if (!StringUtil.isEmpty((String) param.get("provCode"))) {
			param.put("provValue",JedisUtil.hmget("provinceMap",String.valueOf(param.get("provCode"))));
		} else {
			param.put("provValue", "未知");
		}
	}
}
