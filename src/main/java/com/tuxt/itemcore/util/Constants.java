package com.tuxt.itemcore.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String DBKEYEES="ZYZXRGSH";
	/*********system dao  *************/
	public static final int DAO_BATCH_NUMBER_UPDATE=100;
	public static final int DAO_BATCH_NUMBER_INSERT=100;
	public static final int DAO_BATCH_NUMBER_DELETE=100;
	
	
	public static final int PARAM_LIMLT_NUM=4;//文件中至少有4个参数Seq CustName CustCertNo BillId
	public static final int PARAM_MAX_NUM=10;//文件中最多有10个参数Seq CustName CustCertNo BillId
	
	public static final boolean XHSBATCH_SUCESS_FLAG=true;//第三方验证成功
	public static final boolean XHSBATCH_FALSE_FLAG=false;//第三方验证失败
	
	public static final String RETURN＿FILE_FAILE_FLAG="1";//验证文件格式错误
	public static final String RETURN＿FILE_SUCESS_FLAG="0";//验证文件格式正确
	
	public static final String XHSBATCH_FLAG="1";//新华社文件线下处理
	public static final String GZTBATCH_FLAG="2";//国政通实时处理
	
	public static final int CREATE_FILE_NUM=100;//批量查验数量
	public static final String FILENAMELIST="filelist.txt ";
	public static final int FILE_COUNT_NUM=10000;//每个文件的记录数
	
	public static final String BATCH_UPLOAD_FILE_FLAG="0";//是否立即写反馈文件0立即 1查验完毕
	
	
	/*****实时接口的调用参数****/
	public static final int BACTH_SEND_COUNT_MUMBER=100;//默认一次发送100条
	public static final int BACTH_THREAD_COUNT_MUMBER=2000;//20000条按1000条进行拆分 启动线程数20000/2000
	public static final int ONE_FILE_COUNT=20000;//批量查验数量
	
	/*******角色******/
	public static abstract interface ROLE_CODE
	  {
	     public static final String AUDITOR = "1"; //审核人
	     public static final String AUDITOR_ZJ = "2"; //审核专家
	     public static final String FeedBack = "3"; //省公司反馈人员
	     public static final String OPERATION = "4"; //运营人员
	     public static final String ADMIN = "5"; //管理员
	  }
	/*******工单状态******/
	public static abstract interface WORKSTATUS{
		public static final String BEFOREAUDIT="0";//待审核
		public static final String PASS="1";//通过
		public static final String NOPASS="2";//不通过
		public static final String NOSURE="3";//待定
		public static final String BEFOREFEEDBACK="4";//待反馈
		public static final String APPENDREGISTER="5";//补登记
	}
	//省编码
	public static final Map<String,String> provinceMap=new HashMap<String,String>();
	//工单状态
	public static final Map<String,String> workStatus = new HashMap<String, String>();
	//业务类型
	public static final Map<String,String> businessType = new HashMap<String, String>();
	//审核意见
	public static final Map<String,String> auditOpitionType = new HashMap<String, String>();
	// 业务类型
	public static final Map<String,String> busiType = new HashMap<String, String>();
	// 人工审核类型
	public static final Map<String,String> opinionType = new HashMap<String, String>();
	// 机器审核状态
	public static final Map<String,String> robotAudit = new HashMap<String, String>();
	//受理渠道
	public static final Map<String,String> acceptChannel= new HashMap<String, String>();
	
	static{
		acceptChannel.put("0", "网页");
		acceptChannel.put("1", "微信");
		acceptChannel.put("2", "app");
		
		provinceMap.put("100", "北京");
		provinceMap.put("220", "天津");
		provinceMap.put("210", "上海");
		provinceMap.put("230", "重庆");
		provinceMap.put("311", "河北");
		provinceMap.put("371", "河南");
		provinceMap.put("871", "云南");
		provinceMap.put("240", "辽宁");
		provinceMap.put("451", "黑龙江");
		provinceMap.put("731", "湖南");
		provinceMap.put("551", "安徽");
		provinceMap.put("531", "山东");
		provinceMap.put("991", "新疆");
		provinceMap.put("250", "江苏");
		provinceMap.put("571", "浙江");
		provinceMap.put("791", "江西");
		provinceMap.put("270", "湖北");
		provinceMap.put("771", "广西");
		provinceMap.put("931", "甘肃");
		provinceMap.put("351", "山西");
		provinceMap.put("471", "内蒙古");
		provinceMap.put("290", "陕西");
		provinceMap.put("431", "吉林");
		provinceMap.put("591", "福建");
		provinceMap.put("851", "贵州");
		provinceMap.put("200", "广东");
		provinceMap.put("971", "青海");
		provinceMap.put("891", "西藏");
		provinceMap.put("280", "四川");
		provinceMap.put("951", "宁夏");
		provinceMap.put("898", "海南");
		
		workStatus.put("0", "待审核");
		workStatus.put("1", "已通过");
		workStatus.put("2", "不通过");
		workStatus.put("3", "待定");
		workStatus.put("4", "待反馈");
		workStatus.put("5", "补登记");
		
		businessType.put("1", "疑似审核");
		businessType.put("2", "抽样审核");
		businessType.put("3", "定位审核");
		
		auditOpitionType.put("1", "黑白照片");
		auditOpitionType.put("2", "手机，电脑拍摄");
		auditOpitionType.put("3", "PS图");
		auditOpitionType.put("4", "反面一致");
		auditOpitionType.put("5", "其他");
		auditOpitionType.put("6", "工单反馈");
		
		busiType.put("1", "新入网");
		busiType.put("2", "补登记");
		busiType.put("0", "自助卡激活");
		
		opinionType.put("1", "通过");
		opinionType.put("2", "手机照片");
		opinionType.put("3", "复印件");
		opinionType.put("4", "使用同一张身份证拍摄反面信息");
		opinionType.put("5", "使用同一张身份证拍摄反面信息");
		opinionType.put("6", "临时证件");
		opinionType.put("7", "PS证件");
		opinionType.put("8", "其他");
		opinionType.put("9", "电脑照片");
		opinionType.put("10", "正反面不符");
		opinionType.put("11", "无照片");
		opinionType.put("12", "正面复印件");
		opinionType.put("13", "反面复印件");
		opinionType.put("14", "正面ps证件");
		opinionType.put("15", "反面ps证件");
		opinionType.put("16", "正面电脑照片");
		opinionType.put("17", "反面电脑照片");
		
		robotAudit.put("0", "成功");
		robotAudit.put("1", "复印件");
		robotAudit.put("2", "翻拍手机照片");
		robotAudit.put("3", "翻拍电脑照片");
		robotAudit.put("4", "复印件生成器");
		robotAudit.put("5", "PS证件");
		robotAudit.put("6", "临时身份证");
	}
}
