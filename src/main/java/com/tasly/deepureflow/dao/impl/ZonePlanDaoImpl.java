package com.tasly.deepureflow.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.tasly.deepureflow.client.IZonePlanMapper;
import com.tasly.deepureflow.dao.IZonePlanDao;
import com.tasly.deepureflow.domain.system.ZonePlan;
import com.tasly.deepureflow.domain.system.ZonePlanExample;

@Repository("zonePlanDao")
public class ZonePlanDaoImpl implements IZonePlanDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ZonePlanDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public List<ZonePlan> findZonePlanById(String zoneId) {
		IZonePlanMapper zoneMapper = sqlSession.getMapper(IZonePlanMapper.class);
		ZonePlanExample zoneExample=new ZonePlanExample();
		zoneExample.or().andZoneIdEqualTo(zoneId);
		List<ZonePlan> zoneList=zoneMapper.selectByExample(zoneExample);
		return zoneList;
	}

	@Override
	public ZonePlan findZonePlanById(String zoneId, Integer zonePlanType) {
		IZonePlanMapper zoneMapper = sqlSession.getMapper(IZonePlanMapper.class);
		ZonePlanExample zoneExample=new ZonePlanExample();
		zoneExample.or().andZoneIdEqualTo(zoneId).andPlanTypeEqualTo(zonePlanType);
		List<ZonePlan> zonePlanList=zoneMapper.selectByExample(zoneExample);
		return CollectionUtils.isNotEmpty(zonePlanList)?zonePlanList.get(0):null;
	}

	@Override
	public boolean addZonePlan(ZonePlan zonePlan) {
		IZonePlanMapper zoneMapper = sqlSession.getMapper(IZonePlanMapper.class);
		int count=zoneMapper.insert(zonePlan);
		return count>0?true:false;
	}

	@Override
	public boolean updateZonePlan(ZonePlan zonePlan) {
		IZonePlanMapper zoneMapper = sqlSession.getMapper(IZonePlanMapper.class);
		ZonePlanExample zoneExample=new ZonePlanExample();
		zoneExample.or().andZoneIdEqualTo(zonePlan.getZoneId()).andPlanTypeEqualTo(zonePlan.getPlanType());
		int count=zoneMapper.updateByExample(zonePlan, zoneExample);
		return count>0?true:false;
	}

	@Override
	public boolean delZonePlan(String zoneId, Integer zonePlanType) {
		IZonePlanMapper zoneMapper = sqlSession.getMapper(IZonePlanMapper.class);
		ZonePlanExample zoneExample=new ZonePlanExample();
		zoneExample.or().andZoneIdEqualTo(zoneId).andPlanTypeEqualTo(zonePlanType);
		int count=zoneMapper.deleteByExample(zoneExample);
		return count>0?true:false;
	}

}
