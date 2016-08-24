package com.tuxt.itemcore.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.itemcore.service.IFtpService;

//下载后关闭流 completePendingCommand 关闭ftp
//上传前 连接 bin
public class FtpUtil {
	private static final Logger logger = LoggerFactory.getUtilLog(FtpUtil.class);
	public static final String FILE_SPEARATROR = "/";
	public static final int BIN = 0;
	public static final int ASC = 1;
	protected FTPClient client = null;
	private String localPath = null;
	private String remotePathHis;
	private String _host = null;
	private int _port = 0;
	private String _username = null;
	private String _password = null;
	private String _remotePath = null;
	private String _localPath = null;
	private String _localPathTemp = null;
	private String _part = null;
	
	public FtpUtil(String ftpPathCode) throws Exception {
		IFtpService ftpService=(IFtpService) SpringApplicationUtil.getBean("ftpService");
		Map<String, Object> cfgFtpPath=ftpService.queryCfgFtpPath(ftpPathCode);
		Map<String, Object> cfgFtpPart=ftpService.queryCfgFtpPart(String.valueOf(cfgFtpPath.get("cfgFtpCode")));
	    client = new FTPClient();
	    client.connect(String.valueOf(cfgFtpPart.get("hostIp")),Integer.parseInt(String.valueOf(cfgFtpPart.get("port"))));
	    client.login(String.valueOf(cfgFtpPart.get("username")), String.valueOf(cfgFtpPart.get("password")));
	    int reply = client.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			throw new Exception("登录FTP失败");
		}
	    client.changeWorkingDirectory(String.valueOf(cfgFtpPart.get("remotePath")));
	    _host = String.valueOf(cfgFtpPart.get("hostIp"));
	    _port = Integer.parseInt(String.valueOf(cfgFtpPart.get("port")));
	    _username = String.valueOf(cfgFtpPart.get("username"));
	    _password = String.valueOf(cfgFtpPart.get("password"));
	    _part = String.valueOf(cfgFtpPart.get("part"));
	    
