package com.tasly.deepureflow.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.CollectionUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.dao.IAgentDao;
import com.tasly.deepureflow.domain.deepureflow.AgentFlow;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItem;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.dto.AgentFlowShipmentParams;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IAgentService;
import com.tasly.deepureflow.service.IHierarchyService;
import com.tasly.deepureflow.service.IOfficeService;
import com.tasly.deepureflow.service.IProductService;
import com.tasly.deepureflow.service.IStationService;
import com.tasly.deepureflow.service.ITerminalService;
import com.tasly.deepureflow.service.IZoneService;
import com.tasly.deepureflow.util.DateUtil;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.IdGenerator;
import com.tasly.deepureflow.util.MyExcelUtil;
import com.tasly.deepureflow.util.PaginatorResult;
import com.tasly.deepureflow.util.excel.ExcelPoiUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  AgentController   
 * @Description:经销商Controller（包括经销商管理、经销商产品流向管理）
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:50:43 PM   
 *
 */
@Controller
@RequestMapping("/agent")
@SessionAttributes("selectItem")
public class AgentController {
	private final Logger logger = Logger.getLogger(AgentController.class
			.getName());
	@Resource
	private IAgentService agentService;

	@Resource
	private IHierarchyService hierarchyService;

	@Resource
	private IStationService stationService;

	@Resource
	private IProductService productService;

	@Resource
	private ITerminalService terminalService;
	
	@Resource
	private IOfficeService officeService;
	
	@Resource
	private IZoneService zoneService;
	
	@Autowired
	private IAgentDao agentDao;

	@RequestMapping(value = "/agentList")
	@ApiOperation(value = "展示经销商列表", httpMethod = "POST", response = String.class, notes = "展示经销商列表")
	public String agentList(HttpServletRequest request, Model model) {
		model.addAttribute("selectItem", MenuEnum.AGENT.getId());
		List<Hierarchy> hierarchyList = hierarchyService.queryAllHierarchy();
		model.addAttribute("hierarchyList", hierarchyList);

		return "/user/agentList";
	}

	@RequestMapping(value = "/agentPage", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<Agent> agentPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam(required = false, value = "queryAgentId") String queryAgentId,
			@RequestParam(required = false, value = "queryAgentTime") String queryAgentStartTime,
			@RequestParam(required = false, value = "queryAgentEndTime") String queryAgentEndTime,
			@RequestParam(required = false, value = "queryAgentName") String queryAgentName,
			@RequestParam(required = false, value = "queryAgentType") String queryAgentType,
			@RequestParam(required = false, value = "queryAgentVirtual") String queryAgentVirtual) {

		/* System.out.println(queryAgentType); */
		PaginatorResult<Agent> result = new PaginatorResult<Agent>();
		SearchForm searchForm = new SearchForm();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());

		PageList<Agent> agentList = agentService.findAgentForPage(searchForm,
				curPageSize, limit, queryAgentId, queryAgentStartTime,
				queryAgentEndTime, queryAgentName, queryAgentType,queryAgentVirtual);
		if (!CollectionUtils.isEmpty(agentList)) {
			result.setRows(agentList);
			result.setTotal(agentList.getPaginator().getTotalCount());
		}

