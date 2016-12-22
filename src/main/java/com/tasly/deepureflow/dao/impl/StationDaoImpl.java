package com.tasly.deepureflow.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IOfficeMapper;
import com.tasly.deepureflow.client.IStationMapper;
import com.tasly.deepureflow.client.IUserStationMapper;
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.OfficeExample;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.system.StationExample;
import com.tasly.deepureflow.domain.user.UserStationExample;
import com.tasly.deepureflow.domain.user.UserStationKey;
import com.tasly.deepureflow.enums.DeleteStatus;
@Repository("stationDao")
public class StationDaoImpl implements IStationDao {
	
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(StationDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	@Autowired
	private IOfficeDao officeDao;
	
	@Override
	public List<Station> findAllStation() {
		Map<String, Object> params =new HashMap<String, Object>();  
		return sqlSession.selectList(IStationMapper.class.getName()+".queryStationForTerminal", params);
	}

	@Override
	public Integer findOfficeIdById(String stationId) {
		IStationMapper stationMapper = sqlSession.getMapper(IStationMapper.class);
		if(stationMapper.selectByPrimaryKey(Integer.parseInt(stationId))==null){
			return 0;
		}
		String officeCode = stationMapper.selectByPrimaryKey(Integer.parseInt(stationId)).getOfficeCode();
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		officeExample.createCriteria().andOfficeCodeEqualTo(officeCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Office> officeList = officeMapper
				.selectByExample(officeExample);
		if(!officeList.isEmpty()){
			return officeList.get(0).getOfficeId();
		}
		return 0;
	}

	@Override
	public List<Station> findStationForPage(PageBounds pageBounds) {
		Map<String, Object> params =new HashMap<String, Object>();  
		return sqlSession.selectList(IStationMapper.class.getName()+".stationListForPage", params, pageBounds);
	}
	
	@Override
	public boolean deleteAllStation() {
		IStationMapper stationMapper = sqlSession.getMapper(IStationMapper.class);
		StationExample stationExample=new StationExample();
		Station station=new Station();
		station.setIsDelete(1);
		if(CollectionUtils.isEmpty(this.findAllStation())){
			return true;
		}
		int count=stationMapper.updateByExampleSelective(station, stationExample);
		return count>0?true:false;
	}

	@Override
	public boolean insertStation(String stationCode, String stationName,
			String officeCode) {
       IStationMapper stationMapper =sqlSession.getMapper(IStationMapper.class);
       Station station = new Station();
       station.setStationCode(stationCode);
       station.setStationName(stationName);
       station.setOfficeCode(officeCode);
      station.setIsDelete(DeleteStatus.NODELETE.getValue());
      int count=stationMapper.insert(station);
		return count>=0?true:false;
		
	}
	@Override
	public String selectCodeById(String code){
		
		IStationMapper stationMapper=sqlSession.getMapper(IStationMapper.class);
		return stationMapper.selectCode(code);
	}

	@Override
	public List<Station> findStationByZoneCode(String zoneCode) {
		List<Office> officeList = officeDao.findOfficeByZoneCode(zoneCode);
		List<String> officeCodes = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(officeList)){
			for(Office office : officeList){
				officeCodes.add(office.getOfficeCode());
			}
		}
		
		return this.findStationByOfficeCodes(officeCodes);
	}

	private List<Station> findStationByOfficeCodes(List<String> officeCodes) {
		Map<String, Object> params =new HashMap<String, Object>();  
		params.put("officeCodes", officeCodes);
		return sqlSession.selectList(IStationMapper.class.getName()+".queryStationForTerminal", params);
	}

	@Override
	public List<Station> findStationByOfficeCode(String officeCode) {
		Map<String, Object> params =new HashMap<String, Object>(); 
		List<String> officeCodes=new LinkedList<String>();
		officeCodes.add(officeCode);
		
		params.put("officeCodes", officeCodes);
		return sqlSession.selectList(IStationMapper.class.getName()+".queryStationForTerminal", params);
	}

	@Override
	public Station findStationByUser(String id) {
		Map<String, Object> params =new HashMap<String, Object>();  
		 params.put("userId", id);
		 return sqlSession.selectOne(IStationMapper.class.getName()+".queryStationByUser", params);
	}

	@Override
	public UserStationKey validateStation(String userId,String stationCode) {
		IUserStationMapper userStationMapper = sqlSession.getMapper(IUserStationMapper.class);
		UserStationExample example = new UserStationExample();
		if(userId!=""&&userId!=null){
			example.createCriteria().andStationCodeEqualTo(stationCode).andUserIdNotEqualTo(userId);
			List<UserStationKey> userStatinoList = userStationMapper.selectByExample(example);
			return CollectionUtils.isNotEmpty(userStatinoList)?userStatinoList.get(0):null;
		}else{
			example.createCriteria().andStationCodeEqualTo(stationCode);
			List<UserStationKey> userStatinoList = userStationMapper.selectByExample(example);
			return CollectionUtils.isNotEmpty(userStatinoList)?userStatinoList.get(0):null;
		}
	}

	@Override
	public Station findStationByCode(String stationCode) {
		IStationMapper stationMapper = sqlSession.getMapper(IStationMapper.class);
		StationExample stationExample=new StationExample();
		stationExample.or().andStationCodeEqualTo(stationCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Station> stationList = stationMapper.selectByExample(stationExample);
		return CollectionUtils.isNotEmpty(stationList)?stationList.get(0):null;
	}

	@Override
	public boolean updateStation(Station station) {
		IStationMapper stationMapper = sqlSession.getMapper(IStationMapper.class);
		StationExample stationExample=new StationExample();
		stationExample.or().andStationIdEqualTo(station.getStationId());
		int count = stationMapper.updateByExampleSelective(station, stationExample);
		return count>0?true:false;
	}

	@Override
	public Office findOfficeByStationCode(String stationCode) {
		IStationMapper stationMapper = sqlSession.getMapper(IStationMapper.class);
		StationExample stationExample=new StationExample();
		stationExample.or().andStationCodeEqualTo(stationCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Station> stationList=stationMapper.selectByExample(stationExample);
		
		if(CollectionUtils.isEmpty(stationList)){
			return null;
		}
		
		Station station=stationList.get(0);
		
		String officeCode = station.getOfficeCode();
		
		IOfficeMapper officeMapper = sqlSession.getMapper(IOfficeMapper.class);
		OfficeExample officeExample=new OfficeExample();
		officeExample.createCriteria().andOfficeCodeEqualTo(officeCode).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Office> officeList = officeMapper
				.selectByExample(officeExample);
		
		if(CollectionUtils.isEmpty(officeList)){
			return null;
		}
		
		return officeList.get(0);
	}

}
