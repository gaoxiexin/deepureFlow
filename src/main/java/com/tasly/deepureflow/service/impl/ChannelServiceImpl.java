package com.tasly.deepureflow.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.dao.IChannelDao;
import com.tasly.deepureflow.domain.system.Channel;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.service.IChannelService;
import com.tasly.deepureflow.util.DeepureResult;

@Service("channelService") 
public class ChannelServiceImpl implements IChannelService {
	private final Logger logger = Logger.getLogger(ChannelServiceImpl.class.getName());
	
	@Autowired
    private IChannelDao channelDao; 
	@Override
	public PageList<Channel> findChannelForPage(int curPageSize, int limit) {
		PageList<Channel> pageList=null;
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Channel>)this.channelDao.findChannelForPage(pageBounds);
		}
		return pageList;
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult addChannel(String channelName, String hierarchyId) {
		boolean isAdd = false;
		if(StringUtils.isNotEmpty(channelName)){
			isAdd = channelDao.insertChannel(channelName,hierarchyId);
		}
		return DeepureResult.addResult(isAdd);
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult delChannelByArray(Integer[] channelList) {
		boolean isDel=false;
		try{
			isDel=channelDao.deleteChannelByArray(channelList);
		}catch(Exception e){
			logger.error(ResultEnum.INNER_ERROR.getMsg()+":删除渠道出错",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return DeepureResult.delResult(isDel);
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult editChannel(Integer channelId, String channelName,
			String hierarchyId) {
		boolean isEdit = false;
		isEdit = channelDao.editChannel(channelId,channelName,hierarchyId);
		return DeepureResult.editResult(isEdit);
	}
	@Override
	public List<Channel> queryAllChannel() {
		return channelDao.findAllChannel();
	}
	
	@Override
	public PageList<Channel> queryChannelByName(int curPageSize, int limit,String ChannelName) {
		PageList<Channel> pageList=null;
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Channel>)this.channelDao.queryChannelByName(ChannelName,pageBounds);
		}
		return pageList;
	}
	@Override
	public DeepureResult validateName(String hierarchyId, String channelName, String channelId) {
		if(channelDao.validateName(hierarchyId,channelName,channelId)){
			return DeepureResult.success();
		}else{
			return DeepureResult.result(false, "渠道名称已存在");
		}
	}
	@Override
	public List<Channel> findChannelByHierarchyId(String hierarchyId) {
		return channelDao.findChannelByHierarchyId(hierarchyId);
	}

}
