package com.tuxt.itemcore.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ai.frame.bean.OutputObject;

public class ExcelPOI07Util {
	public static void createExcel2007(OutputStream out, OutputObject outObj) {
		try {
			Workbook wb = new XSSFWorkbook();
			String headStr = (String) outObj.getBean().get("excel_head_cnname");//标题
			Sheet sheet = wb.createSheet();
			int startR = 0;
			Map<String, CellStyle> styleMap = createStyles(wb);
			if (!StringUtil.isEmpty(headStr)) {
				
				CellStyle headStyle = styleMap.get("sheet_title_style");
				
				String[] headArr = headStr.split(",");
				String cellSizeStr = (String) outObj.getBean().get("excel_data_cellSize");//宽度
				if (!StringUtil.isEmpty(cellSizeStr)) {
					String[] cellSizeArr = cellSizeStr.split(",");
					createExcelHeadNormal(sheet, startR, headArr, cellSizeArr, headStyle);
				} else {
					createExcelHeadNormal(sheet, startR, headArr, headStyle);
				}
				startR++;
			}
			String dataNameStr = (String) outObj.getBean().get("excel_data_enname");//内容
			if (!StringUtil.isEmpty(dataNameStr)) {
				CellStyle cellStyle = styleMap.get("cell_normal_style");
				String[] dataEnNameArr = dataNameStr.split(",");
				List<Map<String, Object>> dataList = outObj.getBeans();
				createExcelBody(sheet, startR, dataEnNameArr, dataList, cellStyle);
			}
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createExcel2007MergeCells(OutputStream out, OutputObject outObj) {
		try {
			Workbook wb = new XSSFWorkbook();
			String headStr = (String) outObj.getBean().get("excel_head_cnname");//标题
			Sheet sheet = wb.createSheet();
			int startR = 0;
			Map<String, CellStyle> styleMap = createStyles(wb);
			if (!StringUtil.isEmpty(headStr)) {
				CellStyle headStyle = styleMap.get("newsheet_title_style");
				String[] headArr = headStr.split(",");
				String cellSizeStr = (String) outObj.getBean().get("excel_data_cellSize");//宽度
				
				if (!StringUtil.isEmpty(cellSizeStr)) {
					startR = createExcelHeadMergeCells(sheet, startR, headArr, cellSizeStr.split(","), headStyle);
				} else {
					startR = createExcelHeadMergeCells(sheet, startR, headArr,null, headStyle);
				}
			}
			
			String dataNameStr = (String) outObj.getBean().get("excel_data_enname");//内容
			if (!StringUtil.isEmpty(dataNameStr)) {
				CellStyle cellStyle = styleMap.get("cell_normal_style");
				String[] dataEnNameArr = dataNameStr.split(",");
				List<Map<String, Object>> dataList = outObj.getBeans();
				createExcelBody(sheet, startR, dataEnNameArr, dataList, cellStyle);
			}
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createExcelBody(Sheet sheet, int startR, String[] dataEnNameArr,
			List<Map<String, Object>> dataList, CellStyle normalCellStyle) throws Exception {
		for (int i = 0, len = dataList.size(); i < len; i++) {
			Row row = sheet.createRow(startR + i); 
			for (int j = 0, jlen = dataEnNameArr.length; j < jlen; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(StringUtil.trim2Empty((String) dataList.get(i).get(dataEnNameArr[j])));
				cell.setCellStyle(normalCellStyle);
			}
		}
	}

	public static void createExcelHeadNormal(Sheet sheet, int startR, String[] headArr, CellStyle headStyle)
			throws Exception {
		Row row = sheet.createRow(startR);
		row.setHeight((short)500);
		for (int i = 0, len = headArr.length; i < len; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headArr[i]);
			cell.setCellStyle(headStyle);
		}
		for (int i = 0, len = headArr.length; i < len; i++) {
			sheet.setColumnWidth(i, 20 * 256);
		}
	}

	/***
	 * 根据格式 合并 头部 单元格 支持多行、多列  复杂的合并
	 * 如果有格式问题，联系  殊風
	 * @param sheet
	 * @param startR
	 * @param headArr
	 * @param headStyle
	 * @throws Exception
	 */
	public static int createExcelHeadMergeCells(Sheet sheet, int startR, String[] headArr, String[] cellSizeArr, CellStyle headStyle)
			throws Exception {
		int HeadRowCnt = 0, tmpval;
		for (int i = 0; i < headArr.length - 1; i++) {
			tmpval = headArr[i].split("\\|").length;
			
			if (tmpval > HeadRowCnt){
				HeadRowCnt = tmpval;
			}
		}

		List<String[]> headlist = new ArrayList<>();
		for (int i = 0; i < headArr.length; i++) {
			String[] headArray = new String[HeadRowCnt];
			String[] tmpheadArray = headArr[i].split("\\|");
			int deci = headArray.length - 1; 
			for(int j = tmpheadArray.length - 1; j >= 0; j--)
			{
				headArray[deci--] = tmpheadArray[j];
			}
			for(int j = 0; j < headArray.length; j++)
			{
				if(headArray[j] == null || headArray[j].equals("")){
					headArray[j] = tmpheadArray[0];
				}
				else{
					break;
				}
			}
			headlist.add(headArray);
		}
		Row[] rows = new Row[HeadRowCnt];
		for(int i = 0;i< HeadRowCnt;i++)
		{
			rows[i] = sheet.createRow(startR + i);
			rows[i].setHeight((short)300);
		}
		for (int col=0; col<headArr.length;col++)
		{
			String[] Cols= headlist.get(col);
			for (int row = 0; row < Cols.length; row++) {
				Cell cell = rows[row].createCell(col);
				char c = Cols[row].charAt(Cols[row].length() - 1);
				if ('*' == c)
				{
					cell.setCellValue(Cols[row].substring(0, Cols[row].length() - 1));
					Cols[row] = "";
				}
				else
				{
					cell.setCellValue(Cols[row]);
				}
				cell.setCellStyle(headStyle);
			}
		}
		//循环开始合并 Col 与 Row 都是从0开始
		int endrow=0, endcol = 0, tmpcol=0;
		for(int row=0; row < HeadRowCnt; row++){
			for(int col = 0; col < headArr.length; col++){

				if (!headlist.get(col)[row].equals(""))
				{
					tmpcol=col;
					for (int i=row; i < HeadRowCnt; i++) //行
					{
						for(int j = col; j < headArr.length;j++) //列
						{
							if(!headlist.get(col)[row].equals(headlist.get(j)[i])){
								tmpcol = j - 1;
								break;
							}
						}
						if(i==row)
						{
							endcol = tmpcol;
						}
						else if (endcol != tmpcol)
						{
							break;
						}
						endrow = i;
					}
					//是否符合合并条件
					if ((endcol > col) || (endrow > row))
					{
						//合并 并清空 区域内值
						sheet.addMergedRegion(new CellRangeAddress(row, endrow, col, endcol));
						for(int i=row;i<=endrow;i++)
						{
							for(int j=col;j<=endcol;j++)
							{
								headlist.get(j)[i] = "";
							}
						}
					} 
				}
			}
		}

		/*if (cellSizeArr != null){
			for (int i = 0, len = headArr.length; i < len; i++) {
				sheet.setColumnWidth(i,str2Int(cellSizeArr[i], 20) * 256);
			}
		}*/

		for (int i = 0; i < headArr.length; i++) {
			sheet.setColumnWidth(i,20 * 256);
			sheet.autoSizeColumn((short)i);
		}
		return HeadRowCnt;
	}
	
	public static void createExcelHeadNormal(Sheet sheet, int startR, String[] headArr, String[] cellSizeArr,
			CellStyle headStyle) throws Exception {
		Row row = sheet.createRow(startR);
		row.setHeight((short)500);
		for (int i = 0, len = headArr.length; i < len; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headArr[i]);
			cell.setCellStyle(headStyle);
		}
		for (int i = 0, len = headArr.length; i < len; i++) {
			sheet.setColumnWidth(i,str2Int(cellSizeArr[i], 20) * 256);
		}
	}

	public static int str2Int(String s,int defaultVal){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return defaultVal;
		}
		
	}
	
	// 创建Excel样式
	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> stylesMap = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);
		stylesMap.put("cell_normal_style", style);

		Font sheetFont = wb.createFont();
		sheetFont.setFontName("宋体");
		sheetFont.setFontHeightInPoints((short) 12);
		sheetFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		style = wb.createCellStyle();
		style.setFont(sheetFont);
		style.setWrapText(true);
		style.setFillForegroundColor(IndexedColors.LIME.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		stylesMap.put("sheet_title_style", style);
		
		style = wb.createCellStyle();
		style.setFont(sheetFont);
		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		stylesMap.put("newsheet_title_style", style);
		
		return stylesMap;
	}
	
}
