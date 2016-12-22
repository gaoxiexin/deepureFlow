package com.tasly.deepureflow.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.util.DeepureResult;

public interface IHierarchyService {

	List<Hierarchy> queryAllHierarchy();

	public PageList<Hierarchy> findHierarchyForPage(int curPageSize, int limit,String hierarchyName);

	public DeepureResult addHierarchy(String hierarchyName, String hierarchyNick);

	public DeepureResult delHierarchyByArray(Integer[] hierarchyList);

	public DeepureResult editHierarchy(Integer hierarchyId, String hierarchyName,
			String hierarchyNick);
	
	public PageList<Hierarchy> queryHierarchyByName(int curPageSize, int limit,String HierarchyName);

	Hierarchy findHierarchyByName(String hierarchyName);

	Hierarchy findHierarchyById(String hierarchyId);

	boolean validateName(String hierarchyName, String hierarchyId);

}
