package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IAgentFlowShipmentItemMapper {
    int countByExample(AgentFlowShipmentItemExample example);

    int deleteByExample(AgentFlowShipmentItemExample example);

    int deleteByPrimaryKey(String agentFlowShipmentItemId);

    int insert(AgentFlowShipmentItem record);

    int insertSelective(AgentFlowShipmentItem record);

    List<AgentFlowShipmentItem> selectByExample(AgentFlowShipmentItemExample example);

    AgentFlowShipmentItem selectByPrimaryKey(String agentFlowShipmentItemId);

    int updateByExampleSelective(@Param("record") AgentFlowShipmentItem record, @Param("example") AgentFlowShipmentItemExample example);

    int updateByExample(@Param("record") AgentFlowShipmentItem record, @Param("example") AgentFlowShipmentItemExample example);

    int updateByPrimaryKeySelective(AgentFlowShipmentItem record);

    int updateByPrimaryKey(AgentFlowShipmentItem record);
}