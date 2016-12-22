package com.tasly.deepureflow.service;


import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Channel;
import com.tasly.deepureflow.util.DeepureResult;

public interface IChannelService {
	public PageList<Channel> findChannelForPage(int curPageSize, int limit);

	public DeepureResult addChannel(String channelName, String hierarchyId);

	public DeepureResult delChannelByArray(Integer[] channelList);

	public DeepureResult editChannel(Integer channelId, String channelName,
			String hierarchyId);

	public List<Channel> queryAllChannel();
	
	public PageList<Channel> queryChannelByName(int curPageSize, int limit,String ChannelName);

	public DeepureResult validateName(String hierarchyId, String channeName, String channelId);

	public List<Channel> findChannelByHierarchyId(String hierarchyId);

}
