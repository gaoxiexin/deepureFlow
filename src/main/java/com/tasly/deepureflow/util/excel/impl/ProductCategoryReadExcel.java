package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("productCategoryReadExcel")
public class ProductCategoryReadExcel extends ReadExcel {

	@Override
	protected List<ProductCategory> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		 List<ProductCategory> resultList=new LinkedList<ProductCategory>();
		  //循环Excel行数,从第二行开始。标题不入库
	    for(int r=1;r<totalRows;r++)
	    {
	        Row row = sheet.getRow(r);
	        if (row == null||isBlankRow(row)) continue;
	        
	        ProductCategory productCategory =new ProductCategory();
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
							String number = df.format(cell.getNumericCellValue());
		                	productCategory.setCode(number);
		                	}else if(cell.getCellType()==1){
		                		productCategory.setCode(cell.getStringCellValue());
		                	}
	                }
	                
	                else if(c==1){
	                	productCategory.setName(cell.getStringCellValue());
	                }
	                else if(c==2){
	                	productCategory.setDescription(cell.getStringCellValue());
	                }
	                else if(c==3){
	                	if(cell.getCellType()==0){
		                	DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
		                	productCategory.setParentId(Long.parseLong(number));
		                }else if(cell.getCellType()==1){
		                    productCategory.setParentId(Long.parseLong(cell.getStringCellValue()));
		                }
	                }
	            }
	           
	        }   
	        resultList.add(productCategory);
	    }
		return resultList;
	}

}
