package com.tasly.deepureflow.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.tasly.deepureflow.client.IPlaceMapper;
import com.tasly.deepureflow.dao.IPlaceDao;
import com.tasly.deepureflow.domain.system.Place;
import com.tasly.deepureflow.domain.system.PlaceExample;
import com.tasly.deepureflow.domain.system.PlaceExample.Criteria;
import com.tasly.deepureflow.domain.system.PlaceExtended;
@Repository("placeDao")
public class PlaceDaoImpl implements IPlaceDao {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(PlaceDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	

	@Override
	public List<Place> queryAreaByParentCode(String code) {
		IPlaceMapper mapper = sqlSession.getMapper(IPlaceMapper.class);
		PlaceExample placeExample = new PlaceExample();

		Criteria criteria = placeExample.createCriteria();
		placeExample.or(criteria.andParentareacodeEqualTo(code));

		return mapper.selectByExample(placeExample);
	}

	@Override
	public List<Place> queryAreaByAreaCode(String areaCode) {
		IPlaceMapper mapper = sqlSession.getMapper(IPlaceMapper.class);
		PlaceExample placeExample = new PlaceExample();

		Criteria criteria = placeExample.createCriteria();
		placeExample.or(criteria.andAreacodeEqualTo((areaCode)));

		return mapper.selectByExample(placeExample);
	}


	public List<PlaceExtended> selectByExtended() {
		IPlaceMapper mapper = sqlSession.getMapper(IPlaceMapper.class);
		return mapper.selectByExtended();
	}

}
