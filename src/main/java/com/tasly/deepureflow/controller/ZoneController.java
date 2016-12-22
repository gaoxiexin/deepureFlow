package com.tasly.deepureflow.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZonePlan;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.enums.ZoneFunctionEnum;
import com.tasly.deepureflow.service.IZoneService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.PaginatorResult;
import com.tasly.deepureflow.util.excel.ExcelPoiUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  ZoneController   
 * @Description:大区管理Controller
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:56:45 PM   
 *
 */
@Controller
@RequestMapping("/zone")
@SessionAttributes("selectItem")
public class ZoneController {

	private final Logger logger = Logger.getLogger(ZoneController.class
			.getName());

	@Resource
	private IZoneService zoneService;

	@RequestMapping(value = "/zoneList")
	@ApiOperation(value = "展示大区列表", httpMethod = "POST", response = String.class, notes = "展示大区列表")
	public String zoneList(HttpServletRequest request, Model model) {	
		List<Zone> zoneList=zoneService.findAllZone();
		model.addAttribute("zoneList", zoneList);
		
		model.addAttribute("zoneFunctionList", ZoneFunctionEnum.values());
		model.addAttribute("selectItem", MenuEnum.ZONE.getId());
		List<Zone> zoneList1 = zoneService.findAllZone();
		model.addAttribute("zoneList", zoneList1);
		
		return "/system/zoneList";
	}

	@RequestMapping(value = "/zonePage", method = RequestMethod.POST)
	public @ResponseBody PaginatorResult<Zone> zonePage(
			HttpServletRequest request,
			Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit,
			@RequestParam(value="zoneFunction") Integer zoneFunction,
			@RequestParam(value="zoneId") String zoneId) {

		PaginatorResult<Zone> result = new PaginatorResult<Zone>();
		PageList<Zone> zoneList = zoneService.findZoneForPage(curPageSize,
				limit,zoneFunction,zoneId);
		if (!CollectionUtils.isEmpty(zoneList)) {
			result.setRows(zoneList);
			result.setTotal(zoneList.getPaginator().getTotalCount());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/addZone", method = RequestMethod.POST)
	public DeepureResult addzone(@RequestParam("zoneCode") final String zoneCode,@RequestParam("zoneName") final String zoneName,
			@RequestParam("zonePlanStatus") final Integer zonePlanStatus,
			@RequestParam("zoneFlowStatus") final Integer zoneFlowStatus) {
		boolean addResult = this.zoneService.addZone(zoneCode,zoneName, zonePlanStatus,
				zoneFlowStatus);
		return DeepureResult.addResult(addResult);
	}

	@ResponseBody
	@RequestMapping(value = "/delZone", method = RequestMethod.POST)
	public DeepureResult delzone(@RequestParam("zoneList") final String[] zoneList) {
		boolean delResult = false;
		if (ArrayUtils.isNotEmpty(zoneList)) {
			delResult = this.zoneService.delZoneByArray(zoneList);
		}
		return DeepureResult.delResult(delResult);
	}

	@ResponseBody
	@RequestMapping(value = "/editZone", method = RequestMethod.POST)
	public DeepureResult editZone(@RequestParam("zoneId") final Integer zoneId,
			@RequestParam("zoneName") final String zoneName,
			@RequestParam("zonePlanStatus") final Integer zonePlanStatus,
			@RequestParam("zoneFlowStatus") final Integer zoneFlowStatus) {
		boolean editResult = false;
		if (null != zoneId && StringUtils.isNotEmpty(zoneName)) {
			editResult = this.zoneService.editZone(zoneId, zoneName,
					zonePlanStatus, zoneFlowStatus);
		}
		return DeepureResult.editResult(editResult);
	}

	@ResponseBody
	@RequestMapping(value = "/updateZoneStatus", method = RequestMethod.POST)
	public DeepureResult updateZoneStatus(
			@RequestParam("zoneId") final Integer zoneId,
			@RequestParam("planType") Integer planType) {
		boolean updateResult = false;
		if (null != zoneId && null != planType) {
			updateResult = this.zoneService.updateZoneStatus(zoneId, planType);
		}
		return DeepureResult.editResult(updateResult);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryZonePlan", method = RequestMethod.GET)
	public List<ZonePlan> queryZonePlan(
			@RequestParam("zoneId") final String zoneId) {
		List<ZonePlan> zonePlanList = ListUtils.EMPTY_LIST;
		if (null != zoneId) {
			zonePlanList = this.zoneService.queryZonePlan(zoneId);
		}

		return zonePlanList;
	}

	@RequestMapping(value = "/showZonePlan")
	public String showZonePlan(@RequestParam("zoneId") final Integer zoneId,
			Model model) {
		Zone zone = this.zoneService.findZoneById(zoneId);
		model.addAttribute("zone", zone);
		return "/system/zonePlanList";
	}

	@ResponseBody
	@RequestMapping(value = "/addOrUpdateZonePlan", method = RequestMethod.POST)
	public DeepureResult addOrUpdateZonePlan(
			@RequestParam("operation") final Integer operation,
			@RequestParam("zoneId") String zoneId,
			@RequestParam("zonePlanType") Integer zonePlanType,
			@RequestParam("zonePlanStartDay") Integer zonePlanStartDay,
			@RequestParam("zonePlanEndDay") Integer zonePlanEndDay) {
		ZonePlan zonePlan = new ZonePlan();
		if (null != zoneId && null != zonePlanType) {
			if (operation == 0) {
				zonePlan = new ZonePlan();
				zonePlan.setZoneId(zoneId);
				zonePlan.setPlanType(zonePlanType);
				zonePlan.setPlanStartDay(zonePlanStartDay);
				zonePlan.setPlanEndDay(zonePlanEndDay);
				zonePlan.setPlanStatus(1);

				return zoneService.addZonePlan(zonePlan);
			} else if (operation == 1) {
				zonePlan = this.zoneService.queryZonePlan(zoneId, zonePlanType);
				zonePlan.setPlanType(zonePlanType);
				zonePlan.setPlanStartDay(zonePlanStartDay);
				zonePlan.setPlanEndDay(zonePlanEndDay);

				return zoneService.updateZonePlan(zonePlan);
			}
		}
		return DeepureResult.result(false, "系统错误");
	}

	@ResponseBody
	@RequestMapping(value = "/delZonePlan", method = RequestMethod.POST)
	public DeepureResult delZonePlan(@RequestParam("zoneId") String zoneId,
			@RequestParam("zonePlanList") final Integer[] zonePlanList) {
		boolean delResult = false;
		if (ArrayUtils.isNotEmpty(zonePlanList) && null != zoneId) {
			delResult = this.zoneService.delZonePlanByArray(zoneId,
					zonePlanList);
		}
		return DeepureResult.delResult(delResult);
	}

	@RequestMapping(value = "toExportZone", method = RequestMethod.GET)
	public void toExportZone(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("大区编号");
			theader.add("大区名称");
			
			HSSFWorkbook workbook = ExcelPoiUtils.exportExcel("大区模板", theader, null, null, null, null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String excelName = "zone_" + dateFormat.format(new Date())
					+ ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("导出大区报表失败", e);
		}
	}
	
	@RequestMapping(value = "importZoneExcel", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult importZoneExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return zoneService.importExcel(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}
}
