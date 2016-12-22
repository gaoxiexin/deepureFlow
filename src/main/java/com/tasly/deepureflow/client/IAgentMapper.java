package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.AgentExample;

public interface IAgentMapper {
    int countByExample(AgentExample example);

    int deleteByExample(AgentExample example);

    int deleteByPrimaryKey(String agentId);

    int insert(Agent record);

    int insertSelective(Agent record);

    List<Agent> selectByExample(AgentExample example);

    Agent selectByPrimaryKey(String agentId);

    int updateByExampleSelective(@Param("record") Agent record, @Param("example") AgentExample example);

    int updateByExample(@Param("record") Agent record, @Param("example") AgentExample example);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);
    
    void insertExcelAgent(@Param("agentList") List<Agent> agentList);
    
    void updateExcelAgent(@Param("agentList") List<Agent> agentList);
}