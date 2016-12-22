package com.tasly.deepureflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.domain.security.DataResource;
import com.tasly.deepureflow.domain.security.Menu;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.RoleDataResourceRelationship;
import com.tasly.deepureflow.domain.security.RoleMenuRelationship;
import com.tasly.deepureflow.domain.security.RoleResourceRelationship;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;

@Repository("securityDao")
public class SecurityDaoImpl implements ISecurityDao{

	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	@Override
	public int addRole(Role role) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.addRole(role);
	}

	@Override
	public List<Role> getAllRoles(Map<String, Object> params) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getAllRoles(params);
	}

	@Override
	public List<Role> getRolesByUserId(String userId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getRolesByUserId(userId);
	}

	@Override
	public List<String> getResoucesByRoleIds(String roleIds) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getResoucesByRoleIds(roleIds);
	}

	@Override
	public List<Menu> getMenusByRoleIds(String roleIds) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getMenusByRoleIds(roleIds);
	}

	@Override
	public List<com.tasly.deepureflow.domain.security.Resource> getAllResources() {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getAllResources();
	}

	@Override
	public List<Menu> getAllMenus() {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getAllMenus();
	}

	@Override
	public List<Long> getResoucesIdsByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getResoucesIdsByRoleId(roleId);
	}

	@Override
	public List<Long> getMenuIdsByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getMenuIdsByRoleId(roleId);
	}

	@Override
	public void deleteResourceByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteResourceByRoleId(roleId);
	}

	@Override
	public void deleteMenuByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteMenuByRoleId(roleId);
	}

	@Override
	public void allocateResource(RoleResourceRelationship relationship) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.allocateResource(relationship);
	}
	
	@Override
	public void allocateDataResource(RoleDataResourceRelationship relationship) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.allocateDataResource(relationship);
	}

	@Override
	public void allocateMenu(RoleMenuRelationship relationship) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.allocateMenu(relationship);
	}

	@Override
	public int updateRole(Role role) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.updateRole(role);
	}

	@Override
	public List<String> getDataResourceByRole(Map<?, ?> params) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getDataResourceByRole(params);
	}

	@Override
	public Role getRoleByCode(String code) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getRoleByCode(code);
	}
	
	@Override
	public void grantUserRole(UserRoleRelationship userRoleRelationship) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.grantUserRole(userRoleRelationship);
	}

	
	@Override
	public void deleteRoleByUserId(String userId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteRoleByUserId(userId);
	}
	
	@Override
	public List<UserRoleRelationship> findUserRoleByUserId(String userId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.findUserRoleByUserId(userId);
	}

	@Override
	public void updateUserRole(UserRoleRelationship userRoleRelationship) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.updateUserRole(userRoleRelationship);
	}
	
	@Override
	public List<String> getMemberDataResourceByUserId(String userId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getMemberDataResourceByUserId(userId);
	}

	@Override
	public List<DataResource> getAllDataResources() {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getAllDataResources();
	}

	@Override
	public List<Long> getDataResoucesIdsByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getDataResoucesIdsByRoleId(roleId);
	}

	@Override
	public void deleteDataResourceByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteDataResourceByRoleId(roleId);
		
	}

	@Override
	public void deleteMenuByRoleIdAndMenuIds(long roleId, List<Long> menuIds) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteMenuByRoleIdAndMenuIds(roleId, menuIds);
		
	}
	
	@Override
	public void deleteResourceByRoleIdAndResourceIds(long roleId, List<Long> resourceIds) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteResourceByRoleIdAndResourceIds(roleId, resourceIds);
		
	}

	@Override
	public void deleteDataResourceByRoleIdAndDataResourceIds(long roleId, List<Long> dataResourceIds) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteDataResourceByRoleIdAndDataResourceIds(roleId, dataResourceIds);
		
	}

	@Override
	public List<String> getUserIdsByRoleId(long roleId) {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		return securityDao.getUserIdsByRoleId(roleId);
	}

	@Override
	public List<Role> getAllRoles() {
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		Map<String, Object> params =new HashMap<String,Object>();
		return securityDao.getAllRoles(params);
	}
}
