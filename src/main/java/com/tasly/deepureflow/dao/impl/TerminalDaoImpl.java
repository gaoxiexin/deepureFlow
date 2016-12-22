package com.tasly.deepureflow.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IAgentTerminalMapper;
import com.tasly.deepureflow.client.IPlanItemMapper;
import com.tasly.deepureflow.client.ISalePlanMapper;
import com.tasly.deepureflow.client.IStationMapper;
import com.tasly.deepureflow.client.ITerminalFlowItemMapper;
import com.tasly.deepureflow.client.ITerminalFlowMapper;
import com.tasly.deepureflow.client.ITerminalMapper;
import com.tasly.deepureflow.dao.ITerminalDao;
import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.PlanItemExample;
import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.domain.deepureflow.SalePlanExample;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlow;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowExample;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItemExample;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.system.StationExample;
import com.tasly.deepureflow.domain.user.AgentTerminalExample;
import com.tasly.deepureflow.domain.user.AgentTerminalKey;
import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.domain.user.TerminalExample;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.enums.AgentStatus;
import com.tasly.deepureflow.enums.DeleteStatus;

@Repository("terminalDao")
public class TerminalDaoImpl implements ITerminalDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(TerminalDaoImpl.class
			.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	@Override
	public List<Terminal> findTerminalForPage(SearchForm searchForm,
			PageBounds pageBounds, String queryTerminalCode,
			String queryTerminalName, String queryAgent,
			String queryTerminalStatus, String queryTerminalType,
			String queryChannelId, String queryHierarchyId) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("queryTerminalCode", queryTerminalCode);
		params.put("queryTerminalName", queryTerminalName);
		params.put("queryAgent", queryAgent);
		params.put("queryTerminalStatus", queryTerminalStatus);
		params.put("queryTerminalType", queryTerminalType);
		params.put("queryChannelId", queryChannelId);
		params.put("queryHierarchyId", queryHierarchyId);
		
		params.put("userId", searchForm.getUserId());
		params.put("officeCode", searchForm.getOfficeCode());
		params.put("zoneCode", searchForm.getZoneCode());
		return sqlSession.selectList(searchForm.getQueryId(), params,
				pageBounds);
	}

	@Override
	public boolean insertAgentTerminalKey(AgentTerminalKey agentTerminalKey) {
		IAgentTerminalMapper agentTerminalMapper = sqlSession
				.getMapper(IAgentTerminalMapper.class);
		int count = agentTerminalMapper.insertSelective(agentTerminalKey);
		return count > 0 ? true : false;
	}

	@Override
	public boolean insertTerminal(Terminal terminal) {
		Date now = new Date();
		terminal.setCreateDate(now);
		if (StringUtils.isNotEmpty(terminal.getErpCode())) {
			terminal.setJoinDate(now);
		} else if (terminal.getTerminalStatus() == 1
				&& terminal.getTerminalType() == 1) {
			terminal.setJoinDate(now);
		}
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		int count = terminalMapper.insertSelective(terminal);

		return count > 0 ? true : false;
	}

	@Override
	public boolean updateAgentTerminalKey(AgentTerminalKey agentTerminalKey) {
		IAgentTerminalMapper agentTerminalMapper = sqlSession
				.getMapper(IAgentTerminalMapper.class);
		AgentTerminalExample example = new AgentTerminalExample();
		example.or().andTerminalCodeEqualTo(agentTerminalKey.getTerminalCode());
		int count = agentTerminalMapper.updateByExampleSelective(
				agentTerminalKey, example);
		return count > 0 ? true : false;
	}

	@Override
	public boolean updateTerminal(Terminal terminal) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.or().andTerminalIdEqualTo(terminal.getTerminalId());
		int count = terminalMapper.updateByExampleSelective(terminal, example);

		return count > 0 ? true : false;
	}

	@Override
	public boolean delTerminalByArray(String[] terminalIds) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.or().andTerminalIdIn(Arrays.asList(terminalIds));
		Terminal terminal = new Terminal();
		terminal.setIsDelete(1);

		int count = terminalMapper.updateByExampleSelective(terminal, example);
		return count > 0 ? true : false;
	}

	@Override
	public boolean delAllTerminal(Integer terminalType) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample terminalExample = new TerminalExample();
		Terminal terminal = new Terminal();
		if (terminalType != -1) {
			terminalExample.or().andTerminalTypeEqualTo(terminalType);
		}
		terminal.setIsDelete(1);
		int count = terminalMapper.updateByExampleSelective(terminal,
				terminalExample);
		return count > 0 ? true : false;
	}

	@Override
	public boolean turnTerminalByArray(String[] terminalIds) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.or().andTerminalIdIn(Arrays.asList(terminalIds));
		Terminal terminal = new Terminal();
		terminal.setTerminalStatus(AgentStatus.DEVELOPED.getValue());

		int count = terminalMapper.updateByExampleSelective(terminal, example);
		return count > 0 ? true : false;
	}

	/**
	 * 终端销售计划分页查询
	 */
	@Override
	public List<SalePlan> findTerminalSalePlanForPage(SearchForm searchForm,
			PageBounds pageBounds, String planYear, String agentId,
			String terminalId, String zoneCode, String planMonth,
			String stationCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("planYear", planYear);
		params.put("agentId", agentId);
		params.put("terminalId", terminalId);
		params.put("zoneCode_q", zoneCode);
		params.put("planMonth", planMonth);
		params.put("stationCode", stationCode);

		params.put("userId", searchForm.getUserId());
		params.put("officeCode", searchForm.getOfficeCode());
		params.put("zoneCode", searchForm.getZoneCode());
		return sqlSession.selectList(searchForm.getQueryId(), params,
				pageBounds);
	}

	@Override
	public List<Terminal> findAllTerminal() {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample terminalExample = new TerminalExample();
		terminalExample.or().andIsDeleteEqualTo(
				DeleteStatus.NODELETE.getValue());
		List<Terminal> terminalList = terminalMapper
				.selectByExample(terminalExample);
		return terminalList;
	}

	@Override
	public Terminal findTerminalById(String terminalId) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);

		return terminalMapper.selectByPrimaryKey(terminalId);
	}

	@Override
	public Terminal findTerminalByCode(String erpCode) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample terminalExample = new TerminalExample();
		terminalExample.createCriteria()
				.andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue())
				.andErpCodeEqualTo(erpCode);
		List<Terminal> terminalList = terminalMapper
				.selectByExample(terminalExample);

		return CollectionUtils.isNotEmpty(terminalList) ? terminalList.get(0)
				: null;
	}

	@Override
	public Integer findStationIdById(String terminalId) {

		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		String stationCode = terminalMapper.selectByPrimaryKey(terminalId)
				.getStationCode();
		IStationMapper stationMapper = sqlSession
				.getMapper(IStationMapper.class);
		StationExample stationExample = new StationExample();
		stationExample.createCriteria().andStationCodeEqualTo(stationCode)
				.andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Station> stationList = stationMapper
				.selectByExample(stationExample);
		if (!stationList.isEmpty()) {
			return stationList.get(0).getStationId();
		}
		return 0;
	}

	@Override
	public String findHierarchyIdById(String terminalId) {

		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		return terminalMapper.selectByPrimaryKey(terminalId).getHierarchyId();
	}

	@Override
	public String findChannelIdById(String terminalId) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		return terminalMapper.selectByPrimaryKey(terminalId).getChannelId();
	}

	@Override
	public String findAgentIdById(String terminalId) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		return terminalMapper.selectByPrimaryKey(terminalId).getAgentId();
	}

	@Override
	public boolean insertSalePlan(SalePlan salePlan) {
		Date now = new Date();
		salePlan.setCreateAt(now);

		ISalePlanMapper salePlanMapper = sqlSession
				.getMapper(ISalePlanMapper.class);
		int count = salePlanMapper.insertSelective(salePlan);

		return count > 0 ? true : false;
	}

	@Override
	public boolean insertSalePlanItem(PlanItem planItem) {
		IPlanItemMapper planItemMapper = sqlSession
				.getMapper(IPlanItemMapper.class);
		int count = planItemMapper.insertSelective(planItem);
		return count > 0 ? true : false;
	}

	@Override
	public boolean delSalePlanByArray(String[] salePlanIds) {
		ISalePlanMapper salePlanMapper = sqlSession
				.getMapper(ISalePlanMapper.class);
		SalePlanExample example = new SalePlanExample();
		example.or().andSalePlanIdIn(Arrays.asList(salePlanIds));
		SalePlan salePlan = new SalePlan();
		salePlan.setSalePlanStatus(1);

		int count = salePlanMapper.updateByExampleSelective(salePlan, example);
		return count > 0 ? true : false;
	}

	@Override
	public boolean delPlanItemByArray(String[] salePlanIds) {
		IPlanItemMapper planItemMapper = sqlSession
				.getMapper(IPlanItemMapper.class);
		PlanItemExample example = new PlanItemExample();
		example.or().andSalePlanIdIn(Arrays.asList(salePlanIds));
		int count = planItemMapper.deleteByExample(example);
		return count > 0 ? true : false;
	}

	@Override
	public List<PlanItem> findPlanItemById(String salePlanId) {
		IPlanItemMapper planItemMapper = sqlSession
				.getMapper(IPlanItemMapper.class);
		PlanItemExample example = new PlanItemExample();
		example.or().andSalePlanIdEqualTo(salePlanId);
		List<PlanItem> planItemList = planItemMapper.selectByExample(example);
		return planItemList;
	}

	@Override
	public SalePlan findSalePlanById(String salePlanId) {
		ISalePlanMapper salePlanMapper = sqlSession
				.getMapper(ISalePlanMapper.class);
		SalePlanExample example = new SalePlanExample();
		example.or().andSalePlanIdEqualTo(salePlanId);
		return salePlanMapper.selectByExample(example).get(0);
	}

	@Override
	public boolean updateSalePlan(SalePlan salePlan) {
		ISalePlanMapper salePlanMapper = sqlSession
				.getMapper(ISalePlanMapper.class);
		SalePlanExample example = new SalePlanExample();
		example.or().andSalePlanIdEqualTo(salePlan.getSalePlanId());
		int count = salePlanMapper.updateByExampleSelective(salePlan, example);

		return count > 0 ? true : false;
	}

	@Override
	public List<SalePlan> findTerminalFlowForPage(SearchForm searchForm,
			PageBounds pageBounds, String planYear, String planMonth,
			String terminalId, String terminalName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("planYear", planYear);
		params.put("terminalId", terminalId);
		params.put("planMonth", planMonth);
		params.put("terminalName", terminalName);

		params.put("userId", searchForm.getUserId());
		params.put("officeCode", searchForm.getOfficeCode());
		params.put("zoneCode", searchForm.getZoneCode());

		return sqlSession.selectList(searchForm.getQueryId(), params,
				pageBounds);
	}

	@Override
	public boolean delTerminalFlowByArray(String[] terminalFlowIds) {
		ITerminalFlowMapper terminalFlowMapper = sqlSession
				.getMapper(ITerminalFlowMapper.class);
		TerminalFlowExample example = new TerminalFlowExample();
		example.or().andTerminalFlowIdIn(Arrays.asList(terminalFlowIds));
		TerminalFlow terminalFlow = new TerminalFlow();
		terminalFlow.setTerminalFlowStatus(1);

		int count = terminalFlowMapper.updateByExampleSelective(terminalFlow,
				example);
		return count > 0 ? true : false;
	}

	@Override
	public boolean insertTerminalFlow(TerminalFlow terminalFlow) {
		Date now = new Date();
		terminalFlow.setCreateAt(now);
		String terminalId = terminalFlow.getTerminalId();
		if (terminalFlow != null && !terminalId.isEmpty()) {
			Terminal terminal = this.findTerminalById(terminalId);
			terminalFlow.setAgentId(terminal.getAgentId());
		} else {
			return false;
		}

		ITerminalFlowMapper terminalFlowMapper = sqlSession
				.getMapper(ITerminalFlowMapper.class);
		int count = terminalFlowMapper.insertSelective(terminalFlow);

		return count > 0 ? true : false;
	}

	@Override
	public boolean insertTerminalFlowItem(TerminalFlowItem terminalFlowItem) {
		ITerminalFlowItemMapper itemMapper = sqlSession
				.getMapper(ITerminalFlowItemMapper.class);
		int count = itemMapper.insertSelective(terminalFlowItem);
		return count > 0 ? true : false;
	}

	@Override
	public List<TerminalFlowItem> findTerminalFlowItemById(String terminalFlowId) {
		ITerminalFlowItemMapper itemMapper = sqlSession
				.getMapper(ITerminalFlowItemMapper.class);
		TerminalFlowItemExample example = new TerminalFlowItemExample();
		example.or().andTerminalFlowIdEqualTo(terminalFlowId);
		List<TerminalFlowItem> itemList = itemMapper.selectByExample(example);
		return itemList;
	}

	@Override
	public TerminalFlow queryTerminalFlowById(String terminalFlowId) {
		ITerminalFlowMapper terminalFlowMapper = sqlSession
				.getMapper(ITerminalFlowMapper.class);
		TerminalFlowExample example = new TerminalFlowExample();
		example.or().andTerminalFlowIdEqualTo(terminalFlowId);
		List<TerminalFlow> terminalFlowList = terminalFlowMapper
				.selectByExample(example);
		return CollectionUtils.isNotEmpty(terminalFlowList) ? terminalFlowList
				.get(0) : null;
	}

	@Override
	public boolean delTerminalFlowItemByArray(String[] terminalFlowItemIds) {
		ITerminalFlowItemMapper itemMapper = sqlSession
				.getMapper(ITerminalFlowItemMapper.class);
		TerminalFlowItemExample example = new TerminalFlowItemExample();
		example.or().andTerminalFlowIdIn(Arrays.asList(terminalFlowItemIds));
		int count = itemMapper.deleteByExample(example);
		return count > 0 ? true : false;
	}

	@Override
	public SalePlan querySalePlanByTerminalIdAndDate(String terminalId,
			String planMonth) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalId", terminalId);
		params.put("planMonth", planMonth);
		List<SalePlan> salePlanList = sqlSession.selectList(
				ISalePlanMapper.class.getName()
						+ ".findSalePlanByTerminalIdAndDate", params);
		if (CollectionUtils.isNotEmpty(salePlanList)) {
			return salePlanList.get(0);
		}
		return null;
	}

	@Override
	public TerminalFlow queryTerminalFlowByTerminalIdAndDate(String terminalId,
			String planMonth) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalId", terminalId);
		params.put("planMonth", planMonth);
		List<TerminalFlow> terminalFlowList = sqlSession.selectList(
				ITerminalFlowMapper.class.getName()
						+ ".findTerminalFlowByTerminalIdAndDate", params);
		if (CollectionUtils.isNotEmpty(terminalFlowList)) {
			return terminalFlowList.get(0);
		}
		return null;
	}

	@Override
	public List<Terminal> findTerminalByAgentId(String agentId) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.createCriteria().andAgentIdEqualTo(agentId)
				.andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue())
				.andTerminalStatusEqualTo(1);
		List<Terminal> terminalList = terminalMapper.selectByExample(example);
		return terminalList;
	}

	@Override
	public List<PlanItem> findPlanItemByArray(List<String> salePlanIds) {
		IPlanItemMapper planItemMapper = sqlSession
				.getMapper(IPlanItemMapper.class);
		PlanItemExample example = new PlanItemExample();
		example.or().andSalePlanIdIn(salePlanIds);
		return planItemMapper.selectByExample(example);
	}

	@Override
	public List<String> findAllSalePlanId() {
		ISalePlanMapper salePlanMapper = sqlSession
				.getMapper(ISalePlanMapper.class);
		SalePlanExample example = new SalePlanExample();
		example.or().andSalePlanStatusEqualTo(DeleteStatus.NODELETE.getValue());

		List<SalePlan> salePlanList = salePlanMapper.selectByExample(example);
		List<String> salePlanIdList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(salePlanList)) {
			for (SalePlan salePlan : salePlanList) {
				salePlanIdList.add(salePlan.getSalePlanId());
			}
		}
		return salePlanIdList;
	}

	@Override
	public List<Terminal> findTerminalByZoneCode(String zoneCode) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.createCriteria().andZoneCodeEqualTo(zoneCode)
				.andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Terminal> terminalList = terminalMapper.selectByExample(example);
		return terminalList;
	}

	@Override
	public List<Terminal> findTerminalByOfficeCode(String officeCode) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.createCriteria().andOfficeCodeEqualTo(officeCode)
				.andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Terminal> terminalList = terminalMapper.selectByExample(example);
		return terminalList;
	}

	@Override
	public List<Terminal> findTerminalByStationCode(String stationCode) {
		ITerminalMapper terminalMapper = sqlSession
				.getMapper(ITerminalMapper.class);
		TerminalExample example = new TerminalExample();
		example.createCriteria().andStationCodeEqualTo(stationCode)
				.andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Terminal> terminalList = terminalMapper.selectByExample(example);
		return terminalList;
	}

}
