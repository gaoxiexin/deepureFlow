package com.tasly.deepureflow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tasly.deepureflow.domain.system.Place;
import com.tasly.deepureflow.service.IPlaceService;

/**
 * 
 * @ClassName:  AddressSelect   
 * @Description:地址选择Controller   
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:50:14 PM   
 *
 */
@Controller
@RequestMapping("/addressSelect")
public class AddressSelect {

	@Resource
	private IPlaceService placeService;

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<Place> searchPlace(
			@RequestParam(value = "parentCode") final String parentCode) {
		List<Place> placeList=new ArrayList<Place>();
		if(StringUtils.isNotEmpty(parentCode)){
			placeList=placeService.searchAreaByParentCode(parentCode);
		}else{
			placeList=ListUtils.EMPTY_LIST;
		}
		return placeList;
	}
}
