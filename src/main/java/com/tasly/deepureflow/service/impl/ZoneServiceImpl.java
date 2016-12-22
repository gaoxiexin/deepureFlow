package com.tasly.deepureflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.dao.IZonePlanDao;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZonePlan;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.service.IZoneService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.excel.impl.ZoneReadExcel;

@Service("zoneService")
public class ZoneServiceImpl implements IZoneService {
	private final Logger logger = Logger.getLogger(ZoneServiceImpl.class
			.getName());

	@Autowired
	private IZoneDao zoneDao;

	@Autowired
	private IZonePlanDao zonePlanDao;
	
	@Autowired
	private ZoneReadExcel zoneReadExcel;
	
	@Autowired
	private ISecurityDao securityDao;

	@Override
	public PageList<Zone> findZoneForPage(int curPageSize, int limit,
			Integer zoneFunction, String zoneId) {
		PageList<Zone> pageList = null;
		if (curPageSize != 0 && limit != 0) {
			PageBounds pageBounds = new PageBounds(curPageSize, limit);
			pageList = (PageList<Zone>) this.zoneDao.findZoneForPage(
					pageBounds, zoneFunction, zoneId);
		}
		return pageList;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addZone(String zoneId, String zoneName) {
		if (StringUtils.isNotEmpty(zoneId) && StringUtils.isNotEmpty(zoneName)) {
			return zoneDao.insertZone(zoneId, zoneName);
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delZoneByArray(String[] zoneList) {
		boolean isDel = true;
		try {
			isDel = zoneDao.deleteZoneByArray(zoneList);
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":删除大区出错", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			isDel = false;
		}
		return isDel;
	}

	@Override
	public boolean updateActive(String zoneId) {
		// boolean isUpdate = this.zoneDao.updateZoneActive(zoneId);
		return false;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateZoneStatus(Integer zoneId, Integer planType) {
		return this.zoneDao.updateZoneStatus(zoneId, planType);
	}

	@Override
	public List<ZonePlan> queryZonePlan(String zoneId) {
		return zonePlanDao.findZonePlanById(zoneId);
	}

	@Override
	public Zone findZoneById(Integer zoneId) {
		if (null != zoneId) {
			return this.zoneDao.queryZoneById(zoneId);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult addZonePlan(ZonePlan zonePlan) {
		boolean zonePlanNotExist = this.zonePlanDao.findZonePlanById(
				zonePlan.getZoneId(), zonePlan.getPlanType()) == null ? true
				: false;
		if (zonePlanNotExist) {
			return DeepureResult.addResult(this.zonePlanDao.addZonePlan(zonePlan));
		}
		return DeepureResult.result(false, "大区计划已存在");
	}

	@Override
	public ZonePlan queryZonePlan(String zoneId, Integer zonePlanType) {
		if (null != zoneId && null != zonePlanType) {
			return this.zonePlanDao.findZonePlanById(zoneId, zonePlanType);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult updateZonePlan(ZonePlan zonePlan) {
		return DeepureResult.editResult(zonePlanDao.updateZonePlan(zonePlan));
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delZonePlanByArray(String zoneId, Integer[] zonePlanList) {
		if (zonePlanList.length > 0) {
			for (Integer zonePlanType : zonePlanList) {
				boolean result = this.zonePlanDao.delZonePlan(zoneId,
						zonePlanType);
				if (!result) {
					return result;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addZone(String zoneCode,String zoneName, Integer zonePlanStatus,
			Integer zoneFlowStatus) {
		if (StringUtils.isNotEmpty(zoneName)) {
			return zoneDao.insertZone(zoneCode,zoneName, zonePlanStatus, zoneFlowStatus);
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean editZone(Integer zoneId, String zoneName,
			Integer zonePlanStatus, Integer zoneFlowStatus) {
		if (null != zoneId && StringUtils.isNotEmpty(zoneName)) {
			return zoneDao.updateZone(zoneId, zoneName, zonePlanStatus,
					zoneFlowStatus);
		}
		return false;
	}

	@Override
	public List<Zone> findAllZone() {
		return zoneDao.findAllZone();
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
			File file2=new File(path);
			if(file2.exists()){
				file2.delete();
			}
			upFile.transferTo(file2);

			FileInputStream fin = new FileInputStream(file2);
			List<Zone> zoneList=(List<Zone>) zoneReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(zoneList)){
				isImport=this.addZone(zoneList);
			}
		} catch (Exception e) {
			logger.error("导入大区失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DeepureResult.result(false, "导入大区失败");
		}
		return DeepureResult.result(true, "导入大区成功");
	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addZone(List<Zone> zoneList) {
		boolean isAdd=true;
		boolean isDelete=this.zoneDao.deleteAllZone();
		if(isDelete){
			for(Zone zone :zoneList){
				boolean result=this.addZone(zone.getZoneCode(), zone.getZoneName(),null,null);
				if(!result){
					isAdd=false;
					break;
				}
			}
		}
		
		return isAdd&&isDelete;
	}

	@Override
	public Zone findZoneByUser(String id) {
		return zoneDao.findZoneByUser(id);
	}

	@Override
	public List<Zone> findZoneByRole() {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		List<UserRoleRelationship> userRole = securityDao.findUserRoleByUserId(currentUser.getId());
		List<Zone> zoneList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userRole)){
			Integer roleId = (int) userRole.get(0).getRoleId();
			if(roleId==1){
				zoneList=zoneDao.findAllZone();
			}else{
				zoneList.add(zoneDao.findZoneByUser(currentUser.getId())); 
			}
			
		}
		return zoneList;
	}
}
