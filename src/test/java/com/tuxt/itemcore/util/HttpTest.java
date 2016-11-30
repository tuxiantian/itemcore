package com.tuxt.itemcore.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tuxt.itemcore.util.httputil.HttpRequesterHandler;

public class HttpTest {

	@Test
	public void test1() throws IOException {
		HttpRequesterHandler requesterHandler=new HttpRequesterHandler();
		String urlString="http://localhost:8888/rgshcore/RealTimeDataInsertServlet";
		Map<String, String> params=new HashMap<String, String>();
		requesterHandler.sendPost(urlString, params);
	}
	@Test
	public void test2() {
		Map<String, String> a=new HashMap<>();
		String str=a.get("a");
		System.out.println(str);
	}
	
	@Test
	public void test3() {
		HttpRequesterHandler requesterHandler=new HttpRequesterHandler();
		
			String sendUrl="http://localhost/dbhelp/json/post";
			Map req=new HashMap<String, String>();
			req.put("busiId", "30010");
			System.out.println(com.ai.frame.util.JsonUtil.convertObject2Json(req));
//			HttpUtil.sendRestRequestJSON(sendUrl, JsonUtil.getJsonFromMap(req),"2000");
			requesterHandler.sendRestRequestJSON(sendUrl, com.ai.frame.util.JsonUtil.convertObject2Json(req),1000);
			
	}
}
