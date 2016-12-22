package com.tasly.deepureflow.util.excel.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.dao.IAgentDao;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("agentReadExcel")
public class AgentReadExcel extends ReadExcel {
	@Autowired
	private IAgentDao agentDao;

	protected List<Agent> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<Agent> resultList=new LinkedList<Agent>();
		  //循环Excel行数,从第二行开始。标题不入库
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        Agent agent=new Agent();
	        //循环Excel的列
	        for(int c = 0; c <totalCells; c++)
	        {    
	            Cell cell = row.getCell(c); 
	            if (null != cell && cell.toString().length()>0){
	            	switch (c){ 
	            	case 0 :
	            		row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
	            		agent.setErpCode(cell.getStringCellValue());
	            		break; 
	            	case 1 : 
	            		row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
	            		agent.setAgentName(cell.getStringCellValue());
	            		break; 
	            	case 2 : 
	            		row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
	            		try {
							agent.setJoinDate(sdf.parse(cell.getStringCellValue()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
	            		break; 
	            	case 3: 
	            		row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
	            		agent.setAgentId(cell.getStringCellValue());
	            		break;  
	            	} 
	            }else{
	            	switch (c){ 
	            	case 0 :
	            		agent.setErpCode("");
	            		break; 
	            	case 1 : 
	            		agent.setAgentName("");
	            		break; 
	            	case 2 : 
	            		break; 
	            	case 3: 
	            		agent.setAgentId("");
	            		break;  
	            	} 
	            }
	        }   
	        resultList.add(agent);
	    }
		return resultList;
	}

}
