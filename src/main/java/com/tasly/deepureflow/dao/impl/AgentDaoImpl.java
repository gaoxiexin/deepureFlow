package com.tasly.deepureflow.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IAgentFlowMapper;
import com.tasly.deepureflow.client.IAgentFlowShipmentItemMapper;
import com.tasly.deepureflow.client.IAgentFlowStockItemMapper;
import com.tasly.deepureflow.client.IAgentMapper;
import com.tasly.deepureflow.dao.IAgentDao;
import com.tasly.deepureflow.domain.deepureflow.AgentFlow;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowExample;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItemExample;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItemExample;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.AgentExample;
import com.tasly.deepureflow.domain.user.AgentForExport;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.enums.AgentStatus;
import com.tasly.deepureflow.enums.DeleteStatus;

@Repository("agentDao")
public class AgentDaoImpl implements IAgentDao{
	private final Logger logger = Logger.getLogger(AgentDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public List<Agent> findAgentForPage(SearchForm searchForm,PageBounds pageBounds,String queryAgentId,String queryAgentStartTime,String queryAgentEndTime,String queryAgentName,String queryAgentType,String queryAgentVirtual) {
		 Map<String, Object> params =new HashMap<String, Object>();
		 params.put("queryAgentId", queryAgentId);
		 params.put("queryAgentStartTime", queryAgentStartTime);
		 params.put("queryAgentEndTime", queryAgentEndTime);
		 params.put("queryAgentName", queryAgentName);
		 params.put("queryAgentType", queryAgentType);
		 params.put("queryAgentVirtual", queryAgentVirtual);
		 
		 params.put("officeCode", searchForm.getOfficeCode());
		 params.put("zoneCode", searchForm.getZoneCode());
		 params.put("userId", searchForm.getUserId());
		 return sqlSession.selectList(searchForm.getQueryId(), params, pageBounds);
	}

	@Override
	public boolean insertAgent(String agentId,
			String agentName, String hierarchyId, String planDate,
			Integer isVirtual) {
		Date now=new Date();
		Agent agent=new Agent();
		agent.setAgentId(agentId);
		agent.setAgentName(agentName);
		agent.setAgentStatus(String.valueOf(AgentStatus.UNDEVELOPED.getValue()));
		agent.setCreateDate(now);
		agent.setHierarchyId(hierarchyId);
		agent.setIsDelete(DeleteStatus.NODELETE.getValue());
		agent.setIsVirtual(isVirtual);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(planDate);
			agent.setPlanDate(date);
		} catch (ParseException e) {
			logger.error("转换日期报错"+e.getMessage(),e);
		}
		
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
		int count=agentMapper.insertSelective(agent);
		return count>0?true:false;
	}

	@Override
	public boolean deleteAgentByArray(String[] agentIds) {
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
		AgentExample agentExample=new AgentExample();
		agentExample.or().andAgentIdIn(Arrays.asList(agentIds));
		Agent agent=new Agent();
		agent.setIsDelete(1);
		
		int count=agentMapper.updateByExampleSelective(agent, agentExample);
		return count>0?true:false;
	}

	@Override
	public boolean updateAgent(String agentId,
			String agentName, String hierarchyId, String planDate,
			Integer isVirtual) {
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
		AgentExample agentExample=new AgentExample();
		agentExample.or().andAgentIdEqualTo(agentId);
		
		Agent agent=new Agent();
		agent.setAgentId(agentId);
		agent.setAgentName(agentName);
		agent.setHierarchyId(hierarchyId);
		agent.setIsVirtual(isVirtual);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(planDate);
			agent.setPlanDate(date);
		} catch (ParseException e) {
			logger.error("转换日期报错"+e.getMessage(),e);
		}
		
		int count=agentMapper.updateByExampleSelective(agent, agentExample);
		
