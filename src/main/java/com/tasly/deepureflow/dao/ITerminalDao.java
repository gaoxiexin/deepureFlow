package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlow;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;
import com.tasly.deepureflow.domain.user.AgentTerminalKey;
import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.dto.SearchForm;

public interface ITerminalDao {

	List<Terminal> findTerminalForPage(SearchForm searchForm,
			PageBounds pageBounds, String queryTerminalCode,
			String queryTerminalName, String queryAgent,
			String queryTerminalStatus, String queryTerminalType,
			String queryChannelId, String queryHierarchyId);

	boolean insertAgentTerminalKey(AgentTerminalKey agentTerminalKey);

	boolean insertTerminal(Terminal terminal);

	boolean updateAgentTerminalKey(AgentTerminalKey agentTerminalKey);

	boolean updateTerminal(Terminal terminal);

	boolean delTerminalByArray(String[] terminalIds);

	boolean turnTerminalByArray(String[] terminalIds);

	List<SalePlan> findTerminalSalePlanForPage(SearchForm searchForm,
			PageBounds pageBounds, String planYear, String agentId,
			String terminalId, String zoneId, String planMonth, String stationId);

	List<Terminal> findAllTerminal();

	Terminal findTerminalById(String terminalId);

	Terminal findTerminalByCode(String erpCode);

	Integer findStationIdById(String terminalId);

	String findHierarchyIdById(String terminalId);

	String findChannelIdById(String terminalId);

	boolean insertSalePlan(SalePlan salePlan);

	boolean insertSalePlanItem(PlanItem planItem);

	boolean delSalePlanByArray(String[] salePlanIds);

	boolean delPlanItemByArray(String[] salePlanIds);

	List<PlanItem> findPlanItemById(String salePlanId);

	SalePlan findSalePlanById(String salePlanId);

	boolean updateSalePlan(SalePlan salePlan);

	List<SalePlan> findTerminalFlowForPage(SearchForm searchForm,
			PageBounds pageBounds, String planYear, String planMonth,
			String terminalId, String terminalName);

	boolean delTerminalFlowByArray(String[] terminalFlowIds);

	boolean insertTerminalFlow(TerminalFlow terminalFlow);

	boolean insertTerminalFlowItem(TerminalFlowItem terminalFlowItem);

	List<TerminalFlowItem> findTerminalFlowItemById(String terminalFlowId);

	TerminalFlow queryTerminalFlowById(String terminalFlowId);

	boolean delTerminalFlowItemByArray(String[] terminalFlowItemIds);

	SalePlan querySalePlanByTerminalIdAndDate(String terminalId,
			String planMonth);

	TerminalFlow queryTerminalFlowByTerminalIdAndDate(String terminalId,
			String planMonth);

	String findAgentIdById(String terminalId);

	List<Terminal> findTerminalByAgentId(String agentId);

	boolean delAllTerminal(Integer terminalType);

	List<PlanItem> findPlanItemByArray(List<String> salePlanIds);

	List<String> findAllSalePlanId();

	List<Terminal> findTerminalByZoneCode(String zoneCode);

	List<Terminal> findTerminalByOfficeCode(String officeCode);

	List<Terminal> findTerminalByStationCode(String stationCode);

}
