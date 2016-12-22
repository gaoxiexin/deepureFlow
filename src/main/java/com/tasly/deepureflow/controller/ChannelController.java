package com.tasly.deepureflow.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Channel;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IChannelService;
import com.tasly.deepureflow.service.IHierarchyService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.PaginatorResult;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  ChannelController   
 * @Description:渠道Controller
 * @author: 高燮訢
 * @date:   Nov 24, 2016 2:51:30 PM   
 *
 */
@Controller
@RequestMapping("/channel")
@SessionAttributes("selectItem")
public class ChannelController {
	
	@Resource
	private IChannelService channelService;
	
	@Resource
	private IHierarchyService hierarchyService;
	
	@RequestMapping(value="/channelList")   
	@ApiOperation(value="展示渠道列表", httpMethod ="POST", response=String.class, notes ="展示渠道列表")  
    public String channelList(HttpServletRequest request,Model model){  
        model.addAttribute("selectItem", MenuEnum.CHANNEL.getId());
        
        List<Hierarchy> hierarchyList=hierarchyService.queryAllHierarchy();
        model.addAttribute("hierarchyList",hierarchyList);
        return "/system/channelList";  
    }
	
	@RequestMapping(value="/channelPage",method = RequestMethod.GET)  
    public @ResponseBody PaginatorResult<Channel> channelPage(HttpServletRequest request,Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit){  
		PaginatorResult<Channel> result=new PaginatorResult<Channel>();
		PageList<Channel> channelList=channelService.findChannelForPage(curPageSize,limit);
		if(!CollectionUtils.isEmpty(channelList)){
			result.setRows(channelList);
			result.setTotal(channelList.getPaginator().getTotalCount());
		}
        return result;  
    }
	
	@ResponseBody
	@RequestMapping(value = "/addChannel", method = RequestMethod.POST)
	public DeepureResult addChannel(@RequestParam("channelName") final String channelName,@RequestParam("hierarchyId") final String hierarchyId) {
		return this.channelService.addChannel(channelName,hierarchyId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/delChannel", method = RequestMethod.POST)
	public DeepureResult delChannel(@RequestParam("channelList") final Integer[] channelList) {
		if(ArrayUtils.isNotEmpty(channelList)){
			return this.channelService.delChannelByArray(channelList);
		}
		return DeepureResult.result(false, "渠道列表为空，删除失败");
	}
	@ResponseBody
	@RequestMapping(value = "/validateName", method = RequestMethod.POST)
	public DeepureResult validateName(@RequestParam("channelName") String channelName,
			@RequestParam("channelId") String channelId,
			@RequestParam("hierarchyId") String hierarchyId) {
		return channelService.validateName(hierarchyId,channelName,channelId);
	}
		
	@ResponseBody
	@RequestMapping(value = "/editChannel", method = RequestMethod.POST)
	public DeepureResult editChannel(@RequestParam("channelId") final Integer channelId,@RequestParam("channelName") final String channelName,@RequestParam("hierarchyId") final String hierarchyId) {
		if(null!=channelId&&StringUtils.isNotEmpty(channelName)){
			return this.channelService.editChannel(channelId,channelName,hierarchyId);
		}
		return DeepureResult.result(false, "渠道名称为空，修改失败");
	}
	@ResponseBody
	@RequestMapping(value = "/queryChannelByName", method = RequestMethod.GET)
	public PaginatorResult<Channel> queryChannelByName(@RequestParam("channelName") final String channelName,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit										
			) throws UnsupportedEncodingException {

		String hc = new String (channelName.getBytes("ISO-8859-1"),"UTF-8");

		PaginatorResult<Channel> result=new PaginatorResult<Channel>();
		PageList<Channel> channelList = null;
		channelList = (PageList<Channel>) channelService.queryChannelByName(curPageSize, limit,hc);
			result.setRows(channelList);
			result.setTotal(channelList.getPaginator().getTotalCount());
	        return result;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryChannelByHierarchyId",method = RequestMethod.GET)
	public List<Channel> queryChannelByHierarchyId(
			@RequestParam(value = "hierarchyId") final String hierarchyId) {
		List<Channel> channelList=new ArrayList<Channel>();
		if(StringUtils.isNotEmpty(hierarchyId)){
			channelList=channelService.findChannelByHierarchyId(hierarchyId);
		}else{
			channelList=ListUtils.EMPTY_LIST;
		}
		return channelList;
	}
}