		return count>0?true:false;
	}

	@Override
	public List<Agent> findAllAgent() {
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
		AgentExample agentExample=new AgentExample();
		agentExample.or().andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Agent> agentList = agentMapper
				.selectByExample(agentExample);
		return agentList;
	}

	@Override
	public List<AgentFlow> findAgentFlowForPage(SearchForm searchForm, PageBounds pageBounds, String planYear,
			String planMonth, String agentId, String agentName,String createBy) {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("planYear", planYear);
		params.put("agentId", agentId);
		params.put("agentName", agentName);
		params.put("planMonth", planMonth);
		params.put("createBy", createBy);

		params.put("userId", searchForm.getUserId());
		params.put("officeCode", searchForm.getOfficeCode());
		params.put("zoneCode", searchForm.getZoneCode());
		return sqlSession.selectList(searchForm.getQueryId(), params, pageBounds);
	}

	@Override
	public List<AgentFlowShipmentItem> findAgentFlowShipmentItemById(String agentFlowId) {
		
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("agentFlowId", agentFlowId);
		List<AgentFlowShipmentItem> shipmentItemList = sqlSession.selectList(IAgentFlowShipmentItemMapper.class.getName()+".queryAgentFlowShipmentItem", params);
		return shipmentItemList;
	}

	@Override
	public List<AgentFlowStockItem> findAgentFlowStockItemById(String agentFlowId) {
		IAgentFlowStockItemMapper agentFlowStockItemMapper=sqlSession.getMapper(IAgentFlowStockItemMapper.class);
		AgentFlowStockItemExample example=new AgentFlowStockItemExample();
		example.or().andAgentFlowIdEqualTo(agentFlowId);
		List<AgentFlowStockItem> agentFlowStockItemList=agentFlowStockItemMapper.selectByExample(example);
		return agentFlowStockItemList;
	}

	@Override
	public boolean delAgentFlowByArray(String[] agentFlowIds) {
		IAgentFlowMapper agentFlowMapper = sqlSession.getMapper(IAgentFlowMapper.class);
		AgentFlowExample agentFlowExample=new AgentFlowExample();
		agentFlowExample.or().andAgentFlowIdIn(Arrays.asList(agentFlowIds));
		AgentFlow agentFlow=new AgentFlow();
		agentFlow.setAgentFlowStatus(0);
		
		int count=agentFlowMapper.updateByExampleSelective(agentFlow, agentFlowExample);
		return count>0?true:false;
	}

	@Override
	public boolean delAgentFlowShipmentItemByArray(String[] agentFlowItemIds) {
		IAgentFlowShipmentItemMapper agentFlowShipmentItemMapper=sqlSession.getMapper(IAgentFlowShipmentItemMapper.class);
		AgentFlowShipmentItemExample example=new AgentFlowShipmentItemExample();
		if(agentFlowItemIds.length>0){
			example.or().andAgentFlowShipmentItemIdIn(Arrays.asList(agentFlowItemIds));
			int count=agentFlowShipmentItemMapper.deleteByExample(example);
			return count>0?true:false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean delAgentFlowStockItemByArray(String[] agentFlowIds) {
		IAgentFlowStockItemMapper agentFlowStockItemMapper=sqlSession.getMapper(IAgentFlowStockItemMapper.class);
		AgentFlowStockItemExample example=new AgentFlowStockItemExample();
		example.or().andAgentFlowIdIn(Arrays.asList(agentFlowIds));
		int count=agentFlowStockItemMapper.deleteByExample(example);
		return count>0?true:false;
	}

	@Override
	public boolean insertAgentFlow(AgentFlow agentFlow) {
		IAgentFlowMapper agentFlowMapper=sqlSession.getMapper(IAgentFlowMapper.class);
		int count=agentFlowMapper.insertSelective(agentFlow);

		return count>0?true:false;
	}

	@Override
	public boolean insertAgentFlowShipmentItem(AgentFlowShipmentItem shipmentItem) {
		IAgentFlowShipmentItemMapper agentFlowShipmentItemMapper=sqlSession.getMapper(IAgentFlowShipmentItemMapper.class);
		int count=agentFlowShipmentItemMapper.insertSelective(shipmentItem);
		return count>0?true:false;
	}

	@Override
	public boolean insertAgentFlowStockItem(AgentFlowStockItem stockItem) {
		IAgentFlowStockItemMapper agentFlowStockItemMapper=sqlSession.getMapper(IAgentFlowStockItemMapper.class);
		int count=agentFlowStockItemMapper.insertSelective(stockItem);
		return count>0?true:false;
	}

	@Override
	public Agent findAgentByCode(String erpCode) {
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
        AgentExample agentExample=new AgentExample();
        
        agentExample.createCriteria().andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue()).andErpCodeEqualTo(erpCode);
		List<Agent> agentList = agentMapper.selectByExample(agentExample);

		return CollectionUtils.isNotEmpty(agentList)?agentList.get(0):null;
	}

	@Override
	public boolean delAllAgent() {
		List<Agent> agentList=this.findAllAgent();
		if(CollectionUtils.isEmpty(agentList)){
			return true;
		}
		
        IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
        AgentExample agentExample=new AgentExample();
        Agent agent = new Agent();
        agent.setIsDelete(1);
        int count = agentMapper.updateByExampleSelective(agent,agentExample);
        return count>0?true:false;
	}

	@Override
	public boolean updateAgents(Agent agent) {
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
        AgentExample agentExample=new AgentExample();
        agentExample.or().andAgentIdEqualTo(agent.getAgentId());
        int count = agentMapper.updateByExampleSelective(agent,agentExample );
        return count>0?true:false;

	}

	@Override
	public boolean insertAgent(Agent agent) {
		IAgentMapper agentMapper = sqlSession.getMapper(IAgentMapper.class);
         int count = agentMapper.insertSelective(agent);
 		return count>0?true:false;

	}

	@Override
	public AgentFlow queryAgentFlowById(String agentFlowId) {
		IAgentFlowMapper agentFlowMapper=sqlSession.getMapper(IAgentFlowMapper.class);
		AgentFlowExample example=new AgentFlowExample();
		example.or().andAgentFlowIdEqualTo(agentFlowId);
		List<AgentFlow> agentFlowList=agentFlowMapper.selectByExample(example);
		return CollectionUtils.isNotEmpty(agentFlowList)?agentFlowList.get(0):null;
	}

	@Override
	public AgentFlow queryAgentFlowByAgentIdAndDate(String agentId, String agentFlowDate) {
		Map<String, Object> params =new HashMap<String, Object>();
		String planYear = agentFlowDate.substring(0, 4);
		String planMonth = agentFlowDate.substring(5, 7);
		params.put("planYear", planYear);
		params.put("agentId", agentId);
		params.put("planMonth", planMonth);
		params.put("createBy", "");
		List<AgentFlow> agentFlowList = sqlSession.selectList("com.tasly.deepureflow.client.IAgentFlowMapper.queryAllAgentFlow", params);
		return agentFlowList.size()!=0?agentFlowList.get(0):null;
	}

	@Override
	public List<AgentForExport> findAgentForExport(SearchForm searchForm) {
		 Map<String, Object> params =new HashMap<String, Object>();
		 params.put("officeCode", searchForm.getOfficeCode());
		 params.put("zoneCode", searchForm.getZoneCode());
		 params.put("userId", searchForm.getUserId());
		 return sqlSession.selectList(searchForm.getQueryId(), params);
	}

	@Override
	public void insertExcelAgent(List<Agent> addList) {
		sqlSession.selectList("com.tasly.deepureflow.client.IAgentMapper.insertExcelAgent", addList);
	}

	@Override
	public void updateExcelAgent(List<Agent> updateList) {
		sqlSession.selectList("com.tasly.deepureflow.client.IAgentMapper.updateExcelAgent", updateList);
	}

	@Override
	public List<Agent> findAgentByUser(String id) {
		Map<String, Object> params =new HashMap<String, Object>();  
		 params.put("userId", id);
		 return sqlSession.selectList(IAgentMapper.class.getName()+".queryAgentForUser", params);
	}

	@Override
	public Integer counAgentFlowItemById(String agentFlowId) {
		Map<String, Object> params =new HashMap<String, Object>();  
		 params.put("agentFlowId", agentFlowId);
		String numStr = sqlSession.selectOne(IAgentFlowShipmentItemMapper.class.getName()+".counAgentFlowItemById", params);
		if(null==numStr){
			return 0;
		}
		return Integer.parseInt(numStr)+1;
		/*AgentFlowShipmentItemExample example=new AgentFlowShipmentItemExample();
		example.or().andAgentFlowIdEqualTo(agentFlowId);
		IAgentFlowShipmentItemMapper agentFlowShipmentItemMapper=sqlSession.getMapper(IAgentFlowShipmentItemMapper.class);
		return agentFlowShipmentItemMapper.countByExample(example);*/
	}

	@Override
	public void updateAgentFlowShipmentItem(AgentFlowShipmentItem agentFlowShipmentItem) {
		IAgentFlowShipmentItemMapper agentFlowShipmentItemMapper=sqlSession.getMapper(IAgentFlowShipmentItemMapper.class);
		AgentFlowShipmentItemExample example=new AgentFlowShipmentItemExample();
		example.or().andAgentFlowShipmentItemIdEqualTo(agentFlowShipmentItem.getAgentFlowShipmentItemId());
		agentFlowShipmentItemMapper.updateByExampleSelective(agentFlowShipmentItem, example);
		
	}

}
