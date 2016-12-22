package com.tasly.deepureflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.tasly.deepureflow.dao.IAgentDao;
import com.tasly.deepureflow.dao.IOfficeDao;
import com.tasly.deepureflow.dao.ISecurityDao;
import com.tasly.deepureflow.dao.IStationDao;
import com.tasly.deepureflow.dao.ITerminalDao;
import com.tasly.deepureflow.domain.deepureflow.AgentFlow;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;
import com.tasly.deepureflow.domain.deepureflow.AgentFlowStockItem;
import com.tasly.deepureflow.domain.security.Role;
import com.tasly.deepureflow.domain.security.UserRoleRelationship;
import com.tasly.deepureflow.domain.user.Agent;
import com.tasly.deepureflow.domain.user.AgentForExport;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.dto.SearchForm;
import com.tasly.deepureflow.enums.ResultEnum;
import com.tasly.deepureflow.enums.SearchEnum;
import com.tasly.deepureflow.service.IAgentService;
import com.tasly.deepureflow.service.ISecurityService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.IdGenerator;
import com.tasly.deepureflow.util.excel.impl.AgentReadExcel;
@Service("agentService") 
public class AgentServiceImpl implements IAgentService {
	private final Logger logger = Logger.getLogger(AgentServiceImpl.class.getName());
	@Autowired
	private ISecurityDao securityDao;

	@Autowired
	private AgentReadExcel agentReadExcel;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private IAgentDao agentDao;

	@Autowired
	private ITerminalDao terminalDao; 

	@Autowired
	private IOfficeDao officeDao;

	@Autowired
	private IStationDao stationDao;

