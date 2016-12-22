package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.Zone;

public interface IOfficeDao {
	
	public List<Office> findOfficeForPage(PageBounds pageBounds);

	public boolean insertOffice(String OfficeId,String officeName, String zoneId);
	

	public boolean deleteOfficeByArray(Integer[] officeList);

	public boolean editOffice(Integer OfficeId, String officeName,
			String zoneId);

	public List<Office> findAllOffice();

	public Integer findZoneIdById(String officeId);
	
	public boolean deleteAllOffice();

	public Office findOfficeByUser(String userId);

	public List<Office> findOfficeByZoneCode(String zoneCode);

	public Zone findZoneByOfficeCode(String officeCode);
}