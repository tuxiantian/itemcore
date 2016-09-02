package com.tuxt.itemcore.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ai.frame.bean.OutputObject;

public class ExcelPOI07UtilTest {

	@Test
	public void testCreateExcel2007MergeCells() throws FileNotFoundException {
		OutputStream out=new FileOutputStream("E:/test/a.xlsx");
		OutputObject outObj=new OutputObject();
//		outObj.getBean().put("excel_head_cnname", "A,B|C|C,B|D|E,G|D|F,H,H,J|K");
//		outObj.getBean().put("excel_head_cnname", "A,B|C|C,B|D|E,G|D|E*,H,H,J|K");
		outObj.getBean().put("excel_head_cnname", "A,B|C|C,B|D|E,G|D|E,H,H,J|K");
		String dataNameStr="a,b,c,d,e,f,g";
		outObj.getBean().put("excel_data_enname", dataNameStr);
		String[] dataName=dataNameStr.split(",");
		List<Map<String, Object>> beans=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		for (int i = 0,len=dataName.length; i < len; i++) {
			map.put(dataName[i], "1");
		}
		beans.add(map);
		outObj.setBeans(beans);
		ExcelPOI07Util.createExcel2007MergeCells(out, outObj);
		
	}

}
