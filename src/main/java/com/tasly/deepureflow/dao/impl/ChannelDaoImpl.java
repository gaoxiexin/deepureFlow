package com.tasly.deepureflow.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IChannelMapper;
import com.tasly.deepureflow.dao.IChannelDao;
import com.tasly.deepureflow.domain.system.Channel;
import com.tasly.deepureflow.domain.system.ChannelExample;
import com.tasly.deepureflow.enums.DeleteStatus;

@Repository("channelDao")
public class ChannelDaoImpl implements IChannelDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ChannelDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public List<Channel> findChannelForPage(PageBounds pageBounds) {
		 Map<String, Object> params =new HashMap<String, Object>();  
		 return sqlSession.selectList(IChannelMapper.class.getName()+".channelListForPage", params, pageBounds);
	}

	@Override
	public boolean insertChannel(String channelName, String hierarchyId) {
		IChannelMapper channelMapper = sqlSession.getMapper(IChannelMapper.class);
		
		Channel channel= new Channel();
		channel.setChannelName(channelName);
		channel.setHierarchyId(hierarchyId);
		
		int count=channelMapper.insertSelective(channel);
		return count>0?true:false;
	}

	@Override
	public boolean deleteChannelByArray(Integer[] channelList) {
		IChannelMapper channelMapper = sqlSession.getMapper(IChannelMapper.class);
		ChannelExample channelExample=new ChannelExample();
		channelExample.or().andChannelIdIn(Arrays.asList(channelList));
		Channel channel=new Channel();
		channel.setIsDelete(1);
		
		int count=channelMapper.updateByExampleSelective(channel, channelExample);
		return count>0?true:false;
	}

	@Override
	public boolean editChannel(Integer channelId, String channelName,
			String hierarchyId) {
		IChannelMapper channelMapper = sqlSession.getMapper(IChannelMapper.class);
		Channel channel=new Channel();
		channel.setChannelId(String.valueOf(channelId));
		channel.setChannelName(channelName);
		channel.setHierarchyId(hierarchyId);
		channel.setIsDelete(0);
		int count=channelMapper.updateByPrimaryKey(channel);
		return count>0?true:false;
	}

	@Override
	public List<Channel> findAllChannel() {
		IChannelMapper channelMapper = sqlSession.getMapper(IChannelMapper.class);
		ChannelExample channelExample=new ChannelExample();
		channelExample.or().andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Channel> channelList = channelMapper
				.selectByExample(channelExample);
		return channelList;
	}
	@Override
	public List<Channel> queryChannelByName(String channelName,PageBounds pageBounds){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelName", channelName);
		return sqlSession.selectList(IChannelMapper.class.getName()
				+ ".queryChannelByName", params, pageBounds);
		
	}

	@Override
	public boolean validateName(String hierarchyId, String channelName, String channelId) {
		ChannelExample channelExample=new ChannelExample();
		IChannelMapper channelMapper = sqlSession
				.getMapper(IChannelMapper.class);
		if(!"".equals(channelId)&&channelId!=null){
			channelExample.or().andChannelNameEqualTo(channelName).andChannelIdNotEqualTo(Integer.valueOf(channelId)).andHierarchyIdEqualTo(Integer.valueOf(hierarchyId)).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
			List<Channel> channelList = channelMapper.selectByExample(channelExample);
			if(CollectionUtils.isNotEmpty(channelList)){
				return false;
			}else{
				return true;
			}
		}else{
			channelExample.or().andChannelNameEqualTo(channelName).andHierarchyIdEqualTo(Integer.valueOf(hierarchyId)).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
			List<Channel> channelList = channelMapper.selectByExample(channelExample);
			if(CollectionUtils.isNotEmpty(channelList)){
				return false;
			}else{
				return true;
			}
		}
	}

	@Override
	public List<Channel> findChannelByHierarchyId(String hierarchyId) {
		ChannelExample channelExample=new ChannelExample();
		IChannelMapper channelMapper = sqlSession.getMapper(IChannelMapper.class);
		if(hierarchyId!=""&&hierarchyId!=null){
			channelExample.or().andHierarchyIdEqualTo(Integer.parseInt(hierarchyId)).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		}else{
			return null;
		}
		
		return channelMapper.selectByExample(channelExample);
	}

}