	@Override
	public PageList<Agent> findAgentForPage(SearchForm searchForm,int curPageSize, int limit,String queryAgentId,String queryAgentStartTime,String queryAgentEndTime,String queryAgentName,String queryAgentType,String queryAgentVirtual) {
		PageList<Agent> pageList=null;
		List<Role> roleList=securityDao.getRolesByUserId(searchForm.getUserId());
		Role role=CollectionUtils.isNotEmpty(roleList)?roleList.get(0):null;
		if(null!=role){
			searchForm.setRoleCode(role.getCode());
			searchForm.setRoleType(SearchEnum.AGENT.getValue());
			securityService.changeSearchForm(searchForm);
		}
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<Agent>)this.agentDao.findAgentForPage(searchForm,pageBounds,queryAgentId,queryAgentStartTime,queryAgentEndTime,queryAgentName,queryAgentType,queryAgentVirtual);
		}
		return pageList;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addAgent(String agentId, String agentName,
			String hierarchyId, String planDate, Integer isVirtual) {
		if(StringUtils.isNotEmpty(agentId)&&StringUtils.isNotEmpty(agentName)){
			return agentDao.insertAgent(agentId,agentName,hierarchyId,planDate,isVirtual);
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delAgentByArray(String[] agentIds) {
		boolean isDel=true;
		try{
			isDel=agentDao.deleteAgentByArray(agentIds);
		}catch(Exception e){
			logger.error(ResultEnum.INNER_ERROR.getMsg()+":删除用户出错",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			isDel=false;
		}
		return isDel;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean editAgent(String agentId, String agentName,
			String hierarchyId, String planDate, Integer isVirtual) {
		if(StringUtils.isNotEmpty(agentId)&&StringUtils.isNotEmpty(agentName)){
			return agentDao.updateAgent(agentId,agentName,hierarchyId,planDate,isVirtual);
		}
		return false;
	}

	@Override
	public List<Agent> queryAllAgent() {
		return agentDao.findAllAgent();
	}

	@Override
	public PageList<AgentFlow> findAgentFlowForPage(SearchForm searchForm,
			int curPageSize, int limit, String planYear, String planMonth,
			String agentId,String agentName, String createBy) {
		PageList<AgentFlow> pageList=null;
		List<Role> roleList=securityDao.getRolesByUserId(searchForm.getUserId());
		Role role=CollectionUtils.isNotEmpty(roleList)?roleList.get(0):null;
		if(null!=role){
			searchForm.setRoleCode(role.getCode());
			searchForm.setRoleType(SearchEnum.AGENTFLOW.getValue());
			securityService.changeSearchForm(searchForm);
		}
		if(curPageSize!=0&&limit!=0){
			PageBounds pageBounds = new PageBounds(curPageSize, limit);  
			pageList = (PageList<AgentFlow>)this.agentDao.findAgentFlowForPage(searchForm,pageBounds,planYear,planMonth,agentId,agentName,createBy);
		}
		return pageList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentFlowShipmentItem> findAgentFlowShipmentItemById(String agentFlowId) {
		if(StringUtils.isEmpty(agentFlowId)){
			return ListUtils.EMPTY_LIST;
		}
		return agentDao.findAgentFlowShipmentItemById(agentFlowId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentFlowStockItem> findAgentFlowStockItemById(String agentFlowId) {
		if(StringUtils.isEmpty(agentFlowId)){
			return ListUtils.EMPTY_LIST;
		}
		return agentDao.findAgentFlowStockItemById(agentFlowId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delAgentFlowByArray(String[] agentFlowIds) {
		boolean isDel=true;
		try{
			isDel=agentDao.delAgentFlowByArray(agentFlowIds);
		}catch(Exception e){
			logger.error(ResultEnum.INNER_ERROR.getMsg()+":删除经销商产品流向出错",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			isDel=false;
		}
		return isDel;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addAgentFlow(AgentFlow agentFlow) {
		boolean isAddAgentFlow = true;
		boolean isAddAgentFlowShipmentItem = true;
		boolean isAddAgentFlowStockItem = true;
		isAddAgentFlow = agentDao.insertAgentFlow(agentFlow);
		if(CollectionUtils.isNotEmpty(agentFlow.getAgentFlowShipmentItemList())){
			for(AgentFlowShipmentItem shipmentItem : agentFlow.getAgentFlowShipmentItemList()){
				if(StringUtils.isNotEmpty(shipmentItem.getTerminalId())){
					String terminalId = shipmentItem.getTerminalId();
					Integer stationId = terminalDao.findStationIdById(terminalId);
					Integer officeId = stationDao.findOfficeIdById(String.valueOf(stationId));
					Integer zoneId = officeDao.findZoneIdById(String.valueOf(officeId));
					String hierarchyId=terminalDao.findHierarchyIdById(terminalId);
					String channelId=terminalDao.findChannelIdById(terminalId);
					shipmentItem.setStationId(String.valueOf(stationId));
					shipmentItem.setOfficeId(String.valueOf(officeId));
					shipmentItem.setZoneId(String.valueOf(zoneId));
					shipmentItem.setHierarchyId(hierarchyId);
					shipmentItem.setChannelId(channelId);
				}
				if(!agentDao.insertAgentFlowShipmentItem(shipmentItem)){
					isAddAgentFlowShipmentItem = false;
				}
			}
		}

		if(CollectionUtils.isNotEmpty(agentFlow.getAgentFlowStockItemList())){
			for(AgentFlowStockItem stockItem : agentFlow.getAgentFlowStockItemList()){
				if(!agentDao.insertAgentFlowStockItem(stockItem)){
					isAddAgentFlowStockItem = false;
				}
			}
		}
		boolean flag = isAddAgentFlow&&isAddAgentFlowShipmentItem&&isAddAgentFlowStockItem;
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult importAgentExcel(String targetPath, MultipartFile upFile,User currentUser) {
		boolean isImport=false;

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
			@SuppressWarnings("unchecked")
			List<Agent> agentList=(List<Agent>) agentReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(agentList)){
				isImport=this.addOrUpdateAgent(agentList,currentUser);
			}
		} catch (Exception e) {
			logger.error("导入经销商失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DeepureResult.result(false, "导入经销商失败");
		}

		return DeepureResult.result(true, "导入经销商成功");
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addOrUpdateAgent(List<Agent> agentList,User currentUser) {
		boolean flag =true;
		List<Agent> addList = new ArrayList<Agent>();
		List<Agent> updateList = new ArrayList<Agent>();
		List<Agent> dbAgentList = agentDao.findAllAgent();
		boolean isDelAll=agentDao.delAllAgent();
		
		if(isDelAll){
			String dbAgentlId = getDbAgentId(dbAgentList);
			for(Agent agent : agentList){
				String agentId = agent.getAgentId();
				int haveFlag = dbAgentlId.indexOf(agentId);
				if(haveFlag != -1 && agentId!=null && agentId.length()>0){
					if(agent.getErpCode()!=null && agent.getErpCode().length()>0){
						agent.setAgentStatus("1");
					}else{
						agent.setAgentStatus("0");
					}
					agent.setIsDelete(0);
					updateList.add(agent);
					
				}else{
					if(agent.getErpCode()!=null && agent.getErpCode().length()>0){
						agent.setAgentId(IdGenerator.generateCode(IdGenerator.AGENT_PREFIX, 3));
						agent.setAgentStatus("1");
						//Station station = stationDao.findStationByUser(currentUser.getId());
						//agent.setStationId(station.getStationId()==null?"":station.getStationId().toString());
						addList.add(agent);
					}
				}
			}

			try {
				if(addList!=null && addList.size()>0){
					agentDao.insertExcelAgent(addList);
				}
				if(updateList!=null && updateList.size()>0){
					agentDao.updateExcelAgent(updateList);
				}
			} catch (Exception e) {
				flag = false;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		
		return flag;
	}

	
	private String getDbAgentId(List<Agent> dbAgentList) {
		String agentlId = "";
		for(Agent agent : dbAgentList){
			String agentlIdParam = agent.getAgentId();
			agentlId += agentlIdParam + ",";
		}
		return agentlId;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addAgent(List<Agent> agentList) {
		List<Agent> isExistsList = new ArrayList<Agent>();
		List<Agent> notExistsList = new ArrayList<Agent>();
		for(Agent agent:agentList){
			Agent agent_db = agentDao.findAgentByCode(agent.getErpCode());
			if(null==agent_db){
				notExistsList.add(agent);
			}else{
				agent.setAgentId(agent_db.getAgentId());
				isExistsList.add(agent);
			}
		}
		if(agentDao.delAllAgent()){
			for(Agent agent:isExistsList){
				agent.setIsDelete(0);
				agentDao.updateAgents(agent);
			}
			for(Agent agent:notExistsList){
				String agentId = IdGenerator.generateCode(IdGenerator.AGENT_PREFIX,3);
/*				String stationId=agent.getStationId();
				String hierarchyId = agent.getHierarchyId();
*/				agent.setAgentId(agentId);
				agent.setAgentStatus("0");
				/*agent.setHierarchyId(hierarchyId);
				agent.setStationId(stationId);*/
				agent.setIsVirtual(1);
				agent.setCreateDate(new Date());
				agent.setIsDelete(0);
				agentDao.insertAgent(agent);
			}
			
		}
		return true;
	}

	@Override
	public AgentFlow findAgentFlowById(String agentFlowId) {
		if(StringUtils.isEmpty(agentFlowId)){
			return null;
		}
		return agentDao.queryAgentFlowById(agentFlowId);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateAgentFlow(AgentFlow agentFlow,String[] delAgentFlowItemIds) {
		//boolean isAddShipmentItem = false;
		boolean isAddStockItem = false;
		boolean isDelShipmentItem = false;
		boolean isDelStockItem = false;
		String agentFlowId = agentFlow.getAgentFlowId();
		String[] agentFlowIds = new String[]{agentFlowId};
		isDelShipmentItem = agentDao.delAgentFlowShipmentItemByArray(delAgentFlowItemIds);
		int i = agentDao.counAgentFlowItemById(agentFlowId);
		int j = 0;
		for(AgentFlowStockItem item : agentFlow.getAgentFlowStockItemList()){
			item.setAgentFlowId(agentFlowId);
			item.setAgentFlowShipmentItemId(IdGenerator.generateItemCode(agentFlowId, j));
			j++;
		}
		
		if(isDelShipmentItem&&CollectionUtils.isNotEmpty(agentFlow.getAgentFlowShipmentItemList())){
			for(AgentFlowShipmentItem agentFlowShipmentItem : agentFlow.getAgentFlowShipmentItemList()){
					
				if("".equals(agentFlowShipmentItem.getAgentFlowShipmentItemId())){
					String terminalId = agentFlowShipmentItem.getTerminalId();
					Integer stationId = terminalDao.findStationIdById(terminalId);
					Integer officeId = stationDao.findOfficeIdById(String.valueOf(stationId));
					Integer zoneId = officeDao.findZoneIdById(String.valueOf(officeId));
					String hierarchyId=terminalDao.findHierarchyIdById(terminalId);
					String channelId=terminalDao.findChannelIdById(terminalId);
					agentFlowShipmentItem.setStationId(String.valueOf(stationId));
					agentFlowShipmentItem.setOfficeId(String.valueOf(officeId));
					agentFlowShipmentItem.setZoneId(String.valueOf(zoneId));
					agentFlowShipmentItem.setHierarchyId(hierarchyId);
					agentFlowShipmentItem.setChannelId(channelId);
					agentFlowShipmentItem.setAgentFlowId(agentFlowId);
					agentFlowShipmentItem.setAgentFlowShipmentItemId(IdGenerator.generateItemCode(agentFlowId, i));
					agentDao.insertAgentFlowShipmentItem(agentFlowShipmentItem);
					i++;
				}else{
					agentDao.updateAgentFlowShipmentItem(agentFlowShipmentItem);
				}
			}

		}/*else if(CollectionUtils.isEmpty(agentFlow.getAgentFlowShipmentItemList())){
			isAddShipmentItem = true;
		}*/
		
		if(CollectionUtils.isNotEmpty(agentDao.findAgentFlowStockItemById(agentFlow.getAgentFlowId()))){
			isDelStockItem = agentDao.delAgentFlowStockItemByArray(agentFlowIds);
		}else{
			isDelStockItem = true;
		}
		if(isDelStockItem&&CollectionUtils.isNotEmpty(agentFlow.getAgentFlowStockItemList())){
			for(AgentFlowStockItem agentFlowStockItem : agentFlow.getAgentFlowStockItemList()){
				if(agentDao.insertAgentFlowStockItem(agentFlowStockItem)){
					isAddStockItem = true;
				}
			}
		}else if(CollectionUtils.isEmpty(agentFlow.getAgentFlowStockItemList())){
			isAddStockItem = true;
		}
		if(!isAddStockItem){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return isAddStockItem;
	}

	@Override
	public AgentFlow findAgentFlowByAgentIdAndDate(String agentId, String agentFlowDate) {
		return agentDao.queryAgentFlowByAgentIdAndDate(agentId,agentFlowDate);
	}

	@Override
	public List<AgentForExport> findAgentForExport(SearchForm searchForm) throws Exception{
		List<AgentForExport> list=null;
		List<Role> roleList=securityDao.getRolesByUserId(searchForm.getUserId());
		Role role=CollectionUtils.isNotEmpty(roleList)?roleList.get(0):null;
		if(null!=role){
			searchForm.setRoleCode(role.getCode());
			searchForm.setRoleType(SearchEnum.AGENT.getValue());
//			securityService.changeSearchForm(searchForm);
			searchForm.setQueryId("com.tasly.deepureflow.client.IAgentMapper.queryAllAgentForExport");
		}
		list = (List<AgentForExport>)this.agentDao.findAgentForExport(searchForm);
		return list;
	}

	@Override
	public List<Agent> findAgentByRole() {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		SearchForm searchForm=new SearchForm();

		searchForm.setUserId(currentUser.getId());
		List<UserRoleRelationship> userRole = securityDao.findUserRoleByUserId(currentUser.getId());
		List<Agent> agentList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userRole)){
			Integer roleId = (int) userRole.get(0).getRoleId();
			if(roleId!=4){
				agentList=this.findAgentForPage(searchForm,
						1,10000000, "", null,
						null, null, null,null);
			}else{
				agentList=agentDao.findAgentByUser(currentUser.getId()); 
			}
			
		}
		return agentList;
	}

	
	/*@Override
	public List<Agent> queryAgentByRole() {
		User currentUser = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
		Zone zone = zoneDao.findZoneByUser(currentUser.getId());
		Office office = officeDao.findOfficeByUser(currentUser.getId());
		List<UserRoleRelationship> userRole = securityDao.findUserRoleByUserId(currentUser.getId());
		List<Station> stationList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userRole)){
			Integer roleId = (int) userRole.get(0).getRoleId();
			switch (roleId) {
			case 1:
				stationList = stationDao.findAllStation();
				break;
			case 2:
				stationList = stationDao.findAgentByZoneCode(zone.getZoneCode());
				break;
			case 3:
				stationList = stationDao.findAgentByOfficeCode(office.getOfficeCode());
				break;
			case 4:
				stationList.add(currentUser.getStation());
				break;
			default:
				break;
			}
		}
		return stationList;
	}*/



}
