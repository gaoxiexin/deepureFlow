package com.tasly.deepureflow.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IHierarchyService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.PaginatorResult;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  HierarchyController   
 * @Description: 体系Controller
 * @author:高燮訢
 * @date:   Nov 24, 2016 2:51:47 PM   
 *
 */
@Controller
@RequestMapping("/hierarchy")
@SessionAttributes("selectItem")
public class HierarchyController {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(TerminalController.class.getName());
	
	@Resource
	private IHierarchyService hierarchyService;
	
	@RequestMapping(value="/hierarchyList")  
	@ApiOperation(value="展示体系列表", httpMethod ="POST", response=String.class, notes ="展示体系列表")  
    public String channelList(HttpServletRequest request,Model model){  
        model.addAttribute("selectItem", MenuEnum.HIERARCHY.getId());
        return "/system/hierarchyList";  
    }
	
	@RequestMapping(value="/queryHierarchy") 
	@ResponseBody
    public List<Hierarchy> queryHierarchy(HttpServletRequest request,Model model){  
		List<Hierarchy> hierarchyList = hierarchyService.queryAllHierarchy();
		return hierarchyList;
    }
	@RequestMapping(value="/hierarchyPage",method = RequestMethod.POST)  
    public @ResponseBody PaginatorResult<Hierarchy> channelPage(HttpServletRequest request,Model model,@RequestParam("hierarchyName") final String hierarchyName,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit){  
		PaginatorResult<Hierarchy> result=new PaginatorResult<Hierarchy>();
		PageList<Hierarchy> hierarchyList=hierarchyService.findHierarchyForPage(curPageSize, limit,hierarchyName);
		if(!CollectionUtils.isEmpty(hierarchyList)){
			result.setRows(hierarchyList);
			result.setTotal(hierarchyList.getPaginator().getTotalCount());
		}
        return result;  
    }
	
	@RequestMapping(value = "/addHierarchy", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult addHierarchy(@RequestParam("hierarchyName") final String hierarchyName,@RequestParam("hierarchyNick") final String hierarchyNick) {
		DeepureResult addResult=this.hierarchyService.addHierarchy(hierarchyName, hierarchyNick);
		return addResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateName", method = RequestMethod.POST)
	public boolean validateName(@RequestParam("hierarchyName") String hierarchyName,
			@RequestParam("hierarchyId") String hierarchyId) {
		return hierarchyService.validateName(hierarchyName,hierarchyId);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/delHierarchy", method = RequestMethod.POST)
	public DeepureResult delHierarchy(@RequestParam("hierarchyList") final Integer[] hierarchyList) {
		if(ArrayUtils.isNotEmpty(hierarchyList)){
			return this.hierarchyService.delHierarchyByArray(hierarchyList);
		}else{
			return DeepureResult.result(false, "当前体系列表为空");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/editHierarchy", method = RequestMethod.POST)
	public DeepureResult editHierarchy(@RequestParam("hierarchyId") final Integer hierarchyId,@RequestParam("hierarchyName") final String hierarchyName,@RequestParam("hierarchyNick") final String hierarchyNick) {
		if(null!=hierarchyId&&StringUtils.isNotEmpty(hierarchyName)){
			return this.hierarchyService.editHierarchy(hierarchyId, hierarchyName, hierarchyNick);
		}else{
			return DeepureResult.result(false, "体系名称不能为空，修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryHierarchyByCondition", method = RequestMethod.POST)
	public PaginatorResult<Hierarchy> queryHierarchyByCondition(@RequestParam("hierarchyName") final String hierarchyName,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit										
			) {
		PaginatorResult<Hierarchy> result=new PaginatorResult<Hierarchy>();
		PageList<Hierarchy> hierarchyList = null;
		    hierarchyList = (PageList<Hierarchy>) hierarchyService.queryHierarchyByName(curPageSize, limit,hierarchyName);
			result.setRows(hierarchyList);
			result.setTotal(hierarchyList.getPaginator().getTotalCount());
	        return result;
	}
	
	
}
