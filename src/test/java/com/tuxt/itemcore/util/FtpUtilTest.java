package com.tuxt.itemcore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

public class FtpUtilTest  extends TestCase{
	String ftpValueHostIp;
	int ftpValuePort;
	String ftpValueUsername;
	String ftpValuePassword;
	String ftpPathRemotePath;
	String ftpPathLocalPath=null;
	String ftpPathRemotePathHis=null;
	String ftpPathLocalPathTemp=null;
	FtpUtil ftpUtil;
	protected void setUp() throws Exception {
		ftpValueHostIp="localhost";
		ftpValuePort=21;
		ftpValueUsername="admin";
		ftpValuePassword="123";
		ftpPathRemotePath="E:\ftp";
		ftpPathLocalPath="E:/wande/人工审核";
		ftpUtil=new FtpUtil(ftpValueHostIp, ftpValuePort, ftpValueUsername, ftpValuePassword, ftpPathRemotePath, ftpPathLocalPath, ftpPathRemotePathHis, ftpPathLocalPathTemp);
		System.out.println("setUp方法执行了");
	}
	public void delete(){
	
		try {
			ftpUtil.delete("a.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void download(){
		try {
			ftpUtil.download("a.txt", "a.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void upload(){
		String localFileName="人工审核.txt";
		try {
			//ftpUtil.upload("人工审核.txt", localFileName);
			InputStream input=new FileInputStream(new File("E:/wande/人工审核/人工审核.txt"));
			ftpUtil.upload("人工审核.txt", input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void list() {			
		try {
			String[]fileNames=ftpUtil.list();
			for (String string : fileNames) {
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String ftpValueHostIp="localhost";
		int ftpValuePort=21;
		String ftpValueUsername="admin";
		String ftpValuePassword="123";
		String ftpPathRemotePath="E:\ftp";
		String ftpPathLocalPath=null;
		String ftpPathRemotePathHis=null;
		String ftpPathLocalPathTemp=null;
		try {
			FtpUtil ftpUtil=new FtpUtil(ftpValueHostIp, ftpValuePort, ftpValueUsername, ftpValuePassword, ftpPathRemotePath, ftpPathLocalPath, ftpPathRemotePathHis, ftpPathLocalPathTemp);
			String[]fileNames=ftpUtil.list();
			for (String string : fileNames) {
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
