package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("terminalReadExcel")
public class TerminalReadExcel extends ReadExcel {
	@Autowired
	private IStationDao stationDao;
	@Override
	protected List<Terminal> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<Terminal> resultList=new LinkedList<Terminal>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  //循环Excel行数,从第二行开始。标题不入库
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        Terminal terminal = new Terminal();
	        //循环Excel的列
	        for(int c = 0; c <totalCells; c++)
	        {    
	            Cell cell = row.getCell(c);
	            if (null != cell && cell.toString().length()>0){
	            	switch (c){ 
	            	case 0 :
	            		cell.setCellType(Cell.CELL_TYPE_STRING);
	            		terminal.setErpCode(cell.getStringCellValue());
	            		break; 
	            	case 1 : 
	            		cell.setCellType(Cell.CELL_TYPE_STRING);
	            		terminal.setTerminalName(cell.getStringCellValue());
	            		break; 
	            	case 2 : 
	            		if(cell.getCellType()==1){
	                		try {
		                		terminal.setJoinDate(sdf.parse(cell.getStringCellValue()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
	                	}else{
	                		terminal.setJoinDate(cell.getDateCellValue());
	                	}
	            		break; 
	            	case 3: 
	            		if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							terminal.setStationCode(number);

						}else if(cell.getCellType()==1){
							terminal.setStationCode(cell.getStringCellValue());
						}
	            		break; 
	            	case 4:
	            		terminal.setTerminalId(cell.getStringCellValue());
	            		break;
	            	} 
	            }else{
	            	switch (c){ 
	            	case 0 :
	            		terminal.setErpCode("");
	            		break; 
	            	case 1 : 
	            		terminal.setTerminalName("");
	            		break; 
	            	case 2 : 
	            		break; 
	            	case 3: 
	            		terminal.setStationCode("");
	            		break; 
	            	case 4: 
	            		terminal.setTerminalId("");
	            		break;  
	            	} 
	            }
	            /*if (null != cell)  
	            {
	                //第一列
	                if(c==0){
	                	terminal.setErpCode(cell.getStringCellValue());
	                }
	                else if(c==1){
	                	terminal.setTerminalName(cell.getStringCellValue());
	                }
	                else if(c==2){
	                	if(cell.getCellType()==1){
	                		try {
		                		if(!"".equals(cell.getStringCellValue())){
		                			terminal.setJoinDate(sdf.parse(cell.getStringCellValue()));
		                		}
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
	                	}else{
	                		terminal.setJoinDate(cell.getDateCellValue());
	                	}
	                	
	                }
	                else if(c==3){
	                	if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							terminal.setStationCode(number);

						}else if(cell.getCellType()==1){
							terminal.setStationCode(cell.getStringCellValue());
						}
	                }
	                else if(c==4){
	                	cell.setCellType(Cell.CELL_TYPE_STRING);
	                	if(!"".equals(cell.getStringCellValue())){
                			terminal.setTerminalId(cell.getStringCellValue());
                		}else{
                			terminal.setTerminalId("");
                		}
	                	if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							terminal.setTerminalId(number);

						}else if(cell.getCellType()==1){
							terminal.setTerminalId(cell.getStringCellValue());
						}
	                }
	            }*/
	           
	        }   
	        resultList.add(terminal);
	    }
		return resultList;
	}

}
