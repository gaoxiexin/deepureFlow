package com.tasly.deepureflow.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.CollectionUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.ISecurityService;
import com.tasly.deepureflow.service.IStationService;
import com.tasly.deepureflow.service.IUserService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.PaginatorResult;
import com.tasly.deepureflow.util.excel.ExcelPoiUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  UserController   
 * @Description:内部用户Controller  
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:56:21 PM   
 *
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("selectItem")
public class UserController {

	private final Logger logger = Logger.getLogger(UserController.class
			.getName());
	@Resource
	private IUserService userService;

	@Resource
	private IStationService stationService;
	
	@Resource
	private ISecurityService securityService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request, Model model) {
		String userId = request.getParameter("id");
		User user = this.userService.getUserId(userId);
		model.addAttribute("user", user);
		return "showUser";
	}

	@RequestMapping(value = "/getUserInfo")
	@ApiOperation(value = "测试数据：获取用户列表", httpMethod = "POST", response = String.class, notes = "获取用户列表")
	public String getUserInfo(HttpServletRequest request, Model model,String queryRoleName) {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		request.setAttribute("currUser", currentUser.getUsername());
		model.addAttribute("selectItem", MenuEnum.EMPLOYEE.getId());

		List<Station> stationList = stationService.queryAllStation();
		List<Station> newStationList = new LinkedList<>();
		for(Station station : stationList){
			DeepureResult result = stationService.validateStation(null,station.getStationCode());
			if(result.getStatus()){
				String newName = station.getStationName()+"(可用)";
				station.setStationName(newName);
				newStationList.add(station);
			}else{
				newStationList.add(station);
			}
		}
		
		List<Role> roleList=securityService.getAllRoles(queryRoleName);
		model.addAttribute("stationList", newStationList);
		model.addAttribute("roleList", roleList);
		return "/user/userList";
	}
	
	@RequestMapping(value = "/findStation")
	@ResponseBody 
	public List<Station> findStation(HttpServletRequest request, Model model) {

		List<Station> stationList = stationService.queryAllStation();
		List<Station> newStationList = new LinkedList<>();
		for(Station station : stationList){
			DeepureResult result = stationService.validateStation(null,station.getStationCode());
			if(result.getStatus()){
				String newName = station.getStationName()+"(可用)";
				station.setStationName(newName);
				newStationList.add(station);
			}else{
				newStationList.add(station);
			}
		}
		
		//model.addAttribute("stationList", newStationList);
		return newStationList;
	}

	@RequestMapping(value = "/listUser", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<User> userList(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam(required = false, value = "queryUserId") String queryUserId,
			@RequestParam(required = false, value = "queryUserName") String queryUserName
			) {
		PaginatorResult<User> result=new PaginatorResult<User>();
		PageList<User> userList=userService.findUserForPage(curPageSize,
				limit,queryUserId,queryUserName);
		if(!CollectionUtils.isEmpty(userList)){
			result.setRows(userList);
			result.setTotal(userList.getPaginator().getTotalCount());
		}
        return result;  
	}

	@ResponseBody
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	public DeepureResult delUser(@RequestParam("userList") final String[] userList) {
		if (ArrayUtils.isNotEmpty(userList)) {
			return this.userService.delUserByArray(userList);
		}
		return DeepureResult.result(false, "员工列表为空，删除失败");
	}

	@ResponseBody
	@RequestMapping(value = "/addOrUpdateUser", method = RequestMethod.POST)
	public DeepureResult addOrUpdateUser(@RequestParam("userId") String userId,
			@RequestParam("userName") String userName,
			@RequestParam("userCode") String userCode,
			@RequestParam("userPass") String userPass,
			@RequestParam("roleId") String roleId,
			@RequestParam("stationId") String stationId) {
		DeepureResult result = stationService.validateStation(userId,stationId);
		if(result.getStatus()){
			if (StringUtils.isNotEmpty(userId)) {
				return userService.editUser(userId,userName,roleId,stationId);
				
			} else {
				return userService.addUser(userName,userCode, userPass, roleId,stationId);
			}
		}else{
			return result;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateUserId", method = RequestMethod.POST)
	public DeepureResult validateUserId(
			@RequestParam("userId") String userId
			) {
		return this.userService.validateUserId(userId);

	}

	@ResponseBody
	@RequestMapping(value = "/updateActive", method = RequestMethod.POST)
	public DeepureResult updateActive(@RequestParam("userId") final String userId) {
		if (StringUtils.isNotEmpty(userId)) {
			return this.userService.updateActive(userId);
		}
		return DeepureResult.result(false, "员工编号为空，修改失败");
	}

	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public DeepureResult resetPassword(@RequestParam("userId") final String userId) {
		if (StringUtils.isNotEmpty(userId)) {
			return this.userService.resetPassword(userId);
		}
		return DeepureResult.result(false, "员工编号为空，重置密码失败");
	}

	@RequestMapping("/userInfo")
	public String userInfo(HttpServletRequest request, Model model) {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		String userId = String.valueOf(currentUser.getId());
		currentUser = this.userService.getUserId(userId);
		model.addAttribute("user", currentUser);
		return "/user/userInfo";
	}

	@ResponseBody
	@RequestMapping("/modifyUserPassword")
	public DeepureResult modifyUserPassword(Model model,
			@RequestParam("oldPwd") final String oldPwd,
			@RequestParam("password") final String password,
			@RequestParam("confirmPassword") final String confirmPassword) {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		UsernamePasswordToken token = new UsernamePasswordToken(
				currentUser.getId(),  oldPwd);
		try {
			SecurityUtils.getSubject().login(token);
		} catch (Exception e) {
			return DeepureResult.result(false, "原密码错误");
		}
		if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(confirmPassword)) {
			return DeepureResult.result(false, "所有字段不能为空");
		}

		if (oldPwd.equals(password)) {
			return DeepureResult.result(false, "新密码不能与原密码相同");
		}
		if (!password.equals(confirmPassword)) {
			return DeepureResult.result(false, "新密码不能与原密码相同");
		}
		return this.userService.updatePassword(
				String.valueOf(currentUser.getId()), password);
	}

	@RequestMapping(value = "toExportUser", method = RequestMethod.GET)
	public void toExportUser(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("用户编号");
			theader.add("用户名称");
			theader.add("所属岗位编号");

			HSSFWorkbook workbook = ExcelPoiUtils.exportExcel("内部员工模板",
					theader, null, null, null, null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String excelName = "user_" + dateFormat.format(new Date()) + ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("导出内部员工报表失败", e);
		}
		// return true;
	}
	@RequestMapping(value = "importUserExcel", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult importUserExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return userService.importExcel(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}

}
