package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.system.Hierarchy;

public interface IHierarchyDao {

	List<Hierarchy> findAllHierarchy();

	public List<Hierarchy> findHierarchyForPage(String hierarchyName,PageBounds pageBounds);

	public boolean insertHierarchy(String hierarchyName, String hierarchyNick);

	public boolean deleteHierarchyByArray(Integer[] hierarchyList);

	public boolean editHierarchy(Integer hierarchyId, String hierarchyName,
			String hierarchyNick);
	
	List<Hierarchy> queryHierarchyByName(String hierarchyName,PageBounds pageBounds);

	Hierarchy findHierarchyByName(String hierarchyName);

	Hierarchy findHierarchyById(String hierarchyId);

	boolean validateName(String hierarchyName, String hierarchyId);

}
