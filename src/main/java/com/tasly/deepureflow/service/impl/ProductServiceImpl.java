package com.tasly.deepureflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.cache.RedisCache;
import com.tasly.deepureflow.dao.IProductCategoryDao;
import com.tasly.deepureflow.dao.IProductDao;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.service.IProductService;
import com.tasly.deepureflow.util.DeepureResult;
import com.tasly.deepureflow.util.excel.impl.ProductCategoryReadExcel;
import com.tasly.deepureflow.util.excel.impl.ProductReadExcel;

@Service("productService")
public class ProductServiceImpl implements IProductService {
	private final Logger logger = Logger.getLogger(ProductServiceImpl.class
			.getName());

	@Autowired
	private IProductCategoryDao productCategoryDao;

	@Autowired
	private IProductDao productDao;

	@Autowired
	private RedisCache cache;
	@Autowired
	private ProductReadExcel productReadExcel; 
	@Autowired
	private ProductCategoryReadExcel productCategoryReadExcel; 

	@Override
	public List<ProductCategory> queryCategoryByLayer(Integer layer,
			Integer activeFlag) {
		return productCategoryDao.findManyByLayer(layer, activeFlag);
	}

	@Override
	public PageList<Product> findProductForPage(int curPageSize, int limit) {
		PageList<Product> pageList = null;
		PageBounds pageBounds = new PageBounds(curPageSize, limit);

		if (curPageSize != 0 && limit != 0) {
			pageList = (PageList<Product>) this.productDao
					.findProductForPage(pageBounds);

		}
		return pageList;
	}

	@Override
	public PageList<ProductCategory> findProductCategoryForPage(
			int curPageSize, int limit) {
		PageList<ProductCategory> pageList = null;
		PageBounds pageBounds = new PageBounds(curPageSize, limit);
		if (curPageSize != 0 && limit != 0) {
			pageList = (PageList<ProductCategory>) this.productCategoryDao
					.findProductCategoryForPage(pageBounds);
		}

		return pageList;
	}

	@Override
	public Product findProductById(String skuId) {
		
		return productDao.findProductById(skuId);
	}

	@Override
	public Product findProductByCode(String skuCode) {
		return productDao.findProductByCode(skuCode);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult importExcel(String targetPath, MultipartFile upFile) {
		boolean isImport=false;
		
		String sourceName = upFile.getOriginalFilename(); // 原始文件名

		File file = new File(targetPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			String path = targetPath + File.separator + sourceName;

			upFile.transferTo(new File(path));

			FileInputStream fin = new FileInputStream(new File(path));
			List<Product> productList=(List<Product>) productReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(productList)){
				isImport=this.addProduct(productList);
			}
		} catch (Exception e) {
			logger.error("导入产品失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DeepureResult.result(false, "导入产品失败");
		}
		return DeepureResult.result(true, "导入产品成功");

	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addProduct(List<Product> productList) {
		boolean isAdd=true;
		boolean isDelete=this.productDao.deleteAllProduct();
		if(isDelete){
			for(Product product :productList){
				boolean result = productDao.insertProduct(product);
				
				if(!result){
					isAdd=false;
					break;
				}
			}
		}
		
		return isAdd&&isDelete;
	}
	
	/*产品组导入功能*/
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public DeepureResult importExcels(String targetPath, MultipartFile upFile) {
		boolean isImport=false;
		
		String sourceName = upFile.getOriginalFilename(); // 原始文件名
		
		File file = new File(targetPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			String path = targetPath + File.separator + sourceName;

			upFile.transferTo(new File(path));

			FileInputStream fin = new FileInputStream(new File(path));
			List<ProductCategory> productCategoryList=(List<ProductCategory>) productCategoryReadExcel.getExcelInfo(fin,path);
			if(CollectionUtils.isNotEmpty(productCategoryList)){
				isImport=this.addProductCategory(productCategoryList);
			}
		} catch (Exception e) {
			logger.error("导入产品分类失败："+e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		if(!isImport){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return DeepureResult.result(false, "导入产品品类失败");
		}
		return DeepureResult.result(true, "导入产品品类成功");

	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private boolean addProductCategory(List<ProductCategory> productCategoryList) {
		boolean isAdd=true;
		boolean isDelete=this.productCategoryDao.deleteAllProductCategory();
		if(isDelete){
			for(ProductCategory productCategory :productCategoryList){
				boolean result=productCategoryDao.addProductCategory(productCategory);
				if(!result){
					isAdd=false;
					break;
				}
			}
		}
		
		return isAdd&&isDelete;
	}

	/*private boolean addProductCategory( Integer id,String name,String code, 
			                          Long parentId,String description, 
			                          Date createAt, String createBy) {
		if (StringUtils.isNotEmpty(name)){
			return productCategoryDao.insertProductCategory(id,name,code,parentId,description,createAt,createBy);
			}
		return false;
	}*/
	
	@Override
	public List<Product> findAllProducts() {
		return productDao.findAllProducts();
	}
}
