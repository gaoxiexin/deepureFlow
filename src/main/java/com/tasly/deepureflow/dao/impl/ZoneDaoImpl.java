package com.tasly.deepureflow.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IZoneMapper;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZoneExample;
import com.tasly.deepureflow.enums.DeleteStatus;

@Repository("zoneDao")
public class ZoneDaoImpl implements IZoneDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ZoneDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public List<Zone> findAllZone() {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		ZoneExample zoneExample=new ZoneExample();
		zoneExample.or().andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Zone> zoneList=zoneMapper.selectByExample(zoneExample);
		return zoneList;
	}
	
	@Override
	public List<Zone> findZoneForJob(){
		 Map<String, Object> params =new HashMap<String, Object>();  
		 return sqlSession.selectList(IZoneMapper.class.getName()+".queryZoneByCondition", params);
	}

	@Override
	public List<Zone> findZoneForPage(PageBounds pageBounds,Integer zoneFunction,String zoneId) {
		 Map<String, Object> params =new HashMap<String, Object>();  
		 params.put("zoneFunction", zoneFunction);
		 params.put("zoneId", zoneId);
		 return sqlSession.selectList(IZoneMapper.class.getName()+".zoneListForPage", params, pageBounds);
	}

	@Override
	public boolean insertZone(String zoneId,String zoneName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteZoneByArray(String[] zoneList) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		ZoneExample zoneExample=new ZoneExample();
		zoneExample.or().andZoneCodeIn(Arrays.asList(zoneList));
		Zone zone=new Zone();
		zone.setIsDelete(1);
		
		int count=zoneMapper.updateByExampleSelective(zone, zoneExample);
		return count>0?true:false;
	}

	@Override
	public boolean editZone(Zone zone) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		int count=zoneMapper.updateByPrimaryKey(zone);
		return count==0?false:true;
	}

	@Override
	public boolean updateZoneStatus(Integer zoneId, Integer planType) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		Zone zone=zoneMapper.selectByPrimaryKey(zoneId);
		
		if(planType==0){
			zone.setZonePlanStatus(zone.getZonePlanStatus()==0?1:0);
		}else if(planType==1){
			zone.setZoneFlowStatus(zone.getZoneFlowStatus()==0?1:0);
		}
		
		int count=zoneMapper.updateByPrimaryKey(zone);
		return count>=0?true:false;
	}

	@Override
	public Zone queryZoneById(Integer zoneId) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		Zone zone=zoneMapper.selectByPrimaryKey(zoneId);
		return zone;
	}

	@Override
	public boolean insertZone(String zoneCode,String zoneName, Integer zonePlanStatus,
			Integer zoneFlowStatus) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		Zone zone=new Zone();
		zone.setZoneCode(zoneCode);
		zone.setZoneName(zoneName);
		zone.setZonePlanStatus(null==zonePlanStatus?1:zonePlanStatus);
		zone.setZoneFlowStatus(null==zoneFlowStatus?1:zoneFlowStatus);
		zone.setIsDelete(DeleteStatus.NODELETE.getValue());
		
		int count=zoneMapper.insert(zone);
		return count>=0?true:false;
	}

	@Override
	public boolean updateZone(Integer zoneId, String zoneName,
			Integer zonePlanStatus, Integer zoneFlowStatus) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		Zone zone=zoneMapper.selectByPrimaryKey(zoneId);
		
		zone.setZoneName(zoneName);
		zone.setZonePlanStatus(null==zonePlanStatus?1:zonePlanStatus);
		zone.setZoneFlowStatus(null==zoneFlowStatus?1:zoneFlowStatus);
		
		int count=zoneMapper.updateByPrimaryKey(zone);
		return count>=0?true:false;
	}

	@Override
	public Zone findZoneByUser(String userId) {
		 Map<String, Object> params =new HashMap<String, Object>();  
		 params.put("userId", userId);
		 return sqlSession.selectOne(IZoneMapper.class.getName()+".queryZoneByUser", params);
	}

	@Override
	public boolean deleteAllZone() {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		ZoneExample zoneExample=new ZoneExample();
		Zone zone=new Zone();
		zone.setIsDelete(1);
		if(CollectionUtils.isEmpty(this.findAllZone())){
			return true;
		}
		int count=zoneMapper.updateByExampleSelective(zone, zoneExample);
		return count>0?true:false;
	}

	@Override
	public Zone getZoneByCode(String zoneCode) {
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		ZoneExample zoneExample=new ZoneExample();
		zoneExample.createCriteria().andZoneCodeEqualTo(zoneCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Zone> zoneList=zoneMapper.selectByExample(zoneExample);
		if(CollectionUtils.isNotEmpty(zoneList)){
			return zoneList.get(0);
		}
		return null;
	}
}
