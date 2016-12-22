package com.tasly.deepureflow.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlow;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.util.DeepureResult;

public interface ITerminalService {

	PageList<Terminal> findTerminalForPage(SearchForm searchForm,
			int curPageSize, int limit, String queryTerminalCode,
			String queryTerminalName, String queryAgent,
			String queryTerminalStatus, String queryTerminalType,
			String queryChannelId, String queryHierarchyId);

	boolean addTerminal(Terminal terminal);

	boolean editTerminal(Terminal terminal);

	boolean delTerminalByArray(String[] terminalIds);

	boolean turnTerminalByArray(String[] terminalIds);

	DeepureResult importTerminalExcel(String targetPath, MultipartFile upFile);

	// 查询所有终端
	public List<Terminal> queryAllTerminal();

	// 终端销售计划分页
	PageList<SalePlan> findTerminalSalePlanForPage(SearchForm searchForm,
			int curPageSize, int limit, String planYear, String agentId,
			String terminalId, String zoneId, String planMonth, String stationId);

	List<Product> findProductsByTerminalId(String terminalId);

	Integer findStationIdById(String terminalId);

	boolean addSalePlan(SalePlan salePlan);

	boolean delSalePlanByArray(String[] salePlanIds);

	List<PlanItem> findPlanItemById(String salePlanId);

	SalePlan findSalePlanById(String salePlanId);

	boolean updateSalePlanItem(String salePlanId, List<PlanItem> planItems);

	boolean updateSalePlan(SalePlan salePlan);

	DeepureResult importExcel(String targetPath, MultipartFile upFile);

	PageList<SalePlan> findTerminalFlowForPage(SearchForm searchForm,
			int curPageSize, int limit, String planYear, String planMonth,
			String terminalId, String terminalName);

	boolean delTerminalFlowByArray(String[] terminalFlowIds);

	boolean addTerminalFlow(TerminalFlow terminalFlow);

	List<TerminalFlowItem> findTerminalFlowItemById(String terminalFlowId);

	TerminalFlow findTerminalFlowById(String terminalFlowId);

	boolean updateTerminalFlow(TerminalFlow terminalFlow);

	SalePlan findSalePlanByTerminalIdAndDate(String terminalId, String planMonth);

	TerminalFlow findTerminalFlowByTerminalIdAndDate(String terminalId,
			String planMonth);

	List<Terminal> findTerminalByAgentId(String agentId);

	boolean exportSalePlanExcel();

	DeepureResult isFullTerminal(String terminalId);

	List<Terminal> queryTerminalByRole();

}
