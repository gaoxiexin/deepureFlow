package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("userReadExcel")
public class UserReadExcel extends ReadExcel {

	@Override
	protected List<User> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<User> resultList=new LinkedList<User>();
		  //循环Excel行数,从第二行开始。标题不入库
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        User user = new User();
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
							  
							user.setId(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							user.setId(cell.getStringCellValue());
						}
	                }
	               
	                else if(c==1){
	                	user.setUsername(cell.getStringCellValue());
	                }
	                else if(c==2){
	                	Station station= new Station();
	                	if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							  
							station.setStationCode(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							station.setStationCode(cell.getStringCellValue());
						}
	                	user.setStation(station);
	                	
	                }
	            }
	           
	        }   
	        resultList.add(user);
	        
	    }
		return resultList;
	}

}

