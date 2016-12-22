package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.user.UserStationKey;

public interface IStationDao {

	List<Station> findAllStation();

	Integer findOfficeIdById(String stationId);
	
	public boolean deleteAllStation();

	List<Station> findStationForPage(PageBounds pageBounds);
	
	public boolean insertStation(String stationCode,String stationName, String officeCode);
	public String selectCodeById(String code);

	List<Station> findStationByZoneCode(String zoneCode);

	List<Station> findStationByOfficeCode(String officeCode);

	Station findStationByUser(String id);

	UserStationKey validateStation(String userId,String stationId);

	Station findStationByCode(String stationCode);

	boolean updateStation(Station station);
	
	Office findOfficeByStationCode(String stationCode);
}
