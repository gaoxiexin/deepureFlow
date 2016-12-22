package com.tasly.deepureflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.cache.RedisCache;
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.dao.IUserDao;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.domain.user.UserStationKey;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.exception.BizException;
import com.tasly.deepureflow.service.IUserService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.IdGenerator;
import com.tasly.deepureflow.util.MD5Encrypt;
import com.tasly.deepureflow.util.excel.impl.UserReadExcel;

@Service("userService")
public class UserServiceImpl implements IUserService {
	private final Logger logger = Logger.getLogger(UserServiceImpl.class
			.getName());
	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;

	@Autowired
	@Qualifier("securityDao")
	private ISecurityDao securityDao;

	@Autowired
	private RedisCache cache;
	@Autowired
	private UserReadExcel userReadExcel;
	@Autowired
	private IZoneDao zoneDao;
	
	@Autowired
	private IOfficeDao officeDao;
	
	@Autowired
	private IStationDao stationDao;

	@GET
	@Path("/getUser/{userId}")
	public User getUserId(@PathParam("userId") String id) {
		if (StringUtils.isNotEmpty(id)) {
			User user= this.userDao.getEntityByUserId(id);
			List<Role> roleList=securityDao.getRolesByUserId(id);
			if(CollectionUtils.isNotEmpty(roleList)){
				user.setRole(roleList.get(0));
			}

			return user;
		}
		return null;
	}

	@GET
	@Path("/getUserByName/{loginName}")
	public User findUserByLoginName(@PathParam("loginName") String loginName) {
		if (StringUtils.isNotEmpty(loginName)) {
			User user=  this.userDao.findUserByName(loginName);
			List<Role> roleList=securityDao.getRolesByUserId(String.valueOf(user.getId()));
			if(CollectionUtils.isNotEmpty(roleList)){
				user.setRole(roleList.get(0));
			}

			return user;
		}
		return null;
	}

