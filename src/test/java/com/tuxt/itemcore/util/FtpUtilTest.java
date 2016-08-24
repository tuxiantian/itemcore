package com.tuxt.itemcore.util;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.tuxt.itemcore.BaseTest;

public class FtpUtilTest extends BaseTest{

	@Test
	public void testUploadStringString() {
		String ftpPathCode="OL_IMSIINFO_REMOTE";
		try {
			FtpUtil ftpUtil=new FtpUtil(ftpPathCode);
			InputStream localFileName=new FileInputStream("E:/temp.xlsx");
			String remoteFileName=ftpUtil.getRemotePath()+"/"+"temp.xlsx";
			ftpUtil.upload(remoteFileName, localFileName, 1);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
