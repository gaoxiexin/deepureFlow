package com.tasly.deepureflow.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.util.DeepureResult;

public interface IProductService {
	List<ProductCategory> queryCategoryByLayer(Integer layer,Integer activeFlag);

	PageList<Product> findProductForPage(int curPageSize, int limit);

	PageList<ProductCategory> findProductCategoryForPage(int curPageSize,
			int limit);

	Product findProductById(String skuId);

	Product findProductByCode(String skuCode);
	
	public DeepureResult importExcel(String targetPath, MultipartFile upFile);
	
	public DeepureResult importExcels(String targetPath, MultipartFile upFile);

	List<Product> findAllProducts();
}
