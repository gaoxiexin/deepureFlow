package com.tasly.deepureflow.client;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.system.Channel;
import com.tasly.deepureflow.domain.system.ChannelExample;

public interface IChannelMapper {
    int countByExample(ChannelExample example);

    int deleteByExample(ChannelExample example);

    int deleteByPrimaryKey(String channelId);

    int insert(Channel record);

    int insertSelective(Channel record);

    List<Channel> selectByExample(ChannelExample example);

    Channel selectByPrimaryKey(String channelId);

    int updateByExampleSelective(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByExample(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
    List<Channel> queryChannelByName(Map<String,Object> map);
}