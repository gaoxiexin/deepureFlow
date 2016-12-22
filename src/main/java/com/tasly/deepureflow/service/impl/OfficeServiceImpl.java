package com.tasly.deepureflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.service.IOfficeService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.excel.impl.OfficeReadExcel;

@Service("officeService") 
public class OfficeServiceImpl implements IOfficeService {
	private final Logger logger = Logger.getLogger(OfficeServiceImpl.class.getName());
	
	@Autowired
    private IOfficeDao officeDao; 
	@Autowired
	private OfficeReadExcel officeReadExcel;
	@Override
	public PageList<Office> findOfficeForPage(int curPageSize, int limit) {
		PageList<Office> pageList=null;
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Office>)this.officeDao.findOfficeForPage(pageBounds);
		}
		return pageList;
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addOffice(String officeId,String officeName, String zoneId) {
		if(StringUtils.isNotEmpty(officeName)){
			return officeDao.insertOffice(officeId,officeName,zoneId);
		}
		return false;
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delOfficeByArray(Integer[] officeList) {
		boolean isDel=true;
		try{
			isDel=officeDao.deleteOfficeByArray(officeList);
		}catch(Exception e){
			logger.error(ResultEnum.INNER_ERROR.getMsg()+":删除办事处出错",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			isDel=false;
		}
		return isDel;
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean editOffice(Integer officeId, String officeName,
			String zoneId) {
		
		return officeDao.editOffice(officeId,officeName,zoneId);
	}
	@Override
	public List<Office> queryAllOffice() {
		return officeDao.findAllOffice();
	}
	
	@Override
	public Integer findZoneIdById(String officeId) {
		
		return officeDao.findZoneIdById(officeId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult importExcel(String targetPath, MultipartFile upFile) {
		boolean isImport=false;
		
		String sourceName = upFile.getOriginalFilename(); // 原始文件名
		
		File file = new File(targetPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			String path = targetPath + File.separator + sourceName;

			upFile.transferTo(new File(path));
			
			FileInputStream fin = new FileInputStream(new File(path));
			List<Office> officeList=(List<Office>) officeReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(officeList)){
				isImport=this.addOffice(officeList);
			}
		} catch (Exception e) {
			logger.error("导入办事处失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DeepureResult.result(false, "导入办事处失败");
		}
		return DeepureResult.result(true, "导入办事处成功");

	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addOffice(List<Office> officeList) {
		boolean isAdd=true;
		boolean isDelete=this.officeDao.deleteAllOffice();
		if(isDelete){
			for(Office office :officeList){
				boolean result=this.addOffice(office.getOfficeCode(), office.getOfficeName(),office.getZoneCode());
				if(!result){
					isAdd=false;
					break;
				}
			}
		}
		
		return isAdd&&isDelete;
		
		
	}
	
	

	

}
