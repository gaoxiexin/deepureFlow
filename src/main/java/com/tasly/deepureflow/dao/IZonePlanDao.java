package com.tasly.deepureflow.dao;

import java.util.List;

import com.tasly.deepureflow.domain.system.ZonePlan;

/**
 * Created by GXX on 2015/9/8.
 */
public interface IZonePlanDao {
	
	public List<ZonePlan> findZonePlanById(String zoneId);

	public ZonePlan findZonePlanById(String zoneId, Integer zonePlanType);

	public boolean addZonePlan(ZonePlan zonePlan);

	public boolean updateZonePlan(ZonePlan zonePlan);

	public boolean delZonePlan(String zoneId, Integer zonePlanType);

}
