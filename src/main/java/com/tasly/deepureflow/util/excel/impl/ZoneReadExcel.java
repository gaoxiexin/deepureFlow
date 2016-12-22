package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("zoneReadExcel")
public class ZoneReadExcel extends ReadExcel {

	@Override
	protected List<Zone> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<Zone> resultList=new LinkedList<Zone>();
		  //循环Excel行数,从第二行开始。标题不入库
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        Zone zone=new Zone();
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
							  
							zone.setZoneCode(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							zone.setZoneCode(cell.getStringCellValue());
						}
	                	
	                }
	                //获得第二列<手机号>，放到到用户登录bean中。作为登录账号及密码
	                else if(c==1){
	                	zone.setZoneName(cell.getStringCellValue());
	                }
	            }
	           
	        }   
	        resultList.add(zone);
	    }
		return resultList;
	}

}
