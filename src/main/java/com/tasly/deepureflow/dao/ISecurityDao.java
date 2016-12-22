package com.tasly.deepureflow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.security.DataResource;
import com.tasly.deepureflow.domain.security.Menu;
import com.tasly.deepureflow.domain.security.Resource;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.RoleDataResourceRelationship;
import com.tasly.deepureflow.domain.security.RoleMenuRelationship;
import com.tasly.deepureflow.domain.security.RoleResourceRelationship;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;


public interface ISecurityDao {

	int addRole(Role role);
	
	int updateRole(Role role);
	
	List<Role> getAllRoles(Map<String, Object> params);
	
	List<Role> getAllRoles();
	
	List<Role> getRolesByUserId(String userId);
	
	List<String> getResoucesByRoleIds(String roleIds);
	
	List<Long> getResoucesIdsByRoleId(long roleId);
	
	List<Long> getDataResoucesIdsByRoleId(long roleId);
	
	List<String> getUserIdsByRoleId(long roleId);
	
	List<Resource> getAllResources();
	
	List<DataResource> getAllDataResources();
	
	List<Menu> getMenusByRoleIds(String roleIds);
	
	List<Long> getMenuIdsByRoleId(long roleId);
	
	List<Menu> getAllMenus();
	
	void deleteResourceByRoleId(long roleId);
	
	void deleteDataResourceByRoleId(long roleId);
	
	void deleteMenuByRoleId(long roleId);
	
	void deleteMenuByRoleIdAndMenuIds(@Param("roleId")long roleId, @Param("menuIds") List<Long> menuIds);
	
	void deleteResourceByRoleIdAndResourceIds(@Param("roleId")long roleId, @Param("resourceIds") List<Long> resourceIds);
	
	void deleteDataResourceByRoleIdAndDataResourceIds(@Param("roleId")long roleId, @Param("dataResourceIds") List<Long> dataResourceIds);
	
	void allocateResource(RoleResourceRelationship relationship);
	
	void allocateDataResource(RoleDataResourceRelationship relationship);

	void allocateMenu(RoleMenuRelationship relationship);
	
	List<String> getDataResourceByRole(Map<?, ?> params);
		
	Role getRoleByCode(String code);
	
	void grantUserRole(UserRoleRelationship userRoleRelationship);
	
	void deleteRoleByUserId(String userId);

	/**
	 * 
	 * @Title: getMemberDataResourceByUserId 
	 * @Description: retrieve member data resource statement
	 * @param  userId
	 * @return String    statement
	 * @throws
	 */
	List<String> getMemberDataResourceByUserId(String userId);

	
	List<UserRoleRelationship> findUserRoleByUserId(String userId);
	
	void updateUserRole(UserRoleRelationship userRoleRelationship);
	
}
