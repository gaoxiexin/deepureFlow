package com.tasly.deepureflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.cache.RedisCache;
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.IProductCategoryDao;
import com.tasly.deepureflow.dao.IProductDao;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.dao.ITerminalDao;
import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.deepureflow.PlanItem;
import com.tasly.deepureflow.domain.deepureflow.SalePlan;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlow;
import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.user.Terminal;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.enums.SearchEnum;
import com.tasly.deepureflow.service.IOfficeService;
import com.tasly.deepureflow.service.IProductService;
import com.tasly.deepureflow.service.ISecurityService;
import com.tasly.deepureflow.service.IStationService;
import com.tasly.deepureflow.service.ITerminalService;
import com.tasly.deepureflow.util.DateUtil;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.IdGenerator;
import com.tasly.deepureflow.util.excel.ExcelPoiUtils;
import com.tasly.deepureflow.util.excel.impl.SalePlanReadExcel;
import com.tasly.deepureflow.util.excel.impl.TerminalReadExcel;

@Service("terminalService")
public class TerminalServiceImpl implements ITerminalService {
	private final Logger logger = Logger.getLogger(TerminalServiceImpl.class
			.getName());

	@Autowired
	private ITerminalDao terminalDao;

	@Autowired
	private IProductCategoryDao productCategoryDao;

	@Autowired
	private IProductDao productDao;

	@Autowired
	private ISecurityDao securityDao;

	@Autowired
	private IZoneDao zoneDao;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private TerminalReadExcel terminalReadExcel;

	@Autowired
	private SalePlanReadExcel salePlanReadExcel;

	@Resource
	private IStationService stationService;

	@Resource
	private IOfficeService officeService;

	@Resource
	private ITerminalService terminalService;

	@Resource
	private IProductService productService;

	@Resource
	private IStationDao stationDao;

	@Resource
	private IOfficeDao officeDao;

	@Autowired
	private RedisCache cache;

