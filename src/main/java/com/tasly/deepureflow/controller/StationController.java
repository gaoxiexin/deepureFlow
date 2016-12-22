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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.util.CollectionUtils;
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
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IStationService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.PaginatorResult;
import com.tasly.deepureflow.util.excel.ExcelPoiUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  StationController   
 * @Description:岗位Controller
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:54:02 PM   
 *
 */
@Controller
@RequestMapping("/station")
@SessionAttributes("selectItem")
public class StationController {
	private final Logger logger = Logger.getLogger(StationController.class
			.getName());
	@Resource
	private IStationService stationService;
	
	@RequestMapping(value="/stationList")   
	@ApiOperation(value="展示办事处列表", httpMethod ="POST", response=String.class, notes ="展示办事处列表")  
    public String stationList(HttpServletRequest request,Model model){  
        model.addAttribute("selectItem", MenuEnum.STATION.getId());
        return "/system/stationList";  
    }
	
	@RequestMapping(value="/stationPage",method = RequestMethod.GET)  
    public @ResponseBody PaginatorResult<Station> stationPage(HttpServletRequest request,Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit){  
		PaginatorResult<Station> result=new PaginatorResult<Station>();
		PageList<Station> officeList=stationService.findStationForPage(curPageSize,limit);
		if(!CollectionUtils.isEmpty(officeList)){
			result.setRows(officeList);
			result.setTotal(officeList.getPaginator().getTotalCount());
		}
        return result;  
    }
	
	@RequestMapping(value = "toExportStation", method = RequestMethod.GET)
	public void toExportStation(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("岗位编号");
			theader.add("岗位名称");
			theader.add("所属办事处编号");
			
			HSSFWorkbook workbook = ExcelPoiUtils.exportExcel("岗位模板", theader, null, null, null, null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String excelName = "station_" + dateFormat.format(new Date())
					+ ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("导出岗位报表失败", e);
		}
	}
	
	@RequestMapping(value = "importStationExcel", method = RequestMethod.POST)
	@ResponseBody
	public DeepureResult importStationExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return stationService.importExcel(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}

}
