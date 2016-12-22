package com.tasly.deepureflow.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.tasly.deepureflow.domain.security.DataResource;
import com.tasly.deepureflow.domain.security.Menu;
import com.tasly.deepureflow.domain.security.Resource;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.SecurityInfo;
import com.tasly.deepureflow.dto.SearchForm;

public interface ISecurityService {
	
	List<Role> getAllRoles();
	
	List<Role> getAllRoles(String queryRoleName);
	
	List<Long> getMenuIdsByRoleId(long roleId);
	
	List<Long> getResoucesIdsByRoleId(long roleId);
	
	List<Long> getDataResoucesIdsByRoleId(long roleId);
	
	List<String> getUserIdsByRoleCode(String roleCode);
	
	void allocateResources(long roleId, List<Long> resourceIds);
	
	void allocateDataResources(long roleId, List<Long> dataResourceIds);
	
	void allocateMenus(long roleId, List<Long> menuIds);
	
	void updateRole(Role role);
	
	void addRole(Role role);

	SecurityInfo getSecurityInfo(String integer);

	boolean addOrUpdateRole(Role role);

	List<Menu> getMenuTree();

	LinkedHashMap<Integer, List<Resource>> getResourceGroupByCategory();

	LinkedHashMap<Integer, List<DataResource>> getDataResourceGroupbyCategory();

	void changeSearchForm(SearchForm searchForm);
}