	@Override
	public PageList<Terminal> findTerminalForPage(SearchForm searchForm,
			int curPageSize, int limit, String queryTerminalCode,
			String queryTerminalName, String queryAgent,
			String queryTerminalStatus, String queryTerminalType,
			String queryChannelId, String queryHierarchyId) {
		PageList<Terminal> pageList = null;
		List<Role> roleList = securityDao.getRolesByUserId(searchForm
				.getUserId());
		Role role = CollectionUtils.isNotEmpty(roleList) ? roleList.get(0)
				: null;
		if (null != role) {
			searchForm.setRoleCode(role.getCode());
			searchForm.setRoleType(SearchEnum.TERMINAL.getValue());
			securityService.changeSearchForm(searchForm);
		}
		if (curPageSize != 0 && limit != 0) {
			PageBounds pageBounds = new PageBounds(curPageSize, limit);
			pageList = (PageList<Terminal>) this.terminalDao
					.findTerminalForPage(searchForm, pageBounds,
							queryTerminalCode, queryTerminalName, queryAgent,
							queryTerminalStatus, queryTerminalType,queryChannelId,queryHierarchyId);

			if (CollectionUtils.isNotEmpty(pageList)) {
				for (Terminal terminal : pageList) {
					if (StringUtils
							.isNotEmpty(terminal.getProductCategoryIds())) {
						List<String> productCategoryIds = Arrays
								.asList(terminal.getProductCategoryIds().split(
										","));
						List<ProductCategory> productCategoryList = productCategoryDao
								.findByIds(productCategoryIds);

						terminal.setProductCategoryList(productCategoryList);

					}
				}
			}
		}
		return pageList;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addTerminal(Terminal terminal) {
		// boolean isInsertRelation=false;
		boolean isInsertTerminal = false;
		// if(null!=terminal.getAgent()&&StringUtils.isNotEmpty(terminal.getAgent().getAgentId())&&StringUtils.isNotEmpty(terminal.getTerminalId())){
		// AgentTerminalKey agentTerminalKey=new AgentTerminalKey();
		// agentTerminalKey.setAgentId(terminal.getAgent().getAgentId());
		// agentTerminalKey.setTerminalCode(terminal.getTerminalId());
		//
		// isInsertRelation=terminalDao.insertAgentTerminalKey(agentTerminalKey);
		// }

		if (StringUtils.isNotEmpty(terminal.getStationCode())) {
			Office office = stationDao.findOfficeByStationCode(terminal
					.getStationCode());
			terminal.setOfficeCode(office.getOfficeCode());
		}

		if (StringUtils.isNotEmpty(terminal.getOfficeCode())) {
			Zone zone = officeDao
					.findZoneByOfficeCode(terminal.getOfficeCode());
			terminal.setZoneCode(zone.getZoneCode());
		}

		isInsertTerminal = terminalDao.insertTerminal(terminal);
		return isInsertTerminal;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean editTerminal(Terminal terminal) {
		boolean isUpdateTerminal = false;
		// if(null!=terminal.getAgent()&&StringUtils.isNotEmpty(terminal.getAgent().getAgentId())&&StringUtils.isNotEmpty(terminal.getTerminalId())){
		// AgentTerminalKey agentTerminalKey=new AgentTerminalKey();
		// agentTerminalKey.setAgentId(terminal.getAgent().getAgentId());
		// agentTerminalKey.setTerminalCode(terminal.getTerminalId());
		//
		// isUpdateRelation=terminalDao.updateAgentTerminalKey(agentTerminalKey);
		// }
		Terminal oldTerminal = terminalDao.findTerminalById(terminal
				.getTerminalId());
		if (oldTerminal != null && terminal != null) {
			if (oldTerminal.getTerminalStatus() == 0
					&& terminal.getTerminalStatus() == 1) {
				terminal.setJoinDate(new Date());
			}
		}
		if (StringUtils.isNotEmpty(terminal.getStationCode())) {
			Office office = stationDao.findOfficeByStationCode(terminal
					.getStationCode());
			terminal.setOfficeCode(office.getOfficeCode());
		}

		if (StringUtils.isNotEmpty(terminal.getOfficeCode())) {
			Zone zone = officeDao
					.findZoneByOfficeCode(terminal.getOfficeCode());
			terminal.setZoneCode(zone.getZoneCode());
		}

		if (null != cache) {
			String cache_key = RedisCache.CAHCENAME
					+ "|getProductByTerminalId|" + terminal.getTerminalId();

			List<Product> productList = cache.getListCache(cache_key,
					Product.class);
			if (CollectionUtils.isNotEmpty(productList)) {
				cache.deleteCacheWithPattern(cache_key);
			}
		}

		isUpdateTerminal = terminalDao.updateTerminal(terminal);
		return isUpdateTerminal;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delTerminalByArray(String[] terminalIds) {
		boolean isDel = true;
		try {
			isDel = terminalDao.delTerminalByArray(terminalIds);
			if (isDel && null != cache) {
				for (String terminalId : terminalIds) {
					String cache_key = RedisCache.CAHCENAME
							+ "|getProductByTerminalId|" + terminalId;

					List<Product> productList = cache.getListCache(cache_key,
							Product.class);
					if (CollectionUtils.isNotEmpty(productList)) {
						cache.deleteCacheWithPattern(cache_key);
					}
				}
			}
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":删除终端出错", e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			isDel = false;
		}
		return isDel;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean turnTerminalByArray(String[] terminalIds) {
		boolean isTurn = true;
		try {
			isTurn = terminalDao.turnTerminalByArray(terminalIds);
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":终端转正式出错", e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			isTurn = false;
		}
		return isTurn;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public DeepureResult importTerminalExcel(String targetPath,
			MultipartFile upFile) {
		boolean isImport = false;

		String sourceName = upFile.getOriginalFilename(); // 原始文件名

		File file = new File(targetPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			String path = targetPath + File.separator + sourceName;

			upFile.transferTo(new File(path));

			// 上传成功后读取Excel表格里面的数据
			FileInputStream fin = new FileInputStream(new File(path));
			List<Terminal> newTerminalList = new LinkedList<>();

			List<Terminal> terminalList = (List<Terminal>) terminalReadExcel
					.getExcelInfo(fin, path);
			if (CollectionUtils.isNotEmpty(terminalList)) {
				for (Terminal terminal : terminalList) {
					if (terminal.getTerminalType() != null
							&& terminal.getTerminalType() == 0) {
						newTerminalList.add(terminal);
					}
				}
				isImport = this.addTerminal(terminalList);
			}
		} catch (Exception e) {
			logger.error("导入终端失败：" + e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		if (!isImport) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return DeepureResult.result(false, "导入终端失败");
		}
		return DeepureResult.result(true, "导入终端成功");

	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addTerminal(List<Terminal> terminalList) {
		List<Terminal> updateList = new ArrayList<>();
		List<Terminal> addList = new ArrayList<>();
		List<Terminal> dbTerminalList = terminalDao.findAllTerminal();
		String dbTerminallId = getDbTerminalId(dbTerminalList);
		for (Terminal terminal : terminalList) {
			String terminalId = terminal.getTerminalId();
			int haveFlag = dbTerminallId.indexOf(terminalId);
			if (haveFlag != -1 && terminalId != null && terminalId.length() > 0) {
				if (terminal.getErpCode() != null
						&& terminal.getErpCode().length() > 0) {
					terminal.setTerminalStatus(1);
				}
				updateList.add(terminal);
				// terminal.setTerminalId(terminalDao.findTerminalByCode(terminal.getErpCode()).getTerminalId());
				// updateList.add(terminal);

			} else {
				if (terminal.getErpCode() != null
						&& terminal.getErpCode().length() > 0) {
					terminal.setTerminalId(IdGenerator.generateCode(
							IdGenerator.TERMINAL_PREFIX, 3));
					terminal.setTerminalStatus(1);
					addList.add(terminal);
				}
			}
		}
		boolean flag = true;
		try {
			if (terminalDao.delAllTerminal(0)) {
				if (updateList != null && updateList.size() > 0) {
					for (Terminal terminal : updateList) {
						terminal.setIsDelete(0);
						terminalDao.updateTerminal(terminal);
					}
				}

				if (addList != null && addList.size() > 0) {
					for (Terminal terminal : addList) {
						String stationCode = terminal.getStationCode();
						String officeCode = "";
						String zoneCode = "";
						Office office = stationDao
								.findOfficeByStationCode(stationCode);
						if (null != office) {
							officeCode = office.getOfficeCode();
							Zone zone = officeDao
									.findZoneByOfficeCode(officeCode);
							if (null != zone) {
								zoneCode = zone.getZoneCode();
							}
						}

						// String terminalId =
						// IdGenerator.generateCode(IdGenerator.TERMINAL_PREFIX,3);
						terminal.setTerminalType(0);
						// terminal.setTerminalId(terminalId);
						terminal.setOfficeCode(officeCode);
						terminal.setZoneCode(zoneCode);
						terminal.setCreateDate(new Date());
						// terminal.setTerminalStatus(0);;
						terminal.setIsDelete(0);
						terminalDao.insertTerminal(terminal);
					}
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	private String getDbTerminalId(List<Terminal> dbTerminalList) {
		String terminalId = "";
		for (Terminal terminal : dbTerminalList) {
			String terminalIdParam = terminal.getTerminalId();
			terminalId += terminalIdParam + ",";
		}
		return terminalId;
	}

	/**
	 * 终端销售计划列表
	 */
	@Override
	public PageList<SalePlan> findTerminalSalePlanForPage(
			SearchForm searchForm, int curPageSize, int limit, String planYear,
			String agentId, String terminalId, String zoneCode,
			String planMonth, String stationCode) {
		PageList<SalePlan> pageList = null;
		List<Role> roleList = securityDao.getRolesByUserId(searchForm
				.getUserId());
		Role role = CollectionUtils.isNotEmpty(roleList) ? roleList.get(0)
				: null;
		if (null != role) {
			searchForm.setRoleCode(role.getCode());
			searchForm.setRoleType(SearchEnum.SALEPLAN.getValue());
			securityService.changeSearchForm(searchForm);
		}
		if (curPageSize != 0 && limit != 0) {
			PageBounds pageBounds = new PageBounds(curPageSize, limit);
			pageList = (PageList<SalePlan>) this.terminalDao
					.findTerminalSalePlanForPage(searchForm, pageBounds,
							planYear, agentId, terminalId, zoneCode, planMonth,
							stationCode);
		}
		return pageList;
	}

	/**
	 * 查询所有终端
	 */
	@Override
	public List<Terminal> queryAllTerminal() {
		return terminalDao.findAllTerminal();
	}

	/**
	 * 根据terminalId查询该终端下的所有产品
	 */
	@Override
	public List<Product> findProductsByTerminalId(String terminalId) {
		List<Product> products = new LinkedList<Product>();
		try {
			if (StringUtils.isNotEmpty(terminalId)) {
				String cache_key = RedisCache.CAHCENAME
						+ "|getProductByTerminalId|" + terminalId;

				products = cache.getListCache(cache_key, Product.class);
				if (CollectionUtils.isEmpty(products)) {
					Terminal terminal = terminalDao
							.findTerminalById(terminalId);
					String productCategoryIds = terminal
							.getProductCategoryIds();

					if (StringUtils.isNotEmpty(productCategoryIds)) {
						List<String> ids = new ArrayList<>();
						for (String id : productCategoryIds.split(",")) {
							ids.add(id);
						}
						products = productDao.findProductsByCategoryCode(ids);
					}
					// System.out.println("totalCount: "+
					// pageList.getPaginator().getTotalCount());
					cache.putListCacheWithExpireTime(cache_key, products,
							RedisCache.CAHCETIME);
					logger.info("put cache with key:" + cache_key);
				}
			}
		} catch (Exception e) {
			logger.error(ResultEnum.REDIS_ERROR.getMsg(), e);
			Terminal terminal = terminalDao.findTerminalById(terminalId);
			String productCategoryIds = terminal.getProductCategoryIds();

			if (StringUtils.isNotEmpty(productCategoryIds)) {
				List<String> ids = new ArrayList<>();
				for (String id : productCategoryIds.split(",")) {
					ids.add(id);
				}
				products = productDao.findProductsByCategoryCode(ids);
			}
		}

		return products;
	}

	@Override
	public Integer findStationIdById(String terminalId) {
		return terminalDao.findStationIdById(terminalId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addSalePlan(SalePlan salePlan) {
		if (StringUtils.isNotEmpty(salePlan.getTerminalId())) {
			String agentId = terminalDao.findAgentIdById(salePlan
					.getTerminalId());
			salePlan.setAgentId(agentId);
			String hierarchyId = terminalDao.findHierarchyIdById(salePlan
					.getTerminalId());
			salePlan.setHierarchyId(hierarchyId);
			String channelId = terminalDao.findChannelIdById(salePlan
					.getTerminalId());
			salePlan.setChannelId(channelId);
		}

		boolean isAddSalePlan = terminalDao.insertSalePlan(salePlan);
		boolean isAddPlanItem = true;
		if (CollectionUtils.isNotEmpty(salePlan.getPlanItemList())) {
			for (PlanItem planItem : salePlan.getPlanItemList()) {
				if (!terminalDao.insertSalePlanItem(planItem)) {
					isAddPlanItem = false;
				}
			}
		}
		return isAddSalePlan && isAddPlanItem;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delSalePlanByArray(String[] salePlanIds) {
		boolean isDel = true;
		try {
			isDel = terminalDao.delSalePlanByArray(salePlanIds);
			// isDelItem = terminalDao.delPlanItemByArray(salePlanIds);
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":删除销售计划出错", e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			isDel = false;
		}

		return isDel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanItem> findPlanItemById(String salePlanId) {
		if (StringUtils.isEmpty(salePlanId)) {
			return ListUtils.EMPTY_LIST;
		}
		return terminalDao.findPlanItemById(salePlanId);
	}

	@Override
	public SalePlan findSalePlanById(String salePlanId) {
		if (StringUtils.isEmpty(salePlanId)) {
			return null;
		}
		return terminalDao.findSalePlanById(salePlanId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateSalePlanItem(String salePlanId,
			List<PlanItem> planItems) {
		boolean isAddPlanItem = true;
		boolean isDelPlanItem = true;
		String[] salePlanIds = new String[] { salePlanId };
		if (CollectionUtils
				.isNotEmpty(terminalDao.findPlanItemById(salePlanId))) {
			isDelPlanItem = terminalDao.delPlanItemByArray(salePlanIds);
		} else {
			isDelPlanItem = true;
		}

		if (isDelPlanItem && CollectionUtils.isNotEmpty(planItems)) {
			for (PlanItem planItem : planItems) {
				if (!terminalDao.insertSalePlanItem(planItem)) {
					isAddPlanItem = false;
				}
			}

		} else if (CollectionUtils.isEmpty(planItems)) {
			isAddPlanItem = false;
		}
		if (!(isAddPlanItem && isDelPlanItem)) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return isAddPlanItem && isDelPlanItem;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateSalePlan(SalePlan salePlan) {
		return terminalDao.updateSalePlan(salePlan);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult importExcel(String targetPath, MultipartFile upFile) {
		String sourceName = upFile.getOriginalFilename(); // 原始文件名
		DeepureResult result = new DeepureResult();
		List<SalePlan> planList = new ArrayList<>();
		File file = new File(targetPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			String path = targetPath + File.separator + sourceName;

			upFile.transferTo(new File(path));
			// 上传成功后读取Excel表格里面的数据
			FileInputStream fin = new FileInputStream(new File(path));
			planList = (List<SalePlan>) salePlanReadExcel.getExcelInfo(fin,
					path);
			result = salePlanReadExcel.validUpload(planList);

		} catch (Exception e) {
			logger.error("导入销售计划失败：" + e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		if (result.getStatus()) {
			if (CollectionUtils.isNotEmpty(planList)) {
				return DeepureResult.importResult(this.addSalePlan(planList));
			} else {
				return DeepureResult.result(false, "导入的计划列表为空！");
			}
		} else {
			return result;
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addSalePlan(List<SalePlan> planList) {
		boolean isAdd = true;
		// boolean isDelete=this.terminalDao.deleteAllZone();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		// List<SalePlan> newList = new ArrayList<>();
		List<PlanItem> planItemlist = null;
		String salePlanId = "";
		int j = 0;
		SalePlan salePlan = null;
		BigDecimal totalPrice = new BigDecimal(0);
		for (int i = 0; i < planList.size(); i++) {

			boolean isExists = false;

			if (i != 0) {
				isExists = planList.get(i).getSalePlanDate()
						.equals(planList.get(i - 1).getSalePlanDate())
						&& planList.get(i).getTerminalId()
								.equals(planList.get(i - 1).getTerminalId());
			}
			// 判断是否是第一个，或和上一个是不是一致
			if (i == 0 || !isExists) {
				j = 0;

				if (terminalDao.querySalePlanByTerminalIdAndDate(planList
						.get(i).getTerminalId(), sdf.format(planList.get(i)
						.getSalePlanDate())) == null) {
					salePlan = planList.get(i);
					totalPrice = new BigDecimal(0);
					planItemlist = new LinkedList<>();
					salePlanId = IdGenerator.generateCode(
							IdGenerator.SALE_PLAN_PREFIX, 3);
					salePlan.setSalePlanId(salePlanId);
					Integer stationId = terminalService
							.findStationIdById(planList.get(i).getTerminalId());
					Integer officeId = stationService
							.findOfficeIdById(stationId);
					Integer zoneId = officeService.findZoneIdById(String
							.valueOf(officeId));

					User currentUser = (User) SecurityUtils.getSubject()
							.getSession().getAttribute("user");
					String createBy = currentUser.getUsername() + "_excel";
					salePlan.setStationId(String.valueOf(stationId));
					salePlan.setOfficeId(String.valueOf(officeId));
					salePlan.setZoneId(String.valueOf(zoneId));
					salePlan.setCreateBy(createBy);
					salePlan.setSalePlanStatus(0);
					// terminalService.addSalePlan(salePlan);
				}
			}
			if (terminalDao.querySalePlanByTerminalIdAndDate(planList.get(i)
					.getTerminalId(), sdf.format(planList.get(i)
					.getSalePlanDate())) == null) {

				Integer basequantity = planList.get(i).getPlanItemList().get(0)
						.getBaseQuantity();
				PlanItem planItem = new PlanItem();
				planItem.setSalePlanId(salePlanId);
				planItem.setBaseQuantity(basequantity);
				planItem.setSkuId(planList.get(i).getPlanItemList().get(0)
						.getSkuId());
				planItem.setSalePlanItemId(IdGenerator.generateItemCode(
						salePlanId, j));
				Product product = new Product();
				try {
					product = productService.findProductByCode(planList.get(i)
							.getPlanItemList().get(0).getSkuId());
				} catch (Exception e) {
					logger.error("导入销售计划失败：" + e.getMessage(), e);
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
				}
				planItem.setSkuName(product.getName());
				planItem.setBaseUnit(product.getErpUnit());
				planItem.setBasePrice(product.getUnitPrice());
				planItem.setCount(basequantity * product.getUnitQuantity());
				planItem.setMinUnit(product.getErpMinUnit());
				planItem.setTotalPrice(new BigDecimal(basequantity)
						.multiply(product.getUnitPrice()));
				totalPrice = totalPrice.add(planItem.getTotalPrice());
				j++;
				planItemlist.add(planItem);
				if (i != planList.size() - 1
						&& !(planList.get(i + 1).getSalePlanDate()
								.equals(planList.get(i).getSalePlanDate()) && planList
								.get(i + 1).getTerminalId()
								.equals(planList.get(i).getTerminalId()))) {
					salePlan.setTotalPrice(totalPrice);
					salePlan.setPlanItemList(planItemlist);
					isAdd = terminalService.addSalePlan(salePlan);
				} else if (i == planList.size() - 1) {
					salePlan.setTotalPrice(totalPrice);
					salePlan.setPlanItemList(planItemlist);
					isAdd = terminalService.addSalePlan(salePlan);
				}
			}
		}

		return isAdd;
	}

	@Override
	public PageList<SalePlan> findTerminalFlowForPage(SearchForm searchForm,
			int curPageSize, int limit, String planYear, String planMonth,
			String terminalId, String terminalName) {
		PageList<SalePlan> pageList = null;
		List<Role> roleList = securityDao.getRolesByUserId(searchForm
				.getUserId());
		Role role = CollectionUtils.isNotEmpty(roleList) ? roleList.get(0)
				: null;
		if (null != role) {
			searchForm.setRoleCode(role.getCode());
			searchForm.setRoleType(SearchEnum.TERMINALFLOW.getValue());
			securityService.changeSearchForm(searchForm);
		}
		if (curPageSize != 0 && limit != 0) {
			PageBounds pageBounds = new PageBounds(curPageSize, limit);
			pageList = (PageList<SalePlan>) this.terminalDao
					.findTerminalFlowForPage(searchForm, pageBounds, planYear,
							planMonth, terminalId, terminalName);
		}
		return pageList;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delTerminalFlowByArray(String[] terminalFlowIds) {
		boolean isDel = true;
		// boolean isDelItem=true;
		try {
			isDel = terminalDao.delTerminalFlowByArray(terminalFlowIds);
			/*
			 * isDelItem =
			 * terminalDao.delTerminalFlowItemByArray(terminalFlowIds);
			 */
		} catch (Exception e) {
			logger.error(ResultEnum.INNER_ERROR.getMsg() + ":删除终端流向出错", e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			isDel = false;
		}
		return isDel;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addTerminalFlow(TerminalFlow terminalFlow) {
		boolean isAddBase = terminalDao.insertTerminalFlow(terminalFlow);
		boolean isAddItem = true;
		if (CollectionUtils.isNotEmpty(terminalFlow.getTerminalFlowItemList())) {
			for (TerminalFlowItem terminalFlowItem : terminalFlow
					.getTerminalFlowItemList()) {
				if (!terminalDao.insertTerminalFlowItem(terminalFlowItem)) {
					isAddItem = false;
				}
			}
		}
		return isAddBase && isAddItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalFlowItem> findTerminalFlowItemById(String terminalFlowId) {
		if (StringUtils.isEmpty(terminalFlowId)) {
			return ListUtils.EMPTY_LIST;
		}
		return terminalDao.findTerminalFlowItemById(terminalFlowId);
	}

	@Override
	public TerminalFlow findTerminalFlowById(String terminalFlowId) {
		if (StringUtils.isEmpty(terminalFlowId)) {
			return null;
		}
		return terminalDao.queryTerminalFlowById(terminalFlowId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateTerminalFlow(TerminalFlow terminalFlow) {
		boolean isAddPlanItem = true;
		boolean isDelPlanItem = true;
		String[] terminalFlowIds = new String[] { terminalFlow
				.getTerminalFlowId() };
		List<TerminalFlowItem> terminalFlowItems = terminalDao
				.findTerminalFlowItemById(terminalFlow.getTerminalFlowId());
		if (CollectionUtils.isEmpty(terminalFlowItems)) {
			isDelPlanItem = true;
		} else {
			isDelPlanItem = terminalDao
					.delTerminalFlowItemByArray(terminalFlowIds);
		}
		if (isDelPlanItem
				&& CollectionUtils.isNotEmpty(terminalFlow
						.getTerminalFlowItemList())) {
			for (TerminalFlowItem terminalFlowItem : terminalFlow
					.getTerminalFlowItemList()) {
				if (!terminalDao.insertTerminalFlowItem(terminalFlowItem)) {
					isAddPlanItem = false;
					break;
				}
			}

		} else if (CollectionUtils.isEmpty(terminalFlow
				.getTerminalFlowItemList())) {
			isAddPlanItem = true;
		}
		if (!(isAddPlanItem && isDelPlanItem)) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return isAddPlanItem && isDelPlanItem;
	}

	@Override
	public SalePlan findSalePlanByTerminalIdAndDate(String terminalId,
			String planMonth) {

		return terminalDao.querySalePlanByTerminalIdAndDate(terminalId,
				planMonth);
	}

	@Override
	public TerminalFlow findTerminalFlowByTerminalIdAndDate(String terminalId,
			String planMonth) {
		return terminalDao.queryTerminalFlowByTerminalIdAndDate(terminalId,
				planMonth);
	}

	@Override
	public List<Terminal> findTerminalByAgentId(String agentId) {
		List<Terminal> terminalList1 = terminalDao
				.findTerminalByAgentId(agentId);
		List<Terminal> terminalList2 = this.queryTerminalByRole();
		List<Terminal> terminalList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(terminalList1)
				&& CollectionUtils.isNotEmpty(terminalList2)) {
			for (Terminal terminal1 : terminalList1) {
				for (Terminal terminal2 : terminalList2) {
					String terminalId1 = terminal1.getTerminalId();
					String terminalId2 = terminal2.getTerminalId();
					if (terminalId1.equals(terminalId2)) {
						terminalList.add(terminal1);
					}
				}
			}
		} else if (CollectionUtils.isEmpty(terminalList2)) {
			return null;
		}
		return terminalList;

	}

	@Override
	public boolean exportSalePlanExcel() {
		List<String> theader = new ArrayList<String>();
		theader.add("销售计划日期");
		theader.add("对应终端编号");
		theader.add("对应产品编号");
		theader.add("产品数量");
		List<List<Object>> tValue = new ArrayList<List<Object>>();
		List<String> salePlanIds = terminalDao.findAllSalePlanId();
		List<PlanItem> planItemList = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(salePlanIds)) {
			planItemList = terminalDao.findPlanItemByArray(salePlanIds);
		}
		for (PlanItem planItem : planItemList) {
			List<Object> temp = new ArrayList<Object>();
			SalePlan salePlan = terminalDao.findSalePlanById(planItem
					.getSalePlanId());
			temp.add(DateUtil.formatDateByFormat(salePlan.getSalePlanDate(),
					"yyyy-MM"));
			temp.add(terminalDao.findTerminalById(salePlan.getTerminalId())
					.getErpCode());
			temp.add(planItem.getSkuId());
			temp.add(planItem.getBaseQuantity());
			tValue.add(temp);
		}
		try {
			ExcelPoiUtils.exportExcel("销售计划导出表", theader, tValue, null, null,
					"销售计划导出模板.xlsx");
		} catch (Exception e) {
			logger.error("导出终端销售计划出错:", e);
			return false;
		}
		return true;
	}

	@Override
	public DeepureResult isFullTerminal(String terminalId) {
		Terminal terminal = terminalDao.findTerminalById(terminalId);
		Integer stationId = terminalService.findStationIdById(terminalId);
		Integer officeId = stationService.findOfficeIdById(stationId);
		Integer zoneId = officeService.findZoneIdById(String.valueOf(officeId));
		if (stationId == 0 || officeId == 0 || zoneId == 0) {
			return DeepureResult.result(false, "终端已失效！");
		}
		boolean isFull = (terminal.getAgentId() != null && !"".equals(terminal
				.getAgentId()))
				&& (terminal.getChannelId() != null && !"".equals(terminal
						.getChannelId()))
				&& (terminal.getHierarchyId() != null && !"".equals(terminal
						.getHierarchyId()))
				&& (terminal.getOfficeCode() != null && !"".equals(terminal
						.getOfficeCode()))
				&& (terminal.getStationCode() != null && !"".equals(terminal
						.getStationCode()))
				&& (terminal.getZoneCode() != null && !"".equals(terminal
						.getZoneCode()));
		if (isFull) {
			if (terminal.getProductCategoryIds() != null
					&& !"".equals(terminal.getProductCategoryIds())) {
				return DeepureResult.success();
			} else {
				return DeepureResult.result(false,
						"终端下产品分类为空，请先维护该终端下的产品分类再添加销售计划！");
			}
		} else {
			return DeepureResult.result(false, "请先完善终端信息！");
		}
	}

	@Override
	public List<Terminal> queryTerminalByRole() {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		Zone zone = zoneDao.findZoneByUser(currentUser.getId());
		Office office = officeDao.findOfficeByUser(currentUser.getId());
		Station station = stationDao.findStationByUser(currentUser.getId());
		List<UserRoleRelationship> userRole = securityDao
				.findUserRoleByUserId(currentUser.getId());
		List<Terminal> terminalList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(userRole)) {
			Integer roleId = (int) userRole.get(0).getRoleId();
			switch (roleId) {
			case 1:
				terminalList = terminalDao.findAllTerminal();
				break;
			case 2:
				terminalList = terminalDao.findTerminalByZoneCode(zone
						.getZoneCode());
				break;
			case 3:
				terminalList = terminalDao.findTerminalByOfficeCode(office
						.getOfficeCode());
				break;
			case 4:
				terminalList = terminalDao.findTerminalByStationCode(station
						.getStationCode());
				break;
			default:
				break;
			}
		}
		return terminalList;
	}
}