	@SuppressWarnings("finally")
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@DELETE
	@Path("/delUser")
	public DeepureResult delUserByArray(String[] userIds) {
		boolean isDel = false;
		try {
			for (String userId : userIds) {
				isDel = this.userDao.delUserById(userId);
			}
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":删除用户出错", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		} finally {
			if (isDel && null != cache) {
				delUserRedis();
			}
			return DeepureResult.delResult(isDel);
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@POST
	@Path("/searchUser/{curPageSize}/{limit}")
	public PageList<User> findUserForPage(int curPageSize,int limit,String queryUserId,String queryUserName) {
		PageList<User> pageList = null;
//		String sortString = "USER_NAME.asc";
		PageBounds pageBounds = new PageBounds(curPageSize, limit);

		try {
			if (curPageSize != 0 && limit != 0) {
				pageList = (PageList<User>) this.userDao
						.findUserByCondition(pageBounds,queryUserId,queryUserName);
			}
		} catch (Exception e) {
			logger.error(ResultEnum.REDIS_ERROR.getMsg(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		}

		return pageList;
	}

	@SuppressWarnings("finally")
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@POST
	@Path("/addUser/{userName}/{userPass}")
	public DeepureResult addUser(@PathParam("userName") String userName,
			@PathParam("userPass") String userPass) {
		boolean isAdd = false;
		try {
			if (StringUtils.isNotEmpty(userName)
					&& StringUtils.isNotEmpty(userPass)) {
				userPass = MD5Encrypt.createPassword(userPass);
				String userId=IdGenerator.generateCode(IdGenerator.USER_PREFIX, 3);
				isAdd = this.userDao
						.insertUser(userId,userName, userPass);
				if (null != userId) {
					isAdd = true;
				}
			}
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":新增用户出错", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			isAdd = false;
		} finally {
			if (isAdd && null != cache) {
				delUserRedis();
			}
			return DeepureResult.addResult(isAdd);
		}
	}

	@Override
	@GET
	@Path("/loginUser/{userName}/{userPass}")
	public User loginUser(@PathParam("userCode") String userCode,
			@PathParam("userPass") String userPass) {
		try {
			if (StringUtils.isNotEmpty(userPass)
					&& StringUtils.isNotEmpty(userPass)) {
				return this.userDao.login(userCode, userPass);
			}

		} catch (BizException e) {
			logger.error(ResultEnum.LOGIN_USER.getMsg() + ":用户登录问题：， ", e);
		}
		return null;
	}

	@Override
	@GET
	@Path("/updateActive/{userId}")
	public DeepureResult updateActive(@PathParam("userId") String userId) {
		boolean isUpdate = this.userDao.updateUserActive(userId);
		if (isUpdate && null != cache) {
			delUserRedis();
		}

		return DeepureResult.editResult(isUpdate);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@POST
	@Path("/addUser/{userName}/{userCode}/{userPass}/{roleId}")
	public DeepureResult addUser(@PathParam("userName") String userName,
			@PathParam("userCode") String userCode,
			@PathParam("userPass") String userPass,
			@PathParam("roleId") String roleId,
			@PathParam("stationId") String stationId) {
		String userId=userCode;
		userPass = MD5Encrypt.createPassword(userPass);
		boolean isInsertUser= this.userDao.insertUser(userId,userName, userPass);
		if (StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(stationId)) {
			UserRoleRelationship userRoleRelationship = new UserRoleRelationship();
			userRoleRelationship.setRoleId(Long.valueOf(roleId));
			userRoleRelationship.setUserId(String.valueOf(userId));
			try {
				securityDao.grantUserRole(userRoleRelationship);
			} catch (Exception e) {
				isInsertUser=false;
				logger.error(ResultEnum.INNER_ERROR.getMsg() + ":新增用户出错", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			}
		}

		if(StringUtils.isNotEmpty(stationId)){
			UserStationKey userStation=new UserStationKey();
			userStation.setStationCode(stationId);
			userStation.setUserId(userId);
			try {
				userDao.insertUserStation(userStation);
			} catch (Exception e) {
				isInsertUser=false;
				logger.error(ResultEnum.INNER_ERROR.getMsg() + ":新增用户出错", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			}
		}
		return DeepureResult.addResult(isInsertUser);
	}

	@Override
	public DeepureResult resetPassword(String userId) {
		if(updatePassword(userId,"111111").getStatus()){
			return DeepureResult.result(true, "重置密码成功！");
		}else{
			return DeepureResult.result(false, "重置密码失败！");
		}
	}

	@Override
	public DeepureResult updatePassword(String userId, String password) {
		boolean isUpdate=false;
		if(StringUtils.isNotEmpty(userId)){
			User currentUser=this.userDao.getEntityByUserId(userId);
			String userPass = MD5Encrypt.createPassword(password);

			currentUser.setPassword(userPass);

			isUpdate= this.userDao.updateUser(currentUser);

			if (isUpdate && null != cache) {
				delUserRedis();
			}

		}
		return DeepureResult.editResult(isUpdate);
	}


	@Override
	public DeepureResult editUser(String userId, String userName, String roleId,
			String stationId) {
		User user=new User();
		user.setId(userId);
		user.setUsername(userName);

		boolean isUpdateUser= this.userDao.updateUser(user);
		if(CollectionUtils.isEmpty(securityDao.findUserRoleByUserId(userId))){
			if (StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(roleId)) {
				UserRoleRelationship userRoleRelationship = new UserRoleRelationship();
				userRoleRelationship.setRoleId(Long.valueOf(roleId));
				userRoleRelationship.setUserId(String.valueOf(userId));
				try {
					securityDao.grantUserRole(userRoleRelationship);
				} catch (Exception e) {
					isUpdateUser=false;
					logger.error(ResultEnum.INNER_ERROR.getMsg() + ":更新用户出错", e);
				}
			}
		}else{
			if (StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(stationId)) {
				UserRoleRelationship userRoleRelationship = new UserRoleRelationship();
				userRoleRelationship.setRoleId(Long.valueOf(roleId));
				userRoleRelationship.setUserId(String.valueOf(userId));
				try {
					securityDao.updateUserRole(userRoleRelationship);
				} catch (Exception e) {
					isUpdateUser=false;
					logger.error(ResultEnum.INNER_ERROR.getMsg() + ":更新用户出错", e);
				}
			}
		}


		if(StringUtils.isNotEmpty(stationId)){
			UserStationKey userStation=new UserStationKey();
			userStation.setStationCode(stationId);
			userStation.setUserId(userId);
			try {
				if(stationDao.findStationByUser(userId)!=null){
					userDao.updateUserStation(userStation);
				}else{
					userDao.insertUserStation(userStation);
				}

			} catch (Exception e) {
				isUpdateUser=false;
				logger.error(ResultEnum.INNER_ERROR.getMsg() + ":更新用户出错", e);
			}                               
		}

		return DeepureResult.editResult(isUpdateUser);
	}

	@Override
	public List<User> quertAllUser() {
		return userDao.findAllUser();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult importExcel(String targetPath, MultipartFile upFile) {
		boolean isImport=false;

		String sourceName = upFile.getOriginalFilename(); // 原始文件名

		File file = new File(targetPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			String path = targetPath + File.separator + sourceName;

			upFile.transferTo(new File(path));

			FileInputStream fin = new FileInputStream(new File(path));
			List<User> userList=(List<User>) userReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(userList)){
				isImport=this.addUser(userList);
				
			}
		} catch (Exception e) {
			logger.error("导入用户失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		}
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			return DeepureResult.result(isImport, "导入员工失败");
		}
		return DeepureResult.result(isImport, "导入员工成功");

	}
	private boolean addUser(List<User> userList) throws Exception{
		boolean flag = true;
		for(User user:userList){
			if("".equals(user.getId())){
				return false;
			}
			Station station = user.getStation();
			String stationCode="";
			if(station!=null){
				stationCode = station.getStationCode();
			}
				
			if(userDao.findUserById(user.getId())==null){
				if(stationDao.findStationByUser(user.getId())!=null){
					return false;
				}
				flag = this.insertUser(user.getId(),user.getUsername(),"111111",stationCode);
				
			}else{
				flag = userDao.updateUser(user);
				if(!flag){
					return false;
				}
				UserStationKey userStation=new UserStationKey();
				userStation.setStationCode(stationCode);
				userStation.setUserId(user.getId());
				if(!"".equals(stationCode)){
					if(userDao.findUserStationByUserId(user.getId())!=null){
						flag = userDao.updateUserStation(userStation);
					}else{
						flag = userDao.insertUserStation(userStation);
					}
					
				}
			}
			if(!flag){
				return false;
			}
		}
		return flag;
	}
	public boolean insertUser(String userId,String userName,String userPass,String stationCode) {
		userPass = MD5Encrypt.createPassword(userPass);
		boolean isInsertUser= this.userDao.insertUser(userId,userName, userPass);
		if(StringUtils.isNotEmpty(stationCode)){
			UserStationKey userStation=new UserStationKey();
			userStation.setStationCode(stationCode);
			userStation.setUserId(userId);
			try {
				userDao.insertUserStation(userStation);
			} catch (Exception e) {
				isInsertUser=false;
				logger.error(ResultEnum.INNER_ERROR.getMsg() + ":新增用户出错", e);
			}
		}
		return isInsertUser;
	}

	public void delUserRedis(){
		List<User> userList=cache.getListCache(RedisCache.CAHCENAME
				+ "|getUserList|*",User.class);
		if(CollectionUtils.isNotEmpty(userList)){
			cache.deleteCacheWithPattern(RedisCache.CAHCENAME
					+ "|getUserList|*");
		}
	}

	@Override
	public DeepureResult validateUserId(String userId) {

		if(userDao.findUserById(userId)==null){
			return DeepureResult.success();
		}else{
			return DeepureResult.result(false, "员工编号已存在");
		}
	}

	@Override
	public List<User> queryUserByRole() {
			// 判断当前登录用户的角色
			User currentUser = (User) SecurityUtils.getSubject().getSession()
					.getAttribute("user");
			Zone zone = zoneDao.findZoneByUser(currentUser.getId());
			Office office = officeDao.findOfficeByUser(currentUser.getId());
//			Station station=stationDao.findStationByUser(currentUser.getId());
			
			List<UserRoleRelationship> userRole = securityDao.findUserRoleByUserId(currentUser.getId());
			List<User> userList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(userRole)){
				Integer roleId = (int) userRole.get(0).getRoleId();
				switch (roleId) {
				case 1:
					userList = userDao.findAllUser();
					break;
				case 2:
					userList = userDao.findUserByZoneCode(zone.getZoneCode());
					break;
				case 3:
					userList = userDao.findUserByOfficeCode(office.getOfficeCode());
					break;
				case 4:
					userList.add(currentUser);
					break;
				default:
					break;
				}
			}
			return userList;
	}
}
