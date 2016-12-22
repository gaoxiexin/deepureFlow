package com.tasly.deepureflow.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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
import com.tasly.deepureflow.dao.ITerminalDao;
import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlow;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.domain.system.Channel;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.dto.SalePlanParams;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.dto.TerminalFlowParams;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IAgentService;
import com.tasly.deepureflow.service.IChannelService;
import com.tasly.deepureflow.service.IHierarchyService;
import com.tasly.deepureflow.service.IOfficeService;
import com.tasly.deepureflow.service.IProductService;
import com.tasly.deepureflow.service.ISecurityService;
import com.tasly.deepureflow.service.IStationService;
import com.tasly.deepureflow.service.ITerminalService;
import com.tasly.deepureflow.service.IUserService;
import com.tasly.deepureflow.service.IZoneService;
import com.tasly.deepureflow.util.DateUtil;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.IdGenerator;
import com.tasly.deepureflow.util.MyExcelUtil;
import com.tasly.deepureflow.util.PaginatorResult;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  TerminalController   
 * @Description:终端Controller(包括终端管理、终端产品流向管理、终端计划管理)
 * @author: Android_Robot  
 * @date:   Nov 24, 2016 2:55:24 PM   
 *
 */
@Controller
@RequestMapping("/terminal")
@SessionAttributes("selectItem")
public class TerminalController {
	private final Logger logger = Logger.getLogger(TerminalController.class.getName());
	@Resource
	private  ITerminalService terminalService;

	@Resource
	private IChannelService channelService;

	@Resource
	private IStationService stationService;

	@Resource
	private IOfficeService officeService;

	@Resource
	private IHierarchyService hierarchyService;

	@Resource
	private IAgentService agentService;

	@Resource
	private IProductService productService;

/*	@Resource
	private SalePlan salePlan;*/

	@Resource 
	private IUserService userService;

	@Resource 
	private IZoneService zoneService;
	
	@Autowired
	private ITerminalDao terminalDao;

	@Resource 
	private ISecurityService securityService;

