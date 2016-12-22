package com.tasly.deepureflow.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.dao.IHierarchyDao;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.service.IHierarchyService;
import com.tasly.deepureflow.util.DeepureResult;

@Service("hierarchyService") 
public class HierarchyServiceImpl implements IHierarchyService {
	private final Logger logger = Logger.getLogger(HierarchyServiceImpl.class.getName());
	
	@Autowired
    private IHierarchyDao hierarchyDao;
	
	@Override
	public List<Hierarchy> queryAllHierarchy() {
		return hierarchyDao.findAllHierarchy();
	}

	@Override
	public PageList<Hierarchy> findHierarchyForPage(int curPageSize, int limit,String hierarchyName) {
		PageList<Hierarchy> pageList=null;
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Hierarchy>)this.hierarchyDao.findHierarchyForPage(hierarchyName,pageBounds);
		}
		return pageList;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult addHierarchy(String hierarchyName, String hierarchyNick) {
		boolean isAdd = false;
		if(StringUtils.isNotEmpty(hierarchyName)){
			isAdd = hierarchyDao.insertHierarchy(hierarchyName, hierarchyNick);
		}
		return DeepureResult.addResult(isAdd);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult delHierarchyByArray(Integer[] hierarchyList) {
		boolean isDel=false;
		try{
			isDel=hierarchyDao.deleteHierarchyByArray(hierarchyList);
		}catch(Exception e){
			logger.error(ResultEnum.INNER_ERROR.getMsg()+":删除体系出错",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return DeepureResult.delResult(isDel);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult editHierarchy(Integer hierarchyId, String hierarchyName,
			String hierarchyNick) {
		boolean isEdit = false;
		if(StringUtils.isNotEmpty(hierarchyName)&&hierarchyId!=null){
			isEdit = hierarchyDao.editHierarchy(hierarchyId, hierarchyName, hierarchyNick);
		}
		return DeepureResult.editResult(isEdit);
	}
	@Override
	public PageList<Hierarchy> queryHierarchyByName(int curPageSize, int limit,String hierarchyName) {
		PageList<Hierarchy> pageList=null;
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Hierarchy>)this.hierarchyDao.queryHierarchyByName(hierarchyName,pageBounds);
		}
		return pageList;
	}

	@Override
	public Hierarchy findHierarchyByName(String hierarchyName) {
		
		return hierarchyDao.findHierarchyByName(hierarchyName);
	}

	@Override
	public Hierarchy findHierarchyById(String hierarchyId) {
		return hierarchyDao.findHierarchyById(hierarchyId);
	}

	@Override
	public boolean validateName(String hierarchyName, String hierarchyId) {
		return hierarchyDao.validateName(hierarchyName, hierarchyId);
	}

}
