package com.tasly.deepureflow.dto;

import java.util.List;

public class RoleForm {

	private Long roleId;
	private List<Long> menuIds;
	private List<Long> resourceIds;
	private List<Long> dataResourceIds;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public List<Long> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}
	public List<Long> getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(List<Long> resourceIds) {
		this.resourceIds = resourceIds;
	}
	public List<Long> getDataResourceIds() {
		return dataResourceIds;
	}
	public void setDataResourceIds(List<Long> dataResourceIds) {
		this.dataResourceIds = dataResourceIds;
	}
	
	
}
