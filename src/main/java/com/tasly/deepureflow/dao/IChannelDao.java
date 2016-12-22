package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.system.Channel;

public interface IChannelDao {
	
	public List<Channel> findChannelForPage(PageBounds pageBounds);

	public boolean insertChannel(String channelName, String hierarchyId);

	public boolean deleteChannelByArray(Integer[] channelList);

	public boolean editChannel(Integer channelId, String channelName,
			String hierarchyId);

	public List<Channel> findAllChannel();
	
	List<Channel> queryChannelByName(String channelName,PageBounds pageBounds);

	public boolean validateName(String hierarchyId, String channeName, String channelId);

	public List<Channel> findChannelByHierarchyId(String hierarchyId);

}