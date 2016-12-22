package com.tasly.deepureflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IUserMapper;
import com.tasly.deepureflow.client.IUserStationMapper;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IUserDao;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.domain.user.UserExample;
import com.tasly.deepureflow.domain.user.UserStationExample;
import com.tasly.deepureflow.domain.user.UserStationKey;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.exception.BizException;


@Repository("userDao")
public class UserDaoImpl implements IUserDao {
	private final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	public User getEntityByUserId(String id) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		User user= userMapper.selectByPrimaryKey(id);
		return user;
	}

	public User findUserByName(String name) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		UserExample example = new UserExample();
		example.or().andUserNameEqualTo(name);
		
		List<User> userList= userMapper.selectByExample(example);
				
		return CollectionUtils.isNotEmpty(userList)?userList.get(0):null;
	}
	
	public List<User> findUserByCondition(PageBounds pageBounds,String queryUserId,String queryUserName){  
	    Map<String, Object> params =new HashMap<String, Object>();
	    params.put("queryUserId", queryUserId);
	    params.put("queryUserName", queryUserName);
	    return sqlSession.selectList(IUserMapper.class.getName()+".userListForPage", params, pageBounds);  
	}  
	
	public boolean delUserById(String userId) throws BizException{
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		IUserStationMapper userStationMapper = sqlSession.getMapper(IUserStationMapper.class);
		UserStationExample example = new UserStationExample();
		ISecurityDao securityDao = sqlSession.getMapper(ISecurityDao.class);
		securityDao.deleteRoleByUserId(userId);
		example.or().andUserIdEqualTo(userId);
		userStationMapper.deleteByExample(example);
		int count=userMapper.deleteByPrimaryKey(userId);
		
		if(count>0){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean insertUser(String userId,String userName, String userPass) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		
		User user= new User();
		user.setId(userId);
		user.setUsername(userName);
		user.setPassword(userPass);
		user.setIsactive(true);
		
		int count=userMapper.insert(user);
		return count>0?true:false;
	}
	
	/**
	 * KAS-1066
	 * @param userName
	 * @param encodedPass
	 * @return
	 * @throws MemberDataAccessException
	 */
	@Override
	public User login(String userCode, String encodedPass) throws BizException{
		IUserMapper userLoginMapper = sqlSession.getMapper(IUserMapper.class);
		UserExample example = new UserExample();
		example.or().andUserIdEqualTo(userCode).andUserPasswordEqualTo(encodedPass);
		User temp=null;
		try {
			List<User> loginList = userLoginMapper.selectByExample(example);
			if (loginList != null && !loginList.isEmpty()) {
				temp = loginList.get(0);
				
			}
		} catch (Exception e) {
			BizException mdae = new BizException(ResultEnum.LOGIN_USER.getMsg(), e);
			logger.error("userCode or encodedPass 无法登录，", e);
			throw mdae;
		}
		return temp;
	}

	@Override
	public boolean updateUserActive(String userId) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		User user=userMapper.selectByPrimaryKey(userId);
		boolean updateActive=user.getIsactive()==false?true:false;
		user.setIsactive(updateActive);
		
		int count=userMapper.updateByPrimaryKey(user);
		return count==0?false:true;
	}

	@Override
	public boolean updateUser(User currentUser) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		UserExample example =new UserExample();
		example.or().andUserIdEqualTo(currentUser.getId());
		int count=userMapper.updateByExampleSelective(currentUser, example);
		return count==0?false:true;
	}
	
	@Override
	public boolean insertUserStation(UserStationKey userStation){
		IUserStationMapper userStationMapper=sqlSession.getMapper(IUserStationMapper.class);
		int count=userStationMapper.insert(userStation);
		return count>0?true:false;
	}

	@Override
	public boolean updateUserStation(UserStationKey userStation) {
		IUserStationMapper userStationMapper=sqlSession.getMapper(IUserStationMapper.class);
		UserStationExample example =new UserStationExample();
		example.or().andUserIdEqualTo(userStation.getUserId());
		int count=userStationMapper.updateByExample(userStation, example);
		return count>0?true:false;
	}
	
	@Override
	public UserStationKey findUserStationByUserId(String userId) {
		IUserStationMapper userStationMapper=sqlSession.getMapper(IUserStationMapper.class);
		UserStationExample example =new UserStationExample();
		example.or().andUserIdEqualTo(userId);
		List<UserStationKey> userStationList = userStationMapper.selectByExample(example);
		return CollectionUtils.isNotEmpty(userStationList)?userStationList.get(0):null;
	}

	@Override
	public List<User> findAllUser() {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		UserExample example=new UserExample();
		example.or().andIsActiveEqualTo(true);
		List<User> userList = userMapper
				.selectByExample(example);
		return userList;
	}
	
	@Override
	public boolean deleteAllUser() {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		UserExample userExample=new UserExample();
		User user=new User();
       
		user.setIsactive(false);
		
		int count=userMapper.updateByExampleSelective(user, userExample);
		
		return count>0?true:false;
	}

	@Override
	public User findUserById(String id) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> findUserByZoneCode(String zoneCode) {
		Map<String, Object> params =new HashMap<String, Object>();  
		params.put("zoneCode", zoneCode);
		return sqlSession.selectList(IUserMapper.class.getName()+".queryUserByZoneCode", params);
	}

	@Override
	public List<User> findUserByOfficeCode(String officeCode) {
		Map<String, Object> params =new HashMap<String, Object>();  
		params.put("officeCode", officeCode);
		return sqlSession.selectList(IUserMapper.class.getName()+".queryUserByOfficeCode", params);
	}

}
