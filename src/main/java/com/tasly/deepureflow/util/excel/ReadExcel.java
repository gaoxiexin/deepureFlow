package com.tasly.deepureflow.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public abstract class ReadExcel {

	// 总行数
	private static int totalRows = 0;

	// 总条数
	private static int totalCells = 0;

	// 错误信息接收器
	@SuppressWarnings("unused")
	private String errorMsg;


	/**
	 * 描述：验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath) {
		if (filePath == null
				|| !(WDWUtil.isExcel2003(filePath) || WDWUtil
						.isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}

	/**
	 * 描述 :读EXCEL文件
	 * 
	 * @param fielName
	 * @return
	 */
	public List<?> getExcelInfo(String fileName, MultipartFile Mfile) {

		// 把spring文件上传的MultipartFile转换成File
		CommonsMultipartFile cf = (CommonsMultipartFile) Mfile;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File file = fi.getStoreLocation();

		List<?> resultList = ListUtils.EMPTY_LIST;
		InputStream is = null;
		try {
			// 验证文件名是否合格
			if (!validateExcel(fileName)) {
				return null;
			}
			// 判断文件时2003版本还是2007版本
//			boolean isExcel2003 = true;
//			if (WDWUtil.isExcel2007(fileName)) {
//				isExcel2003 = false;
//			}
			is = new FileInputStream(file);
			resultList = getExcelInfo(is, fileName);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	/**
	 * 此方法两个参数InputStream是字节流。isExcel2003是excel是2003还是2007版本
	 * 
	 * @param is
	 * @param isExcel2003
	 * @return
	 * @throws ParseException 
	 * @throws IOException
	 */
	public List<?> getExcelInfo(InputStream is, String filePath) throws ParseException {

		List<?> resultList = ListUtils.EMPTY_LIST;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(filePath)) {
				isExcel2003 = false;
			}
			// 当excel是2003时
			if (isExcel2003) {
				wb = new HSSFWorkbook(is);
			} else {
				wb = new XSSFWorkbook(is);
			}
			resultList = readExcelValue(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 读取Excel里面的信息
	 * 
	 * @param wb
	 * @return
	 * @throws ParseException 
	 */
	private List<?> readExcelValue(Workbook wb) throws ParseException {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		
		// 得到Excel的行数
		totalRows = sheet.getPhysicalNumberOfRows();

		// 得到Excel的列数(前提是有行数)
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		return parseExcel(sheet, totalRows, totalCells);
	}

	protected List<?> parseExcel(Sheet sheet, int totalRows2,
			int totalCells2) {
		return ListUtils.EMPTY_LIST;
	}
	
	public boolean isBlankRow(Row row){
        if(row == null) return true;
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
            Cell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                //case Cell.CELL_TYPE_BLANK:
                //    break;
                default:
                    break;
                }
                 
                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
         
        return result;
    }
	/*protected List<?> newParseExcel(Sheet sheet, int totalRows2,
			int totalCells2) throws ParseException {
		return ListUtils.EMPTY_LIST;
	}*/
}

class WDWUtil  
{  
   // @描述：是否是2003的excel，返回true是2003 
  public static boolean isExcel2003(String filePath)  {  
      return filePath.matches("^.+\\.(?i)(xls)$");  
  }  

   //@描述：是否是2007的excel，返回true是2007 
  public static boolean isExcel2007(String filePath)  {  
      return filePath.matches("^.+\\.(?i)(xlsx)$");  
  }  
}