	@RequestMapping(value = "/terminalList")
	@ApiOperation(value = "展示终端列表", httpMethod = "POST", response = String.class, notes = "展示终端列表")
	public String terminalList(HttpServletRequest request, Model model) {
		model.addAttribute("selectItem", MenuEnum.TERMINAL.getId());
		SearchForm searchForm = new SearchForm();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		List<Channel> channelList = channelService.queryAllChannel();
		
		List<Station> stationList = stationService.queryStationByRole();
		List<Hierarchy> hierarchyList = hierarchyService.queryAllHierarchy();
		/*List<Agent> agentList = agentService.findAgentForPage(searchForm,
				1,10000000, "", null,null, null, null);*/
		List<Agent> agentList = agentService.findAgentByRole();
		List<ProductCategory> productCategoryList = productService
				.queryCategoryByLayer(1, 1);
		//List<User> userList=userService.quertAllUser();
		List<User> userList=userService.queryUserByRole();
		model.addAttribute("productCategoryList", productCategoryList);
		model.addAttribute("agentList", agentList);
		model.addAttribute("hierarchyList", hierarchyList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("stationList", stationList);
		model.addAttribute("userList", userList);

		return "/user/terminalList";
	}

	@RequestMapping(value = "/terminalPage", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<Terminal> agentPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam(required = false, value = "queryTerminalCode") String queryTerminalCode,
			@RequestParam(required = false, value = "queryTerminalName") String queryTerminalName,
			@RequestParam(required = false, value = "queryAgent") String queryAgent,
			@RequestParam(required = false, value = "queryTerminalStatus") String queryTerminalStatus,
			@RequestParam(required = false, value = "queryTerminalType") String queryTerminalType,
			@RequestParam(required = false, value = "queryChannelId") String queryChannelId,
			@RequestParam(required = false, value = "queryHierarchyId") String queryHierarchyId) {
		PaginatorResult<Terminal> result = new PaginatorResult<Terminal>();
		SearchForm searchForm=new SearchForm();

		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());

		PageList<Terminal> terminalList = terminalService.findTerminalForPage(
				searchForm,curPageSize, limit,queryTerminalCode,queryTerminalName,queryAgent,queryTerminalStatus,queryTerminalType,queryChannelId,queryHierarchyId);
		if (!CollectionUtils.isEmpty(terminalList)) {
			result.setRows(terminalList);
			result.setTotal(terminalList.getPaginator().getTotalCount());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/addTerminal", method = RequestMethod.POST)
	public DeepureResult addTerminal(
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("terminalType") final Integer terminalType,
			@RequestParam("erpCode") final String erpCode,
			@RequestParam("stationCode") final String stationCode,
			@RequestParam("channelId") final String channelId,
			@RequestParam("hierarchyId") final String hierarchyId,
			@RequestParam("agentId") final String agentId,
			@RequestParam("terminalStatus") Integer terminalStatus,
			@RequestParam("planDate") final String planDate,
			@RequestParam("province") final Integer province,
			@RequestParam("city") final Integer city,
			@RequestParam("district") final Integer district,
			@RequestParam("terminalAddress") final String terminalAddress,
			@RequestParam("productCategoryIds") final String[] productCategoryIds) {

		Terminal terminal=new Terminal();
		terminal.setTerminalId(terminalId);
		terminal.setTerminalName(terminalName);
		terminal.setTerminalType(terminalType);
		terminal.setTerminalStatus(terminalStatus);
		terminal.setAgentId(agentId);

		terminal.setErpCode(erpCode);
		terminal.setStationCode(stationCode);
		terminal.setChannelId(channelId);
		terminal.setHierarchyId(hierarchyId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(planDate);
			terminal.setPlanDate(date);
		} catch (ParseException e) {
			logger.error("转换日期报错"+e.getMessage(),e);
		}

		Agent agent=new Agent();
		agent.setAgentId(agentId);
		terminal.setAgent(agent);

		terminal.setTerminalProvince(province);
		terminal.setTerminalCity(city);
		terminal.setTerminalDistrict(district);
		terminal.setTerminalAddress(terminalAddress);
		terminal.setProductCategoryIds(StringUtils.join(productCategoryIds, ","));

		boolean addResult = this.terminalService.addTerminal(terminal);
		return DeepureResult.addResult(addResult);
	}

	@ResponseBody
	@RequestMapping(value = "/editTerminal", method = RequestMethod.POST)
	public DeepureResult editTerminal(
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("terminalType") final Integer terminalType,
			@RequestParam("erpCode") final String erpCode,
			@RequestParam("stationCode") final String stationCode,
			@RequestParam("channelId") final String channelId,
			@RequestParam("hierarchyId") final String hierarchyId,
			@RequestParam("agentId") final String agentId,
			@RequestParam("terminalStatus") Integer terminalStatus,
			@RequestParam("planDate") final String planDate,
			@RequestParam("province") final Integer province,
			@RequestParam("city") final Integer city,
			@RequestParam("district") final Integer district,
			@RequestParam("terminalAddress") final String terminalAddress,
			@RequestParam("productCategoryIds") final String[] productCategoryIds) {

		Terminal terminal=new Terminal();
		terminal.setTerminalId(terminalId);
		terminal.setTerminalName(terminalName);
		terminal.setTerminalType(terminalType);
		terminal.setTerminalStatus(terminalStatus);
		terminal.setAgentId(agentId);
		terminal.setErpCode(erpCode);
		terminal.setStationCode(stationCode);
		terminal.setChannelId(channelId);
		terminal.setHierarchyId(hierarchyId);
		
		if(terminalStatus!=1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = sdf.parse(planDate);
				terminal.setPlanDate(date);
				terminal.setJoinDate(null);
			} catch (ParseException e) {
				logger.error("转换日期报错"+e.getMessage(),e);
			}
		}
		

		Agent agent=new Agent();
		agent.setAgentId(agentId);
		terminal.setAgent(agent);

		terminal.setTerminalProvince(province);
		terminal.setTerminalCity(city);
		terminal.setTerminalDistrict(district);
		terminal.setTerminalAddress(terminalAddress);
		terminal.setProductCategoryIds(StringUtils.join(productCategoryIds, ","));

		boolean editResult = this.terminalService.editTerminal(terminal);
		return DeepureResult.editResult(editResult);
	}


	@ResponseBody
	@RequestMapping(value = "/delTerminal", method = RequestMethod.POST)
	public DeepureResult delTerminal(@RequestParam("terminalIds") final String[]
			terminalIds) {
		boolean delResult=false;
		if(ArrayUtils.isNotEmpty(terminalIds)){
			delResult=this.terminalService.delTerminalByArray(terminalIds);
		}
		return DeepureResult.delResult(delResult);
	}

	@ResponseBody
	@RequestMapping(value = "/turnTerminal", method = RequestMethod.POST)
	public boolean turnTerminal(@RequestParam("terminalIds") final String[]
			terminalIds) {
		boolean delResult=false;
		if(ArrayUtils.isNotEmpty(terminalIds)){
			delResult=this.terminalService.turnTerminalByArray(terminalIds);
		}
		return delResult;
	}


	/*@RequestMapping(value = "toExportTerminal", method = RequestMethod.GET)
	public void toExportTerminal(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("终端编号");
			theader.add("终端名称");
			theader.add("所属岗位编号");
			theader.add("终端加入时间编号");

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
		// return true;
	}*/
	@RequestMapping(value = "/toExportTerminal", method = RequestMethod.GET)
	public void toExportTerminal(HttpServletRequest request,HttpServletResponse resp) {
		SearchForm searchForm = new SearchForm();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		
		List<Terminal> terminalList = new LinkedList<>();
		MyExcelUtil excelUtil = new MyExcelUtil();
		List<Object[]> objList = new ArrayList<Object[]> ();
		try {
			terminalList = terminalService.findTerminalForPage(searchForm,
					1,10000000, null, null, null, null,"0",null,null);
			/*for(Terminal terminal : terminalList){
				if(terminal.getTerminalType()!=null&&terminal.getTerminalType()==0){
					newTerminalList.add(terminal);
				}
			}*/
			if(CollectionUtils.isNotEmpty(terminalList)){
				objList = beanToObject(terminalList);
			}
			excelUtil.exportToExcel("终端列表", new String[]{"终端编号","终端名称","终端加入时间","所属岗位编号","帝普洱流向系统编号"}, objList, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Object[]> beanToObject(List<Terminal> terminalList){
		List<Object[]> result= new ArrayList<Object[]>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		for(int i=0;i<terminalList.size();i++){
			Terminal terminal = terminalList.get(i);
			Object [] obj = new Object[5];
			obj[0] = terminal.getErpCode();
			obj[1] = terminal.getTerminalName();
			if(terminal.getJoinDate() != null){
				obj[2] = sdf.format(terminal.getJoinDate());
			}
			Station station = terminal.getStation();
			if(station==null){
				obj[3] = 0;
			}else{
				obj[3] = station.getStationCode();
			}
			
			obj[4] = terminal.getTerminalId();
			result.add(obj);
		}
		return result;
	}
	/**
	 * ----------------------------------终端销售计划----------------------------------
	 */
	@RequestMapping(value = "/planList")
	@ApiOperation(value = "展示终端销售计划列表", httpMethod = "POST", response = String.class, notes = "展示终端销售计划列表")
	public String salePlanList(HttpServletRequest request, Model model) {
		model.addAttribute("selectItem", MenuEnum.TERMINALPLAN.getId());

		List<Terminal> terminalList = terminalService.queryTerminalByRole();
		SearchForm searchForm=new SearchForm();

		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		/*List<Terminal> terminalList = terminalService.findTerminalForPage(searchForm,
				1,10000000, null, null, null, null,null);*/
		/*List<Agent> agentList = agentService.findAgentForPage(searchForm,
				1,10000000, "", null,
				null, null, null);*/
		List<Agent> agentList = agentService.findAgentByRole();
		List<Zone> zoneList = zoneService.findZoneByRole();
		List<Station> stationList = stationService.queryStationByRole();
		//从user拿不出zone，为null
		Zone zone = zoneService.findZoneByUser(currentUser.getId());
		Integer zonePlanStatus = new Integer(0);
		if(zone!=null){
			zonePlanStatus = zone.getZonePlanStatus();
		}

		model.addAttribute("zonePlanStatus", zonePlanStatus);
		model.addAttribute("terminalList", terminalList);
		model.addAttribute("agentList", agentList);
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("stationList", stationList);

		return "/planflow/terminal_sale_plan";
	}
	@RequestMapping(value = "/terminalSalePlanPage", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<SalePlan> terminalSalePlanPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam("planYear") final String planYear,
			@RequestParam("agentId") final String agentId,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("zoneCode") final String zoneCode,
			@RequestParam("planMonth") final String planMonth,
			@RequestParam("stationCode") final String stationCode) {
		PaginatorResult<SalePlan> result = new PaginatorResult<SalePlan>();
		SearchForm searchForm=new SearchForm();

		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		PageList<SalePlan> terminalSalePlanList = terminalService.findTerminalSalePlanForPage(
				searchForm,curPageSize, limit,planYear,agentId,terminalId,zoneCode,planMonth,stationCode);
		if (!CollectionUtils.isEmpty(terminalSalePlanList)) {
			result.setRows(terminalSalePlanList);
			result.setTotal(terminalSalePlanList.getPaginator().getTotalCount());
		}
		return result;
	}

	@RequestMapping(value = "/addTerminalSalePlanPage")
	@ApiOperation(value = "展示添加计划列表", httpMethod = "POST", response = String.class, notes = "展示添加计划列表")
	public String addTerminalSalePlanPage(
			HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("planMonth") final String planMonth,
			Model model) throws UnsupportedEncodingException {

		//查询该终端下的所有产品
		List<Product> productList = terminalService.findProductsByTerminalId(terminalId);
		String salePlanId = IdGenerator.generateCode(IdGenerator.SALE_PLAN_PREFIX,3);
		model.addAttribute("salePlanId", salePlanId);
		model.addAttribute("productList", productList);
		model.addAttribute("terminalId", terminalId);
		model.addAttribute("terminalName", java.net.URLDecoder.decode(terminalName, "UTF-8"));
		model.addAttribute("planMonth", planMonth);
		return "/planflow/addTerminalSalePlanPage";
	}

	@RequestMapping(value = "/editTerminalSalePlanPage")
	public String editTerminalSalePlanPage(
			HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("planMonth") final String planMonth,
			@RequestParam("salePlanId") final String salePlanId,
			Model model) throws UnsupportedEncodingException {

		//查询该终端下的所有产品
		List<Product> productList = terminalService.findProductsByTerminalId(terminalId);
		model.addAttribute("salePlanId", salePlanId);
		model.addAttribute("productList", productList);
		model.addAttribute("terminalId", terminalId);
		model.addAttribute("terminalName", java.net.URLDecoder.decode(terminalName, "UTF-8"));
		model.addAttribute("planMonth", planMonth);
		return "/planflow/editTerminalSalePlanPage";
	}



	@RequestMapping(value = "/addSalePlanItem", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult addSalePlanItem(
			HttpServletRequest request,
			@RequestBody SalePlanParams salePlanParams
			) {
		String salePlanDate = salePlanParams.getSalePlanDate();
		String terminalId = salePlanParams.getTerminalId();
		String salePlanId = salePlanParams.getSalePlanId();
		boolean addResult = false;
		//1.根据terminalId->station->office->zone
		Integer stationId = terminalService.findStationIdById(terminalId);
		Integer officeId = stationService.findOfficeIdById(stationId);
		Integer zoneId = officeService.findZoneIdById(String.valueOf(officeId));
		//2.添加终端销售计划
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("user");
		String createBy = currentUser.getUsername();
		Date planDate = new Date();
		try {
			planDate = DateUtil.parseDate("yyyy-MM", salePlanDate);
		} catch (ParseException e) {
			logger.error("转换日期报错"+e.getMessage(),e);
			e.printStackTrace();
		}
		SalePlan salePlan=new SalePlan();
		salePlan.setSalePlanId(salePlanId);
		salePlan.setSalePlanDate(planDate);
		salePlan.setTerminalId(terminalId);
		salePlan.setStationId(String.valueOf(stationId));
		salePlan.setOfficeId(String.valueOf(officeId));
		salePlan.setZoneId(String.valueOf(zoneId));
		salePlan.setCreateBy(createBy);
		salePlan.setSalePlanStatus(0);
		List<PlanItem> planItems = new ArrayList<>(); 
		int i = 0;
		BigDecimal totalPrice = new BigDecimal(0);
		if(null!=salePlanParams.getPlanItemList()&&salePlanParams.getPlanItemList().length>0){
			for(PlanItem planItem : salePlanParams.getPlanItemList()){
				//3.添加销售计划详情
				planItem.setSalePlanId(salePlanId);
				planItem.setSalePlanItemId(IdGenerator.generateItemCode(salePlanId, i));
				totalPrice=totalPrice.add(planItem.getTotalPrice());
				i++;
				planItems.add(planItem);
			}
		}
		
		salePlan.setTotalPrice(totalPrice);
		salePlan.setPlanItemList(planItems);
		addResult = terminalService.addSalePlan(salePlan);
		return DeepureResult.addResult(addResult);
	}

	@RequestMapping(value = "/editSalePlanItem", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult editSalePlanItem(
			HttpServletRequest request,
			@RequestBody SalePlanParams params
			) {
		String salePlanId = params.getSalePlanId();
		boolean editResult = false;

		SalePlan salePlan = terminalService.findSalePlanById(salePlanId);
		List<PlanItem> planItems = new ArrayList<>(); 
		int i = 0;
		BigDecimal totalPrice = new BigDecimal(0);
		for(PlanItem planItem : params.getPlanItemList()){
			planItem.setSalePlanId(salePlanId);
			planItem.setSalePlanItemId(IdGenerator.generateItemCode(salePlanId, i));
			totalPrice=totalPrice.add(planItem.getTotalPrice());
			i++;
			planItems.add(planItem);
		}
		salePlan.setTotalPrice(totalPrice);
		editResult = terminalService.updateSalePlanItem(salePlanId,planItems)&&terminalService.updateSalePlan(salePlan);

		return DeepureResult.editResult(editResult);
	}

	@ResponseBody
	@RequestMapping(value = "/delSalePlan", method = RequestMethod.POST)
	public DeepureResult delSalePlan(@RequestParam("salePlanIds") final String[]
			salePlanIds) {
		boolean delResult=false;
		if(ArrayUtils.isNotEmpty(salePlanIds)){
			delResult=this.terminalService.delSalePlanByArray(salePlanIds);
		}
		return DeepureResult.delResult(delResult);
	}

	@RequestMapping(value = "/goToSalePlanItemPage")
	@ApiOperation(value = "展示计划详情列表", httpMethod = "POST", response = String.class, notes = "展示计划详情列表")
	public String goToSalePlanItemPage(
			HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("salePlanId") final String salePlanId,
			@RequestParam("salePlanDate") final String salePlanDate,
			Model model) throws UnsupportedEncodingException {
		model.addAttribute("terminalId", terminalId);
		model.addAttribute("terminalName", java.net.URLDecoder.decode(terminalName, "UTF-8"));
		model.addAttribute("planMonth", salePlanDate);
		model.addAttribute("salePlanId", salePlanId);
		return "/planflow/planItemList";
	}

	@RequestMapping(value = "/findPlanItemById")
	@ResponseBody
	public List<PlanItem> findPlanItemById(
			HttpServletRequest request,
			@RequestParam("salePlanId") final String salePlanId,
			Model model) throws UnsupportedEncodingException {
		List<PlanItem> planItemList = terminalService.findPlanItemById(salePlanId);
		return planItemList;
	}

	@RequestMapping(value = "importSalePlanExcel", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult importSalePlanExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return terminalService.importExcel(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}
	@RequestMapping("/exportSalePlanExcel")
	@ResponseBody
	public DeepureResult exportSalePlanExcel(HttpServletRequest request){
		if(terminalService.exportSalePlanExcel()){
			return DeepureResult.result(true, "导出销售计划成功");
		}else{
			return DeepureResult.result(false, "导出销售计划失败");
		}
	}
	@RequestMapping("/validateAddSalePlan")
	@ResponseBody
	public DeepureResult validateAddSalePlan(HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("planMonth") final String planMonth){
		DeepureResult result = terminalService.isFullTerminal(terminalId);
		if(result.getStatus()){
			SalePlan salePlan = terminalService.findSalePlanByTerminalIdAndDate(terminalId,planMonth);
			if(salePlan!=null){
				return DeepureResult.result(false, "此终端本月已添加过销售计划，请勿重复添加！");
			}else{
				return DeepureResult.success();
			}
		}else{
			return result;
		}
	}

	/**
	 * ----------------------------------终端产品流向管理----------------------------------
	 */
	@RequestMapping(value = "/terminalFlowList")
	@ApiOperation(value = "终端产品流向管理", httpMethod = "POST", response = String.class, notes = "终端产品流向管理列表")
	public String terminalFlowList(HttpServletRequest request, Model model) {
		model.addAttribute("selectItem", MenuEnum.TERMINALPRODUCT.getId());
		List<Terminal> terminalList = terminalService.queryTerminalByRole();
		List<Terminal> newTerminalList = new LinkedList<>();
		for(Terminal terminal:terminalList){
			if(1==terminal.getTerminalStatus()&&1==terminal.getTerminalType()){
				newTerminalList.add(terminal);
			}
		}
		//List<Agent> agentList = agentService.queryAllAgent();
		List<Zone> zoneList = zoneService.findAllZone();
		List<Station> stationList = stationService.queryAllStation();
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		Zone zone = zoneService.findZoneByUser(currentUser.getId());
		Integer zoneFlowStatus = new Integer(0);
		if(zone!=null){
			zoneFlowStatus = zone.getZoneFlowStatus();
		}
		model.addAttribute("zoneFlowStatus", zoneFlowStatus);
		model.addAttribute("terminalList", newTerminalList);
		//model.addAttribute("agentList", agentList);
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("stationList", stationList);

		return "/planflow/terminal_sku_flow";
	}
	
	@RequestMapping(value = "/terminalFlowPage", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<SalePlan> terminalFlowPage(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam("planYear") final String planYear,
			@RequestParam("planMonth") final String planMonth,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName) {
		PaginatorResult<SalePlan> result = new PaginatorResult<SalePlan>();
		SearchForm searchForm=new SearchForm();

		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		searchForm.setUserId(currentUser.getId());
		
		PageList<SalePlan> terminalFlowList = terminalService.findTerminalFlowForPage(
				searchForm,curPageSize, limit,planYear,planMonth,terminalId,terminalName);
		if (!CollectionUtils.isEmpty(terminalFlowList)) {
			result.setRows(terminalFlowList);
			result.setTotal(terminalFlowList.getPaginator().getTotalCount());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delTerminalFlow", method = RequestMethod.POST)
	public DeepureResult delTerminalFlow(@RequestParam("terminalFlowIds") final String[]
			terminalFlowIds) {
		boolean delResult=false;
		if(ArrayUtils.isNotEmpty(terminalFlowIds)){
			delResult=this.terminalService.delTerminalFlowByArray(terminalFlowIds);
		}
		return DeepureResult.delResult(delResult);
	}
	
	@RequestMapping(value = "/addTerminalFlowPage")
	@ApiOperation(value = "增加产品流向子项目列表", httpMethod = "POST", response = String.class, notes = "增加产品流向子项目列表")
	public String addTerminalFlowPage(
			HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("planMonth") final String planMonth,
			Model model) throws UnsupportedEncodingException {

		//查询该终端下的所有产品
		List<Product> productList = terminalService.findProductsByTerminalId(terminalId);
		String terminalFlowId = IdGenerator.generateCode(IdGenerator.TERMINAL_FLOW_PREFIX,3);
		model.addAttribute("terminalFlowId", terminalFlowId);
		model.addAttribute("productList", productList);
		model.addAttribute("terminalId", terminalId);
		model.addAttribute("terminalName", java.net.URLDecoder.decode(terminalName, "UTF-8"));
		model.addAttribute("planMonth", planMonth);
		return "/planflow/addTerminalFlowPage";
	}
	@RequestMapping(value = "/addTerminalFlowItem", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult addTerminalFlowItem(
			HttpServletRequest request,
			@RequestBody TerminalFlowParams terminalFlowParams
			) {
		String salePlanDate = terminalFlowParams.getTerminalFlowDate();
		String terminalId = terminalFlowParams.getTerminalId();
		String terminalFlowId = terminalFlowParams.getTerminalFlowId();
		boolean addResult = false;
		//1.根据terminalId->station->office->zone
		Integer stationId = terminalService.findStationIdById(terminalId);
		Integer officeId = stationService.findOfficeIdById(stationId);
		Integer zoneId = officeService.findZoneIdById(String.valueOf(officeId));
		String hierarchyId=terminalDao.findHierarchyIdById(terminalId);
		String channelId=terminalDao.findChannelIdById(terminalId);
		//2.添加终端销售计划
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("user");
		String createBy = currentUser.getUsername();
		Date planDate = new Date();
		try {
			planDate = DateUtil.parseDate("yyyy-MM", salePlanDate);
		} catch (ParseException e) {
			logger.error("转换日期报错"+e.getMessage(),e);
			e.printStackTrace();
		}
		
		TerminalFlow terminalFlow=new TerminalFlow();
		terminalFlow.setTerminalFlowId(terminalFlowId);
		terminalFlow.setTerminalFlowDate(planDate);
		terminalFlow.setTerminalId(terminalId);
		terminalFlow.setStationId(String.valueOf(stationId));
		terminalFlow.setOfficeId(String.valueOf(officeId));
		terminalFlow.setZoneId(String.valueOf(zoneId));
		terminalFlow.setHierarchyId(hierarchyId);
		terminalFlow.setChannelId(channelId);
		terminalFlow.setCreateBy(createBy);
		terminalFlow.setTerminalFlowStatus(0);
		
		List<TerminalFlowItem> terminalFlowItems = new ArrayList<TerminalFlowItem>(); 
		int i = 0;
		
		if(null!=terminalFlowParams.getTerminalFlowItemList()&&terminalFlowParams.getTerminalFlowItemList().length>0){
			for(TerminalFlowItem terminalFlowItem : terminalFlowParams.getTerminalFlowItemList()){
				//3.添加销售计划详情
				terminalFlowItem.setTerminalFlowId(terminalFlowId);
				terminalFlowItem.setTerminalFlowItemId(IdGenerator.generateItemCode(terminalFlowId, i));
				i++;
				terminalFlowItems.add(terminalFlowItem);
			}
		}
		terminalFlow.setTerminalFlowItemList(terminalFlowItems);

		addResult = terminalService.addTerminalFlow(terminalFlow);

		return DeepureResult.addResult(addResult);
	}
	
	@RequestMapping(value = "/editTerminalFlowPage")
	public String editTerminalFlowPage(
			HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("planMonth") final String planMonth,
			@RequestParam("terminalFlowId") final String terminalFlowId,
			Model model) throws UnsupportedEncodingException {

		//查询该终端下的所有产品
		List<Product> productList = terminalService.findProductsByTerminalId(terminalId);
		model.addAttribute("terminalFlowId", terminalFlowId);
		model.addAttribute("productList", productList);
		model.addAttribute("terminalId", terminalId);
		model.addAttribute("terminalName", java.net.URLDecoder.decode(terminalName, "UTF-8"));
		model.addAttribute("planMonth", planMonth);
		return "/planflow/editTerminalFlowPage";
	}
	
	@RequestMapping(value = "/findTerminalFlowItemById")
	@ResponseBody
	public List<TerminalFlowItem> findTerminalFlowItemById(
			HttpServletRequest request,
			@RequestParam("terminalFlowId") final String terminalFlowId,
			Model model){
		List<TerminalFlowItem> terminalFlowItemList = terminalService.findTerminalFlowItemById(terminalFlowId);
		return terminalFlowItemList;
	}
	
	@RequestMapping(value = "/editTerminalFlowItem", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult editTerminalFlowItem(
			HttpServletRequest request,
			@RequestBody TerminalFlowParams terminalFlowParams
			) {
		String terminalFlowId = terminalFlowParams.getTerminalFlowId();
		boolean updateResult = false;

		TerminalFlow terminalFlow = terminalService.findTerminalFlowById(terminalFlowId);
		List<TerminalFlowItem> terminalFlowItems = new ArrayList<TerminalFlowItem>(); 
		int i = 0;
		for(TerminalFlowItem item : terminalFlowParams.getTerminalFlowItemList()){
			item.setTerminalFlowId(terminalFlowId);
			item.setTerminalFlowItemId(IdGenerator.generateItemCode(terminalFlowId, i));
			i++;
			terminalFlowItems.add(item);
		}

		terminalFlow.setTerminalFlowItemList(terminalFlowItems);
		updateResult = terminalService.updateTerminalFlow(terminalFlow);

		return DeepureResult.editResult(updateResult);
	}
	
	@RequestMapping(value = "/goToTerFlowItemPage")
	@ApiOperation(value = "展示终端产品流向详情列表", httpMethod = "POST", response = String.class, notes = "展示终端产品流向详情列表")
	public String goToTerFlowItemPage(
			HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("terminalName") final String terminalName,
			@RequestParam("terminalFlowId") final String terminalFlowId,
			@RequestParam("terminalFlowDate") final String terminalFlowDate,
			Model model) throws UnsupportedEncodingException {
		model.addAttribute("terminalId", terminalId);
		model.addAttribute("terminalName", java.net.URLDecoder.decode(terminalName, "UTF-8"));
		model.addAttribute("terminalFlowDate", terminalFlowDate);
		model.addAttribute("terminalFlowId", terminalFlowId);
		return "/planflow/terminalFlowItemList";
	}
	
	@RequestMapping(value = "importTerminalExcel", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult importTerminalExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return terminalService.importTerminalExcel(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}
	
	@RequestMapping("/validateAddTerminalFlow")
	@ResponseBody
	public DeepureResult validateAddTerminalFlow(HttpServletRequest request,
			@RequestParam("terminalId") final String terminalId,
			@RequestParam("planMonth") final String planMonth){
		TerminalFlow terminalFlow = terminalService.findTerminalFlowByTerminalIdAndDate(terminalId,planMonth);
		//salePlan为空，则可以添加新的计划
		if(terminalFlow==null){
			return DeepureResult.success();
		}else{
			return DeepureResult.result(false, "此终端本月已添加过产品流向，请勿重复添加！");
		}
	}
}
