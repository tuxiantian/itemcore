package com.tuxt.itemcore.util.httputil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

/**
 * 使用该类必须设置URLConnection的超时时间，若使用空构造函数，则默认超时时间为15秒。
 * @author Administrator
 *
 */
public class HttpRequesterHandler {     
	private static Logger logger = Logger.getLogger(HttpRequesterHandler.class);
	private static String defaultContentEncoding;     
	private   static   String timeout;

	/**
	 * Sets a specified timeout value, in <b>milliseconds</b>, to be used when opening a communications link to the resource referenced by this URLConnection. 
	 * @param timeout
	 */
	public HttpRequesterHandler(String timeout) {     
		this.setTimeout(timeout);
		defaultContentEncoding = Charset.defaultCharset().name();
	} 

	private void setTimeout(String timeout) {
		HttpRequesterHandler.timeout	=timeout;
	}
	
	/**
	 * Sets a specified timeout value, in <b>milliseconds</b>, to be used when opening a communications link to the resource referenced by this URLConnection.
	 * @param timeout
	 * @param contentEncoding
	 */
	public HttpRequesterHandler(String timeout,String contentEncoding) {     
		this.setTimeout(timeout);
		defaultContentEncoding = contentEncoding;
	}
	
	public HttpRequesterHandler() {     
		defaultContentEncoding = Charset.defaultCharset().name();
		timeout="15000";
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
	
	/**
	 * 既发送文本参数又发送文件。textMap中放文本参数，fileMap中放文件参数。注意fileMap中的值塞的是文件的路径。
	 * 该方法通常用于测试。
	 * @param urlStr 调用地址
	 * @param textMap 文本参数
	 * @param fileMap 文件参数
	 * @param propertys
	 * @return
	 */
	public String post(String urlStr, Map<String, String> textMap,
			Map<String, String> fileMap,Map<String, String> propertys) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "WebKitFormBoundSmrz"; // boundary就是request头和上传文件内容的分隔符
		boolean ismultipart;
		if (fileMap != null && fileMap.size() > 0) {
			ismultipart = true;
		}else{
			ismultipart=false;
		}
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(Integer.valueOf(timeout));
			conn.setReadTimeout(Integer.valueOf(timeout));
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset",defaultContentEncoding);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			if (ismultipart) {
				conn.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + BOUNDARY);
			} else {
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded; charset=" + defaultContentEncoding);
			}
			
			if(propertys!=null){
				for (Map.Entry<String, String> prop : propertys.entrySet()) {
					String key=prop.getKey();
					String value=prop.getValue();
					if("Content-Type".equals(key)){
						value=value+"; charset="+ defaultContentEncoding;
					}
					conn.setRequestProperty(key,value);
				}
			}

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuilder strBuf = new StringBuilder();
				for (Map.Entry<String, String> entry: textMap.entrySet()) {
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
				
					String contentType=conn.getRequestProperty("Content-Type");//获取请求类型，如果是基本的类型，则转码，否则不转码
					if(contentType==null||contentType.contains("application/x-www-form-urlencoded")){
						inputValue=URLEncoder.encode(inputValue,defaultContentEncoding);
					}
					strBuf.append(inputName).append("=").append(inputValue).append("&");
				}
				
				String param=strBuf.toString();
				if(param.endsWith("&")){
					param=param.substring(0, strBuf.length()-1);
				}
				out.write(param.getBytes(defaultContentEncoding));
			}

			// file
			if (fileMap != null) {
				for (Map.Entry<String, String> entry: fileMap.entrySet()) {
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();
					String contentType = "application/octet-stream";

					StringBuilder strBuf = new StringBuilder();
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"; filename=\"" + filename
							+ "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(
							new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					String endData = "\r\n--" + BOUNDARY + "--\r\n";
					out.write(endData.getBytes());
					in.close();
				}
			}
			
			out.flush();
			out.close();
			// 读取返回数据
			StringBuilder strBuf = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), defaultContentEncoding));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			logger.error("发送POST请求出错。"+urlStr,e);
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
	
	/**
	 * 以resful风格发送请求，直接发送json。
	 * @param url
	 * @param json
	 * @param timeout
	 * @return
	 */
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


