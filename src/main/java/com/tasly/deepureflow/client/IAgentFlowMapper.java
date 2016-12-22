package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.deepureflow.AgentFlow;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IAgentFlowMapper {
    int countByExample(AgentFlowExample example);

    int deleteByExample(AgentFlowExample example);

    int deleteByPrimaryKey(String agentFlowId);

    int insert(AgentFlow record);

    int insertSelective(AgentFlow record);

    List<AgentFlow> selectByExample(AgentFlowExample example);

    AgentFlow selectByPrimaryKey(String agentFlowId);

    int updateByExampleSelective(@Param("record") AgentFlow record, @Param("example") AgentFlowExample example);

    int updateByExample(@Param("record") AgentFlow record, @Param("example") AgentFlowExample example);

    int updateByPrimaryKeySelective(AgentFlow record);

    int updateByPrimaryKey(AgentFlow record);
}