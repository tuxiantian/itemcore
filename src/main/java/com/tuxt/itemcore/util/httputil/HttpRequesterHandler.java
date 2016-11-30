package com.tuxt.itemcore.util.httputil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;


public class HttpRequesterHandler {     
	private static Logger logger = Logger.getLogger(HttpRequesterHandler.class);
	private static String defaultContentEncoding;     
	private   static   String timeout;


	public HttpRequesterHandler(String timeout) {     
		this.setTimeout(timeout);
	} 

	private void setTimeout(String timeout) {
		HttpRequesterHandler.timeout	=timeout;
	}

	public HttpRequesterHandler() {     
		defaultContentEncoding = Charset.defaultCharset().name();       
	}     


	public HttpResponsHandler sendGet(String urlString) throws IOException {     
		return  send(urlString, "GET", null, null);     
	}     


	public  HttpResponsHandler sendGet(String urlString, Map<String, String> params)     
			throws IOException {     
		return  send(urlString, "GET", params, null);     
	}     


	public HttpResponsHandler sendGet(String urlString, Map<String, String> params,     
			Map<String, String> propertys) throws IOException {     
		return  send(urlString, "GET", params, propertys);     
	}     


	public HttpResponsHandler sendPost(String urlString) throws IOException {     
		return  send(urlString, "POST", null, null);     
	}     


	public  HttpResponsHandler sendPost(String urlString, Map<String, String> params)     
			throws IOException {     
		return send(urlString, "POST", params, null);     
	}     


	public HttpResponsHandler sendPost(String urlString, Map<String, String> params,     
			Map<String, String> propertys) throws IOException {     
		return  send(urlString, "POST", params, propertys);     
	}     


	private static HttpResponsHandler send(String urlString, String method,     
			Map<String, String> parameters, Map<String, String> propertys)     
					throws IOException {     
		HttpURLConnection urlConnection = null;     

		if (method.equalsIgnoreCase("GET") && parameters != null) {     
			StringBuffer param = new StringBuffer();     
			int i = 0;     
			for (String key : parameters.keySet()) {     
				if (i == 0)     
					param.append("?");     
				else    
					param.append("&");   

				String replaceStr = java.net.URLEncoder.encode(parameters.get(key),"UTF-8");
				param.append(key).append("=").append(replaceStr);     
				i++;     
			}     
			urlString += param;     
		}     
		URL url = new URL(urlString);     
		urlConnection = (HttpURLConnection) url.openConnection();     
		urlConnection.setConnectTimeout(Integer.valueOf(timeout));  //
		urlConnection.setReadTimeout(Integer.valueOf(timeout));  //
		urlConnection.setRequestMethod(method);   
		urlConnection.setDoOutput(true);      
		urlConnection.setDoInput(true);     
		urlConnection.setUseCaches(false); 
		urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
		urlConnection.setRequestProperty("contentType", "UTF-8");
		if (propertys != null)     
			for (String key : propertys.keySet()) {     
				urlConnection.addRequestProperty(key, propertys.get(key));     
			}     

		if (method.equalsIgnoreCase("POST") && parameters != null) {     
			StringBuffer param = new StringBuffer();     
			for (String key : parameters.keySet()) {     
				param.append("&");    
				String replaceStr = java.net.URLEncoder.encode(parameters.get(key),"UTF-8");
				param.append(key).append("=").append( replaceStr);     
			}     

			PrintWriter out = new PrintWriter(new OutputStreamWriter(urlConnection.getOutputStream(),"utf-8"));   //urlConnection 为 HttpURLConnection对象
			out.write(param.toString());  
			out.flush(); 
			out.close();
			//urlConnection.getOutputStream().write(param.toString().getBytes("UTF-8"));     
			urlConnection.getOutputStream().flush();     
			urlConnection.getOutputStream().close();     
		}     

		return makeContent(urlString, urlConnection);     
	}     


	private static HttpResponsHandler makeContent(String urlString,     
			HttpURLConnection urlConnection) throws IOException {     
		HttpResponsHandler httpResponser = new HttpResponsHandler(); 
		InputStream in = urlConnection.getInputStream();
		try {   

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));     
			httpResponser.contentCollection = new Vector<String>();     
			StringBuffer temp = new StringBuffer();     
			String line = bufferedReader.readLine();     
			while (line != null) {     
				httpResponser.contentCollection.add(line);     
				temp.append(line).append("\r\n");     
				line = bufferedReader.readLine();     
			}     
			bufferedReader.close(); 
			in.close();

			String ecod = urlConnection.getContentEncoding();     
			if (ecod == null)   ecod = defaultContentEncoding;     

			httpResponser.urlString = urlString;     
			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();     
			httpResponser.file = urlConnection.getURL().getFile();     
			httpResponser.host = urlConnection.getURL().getHost();     
			httpResponser.path = urlConnection.getURL().getPath();     
			httpResponser.port = urlConnection.getURL().getPort();     
			httpResponser.protocol = urlConnection.getURL().getProtocol();     
			httpResponser.query = urlConnection.getURL().getQuery();     
			httpResponser.ref = urlConnection.getURL().getRef();     
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();     
			httpResponser.content = new String(temp.toString());     
			httpResponser.contentEncoding = ecod;     
			httpResponser.code = urlConnection.getResponseCode();     
			httpResponser.message = urlConnection.getResponseMessage();     
			httpResponser.contentType = urlConnection.getContentType();     
			httpResponser.method = urlConnection.getRequestMethod();     
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();     
			httpResponser.readTimeout = urlConnection.getReadTimeout();     
			return httpResponser;     
		} catch (IOException e) {     
			throw e;     
		} finally {     
			if (urlConnection != null)     
				urlConnection.disconnect(); 
		}     
	}     


	public String getDefaultContentEncoding() {     
		return defaultContentEncoding;     
	}

	public String getTimeout() {
		return timeout;
	}
	
	public String sendRestRequestJSON(String url, String json, int timeout) {
		String result = null;
		try {
			logger.info("start post requst Url : " + url);
			
			URL urlAddr = new URL(url);
			PostMethod httpPost = new PostMethod(urlAddr.toString());
			HttpClient client = new HttpClient();
			client.getParams().setParameter("http.protocol.version",
					HttpVersion.HTTP_1_1);
			client.getParams().setParameter("http.protocol.content-charset",
					"UTF-8");
			client.getParams().setParameter("http.socket.timeout",
						new Integer(timeout* 1000));
			
			httpPost.addRequestHeader("Content-Type", "application/json");
			httpPost.setRequestEntity(new InputStreamRequestEntity(
					new ByteArrayInputStream(json.getBytes("UTF-8"))));
			client.executeMethod(httpPost);
			logger.info("getStatusCode:"+httpPost.getStatusCode());
			 if (httpPost.getStatusCode() == HttpStatus.SC_OK) {
			     result = httpPost.getResponseBodyAsString();
			 }
			 httpPost.releaseConnection();

		} catch (Exception e) {
			logger.error("调用接口失败", e);
		}

		return result;
	}


}