		return result;  
	}

	@ResponseBody
	@RequestMapping(value = "/addAgent", method = RequestMethod.POST)
	public DeepureResult addAgent(@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("planDate") final String planDate,
			@RequestParam("isVirtual") final Integer isVirtual) {
		boolean addResult = this.agentService.addAgent(agentId,
				agentName, null, planDate, isVirtual);
		return DeepureResult.addResult(addResult);
	}

	@ResponseBody
	@RequestMapping(value = "/delAgent", method = RequestMethod.POST)
	public DeepureResult delAgent(@RequestParam("agentIds") final String[] agentIds) {
		boolean delResult = false;
		if (ArrayUtils.isNotEmpty(agentIds)) {
			delResult = this.agentService.delAgentByArray(agentIds);
		}
		return DeepureResult.delResult(delResult);
	}

	@ResponseBody
	@RequestMapping(value = "/editAgent", method = RequestMethod.POST)
	public DeepureResult editAgent(@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("planDate") final String planDate,
			@RequestParam("isVirtual") final Integer isVirtual) {
		boolean editResult = this.agentService.editAgent(agentId,
				agentName, null, planDate, isVirtual);
		return DeepureResult.editResult(editResult);
	}

	@RequestMapping(value = "toExportAgent"
			+ "", method = RequestMethod.GET)
	public void toExportAgent(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("经销商ERP编号");
			theader.add("经销商流向系统编号");
			theader.add("经销商名称");
			theader.add("经销商加入时间");

			List<Agent> agentList= this.agentService.queryAllAgent();
			List<List<Object>> tValue = new ArrayList<List<Object>>();
			if(!CollectionUtils.isEmpty(agentList)){
				for(Agent agent:agentList){
					List<Object> temp = new ArrayList<Object>();
					temp.add(null == agent.getErpCode() ? "无" : agent.getErpCode());
					temp.add(null == agent.getAgentId() ? "无" : agent.getAgentId());
					temp.add(null == agent.getAgentName() ? "无" : agent.getAgentName());
					temp.add(null == agent.getJoinDate()? "" : agent.getJoinDate());
					tValue.add(temp);
				}
			}
			HSSFWorkbook workbook = ExcelPoiUtils.exportExcel("终端模板", theader, null, null, null, null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String excelName = "terminal_" + dateFormat.format(new Date())
			+ ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("导出终端报表失败", e);
		}
	}
	/**
	 *  经销商产品流向管理
	 * @throws Exception 
	 */
	@RequestMapping(value = "/agentFlowList")
	@ApiOperation(value = "经销商产品流向管理", httpMethod = "POST", response = String.class, notes = "终端产品流向管理列表")
	public String agentFlowList(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("selectItem", MenuEnum.AGENTPRODUCT.getId());
		//List<AgentFlow> terminalList = agentService.queryAllAgent();
		SearchForm searchForm = new SearchForm();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		
		List<Agent> agentList = agentService.findAgentForPage(searchForm,
				1,10000000, "", null,
				null, null, null,null);
		List<Agent> newAgentList = new LinkedList<>();
		for(Agent agent:agentList){
			if(agent.getAgentStatus().equals("1")&&agent.getIsVirtual()==0){
				newAgentList.add(agent);
			}
		}
		Zone zone = zoneService.findZoneByUser(currentUser.getId());
		Integer zoneFlowStatus = new Integer(0);
		if(zone!=null){
			zoneFlowStatus = zone.getZoneFlowStatus();
		}
		model.addAttribute("zoneFlowStatus", zoneFlowStatus);
		model.addAttribute("agentList", newAgentList);

		return "/planflow/agent_sku_flow";
	}
	/**
	 * 经销商产品流向列表
	 * @param request
	 * @param model
	 * @param curPageSize
	 * @param limit
	 * @param planYear
	 * @param planMonth
	 * @param agentId
	 * @param createBy
	 * @return
	 */
	@RequestMapping(value = "/agentFlowPage", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<AgentFlow> agentFlowPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam("planYear") final String planYear,
			@RequestParam("planMonth") final String planMonth,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("createBy") final String createBy) {
		PaginatorResult<AgentFlow> result = new PaginatorResult<AgentFlow>();
		SearchForm searchForm=new SearchForm();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		PageList<AgentFlow> agentFlowList = agentService.findAgentFlowForPage(
				searchForm,curPageSize,limit,planYear,planMonth,agentId,agentName,createBy);
		if (!CollectionUtils.isEmpty(agentFlowList)) {
			result.setRows(agentFlowList);
			result.setTotal(agentFlowList.getPaginator().getTotalCount());
		}
		return result;
	}
	/**
	 * 经销商产品流向详情
	 * @param request
	 * @param terminalId
	 * @param terminalName
	 * @param agentFlowId
	 * @param agentFlowDate
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/goToAgentFlowItemPage")
	@ApiOperation(value = "展示经销商产品流向详情列表", httpMethod = "POST", response = String.class, notes = "展示经销商产品流向详情列表")
	public String goToAgentFlowItemPage(
			HttpServletRequest request,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("agentFlowId") final String agentFlowId,
			@RequestParam("agentFlowDate") final String agentFlowDate,
			Model model) throws UnsupportedEncodingException {
		model.addAttribute("agentId", agentId);
		model.addAttribute("agentName", java.net.URLDecoder.decode(agentName, "UTF-8"));
		model.addAttribute("agentFlowDate", agentFlowDate);
		model.addAttribute("agentFlowId", agentFlowId);
		return "/planflow/agentFlowItemList";
	}

	@RequestMapping(value = "/findAgentFlowItemById")
	@ResponseBody
	public Map<String, Object> findAgentFlowItemById(
			HttpServletRequest request,
			@RequestParam("agentFlowId") final String agentFlowId,
			Model model){
		List<AgentFlowShipmentItem> agentFlowShipmentItemList = agentService.findAgentFlowShipmentItemById(agentFlowId);
		List<AgentFlowStockItem> agentFlowStockItemList = agentService.findAgentFlowStockItemById(agentFlowId);
		Map<String, Object> modelMap = new HashMap<String, Object>(3);  
		modelMap.put("agentFlowShipmentItemList", agentFlowShipmentItemList);  
		modelMap.put("agentFlowStockItemList", agentFlowStockItemList);
		return modelMap;
	}
	/**
	 * 删除经销商产品流向
	 * @param agentFlowIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delAgentFlow", method = RequestMethod.POST)
	public DeepureResult delAgentFlow(@RequestParam("agentFlowIds") final String[]
			agentFlowIds) {
		boolean delResult=false;
		if(ArrayUtils.isNotEmpty(agentFlowIds)){
			delResult=agentService.delAgentFlowByArray(agentFlowIds);
		}
		return DeepureResult.delResult(delResult);
	}
	@RequestMapping(value = "/addAgentFlowStockPage")
	@ApiOperation(value = "增加经销商产品流向库存列表", httpMethod = "POST", response = String.class, notes = "增加经销商产品流向库存列表")
	public String addAgentFlowStockPage(
			HttpServletRequest request,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("agentFlowDate") final String agentFlowDate,
			Model model) throws UnsupportedEncodingException {

		List<Product> productList = productService.findAllProducts();
		String agentFlowId = IdGenerator.generateCode(IdGenerator.AGENT_FLOW_PREFIX,3);
		model.addAttribute("agentFlowId", agentFlowId);
		model.addAttribute("productList", productList);
		model.addAttribute("agentId", agentId);
		model.addAttribute("agentName", java.net.URLDecoder.decode(agentName, "UTF-8"));
		model.addAttribute("agentFlowDate", agentFlowDate);
		return "/planflow/addAgentFlowStockPage";
	}

	@ResponseBody
	@RequestMapping(value = "/saveStockItem", method = RequestMethod.POST)
	public boolean saveStockItem(@RequestBody List<AgentFlowStockItem> agentFlowStockItemList,HttpSession session) {
		List<AgentFlowStockItem> stockItemList = agentFlowStockItemList;
		session.removeAttribute("stockItemList");
		session.setAttribute("stockItemList", stockItemList);
		return true;

	}
	@RequestMapping(value = "/goToShipmentPage")
	public String goToShipmentPage(
			HttpSession session,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("agentFlowId") final String agentFlowId,
			@RequestParam("agentFlowDate") final String agentFlowDate,
			Model model) throws UnsupportedEncodingException {
		model.addAttribute("agentFlowId", agentFlowId);
		model.addAttribute("agentId", agentId);
		model.addAttribute("agentName", java.net.URLDecoder.decode(agentName, "UTF-8"));
		model.addAttribute("agentFlowDate", agentFlowDate);
		model.addAttribute("productList", productService.findAllProducts());
		model.addAttribute("terminalList", terminalService.findTerminalByAgentId(agentId));
		return "/planflow/addAgentFlowShipmentPage";
	}

	@RequestMapping(value = "/addAgentFlow", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult addAgentFlow(
			HttpSession session,
			@RequestBody AgentFlowShipmentParams agentFlowShipmentParams
			) {
		String agentFlowDate = agentFlowShipmentParams.getAgentFlowDate();
		String agentId = agentFlowShipmentParams.getAgentId();
		String agentFlowId = agentFlowShipmentParams.getAgentFlowId();
		boolean addResult = false;

		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("user");
		String createBy = currentUser.getUsername();
		Date flowDate = new Date();
		try {
			flowDate = DateUtil.parseDate("yyyy-MM", agentFlowDate);
		} catch (ParseException e) {
			logger.error("转换日期报错"+e.getMessage(),e);
			e.printStackTrace();
		}

		AgentFlow agentFlow=new AgentFlow();
		agentFlow.setAgentFlowId(agentFlowId);
		agentFlow.setAgentFlowDate(flowDate);
		agentFlow.setAgentId(agentId);
		agentFlow.setCreateBy(createBy);
		agentFlow.setAgentFlowStatus(1);
		agentFlow.setCreateAt(new Date());

		int i = 0;
		List<AgentFlowShipmentItem> agentFlowShipmentItemList = new ArrayList<>();
		if(null!=agentFlowShipmentParams.getAgentFlowShipmentItemList()&&agentFlowShipmentParams.getAgentFlowShipmentItemList().length>0){
			for(AgentFlowShipmentItem agentFlowShipmentItem : agentFlowShipmentParams.getAgentFlowShipmentItemList()){
				agentFlowShipmentItem.setAgentFlowId(agentFlowId);
				agentFlowShipmentItem.setAgentFlowShipmentItemId(IdGenerator.generateItemCode(agentFlowId, i));
				i++;
				agentFlowShipmentItemList.add(agentFlowShipmentItem);
			}
		}
		
		int j = 0;
		List<AgentFlowStockItem> agentFlowStocktItemList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<AgentFlowStockItem> stockItemList = (List<AgentFlowStockItem>) session.getAttribute("stockItemList");
		if(null!=stockItemList&&stockItemList.size()>0){
			for(AgentFlowStockItem agentFlowStockItem : stockItemList){
				agentFlowStockItem.setAgentFlowId(agentFlowId);
				agentFlowStockItem.setAgentFlowShipmentItemId(IdGenerator.generateItemCode(agentFlowId, j));
				j++;
				agentFlowStocktItemList.add(agentFlowStockItem);
			}
		}
			
		agentFlow.setAgentFlowStockItemList(agentFlowStocktItemList);
		agentFlow.setAgentFlowShipmentItemList(agentFlowShipmentItemList);
		addResult = agentService.addAgentFlow(agentFlow);
		
		if(addResult){
			session.removeAttribute("stockItemList");
		}
		return DeepureResult.addResult(addResult);
	}
	
	@RequestMapping(value = "/editAgentFlowStockItemPage")
	public String editAgentFlowStockItemPage(
			HttpServletRequest request,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("agentFlowDate") final String agentFlowDate,
			@RequestParam("agentFlowId") final String agentFlowId,
			Model model) throws UnsupportedEncodingException {

		List<Product> productList = productService.findAllProducts();
		model.addAttribute("agentFlowId", agentFlowId);
		model.addAttribute("productList", productList);
		model.addAttribute("agentId", agentId);
		model.addAttribute("agentName", java.net.URLDecoder.decode(agentName, "UTF-8"));
		model.addAttribute("agentFlowDate", agentFlowDate);
		return "/planflow/editAgentFlowStockPage";
	}

	@RequestMapping(value = "/findAgentFlowStockItemById")
	@ResponseBody
	public List<AgentFlowStockItem> findAgentFlowStockItemById(
			HttpServletRequest request,
			@RequestParam("agentFlowId") final String agentFlowId,
			Model model){
		List<AgentFlowStockItem> agentFlowStockItemList = agentService.findAgentFlowStockItemById(agentFlowId);
		return agentFlowStockItemList;
	}
	
	@RequestMapping(value = "/editAgentFlowShipmentPage")
	public String editAgentFlowShipmentPage(
			HttpSession session,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentName") final String agentName,
			@RequestParam("agentFlowId") final String agentFlowId,
			@RequestParam("agentFlowDate") final String agentFlowDate,
			Model model) throws UnsupportedEncodingException {
		model.addAttribute("agentFlowId", agentFlowId);
		model.addAttribute("agentId", agentId);
		model.addAttribute("agentName", java.net.URLDecoder.decode(agentName, "UTF-8"));
		model.addAttribute("agentFlowDate", agentFlowDate);
		model.addAttribute("productList", productService.findAllProducts());//待改
		model.addAttribute("terminalList", terminalService.findTerminalByAgentId(agentId));
		return "/planflow/editAgentFlowShipmentPage";
	}
	@RequestMapping(value = "/findAgentFlowShipmentItemById")
	@ResponseBody
	public List<AgentFlowShipmentItem> findAgentFlowShipmentItemById(
			HttpServletRequest request,
			@RequestParam("agentFlowId") final String agentFlowId,
			Model model){
		List<AgentFlowShipmentItem> agentFlowShipmentItemList = agentService.findAgentFlowShipmentItemById(agentFlowId);
		return agentFlowShipmentItemList;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editAgentFlowItem", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult editAgentFlowItem(
			HttpSession session,
			@RequestBody AgentFlowShipmentParams agentFlowShipmentParams
			) {
		String agentFlowId = agentFlowShipmentParams.getAgentFlowId();
		boolean updateResult = false;

		AgentFlow agentFlow = agentService.findAgentFlowById(agentFlowId);
		
		agentFlow.setAgentFlowShipmentItemList(Arrays.asList(agentFlowShipmentParams.getAgentFlowShipmentItemList()));
		agentFlow.setAgentFlowStockItemList((List<AgentFlowStockItem>)session.getAttribute("stockItemList"));
		updateResult = agentService.updateAgentFlow(agentFlow,agentFlowShipmentParams.getDelAgentFlowItemIds());

		return DeepureResult.editResult(updateResult);
	}
	
	@RequestMapping(value = "importAgentExcel", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult importAgentExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		return agentService.importAgentExcel(targetPath, upFile,currentUser);  //调用实现类 返回 界面消息 对象
	}
	
	@RequestMapping("/validateAddAgentFlow")
	@ResponseBody
	public DeepureResult validateAddAgentFlow(HttpServletRequest request,
			@RequestParam("agentId") final String agentId,
			@RequestParam("agentFlowDate") final String agentFlowDate){
		AgentFlow agentFlow = agentService.findAgentFlowByAgentIdAndDate(agentId,agentFlowDate);
		//salePlan为空，则可以添加新的计划
		if(agentFlow==null){
			return DeepureResult.success();
		}else{
			return DeepureResult.result(false, "本月产品流向已存在，请勿重复添加!");
		}
		
	}
	
	
	@RequestMapping(value = "/exportData", method = RequestMethod.GET)
	public  void exportData(HttpServletRequest request,HttpServletResponse resp) {
		SearchForm searchForm = new SearchForm();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		
		List<Agent> agentList = new ArrayList<Agent>();
		MyExcelUtil excelUtil = new MyExcelUtil();
		List<Object[]> objList = new ArrayList<Object[]> ();
		try {
			agentList = agentService.findAgentForPage(searchForm,
					1,10000000, "", null,
					null, null, null,null);
			objList = beanToObject(agentList);
			excelUtil.exportToExcel("经销商列表", new String[]{"客户编码","客户名称","创建日期","帝普洱流向系统编号"}, objList, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Object[]> beanToObject(List<Agent> agentList){
		List<Object[]> result= new ArrayList<Object[]>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		for(int i=0;i<agentList.size();i++){
			Agent agent = agentList.get(i);
			Object [] obj = new Object[4];
			obj[0] = agent.getErpCode();
			obj[1] = agent.getAgentName();
			if(agent.getJoinDate() != null){
				obj[2] = sdf.format(agent.getJoinDate());
			}
			obj[3] = agent.getAgentId();
			result.add(obj);
		}
		return result;
	}
	
}
