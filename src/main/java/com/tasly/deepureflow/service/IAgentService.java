package com.tasly.deepureflow.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.deepureflow.AgentFlow;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItem;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.AgentForExport;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.util.DeepureResult;

public interface IAgentService {
	public PageList<Agent> findAgentForPage(SearchForm searchForm,int curPageSize, int limit,String queryAgentId,String queryAgentTime,String queryAgentEndTime,String queryAgentName,String queryAgentType, String queryAgentVirtual);

	public boolean addAgent(String agentId, String agentName,
			String hierarchyId, String planDate, Integer isVirtual);

	public boolean delAgentByArray(String[] agentIds);

	public boolean editAgent(String agentId, String agentName,
			String hierarchyId, String planDate, Integer isVirtual);

	public List<Agent> queryAllAgent();
	
	PageList<AgentFlow> findAgentFlowForPage(SearchForm searchForm,
			int curPageSize, int limit, String planYear, String planMonth,
			String agentId,String agentName, String createBy);

	public List<AgentFlowShipmentItem> findAgentFlowShipmentItemById(String agentFlowId);

	public List<AgentFlowStockItem> findAgentFlowStockItemById(String agentFlowId);

	public boolean delAgentFlowByArray(String[] agentFlowIds);

	public boolean addAgentFlow(AgentFlow agentFlow);

	public DeepureResult importAgentExcel(String targetPath, MultipartFile upFile,User currentUser);

	public AgentFlow findAgentFlowById(String agentFlowId);

	public boolean updateAgentFlow(AgentFlow agentFlow,String[] delAgentFlowItemIds);

	public AgentFlow findAgentFlowByAgentIdAndDate(String agentId, String agentFlowDate);

	public List<AgentForExport> findAgentForExport(SearchForm searchForm) throws Exception;

	public List<Agent> findAgentByRole();

	//public List<Agent> queryAgentByRole();

}
