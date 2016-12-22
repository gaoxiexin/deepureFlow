package com.tasly.deepureflow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.security.DataResource;
import com.tasly.deepureflow.domain.security.Menu;
import com.tasly.deepureflow.domain.security.Resource;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.RoleDataResourceRelationship;
import com.tasly.deepureflow.domain.security.RoleMenuRelationship;
import com.tasly.deepureflow.domain.security.RoleResourceRelationship;
import com.tasly.deepureflow.domain.security.SecurityInfo;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.service.ISecurityService;

@Service("securityService") 
public class SecurityServiceImpl implements ISecurityService {
	
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(SecurityServiceImpl.class.getName());
	
	@Autowired
    private ISecurityDao securityDao;
	
	@Autowired
	private IOfficeDao officeDao;
	
	@Autowired
	private IZoneDao zoneDao;

	@Override
	public List<Long> getMenuIdsByRoleId(long roleId) {
		List<Long> menuIds = securityDao.getMenuIdsByRoleId(roleId);
		return menuIds;
	}

	@Override
	public List<Long> getResoucesIdsByRoleId(long roleId) {
		List<Long> resourceIds = securityDao.getResoucesIdsByRoleId(roleId);
		return resourceIds;
	}

	@Override
	public List<Long> getDataResoucesIdsByRoleId(long roleId) {
		List<Long> resourceIds = securityDao.getDataResoucesIdsByRoleId(roleId);
		return resourceIds;
	}

