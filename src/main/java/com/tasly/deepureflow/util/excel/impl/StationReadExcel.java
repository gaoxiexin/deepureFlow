package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("stationReadExcel")
public class StationReadExcel extends ReadExcel {

	@Override
	protected List<Station> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<Station> resultList=new LinkedList<Station>();
		  //循环Excel行数,从第二行开始。标题不入库
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        Station station= new Station();
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
							  
							station.setStationCode(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							station.setStationCode(cell.getStringCellValue());
						}
	                	
	                }
	               
	                else if(c==1){
	                	station.setStationName(cell.getStringCellValue());
	                }
	                else if(c==2){
	                	if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							  
							station.setOfficeCode(df.format(cell.getNumericCellValue()));
							
						}else if(cell.getCellType()==1){
							station.setOfficeCode(cell.getStringCellValue());
						}
	                	
	                }
	            }
	           
	        }   
	        resultList.add(station);
	        
	    }
		return resultList;
	}

}

