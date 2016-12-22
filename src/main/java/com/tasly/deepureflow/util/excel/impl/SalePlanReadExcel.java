package com.tasly.deepureflow.util.excel.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.dao.ITerminalDao;
import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.util.DateUtil;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.excel.ReadExcel;

@Component("salePlanReadExcel")
public class SalePlanReadExcel extends ReadExcel {
	@Autowired
	private ITerminalDao terminalDao;
	@Override
	protected List<SalePlan> parseExcel(Sheet sheet, int totalRows, int totalCells) {
		List<SalePlan> resultList=new LinkedList<SalePlan>();
		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++)
		{
			Row row = sheet.getRow(r);
			if (row == null||isBlankRow(row)) continue;
			SalePlan salePlan=new SalePlan();
			PlanItem planItem = new PlanItem();
			List<PlanItem> planItems=new LinkedList<PlanItem>();
			//循环Excel的列
			for(int c = 0; c <totalCells; c++)
			{    
				Cell cell = row.getCell(c); 

				if (null != cell)  
				{
					//第一列
					if(c==0){
						//Date date = DateUtil.parseDate("yyyy-MM", );
						try {
							//Date date = parse(cell.getDateCellValue().toString(), "yyyy-MM", Locale.US);
							String planDate = new SimpleDateFormat("yyyy-MM").format(cell.getDateCellValue());
							//System.out.println(f);
							//System.out.println(date.toString());
							salePlan.setSalePlanDate(DateUtil.parseDate("yyyy-MM", planDate));
						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
					//获得第二列
					else if(c==1){
						String erpCode = "";
						String terminalId = "";
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							  
							erpCode = df.format(cell.getNumericCellValue());
							
						}else if(cell.getCellType()==1){
							erpCode = cell.getStringCellValue();
						}
						if(erpCode!=""&&terminalDao.findTerminalByCode(erpCode)!=null){
							terminalId = terminalDao.findTerminalByCode(erpCode).getTerminalId();
						}
						salePlan.setTerminalId(terminalId);
					}
					else if(c==2){
						if(cell.getCellType()==0){
							DecimalFormat df = new DecimalFormat("0");  
							  
							String number = df.format(cell.getNumericCellValue());
							planItem.setSkuId(number);
						}else if(cell.getCellType()==1){
							planItem.setSkuId(cell.getStringCellValue());
						}
						
					}
					else if(c==3){
						DecimalFormat df = new DecimalFormat("0");  
						String number = df.format(cell.getNumericCellValue());
						planItem.setBaseQuantity(Integer.valueOf(number));
					}
				}

			}
			planItems.add(planItem);
			salePlan.setPlanItemList(planItems);
			resultList.add(salePlan);
		}
		Collections.sort(resultList,new Comparator<SalePlan>(){  
			public int compare(SalePlan arg0, SalePlan arg1) {
				if(arg0.getSalePlanDate().before(arg1.getSalePlanDate())){
					return -1;
				}else{
					return 1;
				}
			}  
		}); 
		Collections.sort(resultList,new Comparator<SalePlan>(){  
			public int compare(SalePlan arg0, SalePlan arg1) {
				return arg0.getTerminalId().compareTo(arg1.getTerminalId());
					
			}  
		}); 
		return resultList;
	}
	public static Date parse(String str, String pattern, Locale locale) {
        if(str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static String format(Date date, String pattern, Locale locale) {
        if(date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern, locale).format(date);
    }
    
    public DeepureResult validUpload(List<SalePlan> planList) throws Exception{
        for(SalePlan salePlan : planList){
        	if(salePlan.getSalePlanDate().before(new Date())){
        		return DeepureResult.result(false, "销售计划时间早于当前时间，请修正！");
        	}
        	if(salePlan.getTerminalId()==""){
        		return DeepureResult.result(false, "终端编号有误，请修正！");
        	}
        }
        return DeepureResult.success();
}
}
