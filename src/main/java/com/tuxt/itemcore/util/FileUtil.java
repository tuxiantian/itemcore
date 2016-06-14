package com.tuxt.itemcore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.tuxt.itemcore.util.fileUtil.WriteFileUtil;

public class FileUtil {
	public static String readTxtFile(String filePath){
		StringBuffer buffer=new StringBuffer();
		try {
			String encoding="utf-8";
			File file=new File(filePath);
			if(file.isFile() && file.exists()){ //判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file),encoding);//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
					System.out.println(lineTxt.length());
					buffer.append(lineTxt);
					lineTxt = lineTxt.replaceAll("0", "0 ").replaceAll("1", "1 ");
					WriteFileUtil.appendFile("J:/binary2.txt", lineTxt+"\n");
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return buffer.toString();
	}
	public static void main(String[] args) throws Exception {
		String content=readTxtFile("J:/binary.txt");
		//String content2=readTxtFile("J:/binary2.txt");
		/*content=content.replaceAll("0", "0 ").replaceAll("1", "1 ");
		WriteFileUtil.writeFileSingle("J:/binary2.txt", content);*/
	}
}
