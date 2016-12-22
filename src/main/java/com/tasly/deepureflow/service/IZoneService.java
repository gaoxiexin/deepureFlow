package com.tasly.deepureflow.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZonePlan;
import com.tasly.deepureflow.util.DeepureResult;

public interface IZoneService {

	public PageList<Zone> findZoneForPage(int curPageSize, int limit,Integer zoneFunction,String zoneId);
	
	public List<Zone> findAllZone();

	public boolean addZone(String zoneId, String zoneName);

	public boolean delZoneByArray(String[] zoneList);

	public boolean updateActive(String zoneId);

	public boolean updateZoneStatus(Integer zoneId, Integer planType);
	
	public Zone findZoneById(Integer zoneId); 

	public List<ZonePlan> queryZonePlan(String zoneId);

	public DeepureResult addZonePlan(ZonePlan zonePlan);

	public ZonePlan queryZonePlan(String zoneId, Integer zonePlanType);

	public DeepureResult updateZonePlan(ZonePlan zonePlan);

	public boolean delZonePlanByArray(String zoneId, Integer[] zonePlanList);

	public boolean addZone(String zoneCode,String zoneName, Integer zonePlanStatus,
			Integer zoneFlowStatus);

	public boolean editZone(Integer zoneId, String zoneName,
			Integer zonePlanStatus, Integer zoneFlowStatus);

	public DeepureResult importExcel(String targetPath, MultipartFile upFile);

	public Zone findZoneByUser(String id);

	public List<Zone> findZoneByRole();
}
