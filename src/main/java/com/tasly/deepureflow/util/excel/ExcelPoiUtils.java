package com.tasly.deepureflow.util.excel;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导出Excel工具
 */
public class ExcelPoiUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ExcelPoiUtils.class);
	
	private static final SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	//test
	public static void main(String[] args) throws Exception {
		List<String> theader = new ArrayList<String>();
		theader.add("学号");
		theader.add("姓名");
		theader.add("年龄");
		theader.add("生日");
//		List<Object> list = new ArrayList<Object>();  
//        
//        list.add(theader);  
//        list.add(theader);  
//        list.add(theader); 
		
		List<List<Object>> tValue = new ArrayList<List<Object>>();
		for(int i = 0; i < 4; i++) {
			List<Object> temp = new ArrayList<Object>();
			temp.add(i+1);
			temp.add(i+1);
			temp.add(i+1);
			temp.add(i+1);
			tValue.add(temp);
		}
		exportExcel("学生表一", theader, tValue, null, null, "C:/students1.xls");
	}
	
	/**
     * @功能描述 设置excel文档(单表单)
     * @param tName
     *            excel表名集
     * @param tHeader
     *            excel表头数据集
     * @param tValue(List里的数据为实例对象或者list对象)
     *            excel表单数据集(除表头)
     * @param tHeaderStyle
     *            excel表头单元格样式
     * @param tValueStyle
     *            excel表单数据单元格样式(除表头)
     * @param filePath
     *            excel文件地址
     * @throws Exception
     *             异常往上抛出
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HSSFWorkbook exportExcel(String tName, List<String> tHeader, List tValue,
            Map<String, Short> tHeaderStyle, Map<String, Short> tValueStyle, String filePath) throws Exception {
		HSSFWorkbook workbook = getExportExcel();
		HSSFSheet sheet = workbook.createSheet(tName);
		createHead(workbook, sheet, tHeader, tHeaderStyle);//创建表头
		if(CollectionUtils.isNotEmpty(tValue)){
			createBody(workbook, sheet, tValue, tValueStyle);
		}
		
		if (null != filePath && !"".equals(filePath)) {//有路径就把Excel写到服务器
			createExcel(workbook, filePath);
//			System.out.println("Excel导出成功");
		}
		return workbook;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HSSFWorkbook exportReport(String queryTime, String tName, List<String> tHeader, List tValue,
            Map<String, Short> tHeaderStyle, Map<String, Short> tValueStyle, String filePath) throws Exception {
		HSSFWorkbook workbook = getExportExcel();
		HSSFSheet sheet = workbook.createSheet(tName);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		
		if(tValueStyle == null)
			tValueStyle = new HashMap<String, Short>();
		
		tValueStyle.put("boldweight", Font.BOLDWEIGHT_BOLD);
		tValueStyle.put("fontHeightInPoints", (short)20);
		
//		setCellStyle(workbook, cell, tValueStyle);// 设置样式
		cell.setCellStyle(setCellStyle(workbook, tValueStyle));// 设置样式
		setCellValue(cell,tName);
		
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell1 = row1.createCell(0);
		
		tValueStyle.put("boldweight", Font.BOLDWEIGHT_BOLD);
		tValueStyle.put("fontHeightInPoints", (short)12);
		
//		setCellStyle(workbook, cell1, tValueStyle);// 设置样式
		cell1.setCellStyle(setCellStyle(workbook, tValueStyle));// 设置样式
		if(queryTime != null)
			setCellValue(cell1, "查询时间： " + queryTime);
		else
		    setCellValue(cell1, "查询时间： " + myFmt.format(new Date()));
		
		createReportHead(workbook, sheet, tHeader, tHeaderStyle);//创建表头
		createReportBody(workbook, sheet, tValue, tValueStyle);
		if (null != filePath && !"".equals(filePath)) {//有路径就把Excel写到服务器
			createExcel(workbook, filePath);
//			System.out.println("Excel导出成功");
		}
		return workbook;
	}
	
	/**
	 * @功能描述 设置excel文档(单表单)
	 * @param tName
	 *            excel表名集
	 * @param tHeader
	 *            excel表头数据集
	 * @param tValue(List里的数据为实例对象或者list对象)
	 *            excel表单数据集(除表头)
	 * @param tHeaderStyle
	 *            excel表头单元格样式
	 * @param tValueStyle
	 *            excel表单数据单元格样式(除表头)
	 * @param filePath
	 *            excel文件地址
	 * @throws Exception
	 *             异常往上抛出
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HSSFWorkbook exportExcel(String tName1, List<String> tHeader1, List tValue1,String tName2, List<String> tHeader2, List tValue2,
			Map<String, Short> tHeaderStyle, Map<String, Short> tValueStyle, String filePath) throws Exception {
		HSSFWorkbook workbook = getExportExcel();
		HSSFSheet sheet1 = workbook.createSheet(tName1);
		HSSFSheet sheet2 = workbook.createSheet(tName2);
		createHead(workbook, sheet1, tHeader1, tHeaderStyle);//创建表头
		createBody(workbook, sheet1, tValue1, tValueStyle);
		createHead(workbook, sheet2, tHeader2, tHeaderStyle);//创建表头
		createBody(workbook, sheet2, tValue2, tValueStyle);
		if (null != filePath && !"".equals(filePath)) {//有路径就把Excel写到服务器
			createExcel(workbook, filePath);
//			System.out.println("Excel导出成功");
		}
		return workbook;
	}
	
	/**
	 * 把Excel写到服务端
	 * @param wb
     *            对应一个Excel文件 
     * @param filePath
     *             全路径包括文件名
	 */
	public static void createExcel(HSSFWorkbook wb, String filePath) {
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			logger.error("创建Excel失败",e);
		} 
	}
	
	/**
	 * 创建内部数据，并设置样式
	 * @param workbook
     *            对应一个Excel文件 
     * @param sheet
     *            sheet
     * @param tValue
     *            行数据
     * @param tValueStyle
     *            excel样式
     * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void createBody(HSSFWorkbook workbook, HSSFSheet sheet,
			List<Object> tValue, Map<String, Short> tValueStyle) throws Exception {
		if (null == tValue || 0 == tValue.size()) {
			tValue = new ArrayList<Object>();
			List temp = new ArrayList<String>();
			temp.add("无数据");
			tValue.add(temp);
		}
		HSSFCellStyle cellstyle = setCellStyle(workbook, tValueStyle);
		for (int i = 0; i < tValue.size(); i++) {
			if (tValue.get(i) instanceof List) setOneRow(workbook, sheet.createRow(i+1), (List)tValue.get(i), cellstyle);
			else setOneRow(workbook, sheet.createRow(i+1), tValue.get(i), cellstyle);//按照对象来处理
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void createReportBody(HSSFWorkbook workbook, HSSFSheet sheet,
			List<Object> tValue, Map<String, Short> tValueStyle) throws Exception {
		
		tValueStyle.put("boldweight", null);
		tValueStyle.put("fontHeightInPoints", (short)10);
//		tValueStyle.put("align", HSSFCellStyle.ALIGN_RIGHT);
		
		HSSFCellStyle cellstyle = setCellStyle(workbook, tValueStyle);
		if (null == tValue || 0 == tValue.size()) {
			tValue = new ArrayList<Object>();
			List temp = new ArrayList<String>();
			temp.add("无数据");
			tValue.add(temp);
			
			setOneRow(workbook, sheet.createRow(6), (List)tValue.get(0), cellstyle);
			return;
		}
		for(int j = 0; j < tValue.size(); j++)
	      if (tValue.get(j) instanceof List) setOneRow(workbook, sheet.createRow(j+6), (List)tValue.get(j), cellstyle);
		  else setOneRow(workbook, sheet.createRow(j+6), tValue.get(j), cellstyle);//按照对象来处理
	}
	
	/**
	 * 创建表头，并设置表头样式
	 * @param workbook
     *            对应一个Excel文件 
     * @param sheet
     *            sheet
     * @param tHeader
     *            行数据
     * @param headerStyle
     *            excel样式
     * @throws Exception
	 */
	public static void createHead(HSSFWorkbook workbook, HSSFSheet sheet, List<String> tHeader, Map<String, Short> headerStyle) throws Exception {
		sheet.autoSizeColumn(0); // 设置单元格自适应
		if (null == headerStyle) {//表头没有样式设置默认样式
			headerStyle =  new HashMap<String, Short>();
			headerStyle.put("boldweight", Font.BOLDWEIGHT_BOLD);
//			headerStyle.put("fontHeightInPoints", (short)24);
		}
		HSSFCellStyle cellstyle = setCellStyle(workbook, headerStyle);
		setOneRow(workbook, sheet.createRow(0), tHeader, cellstyle);
	}
	
	public static void createReportHead(HSSFWorkbook workbook, HSSFSheet sheet, List<String> tHeader, Map<String, Short> headerStyle) throws Exception {
		sheet.autoSizeColumn(0); // 设置单元格自适应
		if (null == headerStyle) {//表头没有样式设置默认样式
			headerStyle =  new HashMap<String, Short>();
			headerStyle.put("boldweight", Font.BOLDWEIGHT_BOLD);
			headerStyle.put("align", HSSFCellStyle.ALIGN_RIGHT);
//			headerStyle.put("fontHeightInPoints", (short)24);
		}
		HSSFCellStyle cellstyle = setCellStyle(workbook, headerStyle);
		setOneRow(workbook, sheet.createRow(5), tHeader, cellstyle);
	}
	
	 /**
     * @功能描述 设置excel表单行数据以及统一的样式
     * @param workbook
     *            对应一个Excel文件 
     * @param row
     *            excel表行
     * @param data
     *            行数据
     * @param style
     *            excel样式
     * @throws Exception
     *             异常往外抛出
     */
	public static void setOneRow(HSSFWorkbook workbook, HSSFRow row, @SuppressWarnings("rawtypes") List data, HSSFCellStyle cellstyle) throws Exception {
		for (int i = 0; i < data.size(); i++) {
			HSSFCell cell = row.createCell(i);
//			setCellStyle(workbook, cell, style);// 设置样式
			if(cellstyle != null)
			    cell.setCellStyle(cellstyle);
			setCellValue(cell,data.get(i));
		}
	}
	
	 /**
     * @功能描述 设置excel表单行数据以及统一的样式
     * @param workbook
     *            对应一个Excel文件 
     * @param row
     *            excel表行
     * @param data
     *            行数据
     * @param style
     *            excel样式
     * @throws Exception
     *             异常往外抛出
     */
	@SuppressWarnings("rawtypes")
	public static void setOneRow(HSSFWorkbook workbook, HSSFRow row, Object data, HSSFCellStyle cellstyle) throws Exception {
		Class userCla = (Class) data.getClass();
		Field[] fs = userCla.getDeclaredFields();  
		int i = 0;
		for(Field f : fs){
			f.setAccessible(true); //设置些属性是可以访问的
			Object object = f.get(data);//得到此属性的值
			if ("serialVersionUID".equals(f.getName())) continue;//特殊处理,为了去掉有些实体有序列号的属性(属性名必须为serialVersionUID，写得不通用)
			HSSFCell cell = row.createCell(i++);
//            setCellStyle(workbook, cell, style);// 设置样式
			if(cellstyle != null)
			    cell.setCellStyle(cellstyle);
            setCellValue(cell,object);
		}
	}
	
	/**
	 * @功能描述 设置单元格的值
	 * @param cell
     *            单元格对象
     * @param obj
     *            单元格值的类型包括:Integer、String、Boolean、Date、Calendar、Double、BigDecimal
	 */
	public static void setCellValue(HSSFCell cell, Object obj) {
		if (obj instanceof Integer) // 当数字时
            cell.setCellValue((Integer) obj);
        if (obj instanceof String) // 当为字符串时
            cell.setCellValue((String) obj);
        if (obj instanceof Boolean) // 当为布尔时
            cell.setCellValue((Boolean) obj);
        if (obj instanceof Date) // 当为时间时
            cell.setCellValue((Date) obj);
        if (obj instanceof Calendar) // 当为时间时
            cell.setCellValue((Calendar) obj);
        if (obj instanceof Double) // 当为小数时
            cell.setCellValue((Double) obj);
        if (obj instanceof BigDecimal) // 当数字时
            cell.setCellValue(((BigDecimal) obj).doubleValue());
	}
	
	/**
	 * 创建一个webbook，对应一个Excel文件  
	 */
	public static HSSFWorkbook getExportExcel() {
		return new HSSFWorkbook();
	}
	
	
    /**
     * @功能描述 设置单元格样式
     * @param workbook
     *            对应一个Excel文件 
     * @param cell
     *            单元格对象
     * @param style
     *            样式Map集合
     * @return 设置后单元格样式
     * @throws Exception
     *             异常往外抛出
     */
    private static HSSFCellStyle setCellStyle(HSSFWorkbook workbook, Map<String, Short> style) throws Exception {
    	if (null == style) return null;
        // 声明单元格样式
        HSSFCellStyle cellStyle = null;
        try {
    		// 创建字体
    		HSSFFont font = workbook.createFont();
    		// 设置字体样式
    		// 设置字体颜色(红色为:HSSFFont.COLOR_RED 这里表示short类型 10)
    		if (null != style.get("fontColor")) font.setColor(style.get("fontColor"));
    		// 设置字体形体(宽体为:HSSFFont.BOLDWEIGHT_BOLD 700) -- 粗体
    		if (null != style.get("fontWeight")) font.setBoldweight(style.get("fontWeight"));
    		// 设置粗体 ： Font.BOLDWEIGHT_BOLD
    		if (null != style.get("boldweight")) font.setBoldweight(style.get("boldweight"));
    		// 字体大小
    		if (null != style.get("fontHeightInPoints")) font.setFontHeightInPoints(style.get("fontHeightInPoints"));
    		
    		// 创建单元格样式
    		cellStyle = workbook.createCellStyle();
    		// 添加字体样式
    		cellStyle.setFont(font);
            
            // 创建单元格居中或其他格式  (居中为:HSSFCellStyle.ALIGN_CENTER)
            if(null != style.get("align"))cellStyle.setAlignment(style.get("align"));
        } catch (Exception e) {
            logger.error("设置单元格样式失败",e);
        }
        
//        cell.setCellStyle(cellStyle);
        return cellStyle;
    }
}
