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
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.service.IStationService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.excel.impl.StationReadExcel;

@Service("stationService") 
public class StationServiceImpl implements IStationService {
	private final Logger logger = Logger.getLogger(StationServiceImpl.class
			.getName());
	@Autowired
	private IStationDao stationDao;
	@Autowired
	private ISecurityDao securityDao;
	@Autowired
	private StationReadExcel stationReadExcel;
	@Autowired
	private IZoneDao zoneDao;
	@Autowired
	private IOfficeDao officeDao;
	
	

	@Override
	public List<Station> queryAllStation() {
		return stationDao.findAllStation();
	}

	@Override
	public Integer findOfficeIdById(Integer stationId) {
		Integer officeId= stationDao.findOfficeIdById(String.valueOf(stationId));
		if(officeId!=0){
			return officeId;
		}
		return 0;
	}

	@Override
	public PageList<Station> findStationForPage(int curPageSize, int limit) {
		PageList<Station> pageList=null;
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Station>)this.stationDao.findStationForPage(pageBounds);
		}
		return pageList;
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
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
			List<Station> stationList=(List<Station>) stationReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(stationList)){
				isImport=this.addStation(stationList);
			}
		} catch (Exception e) {
			logger.error("导入岗位失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DeepureResult.result(false, "导入岗位失败");
		}
		return DeepureResult.result(true, "导入岗位成功");

	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addStation(List<Station> stationList) {
		boolean isAdd=true;
		boolean isDelete=this.stationDao.deleteAllStation();
		if(isDelete){
			for(Station station :stationList){
				boolean result=this.addStation(station.getStationCode(), station.getStationName(),station.getOfficeCode());
				if(!result){
					isAdd=false;
					break;
				}
			}
		}
		return isAdd&&isDelete;
	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addStation(String stationCode, String stationName,
			String officeCode) {
		if (StringUtils.isNotEmpty(stationName)) {
			return stationDao.insertStation(stationCode,stationName, officeCode);
		}
		return false;
	}

	@Override
	public List<Station> queryStationByRole() {
		// 判断当前登录用户的角色
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		Zone zone = zoneDao.findZoneByUser(currentUser.getId());
		Office office = officeDao.findOfficeByUser(currentUser.getId());
		Station station=stationDao.findStationByUser(currentUser.getId());
		if(null!=station){
			currentUser.setStation(station);
			currentUser.getStation().setUser(currentUser);
		}
		List<UserRoleRelationship> userRole = securityDao.findUserRoleByUserId(currentUser.getId());
		List<Station> stationList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userRole)){
			Integer roleId = (int) userRole.get(0).getRoleId();
			switch (roleId) {
			case 1:
				stationList = stationDao.findAllStation();
				break;
			case 2:
				stationList = stationDao.findStationByZoneCode(zone.getZoneCode());
				break;
			case 3:
				stationList = stationDao.findStationByOfficeCode(office.getOfficeCode());
				break;
			case 4:
				stationList.add(currentUser.getStation());
				break;
			default:
				break;
			}
		}
		return stationList;
	}

	@Override
	public DeepureResult validateStation(String userId,String stationId) {
		if(stationDao.validateStation(userId,stationId)==null){
			return DeepureResult.success();
		}else{
			return DeepureResult.result(false, "该岗位已存在员工");
		}
	}

}
