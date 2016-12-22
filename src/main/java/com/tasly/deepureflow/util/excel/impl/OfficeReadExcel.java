package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("officeReadExcel")
public class OfficeReadExcel extends ReadExcel {

	@Override
	protected List<Office> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<Office> resultList=new LinkedList<Office>();
		  //循环Excel行数,从第二行开始。标题不入库
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        Office office = new Office();
	        //循环Excel的列
	        for(int c = 0; c <totalCells; c++)
	        {    
	            Cell cell = row.getCell(c); 
	            if (null != cell)  
	            {
	                //第一列
	                if(c==0){
	                	if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							  
							office.setOfficeCode(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							office.setOfficeCode(cell.getStringCellValue());
						}
	                }
	                
	                else if(c==1){
	                	office.setOfficeName(cell.getStringCellValue());
	                }
	                else if(c==2){
	                	if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							  
							office.setZoneCode(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							office.setZoneCode(cell.getStringCellValue());
						}
	                }
	            }
	           
	        }   
	        resultList.add(office);
	    }
		return resultList;
	}

}


