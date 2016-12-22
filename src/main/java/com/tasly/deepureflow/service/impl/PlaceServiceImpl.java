package com.tasly.deepureflow.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tasly.deepureflow.dao.IPlaceDao;
import com.tasly.deepureflow.domain.system.Place;
import com.tasly.deepureflow.domain.system.PlaceExtended;
import com.tasly.deepureflow.service.IPlaceService;

@Service("placeService") 
public class PlaceServiceImpl implements IPlaceService {

	@Autowired
	@Qualifier("placeDao")
	private IPlaceDao placeDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchAreaByParentCode(String code) {
		List<Place> placeList = placeDao.queryAreaByParentCode(code);
		if (CollectionUtils.isNotEmpty(placeList)) {
			return placeList;
		}
		return ListUtils.EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Place> getByAreaCode(String areaCode) {
		List<Place> placeList = placeDao.queryAreaByAreaCode(areaCode);

		if (CollectionUtils.isNotEmpty(placeList)) {
			return placeList;
		}
		return ListUtils.EMPTY_LIST;
	}

	@Override
	public Place searchAreaByAreaCode(String code) throws Exception{
		List<Place> placeList = placeDao.queryAreaByAreaCode(code);

		if(placeList.size() ==1){
			return placeList.get(0);
		}else {
			throw new Exception("");
		}
	}

	public List<PlaceExtended> searchPlaceExtended() {
		return placeDao.selectByExtended();
	}

}
