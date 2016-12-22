package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.system.Zone;

/**
 * Created by GXX on 2015/9/8.
 */
public interface IZoneDao {

	public List<Zone> findAllZone();

	public boolean insertZone(String zoneId,String zoneName);

	public boolean deleteZoneByArray(String[] zoneList);

	public boolean editZone(Zone zone);

	List<Zone> findZoneForPage(PageBounds pageBounds,Integer zoneFunction,String zoneId);
	
	public boolean updateZoneStatus(Integer zoneId, Integer planType);

	public Zone queryZoneById(Integer zoneId);

	public boolean insertZone(String zoneCode,String zoneName, Integer zonePlanStatus,
			Integer zoneFlowStatus);

	public boolean updateZone(Integer zoneId, String zoneName,
			Integer zonePlanStatus, Integer zoneFlowStatus);

	List<Zone> findZoneForJob();

	public Zone findZoneByUser(String userId);

	public boolean deleteAllZone();

	public Zone getZoneByCode(String zoneCode);

}
