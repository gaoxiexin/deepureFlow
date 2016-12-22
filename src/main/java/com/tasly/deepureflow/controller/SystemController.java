package com.tasly.deepureflow.controller;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tasly.deepureflow.domain.security.DataResource;
import com.tasly.deepureflow.domain.security.Menu;
import com.tasly.deepureflow.domain.security.Resource;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.system.JobDTO;
import com.tasly.deepureflow.dto.RoleForm;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IJobService;
import com.tasly.deepureflow.service.ISecurityService;
import com.tasly.deepureflow.util.DeepureResult;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  SystemController   
 * @Description:角色菜单管理Controller
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:54:45 PM   
 *
 */
@Controller
@RequestMapping("/system")
@SessionAttributes("selectItem")
public class SystemController {
	private final Logger logger = Logger.getLogger(SystemController.class
			.getName());
	@Autowired
	private ISecurityService securityService;

	@Autowired
	IJobService jobService;

	@RequestMapping(value = "/roleList")
	@ApiOperation(value = "展示角色列表", httpMethod = "POST", response = String.class, notes = "展示角色列表")
	public String roleList(HttpServletRequest request, Model model,String queryRoleName) {
		model.addAttribute("selectItem", MenuEnum.ROLE.getId());
		List<Role> roleList = null;
		List<Menu> menus = null;
		LinkedHashMap<Integer, List<Resource>> permissions = null;
		LinkedHashMap<Integer, List<DataResource>> dataPermissions = null;

		try {
			roleList = securityService.getAllRoles(queryRoleName);
			menus = securityService.getMenuTree();
			permissions = securityService.getResourceGroupByCategory();
			dataPermissions = securityService.getDataResourceGroupbyCategory();
		} catch (Exception e) {
			logger.error("获取角色管理页面信息出错", e);
		}
		model.addAttribute("menuTree", menus);
		model.addAttribute("roleList", roleList);
		model.addAttribute("permissions", permissions);
		model.addAttribute("dataPermissions", dataPermissions);

		return "/system/roleList";
	}

	@RequestMapping(value = "/roleShow", method = RequestMethod.POST)
	public @ResponseBody List<Role> roleShow(HttpServletRequest request,
			Model model,@RequestParam("queryRoleName") String queryRoleName) {
		// PaginatorResult<Channel> result=new PaginatorResult<Channel>();
		//System.out.println(queryRoleName);
		List<Role> roleList = securityService.getAllRoles(queryRoleName);
		return roleList;
	}

	@ResponseBody
	@RequestMapping(value = "addOrUpdateRole", method = RequestMethod.POST)
	public DeepureResult addOrUpdateRole(@RequestBody Role role) {
		boolean isCreate = false;
		try {
			isCreate = securityService.addOrUpdateRole(role);
		} catch (Exception e) {
			logger.error("更新数据权限信息出错", e);
		}
		return DeepureResult.editResult(isCreate);
	}

	@ResponseBody
	@RequestMapping(value = "getMenuIds", method = RequestMethod.GET)
	public List<Long> getMenuIds(@RequestParam("roleId") Long roleId) {
		return securityService.getMenuIdsByRoleId(roleId);
	}

	@ResponseBody
	@RequestMapping(value = "allocateMenu", method = RequestMethod.POST)
	public DeepureResult allocateMenu(@RequestBody RoleForm roleForm) {
		boolean isUpate = false;
		try {
			securityService.allocateMenus(roleForm.getRoleId(),
					roleForm.getMenuIds());
			isUpate = true;
		} catch (Exception e) {
			isUpate = false;
			logger.error("更新菜单信息出错", e);
		}
		return DeepureResult.editResult(isUpate);
	}

	@ResponseBody
	@RequestMapping(value = "getResourceIds", method = RequestMethod.GET)
	public List<Long> getResourceIds(@RequestParam("roleId") Long roleId) {
		return securityService.getResoucesIdsByRoleId(roleId);
	}

	@ResponseBody
	@RequestMapping(value = "allocateResource", method = RequestMethod.POST)
	public DeepureResult allocateResource(@RequestBody RoleForm roleForm) {
		boolean isUpate = false;
		try {
			securityService.allocateResources(roleForm.getRoleId(),
					roleForm.getResourceIds());
			isUpate = true;
		} catch (Exception e) {
			isUpate = false;
			logger.error("更新权限信息出错", e);
		}
		return DeepureResult.editResult(isUpate);
	}

	@ResponseBody
	@RequestMapping(value = "getDataResourceIds", method = RequestMethod.GET)
	public List<Long> getDataResourceIds(@RequestParam("roleId") Long roleId) {
		return securityService.getDataResoucesIdsByRoleId(roleId);
	}

	@ResponseBody
	@RequestMapping(value = "allocateDataResource", method = RequestMethod.POST)
	public DeepureResult allocateDataResource(@RequestBody RoleForm roleForm) {
		boolean isUpate = false;
		try {
			securityService.allocateDataResources(roleForm.getRoleId(),
					roleForm.getDataResourceIds());
			isUpate = true;
		} catch (Exception e) {
			isUpate = false;
			logger.error("更新数据权限信息出错", e);
		}
		return DeepureResult.editResult(isUpate);
	}

	@RequestMapping(value = "/jobList")
	@ApiOperation(value = "展示角色列表", httpMethod = "POST", response = String.class, notes = "展示角色列表")
	public String jobList(HttpServletRequest request, Model model) {
		model.addAttribute("selectItem", MenuEnum.JOB.getId());
		return "/system/jobList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "jobShow", method = RequestMethod.GET)
	public @ResponseBody List<JobDTO> getAllJobs(Model model) {
		try {
			List<JobDTO> jobs = jobService.getAllJobs();
			return jobs;
		} catch (Exception e) {
			logger.error("获取任务列表失败："+e.getMessage(), e);
			return ListUtils.EMPTY_LIST;
		}
	}

	@ResponseBody
	@RequestMapping(value = "pauseJob", method = RequestMethod.GET)
	public boolean pauseJob(@QueryParam("jobKey") String jobKey) {
		return jobService.pauseJob(jobKey);
	}

	@ResponseBody
	@RequestMapping(value = "resumeJob", method = RequestMethod.GET)
	public boolean resumeJob(@QueryParam("jobKey") String jobKey) {
		return jobService.resumeJob(jobKey);

	}

	@ResponseBody
	@RequestMapping(value = "triggerJob", method = RequestMethod.GET)
	public boolean triggerJob(@QueryParam("jobKey") String jobKey) {
		return jobService.triggerJob(jobKey);

	}

	@ResponseBody
	@RequestMapping(value = "deleteJob", method = RequestMethod.GET)
	public boolean deleteJob(@QueryParam("jobKey") String jobKey) {
		boolean result=true;
		try {
			result= jobService.deleteJob(jobKey);
		} catch (SchedulerException e) {
			result=false;
			logger.error("删除任务出错："+e.getMessage(),e);
		}
		
		return result;

	}

	@ResponseBody
	@RequestMapping(value = "rescheduleJob", method = RequestMethod.GET)
	public boolean rescheduleJob(
			@QueryParam("triggerKey") String triggerKey,
			@QueryParam("cronExpression") String cronExpression) {
		boolean result=true;
		try {
			new CronExpression(cronExpression);
			jobService.rescheduleTrigger(triggerKey, cronExpression);
		} catch (ParseException e) {
			logger.error("格式化周期出错："+e.getMessage(),e);
			result= false;
		} catch (SchedulerException e) {
			logger.error("重设触发周期出错："+e.getMessage(),e);
			result= false;
		}
		
		return result;
	}
}
