package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.user.AgentTerminalExample;
import com.tasly.deepureflow.domain.user.AgentTerminalKey;


public interface IAgentTerminalMapper {
    int countByExample(AgentTerminalExample example);

    int deleteByExample(AgentTerminalExample example);

    int deleteByPrimaryKey(AgentTerminalKey key);

    int insert(AgentTerminalKey record);

    int insertSelective(AgentTerminalKey record);

    List<AgentTerminalKey> selectByExample(AgentTerminalExample example);

    int updateByExampleSelective(@Param("record") AgentTerminalKey record, @Param("example") AgentTerminalExample example);

    int updateByExample(@Param("record") AgentTerminalKey record, @Param("example") AgentTerminalExample example);
}