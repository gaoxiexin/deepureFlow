package com.tasly.deepureflow.dao;

import java.util.List;

import com.tasly.deepureflow.domain.system.Place;
import com.tasly.deepureflow.domain.system.PlaceExtended;

/**
 * Created by GXX on 2015/9/8.
 */
public interface IPlaceDao {
	List<Place> queryAreaByParentCode(String code);

	List<Place> queryAreaByAreaCode(String areaCode);
	List<PlaceExtended> selectByExtended();
	
	
	
}
