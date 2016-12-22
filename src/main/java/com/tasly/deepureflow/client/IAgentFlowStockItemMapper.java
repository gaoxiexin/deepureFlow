package com.tasly.deepureflow.client;

import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IAgentFlowStockItemMapper {
    int countByExample(AgentFlowStockItemExample example);

    int deleteByExample(AgentFlowStockItemExample example);

    int deleteByPrimaryKey(String agentFlowShipmentItemId);

    int insert(AgentFlowStockItem record);

    int insertSelective(AgentFlowStockItem record);

    List<AgentFlowStockItem> selectByExample(AgentFlowStockItemExample example);

    AgentFlowStockItem selectByPrimaryKey(String agentFlowShipmentItemId);

    int updateByExampleSelective(@Param("record") AgentFlowStockItem record, @Param("example") AgentFlowStockItemExample example);

    int updateByExample(@Param("record") AgentFlowStockItem record, @Param("example") AgentFlowStockItemExample example);

    int updateByPrimaryKeySelective(AgentFlowStockItem record);

    int updateByPrimaryKey(AgentFlowStockItem record);
}