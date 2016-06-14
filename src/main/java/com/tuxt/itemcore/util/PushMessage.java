package com.tuxt.itemcore.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ai.frame.bean.OutputObject;
import com.ai.frame.util.JsonUtil;

public class PushMessage{

	public static String push(String inputObject) {
		/*请求的接口及参数如下
		 * http://localhost:8080/ol_java/RealnameServlet?req_json=
		 * {"beans":[],"busiId":"sendNotice",
		 * "logParams":{"billId":"15226195539","vertifyResult":"3"},
		 * "params":{"bossId":"15225192461","createDate":"yyyy-MM-dd hh:mm:ss"}}*/
		String pushmessageaddress=PropertiesUtil.getString("PUSHMESSAGEADDRESS");
		String paramString=	inputObject;
		String result = null;
		//paramString=paramString.replaceAll(" ", "%20");
		Map<String, Object> parameterMap=new HashMap<String, Object>();
		parameterMap.put("req_json", paramString);
		/*String url=pushmessageaddress+"="+paramString;
		System.out.println(url);*/
		try {
			//doGet(url);
		result=doPost(pushmessageaddress, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		//String urlString="http://10.96.233.19:8080/ol_java/RealnameServlet?req_json={\"beans\":[],\"busiId\":\"sendCheckResult\",\"params\":{\"createDate\":\"2015-08-13 10:52:43.0\",\"provCode\":\"100\",\"logid\":\"104648584\",\"checkResult\":\"9\"}}";
		String urlString="http://localhost:8802/mytest/DoGetTest?req_json={\"beans\":[],\"busiId\":\"sendCheckResult\",\"params\":{\"createDate\":\"2015-08-13%2010:52:43.0\",\"provCode\":\"100\",\"logid\":\"104648584\",\"checkResult\":\"9\"}}";
		String url="http://localhost:8802/mytest/DoGetTest";
		String param="{\"beans\":[],\"busiId\":\"sendCheckResult\",\"params\":{\"createDate\":\"2015-08-13 10:52:43.0\",\"provCode\":\"100我\",\"logid\":\"104648584\",\"checkResult\":\"9\"}}";
		Map<String, Object> parameterMap=new HashMap<String, Object>();
		parameterMap.put("req_json", param);
		//doGet(urlString);
		//doPost(url, parameterMap);
		push(param);
	}
	 /**
     * Do POST request
     * @param url
     * @param parameterMap
     * @return
     * @throws Exception 
     */
    public static String doPost(String url, Map parameterMap) throws Exception {
        /* Translate parameter map to parameter date string */
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String)iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String)parameterMap.get(key);
                } else {
                    value = "";
                }
                
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        int timeOut = 0;
        try{
			String timeBefStr = PropertiesUtil.getString("PUSH_MESSAGE_TIMEOUT");
			if (timeBefStr != null) {
				timeOut = Integer.parseInt(timeBefStr);
			}
        }catch(Exception e){
        	timeOut=100000;
        }
        System.out.println("POST parameter : " + parameterBuffer.toString());
        
        URL localURL = new URL(url);
        
        URLConnection connection = null;
		try {
			connection = openConnection(localURL);
		} catch (SocketTimeoutException e) {
			OutputObject out=new OutputObject();
			out.setReturnCode("-9999");
			out.setReturnMessage("调用接口超时");
			e.printStackTrace();
			return JsonUtil.outputObject2Json(out);
		}catch (Exception e) {
			OutputObject out=new OutputObject();
			out.setReturnCode("-2999");
			out.setReturnMessage("调用接口出现异常");
			e.printStackTrace();
			return JsonUtil.outputObject2Json(out);
		}
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));
        httpURLConnection.setConnectTimeout(timeOut);
        httpURLConnection.setReadTimeout(timeOut);
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            
            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();
            
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            
        }catch (SocketTimeoutException e) {
			OutputObject out=new OutputObject();
			out.setReturnCode("-9999");
			out.setReturnMessage("调用补登记接口超时");
			e.printStackTrace();
			return JsonUtil.outputObject2Json(out);
		} catch (Exception e) {
			OutputObject out=new OutputObject();
			out.setReturnCode("-2999");
			out.setReturnMessage("调用补登记接口出现异常");
			e.printStackTrace();
			return JsonUtil.outputObject2Json(out);
		}finally {
            
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }

        return resultBuffer.toString();
    }

    private static URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
     
            connection = localURL.openConnection();
        
        return connection;
    }
	/**
	 * Get Request
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("http.protocol.version", "HTTP protocol version 1.1");
		httpURLConnection.setRequestProperty("http.protocol.content-charset", "UTF-8");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			System.out.println(httpURLConnection.getResponseMessage());
			System.out.println(httpURLConnection.getContent());
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
			if (httpURLConnection!=null) {
				httpURLConnection.disconnect();
			}
		}

		return resultBuffer.toString();
	}
	
}
