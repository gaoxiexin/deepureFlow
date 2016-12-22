package com.tasly.deepureflow.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.util.DeepureResult;

public interface IUserService {

	@GET
	@Path("/getUser")
	public User getUserId(String id);

	public User findUserByLoginName(String loginName);

	public PageList<User> findUserForPage(int curPageSize, int limit,String queryUserId,String queryUserName);

	public DeepureResult delUserByArray(String[] userIds);

	public DeepureResult addUser(String userName, String userPass);

	User loginUser(String userName, String userPass);

	public DeepureResult updateActive(String userId);

	public DeepureResult addUser(String userName, String userCode, String userPass, String roleId, String stationId);

	public DeepureResult resetPassword(String userId);
	
	public DeepureResult updatePassword(String userId,String password);

	public DeepureResult editUser(String userId, String userName, String roleId,String stationId);

	public List<User> quertAllUser();

	public DeepureResult importExcel(String targetPath, MultipartFile upFile);

	public DeepureResult validateUserId(String userId);

	public List<User> queryUserByRole();
}
