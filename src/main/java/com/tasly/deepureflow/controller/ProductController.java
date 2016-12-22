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
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.service.IProductService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.PaginatorResult;
import com.tasly.deepureflow.util.excel.ExcelPoiUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName:  ProductController   
 * @Description:产品Controller
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:52:36 PM   
 *
 */
@Controller
@RequestMapping("/product")
@SessionAttributes("selectItem")
public class ProductController {
	private final Logger logger = Logger.getLogger(ProductController.class
			.getName());
	@Resource
	private IProductService productService;

	@RequestMapping(value="/productList")   
	@ApiOperation(value="进入产品列表页面", httpMethod ="POST", response=String.class, notes ="进入产品列表页面")  
	public String productList(HttpServletRequest request,Model model){   
		model.addAttribute("selectItem", MenuEnum.PRODUCT.getId());
		return "/system/productList";  
	}

	@RequestMapping(value="/productPage",method = RequestMethod.GET)  
	@ApiOperation(value="产品列表分页展示", httpMethod ="GET", response=PaginatorResult.class, notes ="产品列表分页展示") 
	public @ResponseBody PaginatorResult<Product> productPage(HttpServletRequest request,Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit){  
		PaginatorResult<Product> result=new PaginatorResult<Product>();
		PageList<Product> productList=productService.findProductForPage(curPageSize,limit);
		if(!CollectionUtils.isEmpty(productList)){
			result.setRows(productList);
			result.setTotal(productList.getPaginator().getTotalCount());
		}
		return result;  
	}

	@RequestMapping(value = "toExportProduct", method = RequestMethod.GET)
	@ApiOperation(value="导出产品模板", httpMethod ="GET", notes ="导出产品模板") 
	public void toExportProduct(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("SKU编号");
			theader.add("SKU名称");
			theader.add("标准单位");
			theader.add("标准单位编码");
			theader.add("最小单位");
			theader.add("最小单位编码");
			theader.add("产品品类编号");
			theader.add("最小单位数量");
			theader.add("产品价格");

			HSSFWorkbook workbook = ExcelPoiUtils.exportExcel("产品模板", theader, null, null, null, null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String excelName = "product_" + dateFormat.format(new Date())
			+ ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("导出产品报表失败", e);
		}
		// return true;
	}

	@RequestMapping(value="/productCategoryList")   
	@ApiOperation(value="进入产品品类页面", httpMethod ="POST", response=String.class, notes ="进入产品品类页面")  
	public String productCategoryList(HttpServletRequest request,Model model){   
		model.addAttribute("selectItem", MenuEnum.PRODUCT_CATEGORY.getId());
		return "/system/productCategoryList";  
	}

	@RequestMapping(value="/productCategoryPage",method = RequestMethod.GET)
	@ApiOperation(value="产品品类分页展示", httpMethod ="GET", response=PaginatorResult.class, notes ="产品品类分页展示") 
	public @ResponseBody PaginatorResult<ProductCategory> productCategoryPage(HttpServletRequest request,Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit){  
		PaginatorResult<ProductCategory> result=new PaginatorResult<ProductCategory>();
		PageList<ProductCategory> productCategoryList=productService.findProductCategoryForPage(curPageSize,limit);
		if(!CollectionUtils.isEmpty(productCategoryList)){
			result.setRows(productCategoryList);
			result.setTotal(productCategoryList.getPaginator().getTotalCount());
		}
		return result;  
	}

	@RequestMapping(value = "toExportProductCategory", method = RequestMethod.GET)
	@ApiOperation(value="导出产品品类模板", httpMethod ="GET", notes ="导出产品品类模板") 
	public void toExportProductCategory(HttpServletResponse response, Model model)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			List<String> theader = new ArrayList<String>();
			theader.add("品类编号");
			theader.add("品类名称");
			theader.add("帝泊洱产品品类编号");

			HSSFWorkbook workbook = ExcelPoiUtils.exportExcel("产品品类模板", theader, null, null, null, null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String excelName = "productCategory_" + dateFormat.format(new Date())
			+ ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("导出产品品类报表失败", e);
		}
		// return true;
	}

	@RequestMapping(value = "importProductExcel", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="导入产品列表", httpMethod ="POST",response=Boolean.class, notes ="导入产品列表") 
	public DeepureResult importProductExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return productService.importExcel(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}


	@RequestMapping(value = "importProductCategoryExcel", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="导入产品品类", httpMethod ="POST",response=Boolean.class, notes ="导入产品品类") 
	public DeepureResult importProductCategoryExcel(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;//多部分httpRquest对象    是HttpServletRequest类的一个子类接口   支持文件分段上传对象
		MultipartFile upFile = mtRequest.getFile("uploadFile"); // 直接获取文件对象
		if(null == upFile || upFile.getSize()==0){   //文件不存在的情况
			return DeepureResult.result(false, "上传文件不存在或为空文件");
		}
		String targetPath = request.getSession().getServletContext().getRealPath("/file/upload"); //获取服务器 中file/update 的 url地址
		return productService.importExcels(targetPath, upFile);  //调用实现类 返回 界面消息 对象
	}
}

