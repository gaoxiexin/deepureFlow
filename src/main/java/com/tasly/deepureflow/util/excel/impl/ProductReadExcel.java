package com.tasly.deepureflow.util.excel.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.dao.IProductCategoryDao;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("productReadExcel")
public class ProductReadExcel extends ReadExcel {
	@Autowired
	private IProductCategoryDao productCategoryDao;
	@Override
	protected List<Product> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		List<Product> resultList=new LinkedList<Product>();
		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++)
		{
			Row row = sheet.getRow(r);
			if (row == null||isBlankRow(row)) continue;

			Product product = new Product();
			//循环Excel的列
			for(int c = 0; c <totalCells; c++)
			{    
				Cell cell = row.getCell(c); 
				if (null != cell){
					//第一列
					if(c==0){
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							product.setCode(number);
						}else if(cell.getCellType()==1){
							product.setCode(cell.getStringCellValue());
						}
					}
					else if(c==1){
						product.setName(cell.getStringCellValue());
					}
					else if(c==2){
						product.setErpUnit(cell.getStringCellValue());
					}
					else if(c==3){
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							product.setErpUnitCode(number);
						}else if(cell.getCellType()==1){
							product.setErpUnitCode(cell.getStringCellValue());
						}
					}
					else if(c==4){
						product.setErpMinUnit(cell.getStringCellValue());
					}
					else if(c==5){
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							product.setErpMinUnitCode(number);
						}else if(cell.getCellType()==1){
							product.setErpMinUnitCode(cell.getStringCellValue());
						}
					}
					else if(c==6){
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							product.setCategoryCode(productCategoryDao.selectDaoParentId(number));

						}else if(cell.getCellType()==1){
							product.setCategoryCode(productCategoryDao.selectDaoParentId(cell.getStringCellValue()));
						}

					}
					else if(c==7){
						
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							String number = df.format(cell.getNumericCellValue());
							product.setUnitQuantity(Integer.valueOf(number));
						}else if(cell.getCellType()==1){
							product.setUnitQuantity(Integer.valueOf(cell.getStringCellValue()));
						}

					}
					else if(c==8){
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0.00");  
							String number = df.format(cell.getNumericCellValue());

							product.setUnitPrice(new BigDecimal(number));
						}else if(cell.getCellType()==1){
							product.setUnitPrice(new BigDecimal(cell.getStringCellValue()));
						}
					}

				}

			}  
			resultList.add(product);

		}
		return resultList;
	}
}

