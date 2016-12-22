package com.tasly.deepureflow.service;

import java.util.List;

import com.tasly.deepureflow.domain.system.Place;
import com.tasly.deepureflow.domain.system.PlaceExtended;

public interface IPlaceService {

	List<Place> searchAreaByParentCode(String code);

	List<Place> getByAreaCode(String code);
	
	Place searchAreaByAreaCode(String code) throws Exception;
	
	List<PlaceExtended> searchPlaceExtended();
}
