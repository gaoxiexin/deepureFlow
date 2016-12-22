package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.domain.user.UserStationKey;
import com.tasly.deepureflow.exception.BizException;

public interface IUserDao {
    
	public User getEntityByUserId(String id);
	
	public User findUserByName(String name);
	
	public List<User> findUserByCondition(PageBounds pageBounds,String queryUserId,String queryUserName);
	
	public boolean delUserById(String userId) throws BizException;

	public boolean insertUser(String userId,String userName, String userPass);

	User login(String userName, String encodedPass) throws BizException;

	public boolean updateUserActive(String userId);

	public boolean updateUser(User currentUser);

	boolean insertUserStation(UserStationKey userStation);

	boolean updateUserStation(UserStationKey userStation);

	public List<User> findAllUser();
	public boolean deleteAllUser();

	public User findUserById(String id);

	public List<User> findUserByZoneCode(String zoneCode);

	public List<User> findUserByOfficeCode(String officeCode);
	
	UserStationKey findUserStationByUserId(String userId);

}