	    localPath = String.valueOf(cfgFtpPath.get("localPath"));
	    remotePathHis = String.valueOf(cfgFtpPath.get("remotePathHis"));
	    _remotePath = String.valueOf(cfgFtpPath.get("remotePath"));
	    _localPath = String.valueOf(cfgFtpPath.get("localPath"));
	    _localPathTemp = String.valueOf(cfgFtpPath.get("localPathTemp"));
  }
	public String getPart() {
		return _part;
	}

	public void bin() throws Exception {
		client.setFileType(FTP.BINARY_FILE_TYPE);
	}

	public void asc() throws Exception {
		client.setFileType(FTP.ASCII_FILE_TYPE);
	}

	public void setBufferSize(int size) {
		client.setBufferSize(size);
	}

	public void setTimeOut(int time) {
		client.setControlKeepAliveReplyTimeout(time);
		client.setConnectTimeout(time);
		client.setControlKeepAliveTimeout(time);
	}

	public void setFileTransferModeBin() throws Exception {
		client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
	}
	
	public void enterLocalPassiveMode() {
		client.enterLocalPassiveMode();
	}

	public String[] list(String encoding) throws Exception {
		List<String> list = new ArrayList<String>();
		FTPFile[] ftpFiles = client.listFiles(client.printWorkingDirectory());
		for (int i = 0; i < ftpFiles.length; i++) {
			if (ftpFiles[i].isFile()) {
				list.add(new String(ftpFiles[i].getName().getBytes("ISO-8859-1"), encoding));
			}
		}

		return (String[]) list.toArray(new String[0]);
	}

	public FTPFile[] listFtpFiles(String pathName) throws Exception {
		return client.listFiles(pathName);
	}

	/**
	 * 
	 * @throws Exception
	 * @return String[]
	 */
	public String[] list() throws Exception {
		return list("GBK");
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param input
	 *            InputStream
	 * @param mode
	 *            int
	 * @throws Exception
	 */
	public void upload(String remoteFileName, InputStream input, int mode) throws Exception {
		if (mode == BIN) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
		} else if (mode == ASC) {
			client.setFileType(FTP.ASCII_FILE_TYPE);
		} else {
			throw new Exception("不支持的传输模式:" + mode);
		}
		upload(remoteFileName, input);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param input
	 *            InputStream
	 * @throws Exception
	 */
	public void upload(String remoteFileName, InputStream input) throws Exception {
		client.storeFile(remoteFileName, input);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param localFileName
	 *            String
	 * @throws Exception
	 */
	public void upload(String remoteFileName, String localFileName) throws Exception {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(localPath + "/" + localFileName));
			client.storeFile(remoteFileName, is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param localFileName
	 *            String
	 * @param mode
	 *            int
	 * @throws Exception
	 */
	public void upload(String remoteFileName, String localFileName, int mode) throws Exception {
		if (mode == BIN) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
		} else if (mode == ASC) {
			client.setFileType(FTP.ASCII_FILE_TYPE);
		}

		else {
			throw new Exception("不支持的传输模式:" + mode);
		}
		upload(remoteFileName, localFileName);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param output
	 *            OutputStream
	 * @param mode
	 *            int
	 * @throws Exception
	 */
	public void download(String remoteFileName, OutputStream output, int mode) throws Exception {
		// client.setControlEncoding("GBK");
		if (mode == BIN) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
		} else if (mode == ASC) {
			client.setFileType(FTP.ASCII_FILE_TYPE);
		}

		else {
			throw new Exception("不支持的传输模式:" + mode);
		}
		download(remoteFileName, output);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param output
	 *            OutputStream
	 * @throws Exception
	 */
	public void download(String remoteFileName, OutputStream output) throws Exception {
		String filePath = null;
		String downFile = remoteFileName.trim();
		if (downFile.indexOf("\\") >= 0 || downFile.indexOf("/") >= 0) {
			if (downFile.charAt(0) != '.') {
				downFile = "." + downFile;
			}
			if (downFile.indexOf("\\") > downFile.indexOf("/")) {
				filePath = downFile.substring(0, downFile.lastIndexOf("\\"));
				downFile = downFile.substring(downFile.lastIndexOf("\\") + 1, downFile.length());
			} else {
				filePath = downFile.substring(0, downFile.lastIndexOf("/"));
				downFile = downFile.substring(downFile.lastIndexOf("/") + 1, downFile.length());
			}
		}
		downFile = new String(downFile.getBytes("GBK"), "iso-8859-1");
		if (null != filePath) {
			filePath = new String(filePath.getBytes("GBK"), "iso-8859-1");
			this.changeWorkingDirectory(filePath);
		}
		boolean rtn = client.retrieveFile(downFile, output);
		if (rtn == false) {
			throw new Exception("获取文件失败:" + remoteFileName);
		}
	}

	public void downloadSimple(String remoteFileName, OutputStream output) throws Exception {
		boolean rtn = client.retrieveFile(remoteFileName, output);
		if (rtn == false) {
			throw new Exception("下载远程文件:" + remoteFileName + ",不成功");
		}
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param localFileName
	 *            String
	 * @param mode
	 *            int
	 * @throws Exception
	 */
	public void download(String remoteFileName, String localFileName, int mode) throws Exception {
		if (mode == BIN) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
		} else if (mode == ASC) {
			client.setFileType(FTP.ASCII_FILE_TYPE);
		} else {
			throw new Exception("不支持的传输模式ʽ:" + mode);
		}
		download(remoteFileName, localFileName);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param localFileName
	 *            String
	 * @throws Exception
	 */
	public void download(String remoteFileName, String localFileName) throws Exception {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(localPath + "/" + localFileName));
			boolean rtn = client.retrieveFile(remoteFileName, os);
			if (rtn == false) {
				throw new Exception("获取文件失败:" + remoteFileName);
			}
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @throws Exception
	 * @return InputStream
	 */
	public InputStream readRemote(String remoteFileName) throws Exception {
		return client.retrieveFileStream(remoteFileName);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @param mode
	 *            int
	 * @throws Exception
	 * @return InputStream
	 */
	public InputStream readRemote(String remoteFileName, int mode) throws Exception {
		if (mode == BIN) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
		} else if (mode == ASC) {
			client.setFileType(FTP.ASCII_FILE_TYPE);
		} else {
			throw new Exception("不支持的传输模式ʽ:" + mode);
		}
		return readRemote(remoteFileName);
	}

	/**
	 * 
	 * @param oldRemoteFileName
	 *            String
	 * @param newRemoteFileName
	 *            String
	 * @throws Exception
	 */
	public void rename(String oldRemoteFileName, String newRemoteFileName) throws Exception {
		client.rename(oldRemoteFileName, newRemoteFileName);
	}

	/**
	 * 
	 * @param remoteFileName
	 *            String
	 * @throws Exception
	 */
	public void delete(String remoteFileName) throws Exception {
		boolean rtn = client.deleteFile(remoteFileName);
		if (rtn == false) {
			throw new Exception("删除远程文件:" + remoteFileName + ",不成功");
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void completePendingCommand() throws Exception {
		client.completePendingCommand();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		if (client.isConnected()) {
			client.disconnect();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void reconnect() throws Exception {
		if (!client.isConnected()) {
			client = new FTPClient();
			client.connect(_host, _port);
			client.login(_username, _password);
			client.changeWorkingDirectory(_remotePath);

			localPath = _localPath;
		}
	}

	public boolean isConnected() throws Exception {
		return client.isConnected();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void forceReconnect() throws Exception {
		if (!client.isConnected()) {
			client.disconnect();
		}

		client = new FTPClient();
		client.connect(_host, _port);
		client.login(_username, _password);
		client.changeWorkingDirectory(_remotePath);

		localPath = _localPath;
	}

	public OutputStream getOutputStream(String fileName) throws Exception {
		return client.storeFileStream(fileName);
	}

	public void moveFileToRemoteHisDir(String fileName) throws Exception {
		if (client.listFiles(fileName).length == 0) {
			throw new Exception("在远程服务器上未找到文件" + fileName + ",文件移动至历史目录失败");
		}
		StringBuffer newFileName = new StringBuffer();
		newFileName.append(getRemotePathHis());
		newFileName.append(FILE_SPEARATROR);
		newFileName.append(fileName);
		rename(fileName, newFileName.toString());
	}

	public String getRemotePathHis() throws Exception {
		if (remotePathHis == null || remotePathHis.length() < 1) {
			throw new Exception("未配置远程文件历史目录");
		}
		return remotePathHis;
	}

	public String getWorkingDirectory() throws Exception {
		if (this.client != null) {
			return this.client.printWorkingDirectory();
		}
		return null;
	}

	public void changeWorkingDirectory(String pathName) throws Exception {
		if (this.client != null) {
			this.client.changeWorkingDirectory(pathName);
		}
	}

	public String getLocalPath() {
		return this._localPath;
	}

	public String getLocalPathTemp() {
		return this._localPathTemp;
	}

	public String getRemotePath() {
		return _remotePath;
	}

	public boolean appendFile(String remoteName, InputStream in) throws Exception {
		return client.appendFile(remoteName, in);
	}

	public void changeToRightDir(String directoryId) throws Exception {
		if (client != null) {
			String ftpDir = client.printWorkingDirectory();
			if (ftpDir == null)
				throw new Exception("FTP文件夹为NULL");
			String curDir = ftpDir + FILE_SPEARATROR + directoryId;
			boolean flag = client.changeWorkingDirectory(curDir);
			if (flag == false) {
				client.makeDirectory(directoryId);
				client.changeWorkingDirectory(curDir);
			}
		}
	}

	/**
	 * @throws Exception
	 */
	public void changeToRemoteDir() throws Exception {
		if (client != null) {
			String ftpDir = client.printWorkingDirectory();
			if (ftpDir == null)
				throw new Exception("FTP文件夹为NULL");
			client.changeWorkingDirectory(_remotePath);

		}
	}

	public void changeDir(String path) throws Exception {
		if (client != null) {
			System.out.println("changeDir: " + path);
			if (  null ==path  || "".equals(path)) {
				throw new Exception("FTP路径为NULL");
			}
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length; i++) {
				String ftpDir = client.printWorkingDirectory();
				String curDir = ftpDir + FILE_SPEARATROR + paths[i];
				System.out.println("crateDir: " + curDir);
				client.makeDirectory(new String(paths[i].getBytes("UTF-8"), "iso-8859-1"));
				client.changeWorkingDirectory(curDir);
			}
		}
	}

	public boolean makeDirectory(String fileDirectory) throws Exception {
		if (StringUtils.isBlank(fileDirectory)
				|| !StringUtils.containsNone(fileDirectory.substring(fileDirectory.lastIndexOf("/") + 1), new char[] {
						'\\', '/', '?', '"', '*', '>', '<' })) {
			throw new Exception("请检查文件目录是否为空或者含有特殊字符！");
		}
		return client.makeDirectory(fileDirectory);
	}

	public boolean isExistsDirectory(String dirName) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = client.listFiles();
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory() && ftpFile.getName().equals(dirName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public boolean isExistsFile(String fileName) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = client.listFiles();
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isFile() && ftpFile.getName().equals(fileName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void cleanDirectory(String basePath) throws IOException {
		logger.info("准备清空文件夹:", basePath);
		FTPFile[] ftpFileArr = client.listFiles(basePath);
		logger.info("开始清空文件夹:", basePath);
		if (ftpFileArr != null && ftpFileArr.length > 0) {
			for (FTPFile ftpFile : ftpFileArr) {
				String fileName = ftpFile.getName();
				if (fileName.equals(".") || fileName.equals("..")) {
					continue;
				}
				if (ftpFile.isFile()) {
					logger.info("删除文件", basePath + "/" + fileName);
					boolean deleted = deleteFile(basePath + "/" + fileName);
					if (deleted) {
						logger.info("deleted the file:", basePath + "/" + fileName);
					} else {
						logger.info("cannot deleted the file:", basePath + "/" + fileName);
					}
				} else if (ftpFile.isDirectory()) {
					logger.info("删除文件夹", basePath + "/" + fileName);
					removeDirectory(basePath + "/" + fileName, true);
				}
			}
		}
		logger.info("清空文件夹结束:", basePath);
	}

	public boolean removeDirectory(String path) throws IOException {
		return client.removeDirectory(path);
	}

	public boolean removeDirectory(String path, boolean isAll) throws IOException {
		if (!isAll) {
			return removeDirectory(path);
		}
		FTPFile[] ftpFileArr = client.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				logger.info("删除文件夹", path + "/" + name);
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				logger.info("删除文件", path + "/" + name);
				deleteFile(path + "/" + name);
			}
		}
		return client.removeDirectory(path);
	}

	public boolean deleteFile(String pathName) throws IOException {
		return client.deleteFile(pathName);
	}

	public void makeDir(String path) throws Exception {
		if (client != null) {
			if (StringUtil.isEmpty(path)) {
				return;
			}
			path = path.replaceAll("//", "/");
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
			if (path.endsWith("/")) {
				path = path.substring(0, path.length() - 1);
			}
			String[] paths = path.split("/");
			String tmpDir = client.printWorkingDirectory();
			for (int i = 0; i < paths.length; i++) {
				String ftpDir = client.printWorkingDirectory();
				String curDir = ftpDir + FILE_SPEARATROR + paths[i];
				client.makeDirectory(new String(paths[i].getBytes("UTF-8"), "iso-8859-1"));
				client.changeWorkingDirectory(curDir);
			}
			client.changeWorkingDirectory(tmpDir);
		}
	}

	public static void main(String[] args) {
		
	}

}