	@Override
	public List<String> getUserIdsByRoleCode(String roleCode) {
		Role role = securityDao.getRoleByCode(roleCode);
		if(role != null){
			List<String> userIds = securityDao.getUserIdsByRoleId(role.getId());
			return userIds;
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void allocateResources(long roleId, List<Long> resourceIds) {
		List<Long> originalResourceIds = securityDao.getResoucesIdsByRoleId(roleId);
		if(CollectionUtils.isEmpty(resourceIds) && CollectionUtils.isEmpty(originalResourceIds)){
			return;
		}
		if(!CollectionUtils.isEmpty(resourceIds) && !CollectionUtils.isEmpty(originalResourceIds)
				&& resourceIds.containsAll(originalResourceIds) && originalResourceIds.containsAll(resourceIds)){
			return;
		}
		List<Long> removeIds = null;
		List<Long> addIds = null;
		if(CollectionUtils.isEmpty(resourceIds)){
			removeIds = originalResourceIds;
		}else if(CollectionUtils.isEmpty(originalResourceIds)){
			addIds = resourceIds;
		}else{
			List<Long> intersectionList = ListUtils.intersection(originalResourceIds, resourceIds);
			removeIds =  ListUtils.subtract(originalResourceIds, intersectionList);
			addIds =  ListUtils.subtract(resourceIds, intersectionList);
		}
		if(!CollectionUtils.isEmpty(removeIds)){
			securityDao.deleteResourceByRoleIdAndResourceIds(roleId, removeIds);
		}
		if(!CollectionUtils.isEmpty(addIds)){
			for(Long resourceId : addIds){
				RoleResourceRelationship relationship = new RoleResourceRelationship();
				relationship.setResourceId(resourceId);
				relationship.setRoleId(roleId);
				securityDao.allocateResource(relationship);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void allocateDataResources(long roleId, List<Long> dataResourceIds) {
		List<Long> originalDataResourceIds = securityDao.getDataResoucesIdsByRoleId(roleId);
		if(CollectionUtils.isEmpty(dataResourceIds) && CollectionUtils.isEmpty(originalDataResourceIds)){
			return;
		}
		if(!CollectionUtils.isEmpty(dataResourceIds) && !CollectionUtils.isEmpty(originalDataResourceIds)
				&& dataResourceIds.containsAll(originalDataResourceIds) && originalDataResourceIds.containsAll(dataResourceIds)){
			return;
		}
		List<Long> removeIds = null;
		List<Long> addIds = null;
		if(CollectionUtils.isEmpty(dataResourceIds)){
			removeIds = originalDataResourceIds;
		}else if(CollectionUtils.isEmpty(originalDataResourceIds)){
			addIds = dataResourceIds;
		}else{
			List<Long> intersectionList = ListUtils.intersection(originalDataResourceIds, dataResourceIds);
			removeIds =  ListUtils.subtract(originalDataResourceIds, intersectionList);
			addIds =  ListUtils.subtract(dataResourceIds, intersectionList);
		}
		if(!CollectionUtils.isEmpty(removeIds)){
			securityDao.deleteDataResourceByRoleIdAndDataResourceIds(roleId,removeIds);
		}
		if(!CollectionUtils.isEmpty(addIds)){
			for(Long resourceId : addIds){
				RoleDataResourceRelationship relationship = new RoleDataResourceRelationship();
				relationship.setDataResourceId(resourceId);
				relationship.setRoleId(roleId);
				securityDao.allocateDataResource(relationship);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void allocateMenus(long roleId, List<Long> menuIds) {
		List<Long> originalMenuIds = securityDao.getMenuIdsByRoleId(roleId);
		if(CollectionUtils.isEmpty(menuIds) && CollectionUtils.isEmpty(originalMenuIds)){
			return;
		}
		if(!CollectionUtils.isEmpty(menuIds) && !CollectionUtils.isEmpty(originalMenuIds)
				&& menuIds.containsAll(originalMenuIds) && originalMenuIds.containsAll(menuIds)){
			return;
		}
		List<Long> removeIds = null;
		List<Long> addIds = null;
		if(CollectionUtils.isEmpty(menuIds)){
			removeIds = originalMenuIds;
		}else if(CollectionUtils.isEmpty(originalMenuIds)){
			addIds = menuIds;
		}else{
			List<Long> intersectionList = ListUtils.intersection(originalMenuIds, menuIds);
			removeIds =  ListUtils.subtract(originalMenuIds, intersectionList);
			addIds =  ListUtils.subtract(menuIds, intersectionList);
		}
		if(!CollectionUtils.isEmpty(removeIds)){
			securityDao.deleteMenuByRoleIdAndMenuIds(roleId, removeIds);
		}
		if(!CollectionUtils.isEmpty(addIds)){
			for(Long menuId : addIds){
				RoleMenuRelationship relationship = new RoleMenuRelationship();
				relationship.setMenuId(menuId);
				relationship.setRoleId(roleId);
				securityDao.allocateMenu(relationship);
			}
		}
	}

	@Override
	public void updateRole(Role role) {
		securityDao.updateRole(role);
	}

	@Override
	public void addRole(Role role) {
		securityDao.addRole(role);
	}

	@Override
	public SecurityInfo getSecurityInfo(String userId) {
		List<Role> roles = securityDao.getRolesByUserId(userId);
		SecurityInfo userdto = new SecurityInfo();
		if(roles != null && roles.size() > 0){
			Set<String> roleNames = new HashSet<String>();
			List<Long> roleIds = new ArrayList<Long>();
			for(Role role : roles){
				roleIds.add(role.getId());
				roleNames.add(role.getCode());
			}
			userdto.setRoleName(roleNames);
			String roleStr = StringUtils.join(roleIds, ",");
			List<String> permissions = securityDao.getResoucesByRoleIds(roleStr);
			List<Menu> menus = securityDao.getMenusByRoleIds(roleStr);
			Set<String> permissionsSet = new HashSet<String>();
			permissionsSet.addAll(permissions);
			userdto.setPermissions(permissionsSet);
			userdto.setUserId(String.valueOf(userId));
			userdto.setMenus(buildMenuTree(menus));
		}
		
		return userdto;
	}
	
	private List<Menu> buildMenuTree(List<Menu> menus){
		List<Menu> Menus = new ArrayList<Menu>();
		if(!CollectionUtils.isEmpty(menus)){
			for(Iterator<Menu> it = menus.iterator(); it.hasNext();){
				Menu menu = it.next();
				if(menu.getParent_id() == 0){
					Menu Menu = new Menu();
					Menu.setId(menu.getId());
					Menu.setName(menu.getName());
					Menus.add(Menu);
					it.remove();
				}
			}
			for(Menu Menu : Menus){
				if(!CollectionUtils.isEmpty(menus)){
					List<Menu> submenus = Menu.getChildren();
					if(submenus == null){
						submenus = new ArrayList<Menu>();
					}
					for(Menu menu : menus){
						if(Menu.getId() == menu.getParent_id()){
							
							Menu subMenu = new Menu();
							subMenu.setId(menu.getId());
							subMenu.setName(menu.getName());
							subMenu.setUrl(menu.getUrl());
							submenus.add(subMenu);
							
						}
					}
					Menu.setChildren(submenus);
				}
			}
			return Menus;
		}
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles(String queryRoleName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("queryRoleName", queryRoleName);
		List<Role> roles = securityDao.getAllRoles(params);
		return CollectionUtils.isEmpty(roles)?Collections.EMPTY_LIST:roles;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addOrUpdateRole(Role role) {
		if(role.getId() != null && role.getId() > 0){
			return securityDao.updateRole(role)==0?false:true;
		}else{
			return securityDao.addRole(role)==0?false:true;
		}
	}

	@Override
	public List<Menu> getMenuTree() {
		List<Menu> resources = securityDao.getAllMenus();
		return buildMenuTree(resources);
	}

	@Cacheable(value = "resources", key="'allResource'")
	public LinkedHashMap<Integer, List<Resource>> getResourceGroupByCategory(){
		List<com.tasly.deepureflow.domain.security.Resource> resources = securityDao.getAllResources();
		if(!CollectionUtils.isEmpty(resources)){
			LinkedHashMap<Integer, List<com.tasly.deepureflow.domain.security.Resource>> resourceMap = new LinkedHashMap<Integer, List<com.tasly.deepureflow.domain.security.Resource>>();
			for(com.tasly.deepureflow.domain.security.Resource resource : resources){
				Integer category = resource.getCategory();
				List<Resource> resourceList = resourceMap.get(category);
				if(resourceList == null){
					resourceList = new ArrayList<Resource>();
					resourceMap.put(category, resourceList);
				}
				resourceList.add(resource);
			}
			return resourceMap;
		}
		return null;
	}

	@Override
	public LinkedHashMap<Integer, List<DataResource>> getDataResourceGroupbyCategory() {
		List<DataResource> resources = securityDao.getAllDataResources();
		if(!CollectionUtils.isEmpty(resources)){
			LinkedHashMap<Integer, List<DataResource>> resourceMap = new LinkedHashMap<Integer, List<DataResource>>();
			for(DataResource resource : resources){
				Integer type = resource.getType();
				List<DataResource> resourceList = resourceMap.get(type);
				if(resourceList == null){
					resourceList = new ArrayList<DataResource>();
					resourceMap.put(type, resourceList);
				}
				resourceList.add(resource);
			}
			return resourceMap;
		}
		return null;
	}
	
	@Override
	public void changeSearchForm(SearchForm searchForm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", searchForm.getRoleCode());
		map.put("type",searchForm.getRoleType());
		
		List<String> statements = securityDao.getDataResourceByRole(map);
		
		if(CollectionUtils.isNotEmpty(statements)){
			searchForm.setQueryId(statements.get(0));
		}
		
		Office office=officeDao.findOfficeByUser(searchForm.getUserId());
		Zone zone=zoneDao.findZoneByUser(searchForm.getUserId());
		
		searchForm.setOfficeCode(office!=null?office.getOfficeCode():null);
		searchForm.setZoneCode(zone!=null?zone.getZoneCode():null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = securityDao.getAllRoles();
		return CollectionUtils.isEmpty(roles)?ListUtils.EMPTY_LIST:roles;
	}

}
