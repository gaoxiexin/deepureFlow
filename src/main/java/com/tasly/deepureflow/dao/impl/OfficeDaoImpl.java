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
import com.tasly.deepureflow.client.IOfficeMapper;
import com.tasly.deepureflow.client.IZoneMapper;
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.OfficeExample;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZoneExample;
import com.tasly.deepureflow.enums.DeleteStatus;

@Repository("officeDao")
public class OfficeDaoImpl implements IOfficeDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(OfficeDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	@Resource
	private IZoneDao zoneDao;
	@Resource
	private IStationDao stationDao;
	
	@Override
	public List<Office> findOfficeForPage(PageBounds pageBounds) {
		 Map<String, Object> params =new HashMap<String, Object>();  
		 return sqlSession.selectList(IOfficeMapper.class.getName()+".officeListForPage", params, pageBounds);
	}

	@Override
	public boolean insertOffice(String OfficeId,String officeName, String zoneId) {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		
		Office office= new Office();
		office.setOfficeCode(OfficeId);
		office.setOfficeName(officeName);
		office.setZoneCode(zoneId);
		office.setIsDelete(DeleteStatus.NODELETE.getValue());
		
		int count=officeMapper.insertSelective(office);
		return count>0?true:false;
	}

	@Override
	public boolean deleteOfficeByArray(Integer[] officeList){
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		officeExample.or().andOfficeIdIn(Arrays.asList(officeList));
		Office office= new Office();
		office.setIsDelete(DeleteStatus.DELETE.getValue());
		
		int count=officeMapper.updateByExampleSelective(office, officeExample);
		return count>0?true:false;
	}

	@Override
	public boolean editOffice(Integer OfficeId, String officeName,
			String zoneId) {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		Office office= new Office();
		office.setOfficeId(OfficeId);
		office.setOfficeName(officeName);
		office.setZoneCode(zoneId);
		
		int count=officeMapper.updateByPrimaryKey(office);
		return count>0?true:false;
	}

	@Override
	public List<Office> findAllOffice() {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		officeExample.or().andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Office> officeList = officeMapper
				.selectByExample(officeExample);
		return officeList;
	}

	@Override
	public Integer findZoneIdById(String officeId) {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		if(officeMapper.selectByPrimaryKey(Integer.parseInt(officeId))==null){
			return 0;
		}
		String zoneCode = officeMapper.selectByPrimaryKey(Integer.parseInt(officeId)).getZoneCode();
		Zone zone =zoneDao.getZoneByCode(zoneCode);
		if(null!=zone&&null!=zone.getZoneId()){
			return zone.getZoneId();
		}
		return 0;
	}

	@Override
	public Office findOfficeByUser(String userId) {
		 Map<String, Object> params =new HashMap<String, Object>();  
		 params.put("userId", userId);
		 return sqlSession.selectOne(IOfficeMapper.class.getName()+".queryOfficeByUser", params);
	}
	@Override
	public boolean deleteAllOffice() {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		Office office = new Office();
		office.setIsDelete(1);
		if(CollectionUtils.isEmpty(this.findAllOffice())){
			return true;
		}
		int count=officeMapper.updateByExampleSelective(office, officeExample);
		return count>0?true:false;
	}

	@Override
	public List<Office> findOfficeByZoneCode(String zoneCode) {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		officeExample.createCriteria().andZoneCodeEqualTo(zoneCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Office> officeList = officeMapper
				.selectByExample(officeExample);
		return officeList;
	}

	@Override
	public Zone findZoneByOfficeCode(String officeCode) {
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		officeExample.or().andOfficeCodeEqualTo(officeCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Office> officeList=officeMapper.selectByExample(officeExample);
		
		if(CollectionUtils.isEmpty(officeList)){
			return null;
		}
		
		String zoneCode = officeList.get(0).getZoneCode();
		
		IZoneMapper zoneMapper = sqlSession.getMapper(IZoneMapper.class);
		ZoneExample zoneExample=new ZoneExample();
		zoneExample.createCriteria().andZoneCodeEqualTo(zoneCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Zone> zoneList = zoneMapper
				.selectByExample(zoneExample);
		
		if(CollectionUtils.isEmpty(zoneList)){
			return null;
		}
		
		return zoneList.get(0);
	}
}
