package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.deepureflow.AgentFlow;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItem;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.AgentForExport;
import com.tasly.deepureflow.dto.SearchForm;

public interface IAgentDao {
	
	public List<Agent> findAgentForPage(SearchForm searchForm,PageBounds pageBounds,String queryAgentId,String queryAgentTime,String queryAgentEndTime,String queryAgentName,String queryAgentType,String queryAgentVirtual);

	public boolean insertAgent(String agentId,
			String agentName, String hierarchyId, String planDate,
			Integer isVirtual);

	public boolean deleteAgentByArray(String[] agentIds);

	public boolean updateAgent(String agentId,
			String agentName, String hierarchyId, String planDate,
			Integer isVirtual);

	public List<Agent> findAllAgent();

	public List<AgentFlow> findAgentFlowForPage(SearchForm searchForm, PageBounds pageBounds, String planYear,
			String planMonth, String agentId,String agentName, String createBy);

	public List<AgentFlowShipmentItem> findAgentFlowShipmentItemById(String agentFlowId);

	public List<AgentFlowStockItem> findAgentFlowStockItemById(String agentFlowId);

	public boolean delAgentFlowByArray(String[] agentFlowIds);

	public boolean delAgentFlowShipmentItemByArray(String[] agentFlowIds);

	public boolean delAgentFlowStockItemByArray(String[] agentFlowIds);

	public boolean insertAgentFlow(AgentFlow agentFlow);

	public boolean insertAgentFlowShipmentItem(AgentFlowShipmentItem shipmentItem);

	public boolean insertAgentFlowStockItem(AgentFlowStockItem stockItem);

	public Agent findAgentByCode(String erpCode);

	public boolean delAllAgent();
	
	boolean updateAgents(Agent agent);

	public boolean insertAgent(Agent agent);

	public AgentFlow queryAgentFlowById(String agentFlowId);

	public AgentFlow queryAgentFlowByAgentIdAndDate(String agentFlowId, String agentFlowDate);

	public List<AgentForExport> findAgentForExport(SearchForm searchForm);

	public void insertExcelAgent(List<Agent> addList);

	public void updateExcelAgent(List<Agent> updateList);

	public List<Agent> findAgentByUser(String id);

	public Integer counAgentFlowItemById(String agentFlowId);

	public void updateAgentFlowShipmentItem(AgentFlowShipmentItem agentFlowShipmentItem);
}